import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction
import InputValidator.positiveInteger
import InputValidator.yesNo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class InputValidatorTest {
    @Test
    internal fun `should validate yesNo responses`() {
        assertNull(yesNo("Y"))
        assertNull(yesNo("N"))
        kotlin.test.assertEquals("input should be either Y or N", yesNo("X"))
    }

    @Test
    internal fun `should validate inputWithPrediction responses`() {
        assertNull(inputWithPrediction("OO0"))
        assertNull(inputWithPrediction("CC4"))
        kotlin.test.assertEquals(
            "correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand, followed by the prediction (0-4)",
            inputWithPrediction("chicken")
        )
    }

    @Test
    internal fun `should validate inputWithoutPrediction responses`() {
        assertNull(inputWithoutPrediction("OO"))
        assertNull(inputWithoutPrediction("CC"))
        kotlin.test.assertEquals(
            "correct input should be of the form CC, where the first two letters indicate [O]pen or [C]losed state for each hand",
            inputWithoutPrediction("CC2")
        )
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9])
    internal fun `should return null on positiveInteger with valid integers`(input: Int) {
        assertNull(positiveInteger(input.toString()))
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "x", "-1", "100"])
    internal fun `should return error message on positiveInteger with invalid input`(input: String) {
        assertEquals("input should be a positive integer that is less than 100", positiveInteger(input))
    }
}