package com.sliide.test.core


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    class Loading<T>(data: T? = null) : Resource<T>(data)

    override fun equals(other: Any?): Boolean {
        if (other is Resource<*>) {
            return this.data == other.data && this.message == other.message
        }
        return false
    }
}