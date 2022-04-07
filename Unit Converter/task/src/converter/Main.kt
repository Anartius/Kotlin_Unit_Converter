package converter

fun main() {
    val units = listOf("km", "kilometer", "kilometers")

    print("Enter a number and a measure: ")
    val input = readLine()!!.lowercase().split(" ")

    if (units.contains(input[1])) {
        convertKmToM(input[0].toInt())
    } else println("Wrong input")
}

fun convertKmToM(kilometers: Int) {
    val kmWord = if (kilometers == 1) "kilometer" else "kilometers"
    val meters = kilometers * 1000
    val mWord = if (meters == 1) "meter" else "meters"
    println("$kilometers $kmWord is $meters $mWord")
}