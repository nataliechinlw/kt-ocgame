import java.lang.Exception

class PlayerInput() {
    fun countHands(input: String): Int {
        val numberInFirstHand = if (input[0] == 'O') 1 else 0
        val numberInSecondHand = if (input[1] == 'O') 1 else 0
        return numberInFirstHand + numberInSecondHand
    }

    fun verifyInput(input: String): Boolean {
        if (input[0] != 'O' && input[0] != 'C')
            throw Exception("Bad input: correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand")
        if (input[1] != 'O' && input[1] != 'C')
            throw Exception("Bad input: correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand")
        if (input[2].toString().toInt() > 4)
            throw Exception("Bad input: prediction should be in the range of 0-4.")
        return input !== ""
    }
}