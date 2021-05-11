package frontend

import algorithms.Jarvis
import data.DataGenerator
import file.FileHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import tornadofx.View
import tornadofx.vbox

class JarvisView : View() {
    private val CANVAS_SIZE = 800.0
    private val OFFSET = 20.0
    private var MULTIPLIER: Double
    private var points = DataGenerator.generateData(50)
    private var hullPoints = Jarvis(points).convexHull()
    private var canvas = Canvas(CANVAS_SIZE, CANVAS_SIZE)
    private var graphicsContext = canvas.graphicsContext2D
    private var drawHull = false
    private var numberOfRandomPoints = 0

    init {
        MULTIPLIER = 700 / findMaximumPoint()
    }

    override val root = vbox {
        drawCanvas()
        children.add(canvas)
        children.add(object : Button() {
            init {
                text = "Draw"
                setOnAction {
                    graphicsContext.clearRect(0.0, 0.0, canvas.height, canvas.width)
                    drawCanvas()
                }
            }
        })
        children.add(object : ToggleButton() {
            init {
                text = "Draw hull"
                setOnAction {
                    drawHull = false
                }
            }
        })
        children.add(object : Button() {
            init {
                text = "Choose CSV file"
                setOnAction {
                    try {
                        points = FileHandler.readFile(FileChooser().showOpenDialog(primaryStage).absolutePath)
                        hullPoints = Jarvis(points).convexHull()
                        MULTIPLIER = 700 / findMaximumPoint()
                    }catch(e: NullPointerException){

                    }
                }
            }
        })
        children.add(object : Button() {
            init {
                text = "Random"
                setOnAction {
                    try {
                        points = DataGenerator.generateData(numberOfRandomPoints)
                        hullPoints = Jarvis(points).convexHull()
                        MULTIPLIER = 900 / findMaximumPoint()
                    } catch (e: IllegalArgumentException) {
                        val dialog = Stage()
                        dialog.initModality(Modality.APPLICATION_MODAL)
                        dialog.initOwner(primaryStage)
                        val dialogVbox = VBox(20.0)
                        dialogVbox.children.add(Text("Number of points must be greater than 0"))
                        dialogVbox.children.add(object : Button() {
                            init {
                                text = "Close"
                                setOnAction {
                                    dialog.close()
                                }
                            }
                        })
                        val dialogScene = Scene(dialogVbox, 300.0, 100.0)
                        dialog.scene = dialogScene
                        dialog.show()

                    }
                }
            }
        })
        children.add(object : TextField() {
            init {
                textProperty().addListener { observable, oldValue, newValue ->
                    run {
                        if (!newValue.matches(Regex("\\d{0,3}"))) {
                            textProperty().set(oldValue)
                        } else if (newValue.isBlank()) {
                            numberOfRandomPoints = 0
                        } else {
                            numberOfRandomPoints = newValue.toInt()
                        }
                    }
                }
            }
        })
        children.add(object : Button() {
            init {
                text = "Save"
                setOnAction {
                    FileHandler.saveFile(FileChooser().showSaveDialog(primaryStage), points)
                }
            }
        })
        children.add(object : Button() {
            init {
                text = "Save Convex Hull"
                setOnAction {
                    FileHandler.saveFile(FileChooser().showSaveDialog(primaryStage), hullPoints)
                }
            }
        })
        primaryStage.show()
    }

    private fun drawCanvas() {
        if (drawHull) {
            graphicsContext.stroke = Color.GREEN
            graphicsContext.lineWidth = 3.0
            var lastPoint = hullPoints.last()
            for (l in hullPoints) {
                graphicsContext.strokeLine(
                    l.first * MULTIPLIER + OFFSET,
                    l.second * MULTIPLIER + OFFSET,
                    lastPoint.first * MULTIPLIER + OFFSET,
                    lastPoint.second * MULTIPLIER + OFFSET
                )
                lastPoint = l
            }
        }

        graphicsContext.stroke = Color.BLUE
        graphicsContext.lineWidth = 5.0
        for (p in points) {
            graphicsContext.strokeLine(
                p.first * MULTIPLIER + OFFSET,
                p.second * MULTIPLIER + OFFSET,
                p.first * MULTIPLIER + OFFSET,
                p.second * MULTIPLIER + OFFSET
            )
        }

        if (drawHull) {
            graphicsContext.stroke = Color.RED
            graphicsContext.lineWidth = 5.0
            for (p in hullPoints) {
                graphicsContext.strokeLine(
                    p.first * MULTIPLIER + OFFSET,
                    p.second * MULTIPLIER + OFFSET,
                    p.first * MULTIPLIER + OFFSET,
                    p.second * MULTIPLIER + OFFSET
                )
            }
        }
    }

    private fun findMaximumPoint(): Double {
        var max = 0.0
        for (p in points) {
            if (p.first > max) max = p.first
            if (p.second > max) max = p.second
        }
        return max
    }
}