package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0

    var guessLeft = ""
    var secretLeft = ""

    guess.forEachIndexed { index, c ->
        if (c == secret[index]) {
            rightPosition++
        } else {
            guessLeft += c
            secretLeft += secret[index]
        }
    }

    val secretLetterMap = HashMap<Char, Int>()
    for (c in secretLeft) {
        var counter = secretLetterMap.getOrDefault(c, 0)
        counter++
        secretLetterMap[c] = counter
    }

    val guessLetterMap = HashMap<Char, Int>()
    for (c in guessLeft) {
        var counter = guessLetterMap.getOrDefault(c, 0)
        counter++
        guessLetterMap[c] = counter
    }

    var wrongPosition = 0
    guessLetterMap.entries.forEach { g ->
        wrongPosition += secretLetterMap.getOrDefault(g.key, 0).coerceAtMost(g.value)
    }
    return Evaluation(rightPosition, wrongPosition)
}
