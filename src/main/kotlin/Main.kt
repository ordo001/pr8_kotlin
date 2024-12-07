import kotlin.random.Random

fun main() {
    println("Введите количество философов:")
    val philosopherCount = readLine()!!.toInt()

    if (philosopherCount < 2) {
        println("Количество философов должно быть не менее двух.")
        return
    }

    val forks = BooleanArray(philosopherCount) { false } //false - вилка свободна
    val philosophers = MutableList(philosopherCount) { null as String? }

    var currentIndex = Random.nextInt(philosopherCount)
    val remainingPhilosophers = (0 .. philosopherCount - 1).toMutableList()

    while (remainingPhilosophers.isNotEmpty()) {
        val philosopherId = remainingPhilosophers[currentIndex]
        val leftFork = philosopherId
        val rightFork = (philosopherId + 1) % philosopherCount

        val side = TakeFork(forks, leftFork, rightFork)
        if (side != null) {
            philosophers[philosopherId] = side //сторона
            remainingPhilosophers.remove(philosopherId)
        } else {
            philosophers[philosopherId] = null //0
            remainingPhilosophers.remove(philosopherId)
        }

        if (remainingPhilosophers.isEmpty())
            currentIndex = 0;
        else
            currentIndex = (currentIndex + 1) % remainingPhilosophers.size;
    }

    var num:Int = 1;
    for(i in philosophers){
        if(i != null) {
            println("Философ $num обедает, взяв вилку $i")
        } else {
            println("Философ $num размышляет")
        }
        num++
    }
}

fun TakeFork(forks: BooleanArray, leftFork: Int, rightFork: Int): String? {
    val attemptOrder = if (Random.nextBoolean()) listOf("слева" to leftFork, "справа" to rightFork)
    else listOf("справа" to rightFork, "слева" to leftFork)

    for ((position, fork) in attemptOrder) {
        if (!forks[fork]) {
            forks[fork] = true
            return position //  взял
        }
    }
    return null
}
