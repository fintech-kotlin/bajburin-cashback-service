package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo
import kotlin.math.ceil

class CompRule : CashbackRule {
    override fun calculate(transactionInfo: TransactionInfo) = with (transactionInfo) {
        mccCode?.let{
            if (mccCode == MCC_SOFTWARE && loyaltyProgramName == LOYALTY_PROGRAM_ALL) {
                val testSum = ceil(transactionSum * 100).toInt().toString()
                if (testSum.zip(testSum.reversed()){a, b -> if (a != b) 1 else 0}.sum() <= 2) {

                    val nameLen = transactionInfo.firstName.length
                    val snameLen = transactionInfo.lastName.length

                    var lcm = 1
                    while (lcm % nameLen != 0 || lcm % snameLen != 0) {
                        ++lcm
                    }
                    lcm.toDouble() / 100000.0 * transactionSum
                } else .0
            } else .0
        } ?: .0
    }
}