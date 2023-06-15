package generator

import state.ApplicationState
import utils.TableType

fun generateTable() {
    when(ApplicationState.selectedType) {
        TableType.STUDENTS -> StudentTableGenerator().generate()
        TableType.ORDERS -> OrderTableGenerator().generate()
        TableType.DEPARTMENT -> DepartmentTableGenerator().generate()
        TableType.SPECIALIZATION -> SpecialitiesTableGenerator().generate()
        TableType.ENLARGED -> EnlargedTableGenerator().generate()
        TableType.SUMMARY -> SummaryTableGenerator().generate()
        else -> println("no table")
    }
}
