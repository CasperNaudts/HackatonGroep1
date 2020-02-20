package pxl.student.be.hackatongroup1.domain.services

import pxl.student.be.hackatongroup1.LARGEGROUPID
import pxl.student.be.hackatongroup1.data.async.DetectCall
import pxl.student.be.hackatongroup1.data.async.IdentifyCall
import pxl.student.be.hackatongroup1.data.async.OnHttpDataAvailable
import pxl.student.be.hackatongroup1.data.async.PersonCall
import pxl.student.be.hackatongroup1.data.model.*

class FaceService(private val listener: OnHttpDataAvailable){

    fun detectFace(requestData: RequestDetect){
        DetectCall(object: OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                if(data != "[]"){
                    val responseDetect = ResponseDetect.fromJsonToModel(data)
                    val requestIdentify = RequestIdentify.fromDetectToIdentify(responseDetect)
                    identifyFace(requestIdentify)
                } else {
                    listener.onHttpDataAvailable("No Name")
                }
            }
        }).execute(requestData)
    }

    fun identifyFace(requestIdentify: RequestIdentify){
        IdentifyCall(object: OnHttpDataAvailable{
            override fun onHttpDataAvailable(data: String) {
                val responseIdentify = ResponseIdentify.fromJsonToModel(data)
                getPerson(responseIdentify.candidates[0].personId)
            }
        }).execute(requestIdentify)
    }

    fun getPerson(personId: String){
        val personUrl = "/largepersongroups/$LARGEGROUPID/persons/$personId"
        PersonCall(listener).execute(personUrl)
    }
}