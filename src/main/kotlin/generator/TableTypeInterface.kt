package generator

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import utils.writeValueToCell
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.Month
import java.util.*

interface TableTypeInterface {
    val fact: XSSFWorkbook
    val sheet: XSSFSheet?
    val excelFile: File
    var lastRowTable: Int


    val months: List<Month>
        get() = listOf(
            LocalDate.now().month + 2,
            LocalDate.now().month,
            LocalDate.now().month - 1,
            LocalDate.now().month - 2,
            LocalDate.now().month - 3
        )

    val titles: List<String>
        get() = listOf(
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

    fun generate()

    fun close() {

        val file = File("${System.getProperty("user.dir")}/out/")

        if(!file.exists()) {
            file.mkdir()
        }

        with(FileOutputStream(excelFile)) {
            fact.write(this)
            close()
        }
    }

    fun dateTitle(diff: Int, month: String) {
        val year = Calendar.getInstance().get(Calendar.YEAR) - 1
        writeValueToCell(diff, 2, "$month $year")
    }

    fun base(diff: Int) {

        writeValueToCell(diff + 2, 0, "№")
        writeValueToCell(diff + 2, 1, "Показатели")

        titles.forEachIndexed { index, s ->
            writeValueToCell(diff + (index + 5), 0, (index + 1).toString())
            writeValueToCell(diff + (index + 5), 1, s)
        }
    }

}
