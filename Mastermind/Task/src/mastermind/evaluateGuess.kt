package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    // count correct positions
    val rightPosition = guess.zip(secret).count { (guessLetter, secretLetter) -> guessLetter == secretLetter }

    // collect items, which are in wrong positions or guessed incorrectly
    val (guessLeft, secretLeft) = guess.zip(secret)
        .filter { (guessLetter, secretLetter) -> guessLetter != secretLetter }
        .fold("" to "") { (guessLeft, secretLeft), (guessLetter, secretLetter)
            -> (guessLeft + guessLetter) to (secretLetter + secretLeft)}

    // count items left in the secret
    val secretLetterMap = secretLeft.groupingBy { it }.eachCount()

    // count items left in the guess
    val guessLetterMap = guessLeft.groupingBy { it }.eachCount()

    // count wrong positions
    val wrongPosition = guessLetterMap
        .map { (guessLetter, guessLetterCount) ->  secretLetterMap
            .getOrDefault(guessLetter, 0)
            // take amount of items from the secret which positions were not guessed
            // (limited by amount of items guessed successfully)
            .coerceAtMost(guessLetterCount)}
        .fold(0, Integer::sum)
    return Evaluation(rightPosition, wrongPosition)
}
