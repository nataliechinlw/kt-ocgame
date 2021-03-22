import InputValidator.positiveInteger
import InputValidator.yesNo
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.random.Random
import kotlin.test.assertTrue

internal class GameTest {
    private var testGame = spyk(Game(), recordPrivateCalls = true)

    @BeforeEach
    internal fun setUp() {
        unmockkAll()
        mockkObject(Terminal)
        mockkConstructor(Session::class)
        every { Terminal.printMessage(any()) } just runs
        every { Terminal.getInput(::positiveInteger) } returns "1"
        every { Terminal.getInput(::yesNo) } returns "N"
        every { anyConstructed<Session>().run() } just runs
        every { anyConstructed<Session>().winner } returns Player.HUMAN
    }

    @Test
    internal fun `should welcome and run game`() {
        every { Terminal.getInput() } returns "N"
        testGame.start()
        verifySequence {
            testGame.start()
            Terminal.printMessage("Welcome to the game!")
            Terminal.printMessage("What is your target score?")
            Terminal.getInput(::positiveInteger)
            testGame["runSession"]()
            Terminal.printMessage("Do you want to play again?")
            Terminal.getInput(any())
            Terminal.printMessage("Ok, bye!")
        }
    }

    @Test
    internal fun `should run new session if player wants to replay`() {
        every { Terminal.getInput(::yesNo) } returnsMany listOf("Y", "Y", "N")
        testGame.start()
        verify(exactly = 3) { testGame["runSession"]() }

    }

    @ParameterizedTest
    @ValueSource(ints = [2, 5, 10])
    internal fun `should run session multiple times`(targetScore: Int) {
        every { Terminal.getInput(::positiveInteger) } returns targetScore.toString()
        every { anyConstructed<Session>().winner } returnsMany listOf(Player.AI, Player.HUMAN)

        testGame.start()
        verify(atLeast = targetScore) { testGame["runSession"]() }
        assertTrue(testGame.winners.count { it == Player.HUMAN } >= targetScore)
    }
}