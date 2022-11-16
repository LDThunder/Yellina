package com.eastail.yellina.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        /* Основное */
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Yellina"

        /* Локация */
        private const val TABLE_LOCATION = "Location"
        private const val COLUMN_LOCATION_ID = "_id"
        private const val COLUMN_LOCATION_TITLE = "title"
        private const val COLUMN_LOCATION_IMAGE = "image"
        private const val COLUMN_LOCATION_LOCATED = "isLocated"
        private const val COLUMN_LOCATION_TESTED = "isTested"
        private const val COLUMN_LOCATION_IMAGEBIG = "imageBig"

        /* Магазин */
        private const val TABLE_STORE = "Store"
        private const val COLUMN_STORE_ID = "_id"
        private const val COLUMN_STORE_TITLE = "title"
        private const val COLUMN_STORE_DESCRIPTION = "description"
        private const val COLUMN_STORE_COST = "cost"
        private const val COLUMN_STORE_IMAGE = "image"
        private const val COLUMN_STORE_IMAGECOL = "imageColor"
        private const val COLUMN_STORE_BOUGHT = "isBought"
        private const val COLUMN_STORE_ABLE = "isAble"
        private const val COLUMN_STORE_IDLEUP = "idleUp"

        /* Хелпер */
        private const val TABLE_HELPER = "Helper"
        private const val COLUMN_HELPER_ID = "_id"
        private const val COLUMN_HELPER_MUSIC = "music"
        private const val COLUMN_HELPER_SOUND = "sound"
        private const val COLUMN_HELPER_CHEATS = "cheats"
        private const val COLUMN_HELPER_COINS = "coins"
    }

    override fun onCreate(db: SQLiteDatabase) {
        /* Локации */
        val CREATE_LOCATION_TABLE = ("CREATE TABLE " + TABLE_LOCATION + "("
                + COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LOCATION_TITLE + " TEXT,"
                + COLUMN_LOCATION_IMAGE + " INTEGER,"
                + COLUMN_LOCATION_LOCATED + " INTEGER,"
                + COLUMN_LOCATION_TESTED + " INTEGER,"
                + COLUMN_LOCATION_IMAGEBIG + " INTEGER" + ")")

        /* Магазин */
        val CREATE_STORE_TABLE = ("CREATE TABLE " + TABLE_STORE + "("
                + COLUMN_STORE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_STORE_TITLE + " TEXT,"
                + COLUMN_STORE_DESCRIPTION + " TEXT,"
                + COLUMN_STORE_COST + " INTEGER,"
                + COLUMN_STORE_IMAGE + " INTEGER,"
                + COLUMN_STORE_IMAGECOL + " INTEGER,"
                + COLUMN_STORE_BOUGHT + " INTEGER,"
                + COLUMN_STORE_ABLE + " INTEGER,"
                + COLUMN_STORE_IDLEUP + " INTEGER" + ")")

        /* Хелпер */
        val CREATE_HELPER_TABLE = ("CREATE TABLE " + TABLE_HELPER + "("
                + COLUMN_HELPER_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_HELPER_MUSIC + " INTEGER,"
                + COLUMN_HELPER_SOUND + " INTEGER,"
                + COLUMN_HELPER_CHEATS + " INTEGER,"
                + COLUMN_HELPER_COINS + " INTEGER" + ")")

        /* Запрос */
        db.execSQL(CREATE_STORE_TABLE)
        db.execSQL(CREATE_LOCATION_TABLE)
        db.execSQL(CREATE_HELPER_TABLE)
    }

    /* Обновление */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STORE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HELPER")
        onCreate(db) // Создает все заново
    }

    /* Добавители */
    fun addLocation(location: Location) {
        val db = this.writableDatabase // Создание базы данных для записи
        val values = ContentValues() // Пустой набор значений
        values.put(COLUMN_LOCATION_ID, location.id) // В колонку вписать значение
        values.put(COLUMN_LOCATION_TITLE, location.title) // В колонку вписать значение
        values.put(COLUMN_LOCATION_IMAGE, location.image) // В колонку вписать значение
        values.put(COLUMN_LOCATION_LOCATED, location.isLocated) // В колонку вписать значение
        values.put(COLUMN_LOCATION_TESTED, location.isTested)
        values.put(COLUMN_LOCATION_IMAGEBIG, location.imageBig)// В колонку вписать значение
        db.insert(TABLE_LOCATION, null, values)
        db.close() // Обязательно закрыть базу данных
    }

    fun addStore(store: Store) {
        val db = this.writableDatabase // Создание базы данных для записи
        val values = ContentValues() // Пустой набор значений
        values.put(COLUMN_STORE_ID, store.id) // В колонку вписать значение
        values.put(COLUMN_STORE_TITLE, store.title)
        values.put(COLUMN_STORE_DESCRIPTION, store.description)
        values.put(COLUMN_STORE_COST, store.cost)
        values.put(COLUMN_STORE_IMAGE, store.image)
        values.put(COLUMN_STORE_IMAGECOL, store.imageColor)
        values.put(COLUMN_STORE_BOUGHT, store.isBought)
        values.put(COLUMN_STORE_ABLE, store.isAble)
        values.put(COLUMN_STORE_IDLEUP, store.idleUp)
        db.insert(TABLE_STORE, null, values)
        db.close() // Обязательно закрыть базу данных
    }

    fun addHelper(helper: Helper) {
        val db = this.writableDatabase // Создание базы данных для записи
        val values = ContentValues() // Пустой набор значений
        values.put(COLUMN_HELPER_ID, helper.id) // В колонку вписать значение
        values.put(COLUMN_HELPER_MUSIC, helper.music) // В колонку вписать значение
        values.put(COLUMN_HELPER_SOUND, helper.sound) // В колонку вписать значение
        values.put(COLUMN_HELPER_CHEATS, helper.cheats) // В колонку вписать значение
        values.put(COLUMN_HELPER_COINS, helper.coins)
        db.insert(TABLE_HELPER, null, values)
        db.close() // Обязательно закрыть базу данных
    }

    /* Получатели */
    @SuppressLint("Range")
    fun getLocation(): List<Location> {
        val list = mutableListOf<Location>()
        val db = this.readableDatabase
        // Запрос для выборки из ДБ
        val cursor = db.rawQuery("SELECT * FROM $TABLE_LOCATION", null)
        // Перемещать курсор к следующей строчке
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_ID))
                    val title = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION_TITLE))
                    val image = cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_IMAGE))
                    val isLocated =
                        cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_LOCATED)) > 0
                    val isTested = cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_TESTED)) > 0
                    val imageBig = cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_IMAGEBIG))
                    val location = Location(id, title, image, isLocated, isTested, imageBig)
                    list.add(location)
                } while (cursor.moveToNext())
            }
        }
        // Обязательно закрыть курсор
        cursor.close()
        return list.toList()
    }

    @SuppressLint("Range")
    fun getStore(): List<Store> {
        val list = mutableListOf<Store>()
        val db = this.readableDatabase
        // Запрос для выборки из ДБ
        val cursor = db.rawQuery("SELECT * FROM $TABLE_STORE", null)
        // Перемещать курсор к следующей строчке
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_ID))
                    val title = cursor.getString(cursor.getColumnIndex(COLUMN_STORE_TITLE))
                    val description = cursor.getString(
                        cursor.getColumnIndex(
                            COLUMN_STORE_DESCRIPTION
                        )
                    )
                    val cost = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_COST))
                    val image = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_IMAGE))
                    val imageColor = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_IMAGECOL))
                    val isBought = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_BOUGHT))
                    val isAble = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_ABLE)) > 0
                    val idleUp = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_IDLEUP))
                    val store = Store(
                        id,
                        title,
                        description,
                        cost,
                        image,
                        imageColor,
                        isBought,
                        isAble,
                        idleUp
                    )
                    list.add(store)
                } while (cursor.moveToNext())
            }
        }
        // Обязательно закрыть курсор
        cursor.close()
        return list.toList()
    }

    @SuppressLint("Range")
    fun getHelper(): List<Helper> {

        /* Инициализация */
        val list = mutableListOf<Helper>()
        val db = this.readableDatabase

        /* Курсор */
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HELPER", null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(COLUMN_HELPER_ID))
                    val music = cursor.getInt(cursor.getColumnIndex(COLUMN_HELPER_MUSIC)) > 0
                    val sound = cursor.getInt(cursor.getColumnIndex(COLUMN_HELPER_SOUND)) > 0
                    val cheats = cursor.getInt(cursor.getColumnIndex(COLUMN_HELPER_CHEATS)) > 0
                    val coins = cursor.getInt(cursor.getColumnIndex(COLUMN_HELPER_COINS))
                    val helper = Helper(id, music, sound, cheats, coins)
                    list.add(helper)
                } while (cursor.moveToNext())
            }
        }

        /* Закрытие курсора */
        cursor.close()
        return list.toList()
    }

    /* Обновляторы */
    fun updateLocation(id: Long, isLocated: Int, isTested: Int): Int {
        val cv = ContentValues()
        cv.put(COLUMN_LOCATION_LOCATED, isLocated)
        if (isTested != 322) {
            cv.put(COLUMN_LOCATION_TESTED, isTested)
        }
        val whereclause = "$COLUMN_LOCATION_ID=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_LOCATION, cv, whereclause, whereargs)
    }

    fun updateStore(id: Long, isBought: Int, isAble: Int): Int {
        val cv = ContentValues()
        cv.put(COLUMN_STORE_BOUGHT, isBought)
        cv.put(COLUMN_STORE_ABLE, isAble)
        val whereclause = "$COLUMN_STORE_ID=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_STORE, cv, whereclause, whereargs)
    }

    fun updateCost(id: Long, cost: Int): Int {
        val cv = ContentValues()
        cv.put(COLUMN_STORE_COST, cost)
        val whereclause = "$COLUMN_STORE_ID=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_STORE, cv, whereclause, whereargs)
    }

    fun updateHelper(id: Long, music: Int, sound: Int, cheats: Int, coins: Int): Int {
        val cv = ContentValues()
        cv.put(COLUMN_HELPER_MUSIC, music)
        cv.put(COLUMN_HELPER_SOUND, sound)
        cv.put(COLUMN_HELPER_CHEATS, cheats)
        cv.put(COLUMN_HELPER_COINS, coins)
        val whereclause = "$COLUMN_HELPER_ID=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_HELPER, cv, whereclause, whereargs)
    }

    fun updateCoins(id: Long, coins: Int): Int {
        val cv = ContentValues()
        cv.put(COLUMN_HELPER_COINS, coins)
        val whereclause = "$COLUMN_HELPER_ID=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_HELPER, cv, whereclause, whereargs)
    }
}