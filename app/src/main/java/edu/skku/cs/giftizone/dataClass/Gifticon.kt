package edu.skku.cs.giftizone.dataClass

import java.time.LocalDate
import java.util.UUID

data class Gifticon(
    val imagePath: String,
    val barcode: String,
    val tag: String,
    val provider: String,
    val content: String,
    val expiredAt: LocalDate,
    val createAt: LocalDate = LocalDate.now(),
    val id: String = UUID.randomUUID().toString(),
)
