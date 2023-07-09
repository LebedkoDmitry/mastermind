package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    // collect items, which are in wrong positions or guessed incorrectly
    val (guessLeft, secretLeft) = guess.zip(secret)
        .filter { (guessLetter, secretLetter) -> guessLetter != secretLetter }
        .fold("" to "") { (guessLeft, secretLeft), (guessLetter, secretLetter)
            -> (guessLeft + guessLetter) to (secretLetter + secretLeft)}

    // count items left in the secret
    val secretLetterMap = secretLeft.getLetterCountMap()

    // count items left in the guess
    val guessLetterMap = guessLeft.getLetterCountMap()

    // count wrong positions
    val wrongPosition = guessLetterMap
        .map { (guessLetter, guessLetterCount) ->  secretLetterMap
            .getOrDefault(guessLetter, 0)
            // take amount of items from the secret which positions were not guessed
            // (limited by amount of items guessed successfully)
            .coerceAtMost(guessLetterCount)}
        .fold(0, Integer::sum)
    return Evaluation(guess.getMatchingLettersCount(secret), wrongPosition)
}

fun String.getLetterCountMap() = this.groupingBy { it }.eachCount()

fun String.getMatchingLettersCount(anotherString: String) = this.zip(anotherString)
    .count { (guessLetter, secretLetter) -> guessLetter == secretLetter }