package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo
import java.time.LocalDate
import java.time.Month
import kotlin.math.round

internal const val LOYALTY_PROGRAM_BLACK = "BLACK"
internal const val LOYALTY_PROGRAM_ALL = "ALL"
internal const val LOYALTY_PROGRAM_BEER = "BEER"
internal const val MAX_CASH_BACK = 3000.0
internal const val MCC_SOFTWARE = 5734
internal const val MCC_BEER = 5921

class CashbackCalculatorImpl : CashbackCalculator {

    override fun calculateCashback(transactionInfo: TransactionInfo): Double {
        val rules = listOf(BlackRule(), SixSixSixRule(), CompRule(), BeerRule())
        var result = rules.fold(.0) { sum, cashbackRule ->  sum + cashbackRule.calculate(transactionInfo)}
        result = round(result * 100) / 100
        return minOf(3000.0 - transactionInfo.cashbackTotalValue, result)
    }
}