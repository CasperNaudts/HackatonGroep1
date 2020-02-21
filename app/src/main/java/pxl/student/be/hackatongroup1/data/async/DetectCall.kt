package pxl.student.be.hackatongroup1.data.async

import android.R.attr.bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.AsyncTask
import android.util.Log
import pxl.student.be.hackatongroup1.data.model.RequestDetect
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


private const val TAG = "HttpCall"
private const val DETECT = "/face/v1.0/detect?returnFaceAttributes=age,gender&recognitionModel=recognition_02"


enum class NetworkStatus{
    OK, IDLE, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

interface OnHttpDataAvailable{
    fun onHttpDataAvailable(data: String)
}

class DetectCall(private val listener: OnHttpDataAvailable) : AsyncTask<RequestDetect, Void, String>() {

    private var status = NetworkStatus.IDLE

    override fun onPostExecute(result: String?) {
        if(result != null) {
            listener.onHttpDataAvailable(result)
        }
    }

    override fun doInBackground(vararg requestDetect: RequestDetect?): String {
        Log.d(TAG, "doInBackground, called")
        val jsonResponse = StringBuilder()
        if (requestDetect[0] == null) {
            status = NetworkStatus.NOT_INITIALIZED
            return "No URL specified"
        }

        try {
            Log.d(TAG, "Search url is $ENDPOINT$DETECT")
            val searchUrl = URL("$ENDPOINT$DETECT")
            with(searchUrl.openConnection() as HttpURLConnection) {
                requestMethod = "POST" // optional default is GET
                setRequestProperty("Content-Type", "application/octet-stream")
                setRequestProperty("Ocp-Apim-Subscription-Key", KEY)

                outputStream.write(requestDetect[0]!!.data)
                outputStream.flush()

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