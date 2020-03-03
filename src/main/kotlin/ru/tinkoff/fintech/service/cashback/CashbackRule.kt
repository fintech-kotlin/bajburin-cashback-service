package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo
import java.time.Month

interface CashbackRule {

    fun calculate(transactionInfo: TransactionInfo): Double
}
