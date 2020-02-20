package pxl.student.be.hackatongroup1.data.async

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "PersonCall"
private const val PERSON = "/face/v1.0"

class PersonCall(private val listener: OnHttpDataAvailable): AsyncTask<String, Void, String>() {
    private var status = NetworkStatus.IDLE

    override fun onPostExecute(result: String?) {
        if(result != null) {
            listener.onHttpDataAvailable(result)
        }
    }

    override fun doInBackground(vararg endpoints: String?): String {
        Log.d(TAG, "doInBackground, called")
        val jsonResponse = StringBuilder()
        if (endpoints[0] == null) {
            status = NetworkStatus.NOT_INITIALIZED
            return "No URL specified"
        }

        try {
            Log.d(TAG, "Search url is $ENDPOINT$PERSON${endpoints[0]}")
            val searchUrl = URL("$ENDPOINT$PERSON${endpoints[0]}")
            with(searchUrl.openConnection() as HttpURLConnection) {
                requestMethod = "GET" // optional default is GET
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