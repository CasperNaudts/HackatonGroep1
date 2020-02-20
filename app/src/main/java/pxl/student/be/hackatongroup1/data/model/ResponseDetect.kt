package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder

class ResponseDetect(val faceId: String, val recognitionModel: String, val faceAttributes: FaceAttributesModel) {
    companion object{
        fun fromJsonToModel(data: String) : ResponseDetect{
            val gson = GsonBuilder().create()
            return gson.fromJson(data, ResponseDetect::class.java)
        }
    }
}