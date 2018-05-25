package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        val yearComparisonResult = year.compareTo(other.year)
        if (yearComparisonResult != 0 ) return yearComparisonResult

        val monthComparisonResult = month.compareTo(other.month)
        if (monthComparisonResult != 0 ) return monthComparisonResult

        return dayOfMonth.compareTo(other.dayOfMonth)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> { return DateIterator(start, endInclusive) }
}

class DateIterator(private val start: MyDate, private val endInclusive: MyDate) : Iterator<MyDate> {
    private var currentDate : MyDate? = null

    override fun hasNext(): Boolean {
        return start < endInclusive && (currentDate == null || currentDate!! < endInclusive)
    }

    override fun next(): MyDate {
        if (!hasNext()) throw IndexOutOfBoundsException()

        currentDate = if (currentDate == null) start else currentDate?.nextDay()!!

        return currentDate as MyDate
    }

}