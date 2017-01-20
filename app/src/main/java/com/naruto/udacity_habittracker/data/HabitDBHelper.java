package com.naruto.udacity_habittracker.data;

/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2017-01-16
 * Time: 22:34
 * Desc: UdaCity_HabitTracker
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HabitDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "habittracker.db";
	private static final int DATABASE_VERSION = 1;
	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + "(" + HabitContract.HabitEntry._ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + HabitContract.HabitEntry.COLUMN_DATE + " TEXT NOT NULL, "
					+ HabitContract.HabitEntry.COLUMN_START + " TEXT NOT NULL, " + HabitContract.HabitEntry.COLUMN_END + " TEXT NOT NULL, "
					+ HabitContract.HabitEntry.COLUMN_DURATION + " TEXT NOT NULL);";
	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文环境
	 */
	public HabitDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
}