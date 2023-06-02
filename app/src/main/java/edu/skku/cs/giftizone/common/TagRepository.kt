package edu.skku.cs.giftizone.common

import android.content.ContentValues
import android.content.Context

class TagRepository(context: Context) {

    private val dbManager = DatabaseManager(context)

    fun saveTag(tag: String) {
        val db = dbManager.writableDatabase

        val values = ContentValues()
        values.put(DatabaseManager.COLUMN_TAG_NAME, tag)

        db.insert(DatabaseManager.TABLE_TAG, null, values)
        db.close()
    }

    fun getAllTags(): ArrayList<String> {
        val tags = ArrayList<String>()
        val db = dbManager.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseManager.TABLE_TAG}", null)

        if (cursor.moveToFirst()) {
            do {
                val tag = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TAG_NAME))
                tags.add(tag)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return tags
    }

    fun deleteTag(tag: String) {
        val db = dbManager.writableDatabase
        db.delete(DatabaseManager.TABLE_TAG, "${DatabaseManager.COLUMN_TAG_NAME} = ?", arrayOf(tag))
        db.close()
    }

}