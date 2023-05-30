package state

import utils.TableType

object ApplicationState {
    val groupNames: List<String>

    init {
        groupNames = DatabaseSource.groupNames()
    }

    var selectedType: TableType? = null

    fun init() = println("APPLICATION INITIALIZED")
}