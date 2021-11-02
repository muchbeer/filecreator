package raum.muchbeer.filecreator.repository

import androidx.lifecycle.LiveData
import raum.muchbeer.filecreator.data.Client
import raum.muchbeer.filecreator.data.ClientDao

class ClientRepositoryImpl(private val clientDao: ClientDao) : ClientRepository {
    override fun getAllClients(): LiveData<List<Client>> {
        return clientDao.getAllClients()
    }

    override fun getClient(id: Int): LiveData<Client> {
        return clientDao.getClient(id)
    }

    override suspend fun insert(client: Client) {
        clientDao.insert(client)
    }
}