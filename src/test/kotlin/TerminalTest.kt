import io.mockk.unmockkAll
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.Duration

internal class TerminalTest {
    private val standardOut = System.out
    private val standardIn = System.`in`
    private val outputStreamCaptor = ByteArrayOutputStream()
    private var inputStreamCaptor = ByteArrayInputStream("".toByteArray())

    @BeforeEach
    fun setUp() {
        unmockkAll()
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setIn(standardIn)
        System.setOut(standardOut)
    }

    private fun mockUserInput(data: String) {
        inputStreamCaptor = ByteArrayInputStream(data.toByteArray())
        System.setIn(inputStreamCaptor)
    }

    @Test
    internal fun `should print message`() {
        val message = "any string"
        Terminal.printMessage(message)
        assertEquals(message, outputStreamCaptor.toString().trim())
    }

    @Test
    internal fun `should return user input`() {
        val input = "hello world"
        mockUserInput(input)
        assertEquals(input, Terminal.getInput())
    }

    @Disabled
    @Test
    internal fun `should get input until not null`() {
        mockUserInput("")
        assertTimeout(Duration.ofMillis(1)) { Terminal.getInput() }
    }
}