package raum.muchbeer.filecreator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import raum.muchbeer.filecreator.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.profile_preference_key),
            Context.MODE_PRIVATE
        )

        binding.apply {
            txtName.setText(
                sharedPref.getString(
                    getString(R.string.profile_name_key),
                    getString(R.string.profile_name_default)
                )
            )

            btnSave.setOnClickListener {
                with(sharedPref.edit()) {
                    putString(
                        getString(R.string.profile_name_key),
                        txtName.text.toString()
                    )
                    apply()
                }

              //  requireActivity().hideKeyboard()
                Snackbar
                    .make(
                        view,
                        getString(R.string.profile_success),
                        Snackbar.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }

}