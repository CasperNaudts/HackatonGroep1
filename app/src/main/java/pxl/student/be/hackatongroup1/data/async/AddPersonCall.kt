package pxl.student.be.hackatongroup1.data.async

import android.os.AsyncTask
import android.util.Log
import pxl.student.be.hackatongroup1.data.model.RequestAddPerson
import pxl.student.be.hackatongroup1.data.model.RequestDetect
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "AddPersonCall"
private const val DETECT = "/face/v1.0/largepersongroups/ferm/persons"

class AddPersonCall(private val listener: OnHttpDataAvailable) : AsyncTask<RequestAddPerson, Void, String>() {
    private var status = NetworkStatus.IDLE

    override fun onPostExecute(result: String?) {
        if(result != null) {
            listener.onHttpDataAvailable(result)
        }
    }

    override fun doInBackground(vararg requestAddPerson: RequestAddPerson?): String {
        Log.d(TAG, "doInBackground, called")
        val jsonResponse = StringBuilder()
        if (requestAddPerson[0] == null) {
            status = NetworkStatus.NOT_INITIALIZED
            return "No URL specified"
        }

        try {
            Log.d(TAG, "Search url is $ENDPOINT$DETECT")
            val searchUrl = URL("$ENDPOINT$DETECT")
            with(searchUrl.openConnection() as HttpURLConnection) {
                requestMethod = "POST" // optional default is GET
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Ocp-Apim-Subscription-Key", KEY)

                outputStream.write(requestAddPerson[0]!!.fromModelToJson().toByteArray())
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