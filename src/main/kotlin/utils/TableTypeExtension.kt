package utils

import generator.TableTypeInterface
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.RegionUtil
import parser.utils.CellStyler

fun TableTypeInterface.writeValueToCell(row: Int, cell: Int, value: String) =
    with(sheet!!) {
        val rw = getRow(row) ?: createRow(row)
        val cl = rw.getCell(cell) ?: rw.createCell(cell)

            cl.cellStyle = CellStyler.cellStyle


        if(value.toDoubleOrNull() != null)
            cl.setCellValue(value.toDouble())
        else
            cl.setCellValue(value)

        val currentWidth = getColumnWidth(cell)

        if(currentWidth < value.length * 100) {
            setColumnWidth(cell, value.length * 100)
        }
    }


fun TableTypeInterface.defineMergeRegion(firstRow: Int, lastRow: Int, firstCol: Int, lastCol: Int) =
    with(sheet!!) {
        val cellRange = CellRangeAddress(firstRow, lastRow, firstCol, lastCol)

        addMergedRegion(cellRange)

        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRange, sheet!!)
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRange, sheet!!)
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRange, sheet!!)
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRange, sheet!!)
    }
