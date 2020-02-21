package pxl.student.be.hackatongroup1.data.async

import android.os.AsyncTask
import android.util.Log
import pxl.student.be.hackatongroup1.data.model.RequestAddPhoto
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "TrainCall"
private const val DETECT = "/face/v1.0/largepersongroups/ferm/train"

class TrainCall(private val listener : OnHttpDataAvailable) : AsyncTask<String, Void, String>() {
    private var status = NetworkStatus.IDLE

    override fun onPostExecute(result: String?) {
        listener.onHttpDataAvailable("Training started")
    }

    override fun doInBackground(vararg data: String?): String {
        Log.d(TAG, "doInBackground, called")
        val jsonResponse = StringBuilder()

        try {
            Log.d(TAG, "Search url is $ENDPOINT$DETECT")
            val searchUrl = URL("$ENDPOINT$DETECT")
            with(searchUrl.openConnection() as HttpURLConnection) {
                requestMethod = "POST" // optional default is GET
                setRequestProperty("Ocp-Apim-Subscription-Key", KEY)

                inputStream.bufferedReader().use {
                    it.useLines {
                        it.iterator().forEach {
                            jsonResponse.append(it)
                        }
                    }
                }
            }
            return jsonResponse.toString()
        } catch (e: Exception) {
            val errorMessage: String;

            when (e) {
                is MalformedURLException -> {
                    status = NetworkStatus.NOT_INITIALIZED
                    errorMessage = "doInBackground, Invalid URL: ${e.message}"
                }
                is IOException -> {
                    status = NetworkStatus.FAILED_OR_EMPTY
                    errorMessage =
                        "doInBackground, IO Exception reading data: ${e.printStackTrace()}"
                }
                is SecurityException -> {
                    status = NetworkStatus.PERMISSIONS_ERROR
                    errorMessage = "doInBackground, Permissions needed: ${e.message}"
                }
                else -> {
                    status = NetworkStatus.ERROR
                    errorMessage = "doInBackground, unknown error: ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)

            return errorMessage
        }
    }
}