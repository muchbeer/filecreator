package raum.muchbeer.filecreator.repository

import androidx.lifecycle.LiveData
import raum.muchbeer.filecreator.data.Client

interface ClientRepository {

    fun getAllClients(): LiveData<List<Client>>

    fun getClient(id: Int): LiveData<Client>

    suspend fun insert(client: Client)
}