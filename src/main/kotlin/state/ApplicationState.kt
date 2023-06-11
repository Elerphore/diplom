package state

import data.Student
import utils.ScreenType
import utils.TableType

object ApplicationState {
    val groupNames: List<String>

    var selectedType: TableType? = null

    var departments: List<String> = emptyList()

    var students: List<Student> = emptyList()

    var screen: ScreenType = ScreenType.AUTHORIZATION

    init {
        groupNames = DatabaseSource.groupNames()
        departments = DatabaseSource.departNames()
        students = DatabaseSource.students()
    }


    fun init() = println("APPLICATION INITIALIZED")

}