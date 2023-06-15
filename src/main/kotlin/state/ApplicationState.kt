package state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.components.Alert
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import data.Order
import data.Settings
import data.Student
import data.UserList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.ScreenType
import utils.TableType
import java.io.File

object ApplicationState {
    private val mapper = jacksonObjectMapper().apply {
        this.registerKotlinModule()
        this.registerModule(JavaTimeModule())
    }

    var groupNames: List<String> = emptyList()

    var orders: List<Order> = emptyList()

    var selectedType: TableType = TableType.NOT_SELECTED

    var departments: List<String> = emptyList()

    var students: List<Student> = emptyList()

    var screenTypeRender: MutableState<ScreenType?>? = null

    @Composable
    fun screenTypeRendering(scr: MutableState<ScreenType?>) {
        screenTypeRender = scr
    }

    var department: String? = null
        set(value) {

            val settingsString = File(System.getProperty("user.dir") + "/conf/" + "settings.json").readText()
            val settings = mapper.readValue<Settings>(settingsString)

            settings.department = value

            mapper.writeValue(
                File(System.getProperty("user.dir") + "/conf/" + "settings.json"),
                settings
            )

            field = value
        }

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
        if (!File(System.getProperty("user.dir") + "/conf").exists()) {
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
                                    "name": "Бирюкова Ю.Ю",
                                    "password": "Бирюкова",
                                    "department": "Отд. 1 Общеобразовательная подготовка"
                                },
                                {
                                  "name": "Сидорова Н.В",
                                  "password": "Сидорова",
                                  "department": "Отд. 2 Информационные технологии и транспорт"
                                },
                                {
                                  "name": "Науменко О.П",
                                  "password": "Науменко",
                                  "department": "Отд. 3 Механическое, гидравлическое оборудование и металлургия"
                                },
                                {
                                  "name": "Закирова Л.А",
                                  "password": "Закирова",
                                  "department": "Отд. 4 Строительство, экономика и сфера обслуживания"
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
            department = settings.department

            receive()
        }

    }

    fun receive() {
        groupNames = DatabaseSource.groupNames()
        departments = DatabaseSource.departNames()
        students = DatabaseSource.students()
        orders = DatabaseSource.orders()
    }

    fun auth() {
        val usersString = File(System.getProperty("user.dir") + "/conf/" + "available_users.json").readText()
        val usersList = mapper.readValue<UserList>(usersString)

        val user = usersList.users.firstOrNull { it.name == this.username && it.password == this.password }

        user?.let {
            this.screen = ScreenType.MAIN
            this.screenTypeRender?.value = ScreenType.MAIN
            this.department = user.department

            receive()
        } ?: run {
            Alert.description = "Логин или пароль не верны"
            Alert.isShowDialog?.value = true
        }
    }

    fun logout() {
        screenTypeRender?.value = ScreenType.AUTHORIZATION
        screen = ScreenType.AUTHORIZATION
        username = null
        password = null
        department = null
    }
}