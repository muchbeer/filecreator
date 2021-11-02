package raum.muchbeer.filecreator.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import raum.muchbeer.filecreator.data.Client
import raum.muchbeer.filecreator.repository.ClientRepository
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
private val repository : ClientRepository
) : ViewModel() {

    fun insert(client: Client) = viewModelScope.launch {
        repository.insert(client)
    }
}