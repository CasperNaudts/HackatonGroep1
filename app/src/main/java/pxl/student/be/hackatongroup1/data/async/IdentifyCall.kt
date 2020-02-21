package pxl.student.be.hackatongroup1.data.async

import android.os.AsyncTask
import android.util.Log
import pxl.student.be.hackatongroup1.data.model.RequestIdentify
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "IdentifyCall"
private const val IDENTIFY = "/face/v1.0/identify"

class IdentifyCall(private val listener: OnHttpDataAvailable): AsyncTask<RequestIdentify, Void, String>() {
    private var status = NetworkStatus.IDLE

    override fun onPostExecute(result: String?) {
        if(result != null) {
            listener.onHttpDataAvailable(result)
        }
    }

    override fun doInBackground(vararg requestIdentify: RequestIdentify?): String {
        Log.d(TAG, "doInBackground, called")
        val jsonResponse = StringBuilder()
        if (requestIdentify[0] == null) {
            status = NetworkStatus.NOT_INITIALIZED
            return "No URL specified"
        }

        try {
            Log.d(TAG, "Search url is $ENDPOINT$IDENTIFY")
            val searchUrl = URL("$ENDPOINT$IDENTIFY")
            with(searchUrl.openConnection() as HttpURLConnection) {
                requestMethod = "POST" // optional default is GET
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Ocp-Apim-Subscription-Key", KEY)

                val result = requestIdentify[0]!!.fromModelToJson().toByteArray()
                outputStream.write(requestIdentify[0]!!.fromModelToJson().toByteArray())
                outputStream.flush()

                val status = responseCode
                val message = responseMessage

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
            val errorMessage: String

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