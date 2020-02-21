package pxl.student.be.hackatongroup1.domain.services

import android.util.Log
import pxl.student.be.hackatongroup1.LARGEGROUPID
import pxl.student.be.hackatongroup1.data.async.*
import pxl.student.be.hackatongroup1.data.model.*

private const val TAG = "FaceService"

class FaceService(private val listener: OnHttpDataAvailable, private val trainListener: OnHttpDataAvailable){
    lateinit var photoData : ByteArray

    fun detectFace(requestData: RequestDetect){
        DetectCall(object: OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                if(data != "[]"){
                    val responseDetect = ResponseDetect.fromJsonToModel(data)
                    val requestIdentify = RequestIdentify.fromDetectToIdentify(responseDetect)
                    identifyFace(requestIdentify)
                } else {
                    listener.onHttpDataAvailable("No person founded")
                }
            }
        }).execute(requestData)
    }

    fun identifyFace(requestIdentify: RequestIdentify){
        IdentifyCall(object: OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                val responseIdentify = ResponseIdentify.fromJsonToModel(data)
                if(!data.contains("doInBackground") && responseIdentify.candidates.size > 0){
                    getPerson(responseIdentify.candidates[0].personId)
                } else {
                    listener.onHttpDataAvailable("No person founded")
                }
            }
        }).execute(requestIdentify)
    }

    fun getPerson(personId: String){
        val personUrl = "/largepersongroups/$LARGEGROUPID/persons/$personId"
        PersonCall(listener).execute(personUrl)
    }

    fun addPerson(requestAddPerson: RequestAddPerson){
        AddPersonCall(object : OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                val responseAddPerson = ResponseAddPerson.fromJsonToModel(data)
                val requestAddPhoto = RequestAddPhoto.fromAddPhotoToIdentify(responseAddPerson, photoData)
                addPhoto(requestAddPhoto)
            }

        }).execute(requestAddPerson)
    }

    fun addPhoto(requestAddPhoto: RequestAddPhoto){
        AddPhotoCall(object: OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                trainModel()
            }
        }).execute(requestAddPhoto)
    }

    fun trainModel(){
        TrainCall(object: OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                photoData = "".toByteArray()
                trainListener.onHttpDataAvailable("Training started")
            }
        }).execute("Testing")
    }
}