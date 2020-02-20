package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder

class ResponseIdentify(val faceId: String, val candidates: Array<CandidateModel>) {
    companion object{
        fun fromJsonToModel(data: String) : ResponseIdentify{
            val gson = GsonBuilder().create()
            return gson.fromJson(data,ResponseIdentify::class.java)
        }
    }
}
