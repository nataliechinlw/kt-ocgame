import InputValidator.inputWithPrediction
import InputValidator.inputWithoutPrediction
import InputValidator.positiveInteger
import InputValidator.yesNo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class InputValidatorTest {
    @Nested
    inner class YesNoTests {
        @ParameterizedTest
        @ValueSource(strings = ["Y", "N"])
        internal fun `should return null on yesNo with valid responses`(input: String) {
            assertNull(yesNo(input))
        }

        @ParameterizedTest
        @ValueSource(strings = ["A", "X", "", "chicken"])
        internal fun `should return error message on yesNo with valid responses`(input: String) {
            assertEquals("input should be either Y or N", yesNo(input))
        }
    }

    @Test
    internal fun `should validate inputWithPrediction responses`() {
        assertNull(inputWithPrediction("OO0"))
        assertNull(inputWithPrediction("CC4"))
        assertEquals(
            "correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand, followed by the prediction (0-4)",
            inputWithPrediction("chicken")
        )
    }

    @Nested
    inner class InputWithoutPredictionTests {
        @ParameterizedTest
        @ValueSource(strings = ["OO", "OC", "CO", "CC"])
        internal fun `should return null inputWithoutPrediction responses`(inputWithoutPrediction: String) {
            assertNull(inputWithoutPrediction(inputWithoutPrediction))
            assertEquals(
                "correct input should be of the form CC, where the first two letters indicate [O]pen or [C]losed state for each hand",
                inputWithoutPrediction("CC2")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["OO4", "OC3", "CO2", "CC0", "chicken", ""])
        internal fun `should return error message inputWithoutPrediction responses`(inputWithoutPrediction: String) {
            assertEquals(
                "correct input should be of the form CC, where the first two letters indicate [O]pen or [C]losed state for each hand",
                inputWithoutPrediction(inputWithoutPrediction)
            )
        }
    }

    @Nested
    inner class PositiveIntegerTests {
        @ParameterizedTest
        @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 99])
        internal fun `should return null on positiveInteger with valid integers`(input: Int) {
            assertNull(positiveInteger(input.toString()))
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "x", "-1", "100"])
        internal fun `should return error message on positiveInteger with invalid input`(input: String) {
            assertEquals("input should be a positive integer that is less than 100", positiveInteger(input))
        }
    }
}