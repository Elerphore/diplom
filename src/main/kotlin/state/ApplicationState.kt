package state

object ApplicationState {
    val groupNames: List<String>

    init {
        groupNames = DatabaseSource.groupNames()
    }

    fun init() = println("APPLICATION INITIALIZED")
}