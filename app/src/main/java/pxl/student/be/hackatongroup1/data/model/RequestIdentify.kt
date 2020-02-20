package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder
import pxl.student.be.hackatongroup1.LARGEGROUPID

class RequestIdentify(val faceIds: Array<String>,
                      val maxNumOfCandidatesReturned: Number,
                      val confidenceThreshold: Number,
                      val largePersonGroupId: String = LARGEGROUPID) {
    companion object{
        fun fromDetectToIdentify(responseDetect: ResponseDetect): RequestIdentify{
            return RequestIdentify(
                arrayOf(responseDetect.faceId),
                1,
                0.8)
        }
    }

    fun fromModelToJson(): String{
        val gson = GsonBuilder().create()
        return gson.toJson(this, ResponseDetect::class.java)
    }

    override fun toString(): String {
        return fromModelToJson()
    }
}
