import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.PrintStream
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import org.junit.jupiter.api.AfterEach

internal class GameTest {
    private val testGame = Game()
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    internal fun `should print welcome message`() {
        testGame.start()
        assertEquals("Welcome to the game!", outputStreamCaptor.toString().trim())
    }
}