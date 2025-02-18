package za.co.lloyd.lloyd_intermediate_assessment.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.lloyd.lloyd_intermediate_assessment.data.remote.ToDoApiService
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.location.DefaultLocationTracker
import za.co.lloyd.lloyd_intermediate_assessment.repository.remote.ToDoAppRepository
import za.co.lloyd.lloyd_intermediate_assessment.utils.location.LocationTracker
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideToDoRepository(toDoApiService: ToDoApiService): ToDoAppRepository {
        return ToDoAppRepository(toDoApiService)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(application: Application): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationTracker(fusedLocationProviderClient: FusedLocationProviderClient, application: Application): LocationTracker = DefaultLocationTracker(fusedLocationProviderClient = fusedLocationProviderClient, application = application)

}