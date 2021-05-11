package file

import java.io.File

class FileHandler {
    companion object {
        fun readFile(filename: String): List<Pair<Double, Double>> {
            val points = mutableListOf<Pair<Double, Double>>()

            File(filename).forEachLine {
                val coordinates = it.split(",")
                points.add(Pair(coordinates[0].toDouble(), coordinates[1].toDouble()))
            }

            return points
        }

        fun saveFile(file: File, points: List<Pair<Double, Double>>) {
            file.printWriter().use { out ->
                points.forEach {
                    out.println("${it.first},${it.second}")
                }
            }
        }
    }
}