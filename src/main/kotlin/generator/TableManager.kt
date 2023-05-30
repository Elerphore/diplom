package generator

import state.ApplicationState
import utils.TableType

fun generateTable() {
    when(ApplicationState.selectedType) {
        TableType.STUDENTS -> StudentTableGenerator().generate()
        TableType.ORDERS -> OrderTableGenerator().generate()
        else -> println("no table")
    }
}
