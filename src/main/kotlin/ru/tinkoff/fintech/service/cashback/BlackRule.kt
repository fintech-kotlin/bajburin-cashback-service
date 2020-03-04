package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo

class BlackRule : CashbackRule {
    override fun calculate(transactionInfo: TransactionInfo) = with (transactionInfo) {
        if (loyaltyProgramName == LOYALTY_PROGRAM_BLACK) transactionSum / 100 else .0
    }
}