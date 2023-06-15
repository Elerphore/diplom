package utils

enum class TableType(val stringName: String) {
    NOT_SELECTED("Выберите таблицу..."),
    STUDENTS("Студенты"),
    ORDERS("Приказы"),
    DEPARTMENT("Отделения"),
    SPECIALIZATION("Специализация"),
    ENLARGED("Укрупнённые"),
    SUMMARY("Итог"),
}
