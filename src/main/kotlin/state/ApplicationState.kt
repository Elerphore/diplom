package state

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import data.Settings
import data.Student
import utils.ScreenType
import utils.TableType
import java.io.File

object ApplicationState {
    private val mapper = jacksonObjectMapper().apply {
        this.registerKotlinModule()
        this.registerModule(JavaTimeModule())
    }

    val groupNames: List<String>

    var selectedType: TableType? = null

    var departments: List<String> = emptyList()

    var students: List<Student> = emptyList()

    var screen: ScreenType = ScreenType.AUTHORIZATION
        set(value) {

            val settingsString = File(System.getProperty("user.dir") + "/conf/" + "settings.json").readText()
            val settings = mapper.readValue<Settings>(settingsString)

            settings.screen = value

            mapper.writeValue(
                File(System.getProperty("user.dir") + "/conf/" + "settings.json"),
                settings
            )

            field = value
        }

    var username: String? = null
        set(value) {

            val settingsString = File(System.getProperty("user.dir") + "/conf/" + "settings.json").readText()
            val settings = mapper.readValue<Settings>(settingsString)

            settings.username = value

            mapper.writeValue(
                File(System.getProperty("user.dir") + "/conf/" + "settings.json"),
                settings
            )

            field = value
        }

    var password: String? = null
        set(value) {
            val settingsString = File(System.getProperty("user.dir") + "/conf/" + "settings.json").readText()
            val settings = mapper.readValue<Settings>(settingsString)

            settings.password = value

            mapper.writeValue(
                File(System.getProperty("user.dir") + "/conf/" + "settings.json"),
                settings
            )

            field = value
        }

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

            mapper.writeValue(
                File(System.getProperty("user.dir") + "/conf/" + "settings.json"),
                Settings()
            )
        } else {
            val settingsString = File(System.getProperty("user.dir") + "/conf/" + "settings.json").readText()
            val settings = mapper.readValue<Settings>(settingsString)

            screen = settings.screen
            username = settings.username
            password = settings.password
        }

    }

    fun init() = println("!!! APPLICATION INITIALIZED")


    fun auth() {

    }
}