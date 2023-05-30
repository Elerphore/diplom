package generator

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import state.DatabaseSource
import java.io.File
import java.io.FileOutputStream

class OrderTableGenerator : TableTypeInterface {
    override val fact: XSSFWorkbook = XSSFWorkbook()
    override val sheet: XSSFSheet = fact.createSheet()
    override val styles = CellStyler(fact)
    override val excelFile = File("${System.getProperty("user.dir")}/orders.xlsx")
    override var lastRowTable: Int = 0

    override fun generate() {
        sheet.createRow(0).createCell(0).setCellValue("Перс. код")
        sheet.getRow(0).createCell(1).setCellValue("Код")
        sheet.getRow(0).createCell(2).setCellValue("Название приказа")
        sheet.getRow(0).createCell(3).setCellValue("Номер приказа")
        sheet.getRow(0).createCell(4).setCellValue("Дата приказа")

        val orders = DatabaseSource.orders()

        orders.forEachIndexed { i, student ->
            val index = i + 1
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(0).setCellValue(student.persKod.toString())
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(1).setCellValue(student.kodaGr.toString())
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(2).setCellValue(student.namePrikaz)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(3).setCellValue(student.nomPrikaz)
            (sheet.getRow(index) ?: sheet.createRow(index)).createCell(4).setCellValue(student.datePrikaz)
        }

        with(FileOutputStream(excelFile)) {
            fact.write(this)
            close()
        }
    }
}