package com.vxplore.thetimesgroup.mainController

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.vxplore.core.domain.repositoriess.*
import com.vxplore.core.helpers.AppStore
import com.vxplore.core.helpers.Info
import com.vxplore.thetimesgroup.data.online.AppVersionApi
import com.vxplore.thetimesgroup.helpers_impl.AppInfo
import com.vxplore.thetimesgroup.helpers_impl.AppStoreImpl
import com.vxplore.thetimesgroup.repository_impls.*
import com.vxplore.thetimesgroup.utility.Constants
import com.vxplore.thetimesgroup.utility.Metar
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {


    companion object {
        private val Context.dataStore by preferencesDataStore("timesGroup")

        private fun <T> provideApi(klass: Class<T>): T {
            return Retrofit.Builder()
                .baseUrl(Metar[Constants.BASE_URL])
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(klass)
        }
        private fun <T> provideApi2(klass: Class<T>): T {
            return Retrofit.Builder()
                .baseUrl("https://api.npoint.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(klass)
        }

        @Singleton
        @Provides
        fun provideAppVersion(): AppVersionApi = provideApi2(AppVersionApi::class.java)

        @Singleton
        @Provides
        fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> = appContext.dataStore

        @Singleton
        @Provides
        fun provideBaseUrl(): MyApiList = provideApi(MyApiList::class.java)

    }//companion object
////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Binds
    fun bindAppStore(appStoreImpl: AppStoreImpl): AppStore

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Binds
    fun bindBaseUrlRepo(impl: BaseUrlRepositoryImpl): BaseUrlRepository

    @Binds
    fun bindRegisterRepo(impl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    fun bindMobileNoScreenRepo(impl: MobileNoScreenRepositoryImpl): MobileNoScreenRepository

    @Binds
    fun bindAppInfo(appInfo: AppInfo): Info

    @Binds
    fun bindSplashRepo(impl: SplashRepositoryImpl): SplashRepository

    @Binds
    fun bindOtpRepo(impl: OtpRepositoryImpl): OtpRepository

    @Binds
    fun bindVendorDetailsRepo(impl: VendorDetailsRepositoryImpl): VendorDetailsRepository





}