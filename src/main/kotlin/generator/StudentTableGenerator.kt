package generator

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import state.DatabaseSource
import java.io.File
import java.io.FileOutputStream

class StudentTableGenerator : TableTypeInterface {
    override val fact: XSSFWorkbook = XSSFWorkbook()
    override val sheet: XSSFSheet = fact.createSheet()
    override val styles = CellStyler(fact)
    override val excelFile = File("${System.getProperty("user.dir")}/students.xlsx")
    override var lastRowTable: Int = 0

    override fun generate() {
        sheet.createRow(0).createCell(0).setCellValue("Перс. код")
        sheet.getRow(0).createCell(1).setCellValue("Код")
        sheet.getRow(0).createCell(2).setCellValue("Пол")
        sheet.getRow(0).createCell(3).setCellValue("Основа")
        sheet.getRow(0).createCell(4).setCellValue("Специальность")
        sheet.getRow(0).createCell(5).setCellValue("Имя специальности")
        sheet.getRow(0).createCell(6).setCellValue("Активный")
        sheet.getRow(0).createCell(7).setCellValue("Дата приказа")
        sheet.getRow(0).createCell(8).setCellValue("Дата окончания")
        sheet.getRow(0).createCell(9).setCellValue("Возраст")
        sheet.getRow(0).createCell(10).setCellValue("Департамент")
        sheet.getRow(0).createCell(11).setCellValue("Имя группы")
        sheet.getRow(0).createCell(12).setCellValue("Имя формы")

        val students = DatabaseSource.students()

        students.forEachIndexed { i, student ->
            val index = i + 1
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(0).setCellValue(student.persKod.toString())
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(1).setCellValue(student.kodAgr.toString())
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(2).setCellValue(student.Sex)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(3).setCellValue(student.osnova)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(4).setCellValue(student.codeSpec)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(5).setCellValue(student.nameSpec)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(6).setCellValue(student.isActive)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(7).setCellValue(student.age.toString())
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(8).setCellValue(student.depart)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(9).setCellValue(student.nameGr)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(10).setCellValue(student.nameFormO)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(11).setCellValue(student.datePrikazFirst)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(12).setCellValue(student.dateEndOb)
        }

        with(FileOutputStream(excelFile)) {
            fact.write(this)
            close()
        }
    }
}