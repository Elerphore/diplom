package generator

import data.Student
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import state.ApplicationState
import utils.*
import java.io.File
import java.util.*

class DepartmentTableGenerator() : TableTypeInterface {

    override val fact: XSSFWorkbook = XSSFWorkbook()
    override var sheet: XSSFSheet? = null
    override val styles = CellStyler(fact)
    override val excelFile = File("${System.getProperty("user.dir")}/form_one.xlsx")
    override var lastRowTable: Int = 0

    private val students = ApplicationState.students

    private val titles = listOf(
        "Количество групп",
        "Кол-во несовершн.студент.",
        "Кол-во юношей",
        "Число студентов на 1:",
        "в том числе:",
        "академический отпуск",
        "отпуск по уходу за ребенком",
        "призваны в ряды РА",
        "Прибыло всего человек:",
        "в том числе:",
        "зачислено на обучение",
        "прибыло из других уч. заведений",
        "переведено с др. видов обучения",
        "восстановлено",
        "Выбыло всего:",
        "в том числе:",
        "переведено в другие уч. заведения",
        "переведено на др.виды обуч. (внутри колледжа)",
        "призваны в ряды РА",
        "за нарушение условий Договора",
        "за акад. задолженности",
        "не прошли Итоговую аттестацию",
        "закончили обучение",
        "выбыли по др. причинам"
    )

    override fun generate() {
        students.groupByDepart().forEach { (depName, stds) ->
            sheet = fact.createSheet(depName)

            dateTitle()
            departmentName(depName)
            base()

            codeSpec(stds)

        }

        close()
    }

    private fun codeSpec(stds: List<Student>) {

        var previousGroupsAmount = 0

        stds.groupByCodeSpec().forEach { (codeSpec, studscs) ->

            val studgrps = studscs.groupByGroups()

            val currentGroupsAmount = studgrps.size

            codeSpecName(groupCount = currentGroupsAmount, previousGroupCount = previousGroupsAmount, csName = codeSpec)

            defineMergeRegion(
                firstRow = 2,
                lastRow = 2,
                firstCol = (previousGroupsAmount * 2) + 2,
                lastCol = ((previousGroupsAmount * 2) + (currentGroupsAmount * 2)) + 1,
            )

            groups(studgrps, previousGroupsAmount)

            previousGroupsAmount += currentGroupsAmount
        }
    }

    private fun groups(studgrps: Map<String, List<Student>>, previousGroupsAmount: Int) {
        var prevGRAmount = 0
        studgrps.forEach { (groupName, studgrp) ->

            writeValueToCell(
                3,
                (prevGRAmount * 2) + (previousGroupsAmount * 2) + 2,
                groupName
            )

            writeValueToCell(
                4,
                (prevGRAmount * 2) + (previousGroupsAmount * 2) + 2,
                "В"
            )

            writeValueToCell(
                4,
                (prevGRAmount * 2) + (previousGroupsAmount * 2) + 3,
                "ВБ"
            )

            defineMergeRegion(
                firstRow = 3,
                lastRow = 3,
                firstCol = (prevGRAmount * 2) + (previousGroupsAmount * 2) + 2,
                lastCol = (prevGRAmount * 2) + ((previousGroupsAmount * 2)) + 3,
            )

            information(
                studgrp.groupByOsnova()["Бюджетная"] ?: emptyList(),
                false,
                (previousGroupsAmount * 2) + (prevGRAmount * 2)
            )

            information(
                studgrp.groupByOsnova()["Коммерческая"] ?: emptyList(),
                true,
                (previousGroupsAmount * 2) + (prevGRAmount * 2)
            )

            prevGRAmount += 1
        }
    }

    private fun dateTitle() {
        val cl = Calendar.getInstance()
        val month = cl.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ROOT)
        val year = cl.get(Calendar.YEAR)

        writeValueToCell(0, 2, "$month $year")
    }

    private fun departmentName(depName: String) = writeValueToCell(1, 0, depName)

    private fun base() {

        writeValueToCell(2, 0, "№")
        writeValueToCell(2, 1, "Показатели")

        titles.forEachIndexed { index, s ->
            writeValueToCell(index + 5, 0, (index + 1).toString())
            writeValueToCell(index + 5, 1, s)
        }
    }

    private fun codeSpecName(
        groupCount: Int,
        previousGroupCount: Int,
        csName: String,
    ) {
        writeValueToCell(
            row = 2,
            cell = (previousGroupCount * 2) + 2,
            value = csName
        )
    }


    private fun evaluateValue(type: String, stds: List<Student>): Int =
        when (type) {
            "Количество групп" -> 0
            "Кол-во несовершн.студент." -> stds.count { it.age < 18 }
            "Кол-во юношей" -> stds.count { it.Sex == "м" }
            "Число студентов на 1:" -> 0
            "в том числе:" -> 0
            "академический отпуск" -> 0
            "отпуск по уходу за ребенком" -> 0
            "призваны в ряды РА" -> 0
            "Прибыло всего человек:" -> 0
            "в том числе:" -> 0
            "зачислено на обучение" -> stds.count()
            "прибыло из других уч. заведений" -> 0
            "переведено с др. видов обучения" -> 0
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

    private fun information(studgrp: List<Student>, osnova: Boolean, i: Int) {
        val diff = if (osnova) 1 else 0

        titles.forEachIndexed { index, s ->
            val value = evaluateValue(s, studgrp)

            writeValueToCell(index + 5, i + 2 + diff, value.toString())
        }

    }

}