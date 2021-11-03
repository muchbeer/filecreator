package raum.muchbeer.filecreator.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.filecreator.MainActivity
import raum.muchbeer.filecreator.R
import raum.muchbeer.filecreator.data.Client
import raum.muchbeer.filecreator.databinding.FragmentAddBinding

@AndroidEntryPoint
class AddViewModelFragment : Fragment() {

    private lateinit var binding : FragmentAddBinding
    private val addViewModel : AddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSave.setOnClickListener {
                addViewModel.insert(
                    Client(
                        date = System.currentTimeMillis(),
                        name = txtName.text.toString(),
                        order = txtOrder.text.toString(),
                        terms = txtTerms.text.toString()
                    )
                )

                (requireActivity() as MainActivity).hideKeyboard()
                val navController = Navigation.findNavController(view)
                navController.navigate(AddViewModelFragmentDirections.actionBackToClient())
            }
        }
    }
}