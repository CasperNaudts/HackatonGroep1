package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class ResponseDetect(val faceId: String, val recognitionModel: String, val faceAttributes: FaceAttributesModel) {
    companion object{
        fun fromJsonToModel(data: String) : ResponseDetect{
            val list = object : TypeToken<List<ResponseDetect>>() {}.type
            val gson = GsonBuilder().create()
            return gson.fromJson<List<ResponseDetect>>(data, list)[0]
        }
    }
}