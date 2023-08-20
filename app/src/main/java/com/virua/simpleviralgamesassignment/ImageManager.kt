package com.virua.simpleviralgamesassignment




import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.LruCache

class  ImageManager(private val context: Context) {

    companion object {
        var keyCounter=0
        fun init(context: Context) {
            ImageManager.context=context
        }
        private const val MAX_LINKS = 20
//        private const val PREF_NAME = "ImageLinkPrefs"
        private const val DATABASE_NAME = "ImageLinks.db"
        private const val DATABASE_VERSION = 1


//        private const val KEY_LINK_PREFIX = "image_link_"
        var context: Context?=null
        var imageManager: ImageManager?=null
        fun getIntance():ImageManager {
            if(imageManager==null) {
                imageManager=ImageManager(context!!)

            }
            return imageManager!!
        }
    }

    private val linkCache = LruCache<Int, String>(MAX_LINKS)
    private val dbHelper = DatabaseHelper(context)

    init {
        loadCacheFromDatabase()
    }

    private fun generateKey(): Int {
        return keyCounter++
    }

    fun addImageLink(imageLink: String) {
        // Ensure the cache doesn't exceed the maximum size :)
        if (linkCache.size() >= MAX_LINKS) {
            // Remove the oldest entry to make space for the new link
            val oldestKey = linkCache.snapshot().keys.firstOrNull()
            oldestKey?.let {
                linkCache.remove(it)
                deleteLinkFromDatabase(it)
            }
        }

        // Add the new link to the cache and the database
        val newKey = generateKey()
        linkCache.put(newKey, imageLink)
        insertLinkIntoDatabase(newKey, imageLink)
    }

    fun getImageLinks(): List<String> {
        return linkCache.snapshot().values.toList()
    }

    fun clearImageLinks() {
        linkCache.evictAll()
        clearDatabase()
    }

    @SuppressLint("Range")
    private fun loadCacheFromDatabase() {
        val db = dbHelper.readableDatabase
        val cursor = db.query("image_links", arrayOf("link_id", "link"),
            null, null, null, null, "link_id DESC", MAX_LINKS.toString())

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val link = cursor.getString(cursor.getColumnIndex("link"))
            linkCache.put(cursor.getInt(cursor.getColumnIndex("link_id")), link)
            cursor.moveToNext()
        }

        cursor.close()
        db.close()
    }

    private fun insertLinkIntoDatabase(key: Int, link: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("link_id", key)
            put("link", link)
        }
        db.insert("image_links", null, values)
        db.close()
    }

    private fun deleteLinkFromDatabase(key: Int) {
        val db = dbHelper.writableDatabase
        db.delete("image_links", "link_id = ?", arrayOf(key.toString()))
        db.close()
    }

    private fun clearDatabase() {
        val db = dbHelper.writableDatabase
        db.delete("image_links", null, null)
        db.close()
    }

    private class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE image_links (" +
                        "link_id INTEGER PRIMARY KEY," +
                        "link TEXT)"
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS image_links")
            onCreate(db)
        }
    }
}

