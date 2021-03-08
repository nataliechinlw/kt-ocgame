import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PlayerInputTest {
    val testPlayer = PlayerInput()

    @Test
    internal fun `should count number of open hands`() {
        assertEquals(2, testPlayer.countHands("OO4"))
    }
}