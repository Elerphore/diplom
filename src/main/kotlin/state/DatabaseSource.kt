package state

import data.Order
import data.Student
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

object DatabaseSource {
    private var statement: Statement? = null

    init {

    }

    fun init() = println("!!! DATASOURCE INITIALIZED")

    fun groupNames(): List<String> =
        executeQuery("select distinct(NameGr) from \"vStuds\"") { res ->
            res.getString("NameGr")
        }

    fun departNames(): List<String> =
        executeQuery("select distinct(Depart) from \"vStuds\"") { res ->
            res.getString("Depart")
        }

    fun orderTypes(): List<String> =
        executeQuery("select distinct(Name_Prikaz) from \"vStudPrikaz\"") { res ->
            res.getString("Name_Prikaz")
        }

    fun orders(): List<Order> =
        executeQuery("select Pers_Kod, Kod_aGr, Name_Prikaz, Nom_Prikaz, Date_Prikaz from \"vStudPrikaz\"") { result ->
            Order(
                persKod = result.getInt("Pers_Kod"),
                kodaGr = result.getInt("Kod_aGr"),
                namePrikaz = result.getString("Name_Prikaz"),
                nomPrikaz = result.getString("Nom_Prikaz"),
                datePrikaz = result.getDate("Date_Prikaz"),
            )
        }

    fun students(): List<Student> =
        executeQuery("select Pers_Kod, Kod_aGr, Sex, osnova, codeSpec, Name_Spec, isActive, datePrikazFirst, Date_EndOb, Age, Depart, NameGr, Name_FormO from \"vStuds\" where Depart = '${ApplicationState.department}'") { result ->
            Student(
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

    fun studentsFromDepartment(department: String): List<Student> =
        executeQuery("select Pers_Kod, Kod_aGr, Sex, osnova, codeSpec, Name_Spec, isActive, datePrikazFirst, Date_EndOb, Age, Depart, NameGr, Name_FormO from \"vStuds\" where Depart = '$department'") { result ->
            Student(
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