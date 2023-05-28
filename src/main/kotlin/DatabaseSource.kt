import com.microsoft.sqlserver.jdbc.SQLServerDataSource
import data.Order
import data.Student
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

object DatabaseSource {
    private var serverName: String = System.getenv("serverName")
    private var databaseName: String? = System.getenv("databaseName")
    private var username: String = System.getenv("username")
    private var password: String = System.getenv("password")
    private var source: SQLServerDataSource? = null
    private var connection: Connection? = null
    private var statement: Statement? = null

    init {
        source = SQLServerDataSource()

        source!!.sslProtocol = "TLSv1"
        source!!.serverName = serverName
        source!!.databaseName = databaseName
        source!!.user = username
        source!!.setPassword(password)

        connection = source!!.connection

        statement = connection!!.createStatement()
    }

    fun orders(): List<Order> =
        executeQuery("select Pers_Kod, Kod_aGr, Name_Prikaz, Nom_Prikaz, Date_Prikaz from vStudPrikaz") { result ->
            return@executeQuery Order(
                persKod = result.getInt("Pers_Kod"),
                kodaGr = result.getInt("Kod_aGr"),
                namePrikaz = result.getString("Name_Prikaz"),
                nomPrikaz = result.getString("Nom_Prikaz"),
                datePrikaz = result.getDate("Date_Prikaz"),
            )
        }

    fun students(): List<Student> =
        executeQuery("select Pers_Kod, Kod_aGr, Sex, osnova, codeSpec, Name_Spec, isActive, datePrikazFirst, Date_EndOb, Age, Depart, NameGr, Name_FormO from vStuds") { result ->
            return@executeQuery Student(
                persKod = result.getInt("Pers_Kod"),
                kodAgr = result.getInt("Kod_aGr"),
                Sex = result.getString("Sex"),
                osnova = result.getString("osnova"),
                codeSpec = result.getString("codeSpec"),
                nameSpec = result.getString("Name_Spec"),
                isActive = result.getBoolean("isActive"),
                age = result.getInt("Age"),
                depart = result.getString("Depart"),
                nameGr = result.getString("NameGr"),
                nameFormO = result.getString("Name_FormO"),
                datePrikazFirst = result.getDate("datePrikazFirst"),
                dateEndOb = result.getDate("Date_EndOb")
            )
        }

    private fun <T> executeQuery(query: String, body: (res: ResultSet) -> T): List<T> {
        val result = statement!!.executeQuery(query)

        val list: MutableList<T> = mutableListOf()

        while (result.next()) {
            val item: T = body(result)
            list.add(item)
        }

        return list
    }

}