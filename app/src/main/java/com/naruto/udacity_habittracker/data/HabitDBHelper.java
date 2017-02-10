package com.naruto.udacity_habittracker.data;

/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2017-01-16
 * Time: 22:34
 * Desc: UdaCity_HabitTracker
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

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

	}

	/**
	 * 插入一条数据
	 */
	public static void insert(long startTime, ContentValues values, HabitDBHelper DBHelper) {
		Date rightNow = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm aaa");
		long endTime = rightNow.getTime();
		long difference = endTime - startTime;

		// 以string类型将数据插入到数据库中
		String currentDate = dateFormat.format(rightNow);
		String startTimeString = timeFormat.format(startTime);
		String endTimeString = timeFormat.format(endTime);
		String durationString = (difference / 60000) + " min.";

		// 清空当前values
		values.clear();
		// 往values中防止数据
		values.put(HabitContract.HabitEntry.COLUMN_DATE, currentDate);
		values.put(HabitContract.HabitEntry.COLUMN_START, startTimeString);
		values.put(HabitContract.HabitEntry.COLUMN_END, endTimeString);
		values.put(HabitContract.HabitEntry.COLUMN_DURATION, durationString);

		// 获取数据库对象，执行插入操作
		SQLiteDatabase db = DBHelper.getWritableDatabase();
		db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param DBHelper
	 */
	public static void deleteAll(HabitDBHelper DBHelper) {
		// 获取数据库对象
		SQLiteDatabase db = DBHelper.getWritableDatabase();
		// 执行清除数据操作
		db.delete(HabitContract.HabitEntry.TABLE_NAME, null, null);
	}
}