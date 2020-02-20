package pxl.student.be.hackatongroup1.domain.entity

import pxl.student.be.hackatongroup1.data.model.ResponsePersonLargePersonGroup

class Person(val name: String){
    companion object{
        fun fromModelToEntity(largePersonGroup: ResponsePersonLargePersonGroup): Person{
            return Person(largePersonGroup.name)
        }
    }
}