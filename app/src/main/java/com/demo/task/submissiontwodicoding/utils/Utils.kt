package com.demo.task.submissiontwodicoding.utils

enum class LoadingStatus {
    LOADING,
    ERROR,
    NO_CONNECTION,
    SUCCESS
}

fun definePlurals(value: Double?): Int {
    var data = 1
    value?.let {
        data = if (value > 1f) 2 else 1
    }
    return data
}