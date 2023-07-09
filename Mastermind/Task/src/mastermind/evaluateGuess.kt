package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0

    var guessLeft = ""
    var secretLeft = ""

    guess.zip(secret)
        .forEach { (guessLetter, secretLetter) ->
            if (guessLetter == secretLetter) {
                rightPosition++
            } else {
                guessLeft += guessLetter
                secretLeft += secretLetter
            }
        }

    val secretLetterMap = secretLeft.groupingBy { it }.eachCount()

    val guessLetterMap = guessLeft.groupingBy { it }.eachCount()

    val wrongPosition = guessLetterMap
        .map { (key, value) ->  secretLetterMap
            .getOrDefault(key, 0)
            .coerceAtMost(value)}
        .fold(0, Integer::sum)
    return Evaluation(rightPosition, wrongPosition)
}
