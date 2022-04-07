package converter

fun main() {
    val units = listOf(
        listOf("m", "meter", "meters"),
        listOf("km", "kilometer", "kilometers"),
        listOf("cm", "centimeter", "centimeters"),
        listOf("mm", "millimeter", "millimeters"),
        listOf("mi", "mile", "miles"),
        listOf("yd", "yard", "yards"),
        listOf("ft", "foot", "feet"),
        listOf("in", "inch", "inches"))

    print("Enter a number and a measure of length: ")
    val input = readLine()!!.lowercase().split(" ")

    var unitCorrect = false
    for (i in units.indices) {
        if (units[i].contains(input[1])) {
            convertToMeters(input, units[i][1], units[i])
            unitCorrect = true
            break
        }
    }
    if (!unitCorrect) println("Wrong input. Unknown unit ${input[1]}")
}

fun convertToMeters(input: List<String>, unit: String, units: List<String>) {
    val number = input[0].toDouble()
    val unitsPlural = if (number == 1.0) units[1] else units[2]
    val meters: Double = when (unit) {
        "meter" -> number
        "kilometer" -> number * 1000
        "centimeter" -> number * 0.01
        "millimeter" -> number * 0.001
        "mile" -> number * 1609.35
        "yard" -> number * 0.9144
        "foot" -> number * 0.3048
        else -> number * 0.0254
    }
    val metersPlural = if (meters == 1.0) "" else "s"
    println("$number $unitsPlural " +
            "is $meters meter$metersPlural")
}