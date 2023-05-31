package edu.skku.cs.giftizone.dataClass

import java.time.LocalDateTime

data class Gifticon(
    val photoPath: String,
    val barcode: String,
    val tag: String,
    val productProvider: String,
    val productName: String,
    val createAt: LocalDateTime = LocalDateTime.now(),
)
