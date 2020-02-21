package pxl.student.be.hackatongroup1.data.model

class RequestAddPhoto(val data: ByteArray, val endpoint: String) {
    companion object{
        fun fromAddPhotoToIdentify(responseAddPerson: ResponseAddPerson, data: ByteArray): RequestAddPhoto{
            return RequestAddPhoto(data, "/${responseAddPerson.personId}/persistedfaces")
        }
    }
}