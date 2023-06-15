package data

enum class OrderType(val orderName: String) {
    TRANSFER_FROM_ORGANIZATION("Зачислить в порядке перевода из другой образовательной организации"),
    APPLY_ON_FREE_BASE("Зачислить на бюджетной основе обучения"),
    TRANSFER_FROM_PAID_TO_FREE_BASE("Перевести с платной основы обучения на бюджетную"),
    TRANSFER_TO_SPO("Перевести на уровень СПО"),
    APPLY_TO_FIRST_COURSE("Зачислить на первый курс"),
    APPLY_ON_PAID_BASE("Зачислить на платной основе обучения")
}
