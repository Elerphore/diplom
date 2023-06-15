package generator

import data.Student
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import state.ApplicationState
import utils.*
import java.io.File
import java.time.Month

class SummaryTableGenerator() : TableTypeInterface {
    override val fact: XSSFWorkbook = XSSFWorkbook()
    override var sheet: XSSFSheet? = null
    override val excelFile: File = File("${System.getProperty("user.dir")}/out/summary.xlsx")
    override var lastRowTable: Int = 0

    private val students = ApplicationState.students

    var currentMonth: Month? = null

    override fun generate() {
        CellStyler.init(fact)

        months.forEachIndexed { index, month ->
            currentMonth = month
            if(sheet == null) sheet = fact.createSheet("Итог")

            val diff = index * (titles.size + 8)
            dateTitle(diff, month.getRussianName())
            base(diff)

            group(students, diff)
        }

        close()

    }

    fun group(stds: List<Student>, diff: Int) {
        var previousGroupsAmount = 0

        stds.groupByCourser().forEach { course, studcourse ->
            val studSpec = studcourse.groupByCodeSpec()
            val currentGroupsAmount = studSpec.size

            groupName(previousGroupCount = previousGroupsAmount, courseName = course, diff = diff)

            defineMergeRegion(
                firstRow = diff + 2,
                lastRow = diff + 2,
                firstCol = (previousGroupsAmount * 2) + 2,
                lastCol = ((previousGroupsAmount * 2) + (currentGroupsAmount * 2)) + 1,
            )

            groups(studSpec, previousGroupsAmount, diff)

            previousGroupsAmount += currentGroupsAmount

        }
    }

    fun groupName(previousGroupCount: Int, courseName: String, diff: Int) {
        writeValueToCell(
            row = diff + 2,
            cell = (previousGroupCount * 2) + 2,
            value = courseName
        )
    }

    fun groups(studspec: Map<String, List<Student>>, previousGroupsAmount: Int, diff: Int) {
        var prevGRAmount = 0

        studspec.forEach { (spec, stds) ->

            writeValueToCell(
                diff + 3,
                (prevGRAmount * 2) + (previousGroupsAmount * 2) + 2,
                spec
            )

            writeValueToCell(
                diff + 4,
                (prevGRAmount * 2) + (previousGroupsAmount * 2) + 2,
                "В"
            )

            writeValueToCell(
                diff + 4,
                (prevGRAmount * 2) + (previousGroupsAmount * 2) + 3,
                "ВБ"
            )

            defineMergeRegion(
                firstRow = diff + 3,
                lastRow = diff + 3,
                firstCol = (prevGRAmount * 2) + (previousGroupsAmount * 2) + 2,
                lastCol = (prevGRAmount * 2) + ((previousGroupsAmount * 2)) + 3
            )

            information(
                stds.groupByOsnova()["Бюджетная"] ?: emptyList(),
                false,
                (previousGroupsAmount * 2) + (prevGRAmount * 2),
                diff
            )

            information(
                stds.groupByOsnova()["Коммерческая"] ?: emptyList(),
                true,
                (previousGroupsAmount * 2) + (prevGRAmount * 2),
                diff
            )

            prevGRAmount += 1
        }
    }

    private fun information(studgrp: List<Student>, osnova: Boolean, i: Int, di: Int) {
        val diff = if (osnova) 1 else 0

        titles.forEachIndexed { index, s ->
            val value = evaluateValue(s, studgrp)

            writeValueToCell(di + (index + 5), i + 2 + diff, value.toString())
        }

    }

    private fun evaluateValue(type: String, stds: List<Student>): Int =
        when (type) {
            "Количество групп" -> stds.countGroups()
            "Кол-во несовершн.студент." -> stds.count { it.age < 18 }
            "Кол-во юношей" -> stds.count { it.Sex == "м" }
            "Число студентов на 1:" -> stds.firstOrNull()?.let { ApplicationState.orders.countAppliedOnFirstCourse(it.kodAgr, currentMonth!!) } ?: 0
            "в том числе:" -> 0
            "академический отпуск" -> 0
            "отпуск по уходу за ребенком" -> 0
            "призваны в ряды РА" -> 0
            "Прибыло всего человек:" -> stds.count()
            "в том числе:" -> stds.count()
            "зачислено на обучение" ->
                stds.firstOrNull()?.let {
                    ApplicationState.orders.countAppliedOnPaidBase(it.kodAgr, currentMonth!!) +
                            ApplicationState.orders.countAppliedOnFreeBase(it.kodAgr, currentMonth!!) +
                            ApplicationState.orders.countTransferFromAnotherOrganization(it.kodAgr, currentMonth!!)
                } ?: 0
            "прибыло из других уч. заведений" -> stds.firstOrNull()?.let { ApplicationState.orders.countTransferFromAnotherOrganization(it.kodAgr, currentMonth!!) } ?: 0
            "переведено с др. видов обучения" -> stds.firstOrNull()?.let { ApplicationState.orders.countTransferFromAnotherOrganization(it.kodAgr, currentMonth!!) } ?: 0
            "восстановлено" -> 0
            "Выбыло всего:" -> 0
            "в том числе:" -> 0
            "переведено в другие уч. заведения" -> 0
            "переведено на др.виды обуч. (внутри колледжа)" -> 0
            "призваны в ряды РА" -> 0
            "за нарушение условий Договора" -> 0
            "за акад. задолженности" -> 0
            "не прошли Итоговую аттестацию" -> 0
            "закончили обучение" -> 0
            "выбыли по др. причинам" -> 0
            else -> 0
        }

}