package utils

import generator.TableTypeInterface
import org.apache.poi.ss.util.CellRangeAddress

fun TableTypeInterface.writeValueToCell(row: Int, cell: Int, value: String) =
    with(sheet!!) {
        val rw = getRow(row) ?: createRow(row)
        rw.getCell(cell) ?: rw.createCell(cell)
    }
    .setCellValue(value)

fun TableTypeInterface.defineMergeRegion(firstRow: Int, lastRow: Int, firstCol: Int, lastCol: Int) =
    with(sheet!!) {
        addMergedRegion(CellRangeAddress(firstRow, lastRow, firstCol, lastCol))
    }
