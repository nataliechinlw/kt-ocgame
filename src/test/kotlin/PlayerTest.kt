import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PlayerTest {
    @Test
    internal fun `should print user winner if HUMAN winner`() {
        assertEquals("You WIN!!", Player.HUMAN.getWinnerMessage())
    }

    @Test
    internal fun `should print AI winner if AI winner`() {
        assertEquals("AI WINS!!", Player.AI.getWinnerMessage())
    }
}