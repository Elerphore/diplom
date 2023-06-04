package utils

import data.Student

fun List<Student>.groupByCodeSpec() : Map<String, List<Student>> = this.groupBy { it.codeSpec }

fun List<Student>.groupByOsnova() : Map<String, List<Student>> = this.groupBy { it.osnova }

fun List<Student>.groupByDatePrikazFirst() : Map<String, List<Student>> = this.groupBy { it.datePrikazFirst.toString() }

fun List<Student>.groupByDepart() : Map<String, List<Student>> = this.groupBy { it.depart }

fun List<Student>.groupByGroups() : Map<String, List<Student>> = this.groupBy { it.nameGr }
