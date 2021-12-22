package andre.hitchman.com.news.repository.di

import andre.hitchman.com.news.repository.networkcall.SafeNetworkCallDelegate
import andre.hitchman.com.news.repository.networkcall.SafeNetworkCallDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSafeNetworkCallDelegate(
        safeNetworkCallDelegate: SafeNetworkCallDelegateImpl
    ): SafeNetworkCallDelegate = safeNetworkCallDelegate
}