package state

import data.Student
import utils.TableType

object ApplicationState {
    val groupNames: List<String>

    var selectedType: TableType? = null

    var departments: List<String> = emptyList()

    var students: List<Student> = emptyList()

    init {
        groupNames = DatabaseSource.groupNames()
        departments = DatabaseSource.departNames()
        students = DatabaseSource.students()
    }


    fun init() = println("APPLICATION INITIALIZED")

}