import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

internal class PlayerTest {
    @ParameterizedTest
    @CsvSource("HUMAN, You WIN!!", "AI, AI WINS!!")
    internal fun `should print correct winner message given winning Player`(playerEnum: String, winnerMessage: String) {
        assertEquals(winnerMessage, Player.valueOf(playerEnum).getWinnerMessage())
    }
}