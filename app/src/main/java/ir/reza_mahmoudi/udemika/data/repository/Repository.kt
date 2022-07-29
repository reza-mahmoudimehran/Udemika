package ir.reza_mahmoudi.udemika.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import ir.reza_mahmoudi.udemika.data.remote.RemoteDataSource
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
){
    val remote=remoteDataSource
}