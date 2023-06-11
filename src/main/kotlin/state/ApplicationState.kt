package state

import data.Student
import utils.ScreenType
import utils.TableType
import java.io.File

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

        if(!File(System.getProperty("user.dir") + "/conf").exists()) {
            println("!!! CREATE CONF DIRECTORY")
            File(System.getProperty("user.dir") + "/conf").mkdir()

            File(System.getProperty("user.dir") + "/conf/" + "available_users.json").createNewFile()
            File(System.getProperty("user.dir") + "/conf/" + "settings.json").createNewFile()

            File(System.getProperty("user.dir") + "/conf/" + "available_users.json")
                .writeText(
                    """
                        {
                          "users": [
                            {
                              "name": "NAME_ONE",
                              "password": "PASSWORD_ONE"
                            },
                            {
                              "name": "NAME_SECOND",
                              "password": "PASSWORD_SECOND"
                            },
                            {
                              "name": "NAME_THIRD",
                              "password": "PASSWORD_THIRD"
                            }
                          ]
                        }
                    """.trimIndent()
                )

        }

    }


    fun init() = println("!!! APPLICATION INITIALIZED")

}