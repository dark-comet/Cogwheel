package xyz.darkcomet.cogwheel.models

import org.slf4j.LoggerFactory

data class ShardId(val id: Int, val shardCount: Int) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(ShardId::class.java)
        
        fun from(rawData: List<Int>?): ShardId? {
            if (rawData == null) {
                return null
            }
            
            if (rawData.size != 2) {
                LOGGER.warn("Malformed ShardId raw data array size: {} (expected 2)", rawData.size)
                return null
            }
            
            return ShardId(rawData[0], rawData[1])
        }
    }
}