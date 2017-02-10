package com.naruto.udacity_habittracker.data;

/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2017-01-16
 * Time: 22:48
 * Desc: UdaCity_HabitTracker
 */

import android.provider.BaseColumns;

public class HabitContract {

	/**
	 * An empty private constructor makes sure that the class is not going to be
	 * initialised.
	 */
	private HabitContract() {
	}

	public class HabitEntry implements BaseColumns {
		public static final String TABLE_NAME = "walk_the_dog";
		public static final String COLUMN_DATE = "date";
		public static final String COLUMN_START = "start";
		public static final String COLUMN_END = "end";
		public static final String COLUMN_DURATION = "duration";
	}
}