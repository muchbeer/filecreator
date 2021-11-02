package raum.muchbeer.filecreator.ui.client

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.filecreator.databinding.FragmentClientBinding
import raum.muchbeer.filecreator.util.buildPdf
import raum.muchbeer.filecreator.util.getFiles
import raum.muchbeer.filecreator.util.proposalExists
import raum.muchbeer.filecreator.util.saveImage
import java.io.File
import java.io.FileNotFoundException
import java.util.*

@AndroidEntryPoint
class ClientFragment : Fragment() {

    private val TAG = ClientFragment::class.simpleName
    private lateinit var inputPFD: ParcelFileDescriptor


    private lateinit var binding : FragmentClientBinding
    private val clientViewModel : ClienVM by viewModels()
    private var adapter = ClientAdapter {  file ->
        onClickFile(file)
    }
    private var clientId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentClientBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launcherImage()

    }

    private fun launcherImage () {

        val imagePicker : ActivityResultLauncher<Intent>  = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK ||
                result.data != null
            ) {
                result.data?.data?.also { returnUri->
                    inputPFD = try {
                        requireActivity().contentResolver.openFileDescriptor(returnUri, "r")!!
                    } catch (e: FileNotFoundException) {
                        Log.e("ClientFragment", "File not found", e)
                        return@also
                    }
                }

            }

            saveImageToFolderAndList()
        }

        bindingDataToImage(imagePicker)

    }

    private fun bindingDataToImage( imagePicker : ActivityResultLauncher<Intent>) {

        val calendar = Calendar.getInstance()
        val dateFormat = DateFormat.getDateFormat(requireContext())

        binding.apply {
            listFiles.layoutManager = LinearLayoutManager(activity)
            listFiles.adapter = adapter

            arguments?.let { bundle ->
                val passedArguments = ClientFragmentArgs.fromBundle(bundle)
                clientViewModel.getClient(passedArguments.clientId)
                    .observe(viewLifecycleOwner)  { client ->
                        name.text = client.name
                        order.text = client.order
                        terms.text = client.terms
                        clientId = client.id

                        calendar.timeInMillis = client.date
                        date.text = dateFormat.format(calendar.time)

                        // adapter.setFiles(context!!.getFiles(clientId))
                        adapter.submitList(requireContext().getFiles(clientId))

                        if (requireContext().proposalExists(clientId)) {
                            btnProposal.visibility = View.INVISIBLE
                            btnPicture.visibility = View.VISIBLE
                        } else {
                            btnProposal.setOnClickListener {
                                requireContext().buildPdf(client)
                                it.visibility = View.INVISIBLE
                                adapter.submitList(requireContext().getFiles(clientId))
                                // adapter.setFiles(context!!.getFiles(clientId))
                            }
                        }
                        btnPicture.setOnClickListener {
                            val requestFileIntent = Intent(
                                Intent.ACTION_OPEN_DOCUMENT
                            ).apply {
                                type = "image/*"
                                addCategory(Intent.CATEGORY_OPENABLE)
                            }

                            imagePicker.launch(requestFileIntent)
                        }
                    }
            }
        }
    }

    private fun saveImageToFolderAndList() {
        val fd = inputPFD.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fd)
        inputPFD.close()

        val input = TextInputEditText(requireContext())
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = layoutParams
        input.hint = "Picture Name"

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Picture")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val name = input.text.toString()
                requireContext().saveImage(image, name, clientId)
                adapter.submitList(requireContext().getFiles(clientId))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun onClickFile(file:File) {
        val uri : Uri? = try {
            FileProvider.getUriForFile(
                requireContext(),
                "raum.muchbeer.filecreator",
                file
            )
        } catch (e : IllegalArgumentException) {
            Log.e(TAG, "can't share file : $file")
            null
        }

        if(uri !=null) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                uri,
                requireActivity().contentResolver.getType(uri)
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }
    }

    companion object {
        const val PICTURE_REQUEST = 6
    }
}