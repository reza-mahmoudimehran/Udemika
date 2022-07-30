package ir.reza_mahmoudi.udemika.utils

sealed class NetworkResult(
    val message: String? = null
) {
    class Success(): NetworkResult()
    class Error(message: String?): NetworkResult(message)
}