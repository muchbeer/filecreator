package raum.muchbeer.filecreator.ui.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.filecreator.databinding.FragmentClientBinding
import raum.muchbeer.filecreator.databinding.FragmentsClientBinding

@AndroidEntryPoint
class ClientsFragment() : Fragment() {

    private lateinit var binding : FragmentsClientBinding
    private val clientsViewModel : ClientViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentsClientBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = ClientsAdapter { id ->
            val action = ClientsFragmentDirections.actionOpenClient(clientId = id)
          //  action.clientId = id
            val navController = Navigation.findNavController(requireView())
            navController.navigate(action)
        }

        binding.apply {
            listClients.layoutManager = LinearLayoutManager(activity)
            listClients.adapter = adapter
        }

        clientsViewModel.allClients.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}