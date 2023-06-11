package generator

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import parser.utils.CellStyler
import java.io.File

class SpecialitiesTableGenerator : TableTypeInterface {
    override val fact: XSSFWorkbook = XSSFWorkbook()
    override val sheet: XSSFSheet? = null
    override val styles: CellStyler = CellStyler(fact)
    override val excelFile: File = File("${System.getProperty("user.dir")}/out/specialities.xlsx")
    override var lastRowTable: Int  = 0

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
        TODO("Not yet implemented")
    }
}