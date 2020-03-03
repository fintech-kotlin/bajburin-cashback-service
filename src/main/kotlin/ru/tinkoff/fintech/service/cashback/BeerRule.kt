package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo
import java.time.LocalDate
import java.time.Month

class BeerRule : CashbackRule {
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

    override fun calculate(transactionInfo: TransactionInfo) = with (transactionInfo) {
        mccCode?.let {
            if (loyaltyProgramName == LOYALTY_PROGRAM_BEER && mccCode == MCC_BEER) {
                 when {
                    firstName.toLowerCase() == "олег" && lastName.toLowerCase() == "олегов" -> transactionSum * 0.1
                    firstName.toLowerCase() == "олег" -> transactionSum * 0.07
                    monthWithFirstLetter[LocalDate.now().month.value] == firstName[0] -> transactionSum * 0.05
                    monthWithFirstLetter[LocalDate.now().minusMonths(1).month.value] == firstName[0] -> transactionSum * 0.03
                    monthWithFirstLetter[LocalDate.now().plusMonths(1).month.value] == firstName[0] -> transactionSum * 0.03
                    else -> transactionSum * 0.02
                }
            } else .0
        } ?: .0
    }

}