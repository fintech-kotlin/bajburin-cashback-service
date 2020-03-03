package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo
import java.time.LocalDate
import java.time.Month
import kotlin.math.ceil
import kotlin.math.round

internal const val LOYALTY_PROGRAM_BLACK = "BLACK"
internal const val LOYALTY_PROGRAM_ALL = "ALL"
internal const val LOYALTY_PROGRAM_BEER = "BEER"
internal const val MAX_CASH_BACK = 3000.0
internal const val MCC_SOFTWARE = 5734
internal const val MCC_BEER = 5921

class CashbackCalculatorImpl : CashbackCalculator {

    private val monthWithFirstLetter = mapOf(
        Month.JANUARY.value to 'я',
        Month.FEBRUARY.value to 'ф',
        Month.MARCH.value to 'м',
        Month.APRIL.value to 'а',
        Month.MAY.value to 'м',
        Month.JUNE.value to 'и',
        Month.JULY.value to 'и',
        Month.AUGUST.value to 'а',
        Month.SEPTEMBER.value to 'с',
        Month.OCTOBER.value to 'о',
        Month.NOVEMBER.value to 'н',
        Month.DECEMBER.value to 'д'
    )

    override fun calculateCashback(transactionInfo: TransactionInfo): Double {
        var result = .0

        if (transactionInfo.loyaltyProgramName == LOYALTY_PROGRAM_BLACK) {
            result += transactionInfo.transactionSum / 100
        }

        if (transactionInfo.transactionSum % 666.0 == .0) {
            result += 6.66
        }

        transactionInfo.mccCode?.let {
            if (transactionInfo.mccCode == 5734 && transactionInfo.loyaltyProgramName == LOYALTY_PROGRAM_ALL) {
                val testSum = ceil(transactionInfo.transactionSum * 100).toInt().toString()
                if (testSum.zip(testSum.reversed()){a, b -> if (a != b) 1 else 0}.sum() <= 2) {

                    val nameLen = transactionInfo.firstName.length
                    val snameLen = transactionInfo.lastName.length

                    var lcm = 1
                    while (lcm % nameLen != 0 || lcm % snameLen != 0) {
                        ++lcm
                    }
                    result += lcm.toDouble() / 100000.0 * transactionInfo.transactionSum
                }
            }

            if (transactionInfo.loyaltyProgramName == LOYALTY_PROGRAM_BEER && transactionInfo.mccCode == 5921) {
                result += when {
                    transactionInfo.firstName.toLowerCase() == "олег" && transactionInfo.lastName.toLowerCase() == "олегов" -> transactionInfo.transactionSum * 0.1
                    transactionInfo.firstName.toLowerCase() == "олег" -> transactionInfo.transactionSum * 0.07
                    monthWithFirstLetter[LocalDate.now().month.value] == transactionInfo.firstName[0] -> transactionInfo.transactionSum * 0.05
                    monthWithFirstLetter[LocalDate.now().minusMonths(1).month.value] == transactionInfo.firstName[0] -> transactionInfo.transactionSum * 0.03
                    monthWithFirstLetter[LocalDate.now().plusMonths(1).month.value] == transactionInfo.firstName[0] -> transactionInfo.transactionSum * 0.03
                    else -> transactionInfo.transactionSum * 0.02
                }
            }
        }

        result = round(result * 100) / 100

        return minOf(3000.0 - transactionInfo.cashbackTotalValue, result)
    }

}