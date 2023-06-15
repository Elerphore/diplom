package parser.utils

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook

object CellStyler {
    var fact: XSSFWorkbook? = null

    var cellStyle: XSSFCellStyle? = null

    fun init(fact: XSSFWorkbook) {
        this.fact = fact

        cellStyle = fact!!.createCellStyle().apply {
            this.styleXf.applyBorder = true
            this.bottomBorderColor = IndexedColors.BLACK.index
            this.borderBottom = BorderStyle.THIN
            this.topBorderColor = IndexedColors.BLACK.index
            this.borderTop = BorderStyle.THIN
            this.leftBorderColor = IndexedColors.BLACK.index
            this.borderLeft = BorderStyle.THIN
            this.rightBorderColor = IndexedColors.BLACK.index
            this.borderRight = BorderStyle.THIN
            this.alignment = HorizontalAlignment.CENTER
            this.verticalAlignment = VerticalAlignment.CENTER
        }
    }
}
