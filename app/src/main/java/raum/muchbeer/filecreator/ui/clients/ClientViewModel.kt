package raum.muchbeer.filecreator.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import raum.muchbeer.filecreator.data.Client
import raum.muchbeer.filecreator.repository.ClientRepository
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
private val repository : ClientRepository
) : ViewModel() {

    val allClients: LiveData<List<Client>> = repository.getAllClients()

}