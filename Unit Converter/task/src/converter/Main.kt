package converter

enum class Unit(
    val type: String, val ratio: Double, val short: String,
    val single: String, val plural: String) {

    WRONG("wrong", 1.0,"???", "???", "???"),
    METER("Length",1.0, "m", "meter", "meters"),
    KILOMETER("Length", 1000.0, "km", "kilometer", "kilometers"),
    CENTIMETER("Length", 0.01, "cm", "centimeter", "centimeters"),
    MILLIMETER("Length", 0.001, "mm", "millimeter", "millimeters"),
    MILE("Length", 1609.35, "mi", "mile", "miles"),
    YARD("Length", 0.9144, "yd", "yard", "yards"),
    FOOT("Length", 0.3048, "ft", "foot", "feet"),
    INCH("Length", 0.0254, "in", "inch", "inches"),
    GRAM("Weight", 1.0, "g", "gram", "grams"),
    KILOGRAM("Weight",1000.0, "kg", "kilogram", "kilograms"),
    MILLIGRAM("Weight", 0.001, "mg", "milligram", "milligrams"),
    POUND("Weight", 453.592, "lb", "pound", "pounds"),
    OUNCE("Weight", 28.3495, "oz", "ounce", "ounces"),
    CELSIUS("temperature", 1.0, "c",
        "degree Celsius", "degrees Celsius"),
    FAHRENHEIT("temperature", 1.0, "f",
        "degree Fahrenheit", "degrees Fahrenheit"),
    KELVIN("temperature", 1.0, "k", "kelvin", "kelvins")
}


fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val userInput = readLine()!!.lowercase()
        if (userInput == "exit") return

        val input = parseInput(userInput)

        val inputValue = try {
            if (input.size < 4) {
                throw NumberFormatException()
            }
            input[0].toDouble()
        } catch (e: NumberFormatException) {
            println("Parse error\n ")
            continue
        }

        var fromUnit = Unit.WRONG
        var toUnit = Unit.WRONG

        for (i in Unit.values()) {
            if (input[1] in listOf(i.short, i.single, i.plural)) {
                fromUnit = i
            }
            if (input[3] in listOf(i.short, i.single, i.plural)) {
                toUnit = i
            }
        }

        if (fromUnit.type != "wrong"
            && toUnit.type != "wrong" && fromUnit.type == toUnit.type) {

            if (inputValue < 0 && fromUnit.type != "temperature") {
                println("${fromUnit.type} shouldn't be negative\n")
                continue
            }

            val result: Double = if (toUnit.type == "temperature") {
                convertTemperature(inputValue, fromUnit, toUnit)
            } else input[0].toDouble() * fromUnit.ratio / toUnit.ratio

            println("$inputValue " +
                    (if (inputValue == 1.0) fromUnit.single else fromUnit.plural) +
                    " is $result ${if (result == 1.0) toUnit.single else toUnit.plural}\n")

        } else {
            println("Conversion from ${fromUnit.plural} to ${toUnit.plural} is impossible\n")
        }
    }
}

fun parseInput(input: String): List<String> {

    var isAdded = false
    val result = mutableListOf<String>()
    val words = input.replace("dc", "c")
        .replace("df", "f")
        .split(" ")

    for (i in words.indices) {
        if (!isAdded) {
            if (words[i] == "degree" || words[i] == "degrees") {

                result.add(words[i] + " " +
                        words[i + 1].first().uppercase() + words[i + 1].drop(1))

                isAdded = true
            } else result.add(words[i])
        } else {
            isAdded = false
        }
    }

    for (i in result.indices) {
        if (result[i] == "celsius") result[i] = "c"
        if (result[i] == "fahrenheit") result[i] = "f"
    }
    return result
}


fun convertTemperature(value: Double, fromUnit: Unit, toUnit: Unit): Double {

    if (fromUnit.short == toUnit.short) return value

    return when (fromUnit.short) {
        "c" -> {
            if (toUnit.short == "k") { value + 273.15 } else { value * 9 / 5 + 32 }
        }
        "k" -> {
            if (toUnit.short == "c") { value - 273.15 } else { value * 9 / 5 - 459.67 }
        }
        else -> {
            if (toUnit.short == "c") { (value - 32) * 5 / 9 } else { (value + 459.67) * 5 / 9 }
        }
    }
}