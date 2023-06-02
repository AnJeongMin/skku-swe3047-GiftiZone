package edu.skku.cs.giftizone.common

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.time.LocalDate
import java.util.*

class GifticonRepository(context: Context) {

    private val dbManager = DatabaseManager(context)

    fun saveGifticon(gifticon: Gifticon) {
        val db = dbManager.writableDatabase

        val values = ContentValues()
        values.put(DatabaseManager.COLUMN_ID, gifticon.id)
        values.put(DatabaseManager.COLUMN_IMAGE_PATH, gifticon.imagePath)
        values.put(DatabaseManager.COLUMN_BARCODE, gifticon.barcode)
        values.put(DatabaseManager.COLUMN_TAG, gifticon.tag)
        values.put(DatabaseManager.COLUMN_PROVIDER, gifticon.provider)
        values.put(DatabaseManager.COLUMN_CONTENT, gifticon.content)
        values.put(DatabaseManager.COLUMN_EXPIRED_AT, gifticon.expiredAt.toString())
        values.put(DatabaseManager.COLUMN_CREATED_AT, gifticon.createAt.toString())

        db.insert(DatabaseManager.TABLE_GIFTICON, null, values)
        db.close()
    }

    fun getAllGifticons(): ArrayList<Gifticon> {
        val gifticons = ArrayList<Gifticon>()
        val db = dbManager.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseManager.TABLE_GIFTICON}", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_ID))
                val imagePath = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_IMAGE_PATH))
                val barcode = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_BARCODE))
                val tag = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TAG))
                val provider = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_PROVIDER))
                val content = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CONTENT))
                val expiredAt = LocalDate.parse(cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_EXPIRED_AT)))
                val createAt = LocalDate.parse(cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CREATED_AT)))
                val gifticon = Gifticon(imagePath, barcode, tag, provider, content, expiredAt, createAt, id)
                gifticons.add(gifticon)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return gifticons
    }

    fun deleteGifticon(gifticonId: String) {
        val db = dbManager.writableDatabase
        db.delete(DatabaseManager.TABLE_GIFTICON, "${DatabaseManager.COLUMN_ID} = ?", arrayOf(gifticonId))
        db.close()
    }
}