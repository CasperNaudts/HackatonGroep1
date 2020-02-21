package pxl.student.be.hackatongroup1.domain.entity

import pxl.student.be.hackatongroup1.data.model.ResponsePersonLargePersonGroup
import java.io.Serializable

class Person(val name: String) : Serializable{
    companion object{
        fun fromModelToEntity(largePersonGroup: ResponsePersonLargePersonGroup): Person{
            return Person(largePersonGroup.name)
        }
    }
}