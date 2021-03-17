import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

internal class PlayerTest {
    @ParameterizedTest
    @CsvSource("HUMAN, You WIN!!", "AI, AI WINS!!")
    internal fun `should print correct winner message given winning Player`(playerEnum: String, winnerMessage: String) {
        assertEquals(winnerMessage, Player.valueOf(playerEnum).getWinnerMessage())
    }

    @ParameterizedTest
    @CsvSource("HUMAN; You are the predictor, what is your input?", "AI; AI is the predictor, what is your input?", delimiter = ';')
    internal fun `should print correct ask for input message given current predicting Player`(playerEnum: String, askForInputMessage: String) {
        assertEquals(askForInputMessage, Player.valueOf(playerEnum).getAskForInputMessage())
    }
}