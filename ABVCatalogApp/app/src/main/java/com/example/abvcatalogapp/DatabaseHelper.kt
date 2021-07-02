package com.example.abvcatalogapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import org.threeten.bp.OffsetDateTime

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                "CREATE TABLE $TABLE_NAME " +
                        "($COLUMN_ID INTEGER PRIMARY KEY, " +
                            "$COLUMN_NAME TEXT, " +
                            "$COLUMN_ABV FLOAT, " +
                            "$COLUMN_CAT TEXT, " +
                            "$COLUMN_STYLE FLOAT, " +
                            "$COLUMN_IBU FLOAT, " +
                            "$COLUMN_SRM FLOAT, " +
                            "$COLUMN_UPC INT, " +
                            "$COLUMN_DESCRIPT TEXT )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertRow(beer: Beer) {
        val values = ContentValues()
        values.put(COLUMN_NAME, beer.name)
        values.put(COLUMN_CAT, beer.cat)
        values.put(COLUMN_STYLE, beer.style)
        values.put(COLUMN_ABV, beer.abv)
        values.put(COLUMN_IBU, beer.ibu)
        values.put(COLUMN_SRM, beer.srm)
        values.put(COLUMN_UPC, beer.upc)
        values.put(COLUMN_DESCRIPT, beer.descript)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateRow(row_id: String,
                  name: String,
                  age:String,
                  email: String,
                  cat: Int,
                  style: Int,
                  abv: Float,
                  ibu: Float,
                  srm: Float,
                  upc: Int,
                  filepath: String,
                  descript: String,
                  add_user: Int,
                  last_mod: OffsetDateTime
    ) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_CAT, cat)
        values.put(COLUMN_STYLE, style)
        values.put(COLUMN_ABV, abv)
        values.put(COLUMN_IBU, ibu)
        values.put(COLUMN_SRM, srm)
        values.put(COLUMN_UPC, upc)
        values.put(COLUMN_FILEPATH, filepath)
        values.put(COLUMN_DESCRIPT, descript)
        values.put(COLUMN_ADD_USER, add_user)

        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun deleteRow(row_id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun getAllRow(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "jason.db"
        const val TABLE_NAME = "beers"

        const val COLUMN_ID = "brewery_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_CAT = "cat_id"
        const val COLUMN_STYLE = "style_id"

        const val COLUMN_ABV = "abv"
        const val COLUMN_IBU = "ibu"
        const val COLUMN_SRM = "srm"

        const val COLUMN_UPC = "upc"
        const val COLUMN_FILEPATH = "filepath"
        const val COLUMN_DESCRIPT = "descript"

        const val COLUMN_ADD_USER = "add_user"
        const val COLUMN_LAST_MOD = "last_mod"

    }
}