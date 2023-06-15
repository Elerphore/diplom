package generator

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import state.DatabaseSource
import utils.writeValueToCell
import java.io.File

class StudentTableGenerator : TableTypeInterface {
    override val fact: XSSFWorkbook = XSSFWorkbook()
    override val sheet: XSSFSheet = fact.createSheet()
    override val excelFile = File("${System.getProperty("user.dir")}/out/students.xlsx")
    override var lastRowTable: Int = 0

    override fun generate() {
        CellStyler.init(fact)

        writeValueToCell(0, 0, "Перс. код")
        writeValueToCell(0, 1, "Код")
        writeValueToCell(0, 2, "Пол")
        writeValueToCell(0, 3, "Основа")
        writeValueToCell(0, 4, "Специальность")
        writeValueToCell(0, 5, "Имя специальности")
        writeValueToCell(0, 6, "Активный")
        writeValueToCell(0, 7, "Возраст")
        writeValueToCell(0, 8, "Дата Начала")
        writeValueToCell(0, 9, "Дата окончания")
        writeValueToCell(0, 10, "Департамент")
        writeValueToCell(0, 11, "Имя группы")
        writeValueToCell(0, 12, "Имя формы")

        val students = DatabaseSource.students()

        students.forEachIndexed { i, student ->
            val index = i + 1
            writeValueToCell(index, 0, student.persKod.toString())
            writeValueToCell(index, 1, student.kodAgr.toString())
            writeValueToCell(index, 2, student.Sex.toString())
            writeValueToCell(index, 3, student.osnova)
            writeValueToCell(index, 4, student.codeSpec)
            writeValueToCell(index, 5, student.nameSpec)
            writeValueToCell(index, 6, student.isActive.toString())
            writeValueToCell(index, 7, student.age.toString())
            writeValueToCell(index, 8, student.datePrikazFirst.toString())
            writeValueToCell(index, 9, student.dateEndOb.toString())
            writeValueToCell(index, 10, student.depart)
            writeValueToCell(index, 11, student.nameGr)
            writeValueToCell(index, 12, student.nameFormO)
        }

        close()
    }
}