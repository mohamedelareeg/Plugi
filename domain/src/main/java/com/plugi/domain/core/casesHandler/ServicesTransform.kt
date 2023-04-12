import com.plugi.domain.core.casesHandler.Results
import retrofit2.Call
import java.io.IOException

object ServicesTransform {
    fun <T : Any> transform(api: Call<T>): Results<T?> {
        return try {
            handleCodesCases(api)
        } catch (exception: Exception) {
            if (exception is IOException)
                Results.Error(Results.FailureReason.OFFLINE)
            else
                Results.Error(Results.FailureReason.UNKNOWN_REASON)
        }
    }

    private fun <T : Any> handleCodesCases(api: Call<T>): Results<T?> {
        val response = api.execute()
        return if (response.isSuccessful) {
//            Results.Success(response.body(), getServerMessageFromResponse(response.body()))
            Results.Success(response.body(), " ")
        } else {
            when (response.code()) {
                401 -> Results.Error(Results.FailureReason.UNAUTHORIZED)
                in 400..500 -> Results.Error(
                    Results.FailureReason.USER_SIDE,
                   " "
                )
                in 500..600 -> Results.Error(Results.FailureReason.SERVER_SIDE)
                else -> Results.Error(Results.FailureReason.UNKNOWN_REASON)
            }
        }
//            return if (exception is IOException){
//                Results.Error(Results.FailureReason.OFFLINE)
//            }else{
//                Results.Error(Results.FailureReason.UNKNOWN_REASON)
//
//            }
    }

//    private fun <T> getServerMessageFromResponse(body: Calling<T>?): String? {
//        body?.let {
//            it.message?.let { y ->
//                if (y.isNotEmpty()) {
//                    return y
//                }
//            }
//        }
//        return null
//    }

//    private fun getServerErrorFromResponse(errorBody: ResponseBody?): String? {
//        var value = "Un Handle Error"
//        errorBody?.let {
//            try {
//                val json = JSONObject(errorBody.string())
//                value = json.getString("error")
//            } catch (e: Exception) {
//                Timber.e("UNHANDLE ERROR ${e.message}")
//            }
//        }
//        return value
//
//    }
}