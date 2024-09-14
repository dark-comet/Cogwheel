package xyz.darkcomet.cogwheel.network

interface CancellationToken {
    fun isCanceled(): Boolean
}