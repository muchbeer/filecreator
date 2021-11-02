package raum.muchbeer.filecreator.ui.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import raum.muchbeer.filecreator.data.Client
import raum.muchbeer.filecreator.repository.ClientRepository
import javax.inject.Inject

@HiltViewModel
class ClienVM @Inject constructor(
val repository : ClientRepository
) : ViewModel() {

    fun getClient(id: Int): LiveData<Client> {
        return repository.getClient(id)
    }
}