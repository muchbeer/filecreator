package raum.muchbeer.filecreator.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import raum.muchbeer.filecreator.data.ClientDao
import raum.muchbeer.filecreator.data.ClientDatabase
import raum.muchbeer.filecreator.data.ClientDatabase.Companion.getInstance
import raum.muchbeer.filecreator.repository.ClientRepository
import raum.muchbeer.filecreator.repository.ClientRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun provideClientDatabase(
        @ApplicationContext context : Context ) : ClientDatabase {
        return getInstance(context)
    }


    @Singleton
    @Provides
    fun provideClientDao(clientDatabase: ClientDatabase) : ClientDao {
        return clientDatabase.clientDao()
    }

    @Singleton
    @Provides
    fun provideCallBackCoroutine() = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideClientRepository(dao: ClientDao) : ClientRepository {
        return ClientRepositoryImpl(dao)
    }

}