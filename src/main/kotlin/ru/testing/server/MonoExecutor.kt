package ru.testing.server

import ru.testing.queue.TestingQueue
import ru.testing.utils.TestingConfiguration
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class MonoExecutor {

    private var isExited = false

    fun run() {
        while (!isExited) {
            val newTask = TestingQueue.get() ?: continue
            val rootPath = TestingConfiguration.DEPLOY_DIRECTORY
            val templateDirectory = Files.createTempDirectory(rootPath, "temp")
            val solveFile = templateDirectory.resolve("${newTask.taskFile.title}")
            solveFile.toFile().printWriter(StandardCharsets.UTF_8).use { out ->
                out.println(newTask.taskFile.listing)
            }
            val results = newTask.taskFile.fileType.runSolveFile(solveFile, "")
            println("Code: ${results.code}")
            println("Error: ${results.error}")
            println("Output: ${results.output}")
        }
    }

    fun stop() {
        isExited = true
    }
}