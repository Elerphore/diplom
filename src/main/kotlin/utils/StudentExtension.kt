package utils

import data.Order
import data.Student
import java.time.Month

fun List<Student>.groupByCodeSpec() : Map<String, List<Student>> = this.groupBy { it.codeSpec }

fun List<Student>.groupByOsnova() : Map<String, List<Student>> = this.groupBy { it.osnova }

fun List<Student>.groupByDatePrikazFirst() : Map<String, List<Student>> = this.groupBy { it.datePrikazFirst.toString() }

fun List<Student>.groupByDepart() : Map<String, List<Student>> = this.groupBy { it.depart }

fun List<Student>.groupByGroups() : Map<String, List<Student>> = this.groupBy { it.nameGr }

fun List<Student>.groupByCourser() : Map<String, List<Student>> =
    this.groupBy {
        val year = it.nameGr.split("-", ignoreCase = false, limit = 0)[1]

        val course = when(year) {
            "22" -> "1 курс"
            "21" -> "2 курс"
            "20" -> "3 курс"
            "19" -> "4 курс"
            else -> "1 курс"
        }

        course
    }

fun List<Student>.countGroups(): Int = this.groupByGroups().count()

fun List<Order>.countAppliedOnFirstCourse(kod_agr: Int, month: Month): Int =
    this.count {
        it.kodaGr == kod_agr &&
                it.datePrikaz.month + 1 == month.value
                && (it.namePrikaz in listOf("Зачислить на первый курс"))
    }

fun List<Order>.countAppliedOnFreeBase(kod_agr: Int, month: Month): Int =
    this.count {
        it.kodaGr == kod_agr &&
                it.datePrikaz.month + 1 == month.value
                && (it.namePrikaz in listOf("Зачислить на бюджетной основе обучения", "Перевести с платной основы обучения на бюджетную"))
    }

fun List<Order>.countAppliedOnPaidBase(kod_agr: Int, month: Month) =
    this.count {
        it.kodaGr == kod_agr &&
                it.datePrikaz.month + 1 == month.value
                && (
                it.namePrikaz in listOf("Зачислить на платной основе обучения"))
    }

fun List<Order>.countTransferFromAnotherOrganization(kod_agr: Int, month: Month) =
    this.count {
        it.kodaGr == kod_agr &&
                it.datePrikaz.month + 1 == month.value
                && (it.namePrikaz in listOf("Зачислить в порядке перевода из другой образовательной организации"))
    }