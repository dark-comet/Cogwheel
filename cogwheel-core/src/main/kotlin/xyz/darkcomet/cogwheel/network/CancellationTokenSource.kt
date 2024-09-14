package xyz.darkcomet.cogwheel.network

import java.util.concurrent.atomic.AtomicBoolean

open class CancellationTokenSource : CancellationToken {
    
    protected val canceled = AtomicBoolean(false)
    
    override fun isCanceled(): Boolean {
        return canceled.get()
    }
    
    fun cancel() {
        canceled.set(true)
    }
    
}