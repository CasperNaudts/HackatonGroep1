package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class ResponseIdentify(val faceId: String, val candidates: Array<CandidateModel> = arrayOf()) {
    companion object{
        fun fromJsonToModel(data: String) : ResponseIdentify{
            val list = object : TypeToken<List<ResponseIdentify>>() {}.type
            val gson = GsonBuilder().create()
            return gson.fromJson<List<ResponseIdentify>>(data, list)[0]
        }
    }
}
