package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo

class SixSixSix : CashbackRule {
    override fun calculate(transactionInfo: TransactionInfo) =
        if (transactionInfo.transactionSum % 666.0 == .0)  6.66 else .0
}
