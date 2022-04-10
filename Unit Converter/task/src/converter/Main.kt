package converter

fun main() {

    val units = listOf(
        listOf(
            listOf("m", "meter", "meters"),
            listOf("km", "kilometer", "kilometers"),
            listOf("cm", "centimeter", "centimeters"),
            listOf("mm", "millimeter", "millimeters"),
            listOf("mi", "mile", "miles"),
            listOf("yd", "yard", "yards"),
            listOf("ft", "foot", "feet"),
            listOf("in", "inch", "inches")),
        listOf(
            listOf("g", "gram", "grams"),
            listOf("kg", "kilogram", "kilograms"),
            listOf("mg", "milligram", "milligrams"),
            listOf("lb", "pound", "pounds"),
            listOf("oz", "ounce", "ounces"))
    )
    val unitType = mutableListOf<String>()
    var result: Double

    while (true) {
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!.lowercase().split(" ")
        if (input.first() == "exit") return

        try {
            unitType.addAll(defineInputUnits(input, units))
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input")
            continue
        }

        if (conversionIsPossible(unitType, units)) {
            result = if (unitType[0] == "length") {
                val meters = convertToMeters(input, units[0], unitType)
                convertFromMeters(meters, units[0], unitType)
            } else {
                val grams = convertToGrams(input, units[1], unitType)
                convertFromGrams(grams, units[1], unitType)
            }
            printResult(input[0].toDouble(), result, units, unitType)
        }

        unitType.clear()
    }

}

fun defineInputUnits(
    input: List<String>,
    units: List<List<List<String>>>): MutableList<String> {


    val unitType = mutableListOf("unknown", "unknown", "", "")
    var isLength = false
    var isWeight = false

    var unitPosition = 1
    for (j in 0..1) {
        for (i in units[0].indices) {
            if (units[0][i].contains(input[unitPosition])) {
                unitType[j] = "length"
                unitType[j + 2] = i.toString()
                isLength = true
                break
            }
        }
        if (!isLength) {
            for (i in units[1].indices) {
                if (units[1][i].contains(input[unitPosition])) {
                    unitType[j] = "weight"
                    unitType[j + 2] = i.toString()
                    isWeight = true
                    break
                }
            }
        }

        if (!(isLength || isWeight)) {
            unitType[j] = "unknown"
        }

        isWeight = false
        isLength = false
        unitPosition += 2
    }
    return unitType
}


fun conversionIsPossible(
    unitType: MutableList<String>,
    units: List<List<List<String>>>): Boolean {

    if (unitType[0] == "unknown" || unitType[1] == "unknown") {

        val firstUnit = if (unitType[0] == "unknown") {
            "???"
        } else {
            if (unitType[0] == "length") {
                units[0][unitType[2].toInt()][2]
            } else {
                units[1][unitType[2].toInt()][2]
            }
        }

        val secondUnit = if (unitType[1] == "unknown") {
            "???"
        } else {
            if (unitType[1] == "length") {
                units[0][unitType[3].toInt()][2]
            } else {
                units[1][unitType[3].toInt()][2]
            }
        }
        println("Conversion from $firstUnit to $secondUnit is impossible")
        return false
    }

    if (unitType[1] != "unknown" && (unitType[0] != unitType[1])) {
        val from = if (unitType[0] == "length") {
            units[0][unitType[2].toInt()][2]
        } else units[1][unitType[2].toInt()][2]

        val to = if (unitType[1] == "length") {
            units[0][unitType[3].toInt()][2]
        } else units[1][unitType[3].toInt()][2]

        println("Conversion from $from to $to is impossible")
        return false
    }
    return true
}


fun convertToMeters(input: List<String>,
                    lengthUnits: List<List<String>>,
                    unitType: MutableList<String>): Double {

    val value = input[0].toDouble()

    val unit = lengthUnits[unitType[2].toInt()][1]
    val result: Double = when (unit) {
        "meter" -> value
        "kilometer" -> value * 1000
        "centimeter" -> value * 0.01
        "millimeter" -> value * 0.001
        "mile" -> value * 1609.35
        "yard" -> value * 0.9144
        "foot" -> value * 0.3048
        else -> value * 0.0254
    }

    return result
}

fun convertFromMeters(
    length: Double,
    lengthUnits: List<List<String>>,
    unitType: MutableList<String>): Double {

    val outputUnit = lengthUnits[unitType[3].toInt()][1]
    val result: Double = when (outputUnit) {
        "meter" -> length
        "kilometer" -> length / 1000
        "centimeter" -> length / 0.01
        "millimeter" -> length / 0.001
        "mile" -> length / 1609.35
        "yard" -> length / 0.9144
        "foot" -> length / 0.3048
        else -> length / 0.0254
    }

    return result
}


fun convertToGrams(
    input: List<String>,
    weightUnits: List<List<String>>,
    unitType: MutableList<String>): Double {

    val value = input[0].toDouble()

    val unit = weightUnits[unitType[2].toInt()][1]
    val result: Double = when (unit) {
        "gram" -> value
        "kilogram" -> value * 1000
        "milligram" -> value * 0.001
        "pound" -> value * 453.592
        else -> value * 28.3495
    }

    return result
}


fun convertFromGrams(
    weight: Double,
    weightUnits: List<List<String>>,
    unitType: MutableList<String>): Double {

    val outputUnit = weightUnits[unitType[3].toInt()][1]
    val result: Double = when (outputUnit) {
        "gram" -> weight
        "kilogram" -> weight / 1000
        "milligram" -> weight / 0.001
        "pound" -> weight / 453.592
        else -> weight / 28.3495
    }

    return result
}


fun printResult(
    inputValue: Double,
    result: Double,
    units: List<List<List<String>>>,
    unitType: MutableList<String>) {

    val inputUnits =
        if (inputValue == 1.0) {
            if (unitType[0] == "length") {
                units[0][unitType[2].toInt()][1]
            } else units[1][unitType[2].toInt()][1]
        } else {
            if (unitType[0] == "length") {
                units[0][unitType[2].toInt()][2]
            } else units[1][unitType[2].toInt()][2]
        }

    val resultUnits =
        if (result == 1.0) {
            if (unitType[1] == "length") {
                units[0][unitType[3].toInt()][1]
            } else units[1][unitType[3].toInt()][1]
        } else {
            if (unitType[1] == "length") {
                units[0][unitType[3].toInt()][2]
            } else units[1][unitType[3].toInt()][2]
        }

    println("$inputValue $inputUnits " +
            "is $result $resultUnits")

}