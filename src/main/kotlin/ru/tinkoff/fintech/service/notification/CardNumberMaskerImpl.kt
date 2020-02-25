package ru.tinkoff.fintech.service.notification

import java.lang.Integer.min

class CardNumberMaskerImpl: CardNumberMasker {

    override fun mask(cardNumber: String, maskChar: Char, start: Int, end: Int): String {
        require(start <= end) {
            throw IndexOutOfBoundsException("Start index cannot be greater than end index")
        }

        if (cardNumber.isEmpty()) return ""

        val length = cardNumber.length
        val rangeEnd = min(end, length)

        return StringBuilder()
            .append(cardNumber, 0, start)
            .append(maskChar.toString().repeat(rangeEnd - start))
            .append(cardNumber, rangeEnd, length)
            .toString()
    }
}