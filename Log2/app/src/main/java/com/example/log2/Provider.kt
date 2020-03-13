package com.example.log2

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import org.jetbrains.anko.db.insert

class Provider() : ContentProvider() {
    var db: SQLiteDatabase? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return db!!.delete("User", selection, selectionArgs)
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val insert = db!!.insert("User", null, values)
        return null
    }

    override fun onCreate(): Boolean {
        var helper: MySqliteDBHepler = MySqliteDBHepler(context)
        db = helper.readableDatabase
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return db?.query("User", projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return db!!.update("User", values, selection, selectionArgs)
    }
}
