package edu.skku.cs.giftizone.common

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Giftizone"
        const val TABLE_GIFTICON = "Gifticon"
        const val COLUMN_ID = "id"
        const val COLUMN_IMAGE_PATH = "imagePath"
        const val COLUMN_BARCODE = "barcode"
        const val COLUMN_TAG = "tag"
        const val COLUMN_PROVIDER = "provider"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_EXPIRED_AT = "expiredAt"
        const val COLUMN_CREATED_AT = "createdAt"

        const val TABLE_TAG = "Tag"
        const val COLUMN_TAG_NAME = "tagName"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_GIFTICON_TABLE = ("CREATE TABLE " + TABLE_GIFTICON + "("
                + COLUMN_ID + " TEXT PRIMARY KEY," + COLUMN_IMAGE_PATH + " TEXT,"
                + COLUMN_BARCODE + " TEXT," + COLUMN_TAG + " TEXT,"
                + COLUMN_PROVIDER + " TEXT," + COLUMN_CONTENT + " TEXT,"
                + COLUMN_EXPIRED_AT + " TEXT," + COLUMN_CREATED_AT + " TEXT" + ")")
        db.execSQL(CREATE_GIFTICON_TABLE)

        val CREATE_TAG_TABLE = "CREATE TABLE $TABLE_TAG (" +
                "$COLUMN_TAG_NAME TEXT PRIMARY KEY)"
        db.execSQL(CREATE_TAG_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GIFTICON")
        onCreate(db)

        db.execSQL("DROP TABLE IF EXISTS $TABLE_TAG")
        onCreate(db)
    }
}