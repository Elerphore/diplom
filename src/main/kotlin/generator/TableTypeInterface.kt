package generator

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import java.io.File

interface TableTypeInterface {
    val fact: XSSFWorkbook
    val sheet: XSSFSheet
    val styles: CellStyler
    val excelFile: File
    var lastRowTable: Int

    fun generate()
}
