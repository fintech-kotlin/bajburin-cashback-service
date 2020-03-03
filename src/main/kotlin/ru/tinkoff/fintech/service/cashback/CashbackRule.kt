package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo

interface CashbackRule {

    fun calculate(transactionInfo: TransactionInfo): Double
}
