package com.demo.task.submissiontwodicoding.utils

import android.content.Context
import android.util.Log
import com.demo.task.submissiontwodicoding.models.FileModel
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStreamWriter

internal object FileHelper {
    private val TAG = FileHelper::class.java.simpleName

    fun writeToFile(fileModel: FileModel, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                context.openFileOutput(
                    fileModel.filename.toString(),
                    Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.d(TAG, "FailedWriteFile: ${e.message.toString()}")
        }
    }

    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel()

        try {
            val inputStream = context.openFileInput(filename)
            if (inputStream != null) {
                val receiveString = inputStream.bufferedReader()
                    .use(BufferedReader::readText)
                inputStream.close()

                fileModel.data = receiveString
                fileModel.filename = filename
            }
        } catch (e: FileNotFoundException) {
            Log.d(TAG, "File not found: ${e.message.toString()}")
        } catch (e: IOException) {
            Log.d(TAG, "Cannot read file: ${e.message.toString()}")
        }

        return fileModel
    }
}