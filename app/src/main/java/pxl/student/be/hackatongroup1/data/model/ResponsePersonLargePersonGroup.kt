package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder
import org.json.JSONObject

class ResponsePersonLargePersonGroup(val personId: String,
                                     val persistedFaceIds: Array<String>,
                                     val name: String,
                                     val userData: String){
    companion object{
        fun fromJsonToModel(data: String) : ResponsePersonLargePersonGroup{
            val gson = GsonBuilder().create()
            return gson.fromJson(data,ResponsePersonLargePersonGroup::class.java)
        }
    }
}