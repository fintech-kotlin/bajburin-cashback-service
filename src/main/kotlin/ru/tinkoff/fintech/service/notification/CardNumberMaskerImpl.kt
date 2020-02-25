package ru.tinkoff.fintech.service.notification

class CardNumberMaskerImpl: CardNumberMasker {

    override fun mask(cardNumber: String, maskChar: Char, start: Int, end: Int): String {
        require(start <= end && (start < cardNumber.length || start == 0)) {
            throw IndexOutOfBoundsException("Start index cannot be greater than end index")
        }

        if (cardNumber.isEmpty()) return ""

        val rangeEnd = minOf(end, cardNumber.length)

        return cardNumber.replaceRange(start, rangeEnd, maskChar.toString().repeat(rangeEnd - start))
    }
}