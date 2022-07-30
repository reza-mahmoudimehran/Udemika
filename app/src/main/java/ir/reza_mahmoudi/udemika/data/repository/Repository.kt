package ir.reza_mahmoudi.udemika.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import ir.reza_mahmoudi.udemika.data.local.LocalDataSource
import ir.reza_mahmoudi.udemika.data.remote.RemoteDataSource
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
){
    val remote=remoteDataSource
    val local=localDataSource
}