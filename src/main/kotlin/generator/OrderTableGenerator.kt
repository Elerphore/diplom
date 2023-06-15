package generator

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import state.DatabaseSource
import utils.writeValueToCell
import java.io.File

class OrderTableGenerator : TableTypeInterface {
    override val fact: XSSFWorkbook = XSSFWorkbook()
    override val sheet: XSSFSheet = fact.createSheet()
    override val excelFile = File("${System.getProperty("user.dir")}/out/orders.xlsx")
    override var lastRowTable: Int = 0

    override fun generate() {
        CellStyler.init(fact)

        writeValueToCell(0, 0, "Перс. код")
        writeValueToCell(0, 1, "Код")
        writeValueToCell(0, 2, "Название приказа")
        writeValueToCell(0, 3, "Номер приказа")
        writeValueToCell(0, 4, "Дата приказа")

        val orders = DatabaseSource.orders()

        orders.forEachIndexed { i, student ->
            val index = i + 1
            writeValueToCell(index, 0, student.persKod.toString())
            writeValueToCell(index, 1, student.kodaGr.toString())
            writeValueToCell(index, 2, student.namePrikaz)
            writeValueToCell(index, 3, student.nomPrikaz)
            writeValueToCell(index, 4, student.datePrikaz.toString())
        }

        close()
    }
}