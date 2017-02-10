package com.naruto.udacity_habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naruto.udacity_habittracker.data.HabitContract;
import com.naruto.udacity_habittracker.data.HabitDBHelper;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private HabitDBHelper mDBHelper;
	private boolean mButtonToggle = false;
	private long mStartTime;
	private ProgressBar pb_time_keeping;
	private Button bt_action;
	private TextView tv_title;
	private TextView tv_result;
	private ContentValues mValues = new ContentValues();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		updateUI();
	}

	private void initUI() {
		// 找到textview
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_result = (TextView) findViewById(R.id.tv_result);
		// 找到进度条控件，用于提示用户当前正在计时中
		pb_time_keeping = (ProgressBar) findViewById(R.id.pb_time_keeping);
		pb_time_keeping.setVisibility(View.GONE);
		// 找到开始/结束，清除按钮，设置点击事件
		bt_action = (Button) findViewById(R.id.bt_action);
		Button bt_clear = (Button) findViewById(R.id.bt_clear);
		bt_action.setOnClickListener(this);
		bt_clear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_action:
			buttonToggle();
			updateUI();

			break;

		case R.id.bt_clear:
			HabitDBHelper.deleteAll(mDBHelper);
			updateUI();

			break;

		default:
			break;
		}
	}

	/**
	 * 更新UI
	 */
	public void updateUI() {
		Cursor cursor = read();
		try {
			int count = cursor.getCount();
			tv_title.setText("There are currently " + count + " dog walking records");

			// 获取对应的索引值
			int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
			int dateColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DATE);
			int startColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_START);
			int endColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_END);
			int durationColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DURATION);
			// cursor不为空才做内容显示，否则为空
			if (count != 0) {
				tv_result.setText("");
				while (cursor.moveToNext()) {
					// 根据索引值获取内容
					String currentDate = cursor.getString(dateColumnIndex);
					String currentStart = cursor.getString(startColumnIndex);
					String currentEnd = cursor.getString(endColumnIndex);
					String currentDuration = cursor.getString(durationColumnIndex);

					tv_result.append("Date: " + currentDate + "\nStart Time: " + currentStart + "\nFinish Time: " + currentEnd + "\nDuration: "
									+ currentDuration + "\n\n");
				}
			} else {
				tv_result.setText("");
			}
		} finally {
			// 关闭cursor
			cursor.close();
		}
	}

	private Cursor read() {
		// 建立数据库连接
		mDBHelper = new HabitDBHelper(this);
		SQLiteDatabase db = mDBHelper.getReadableDatabase();

		String[] projection = {HabitContract.HabitEntry._ID, HabitContract.HabitEntry.COLUMN_DATE, HabitContract.HabitEntry.COLUMN_START,
						HabitContract.HabitEntry.COLUMN_END, HabitContract.HabitEntry.COLUMN_DURATION};

		return db.query(HabitContract.HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
	}

	/**
	 * 根据按钮被点击的次数，正确的显示START/STOP,并执行相应的逻辑
	 */
	public void buttonToggle() {
		Date rightNow = new Date();
		if (!mButtonToggle) {
			// 开始计时，获取开始时间
			mStartTime = rightNow.getTime();
			mButtonToggle = true;
			// 改变按钮文字
			bt_action.setText(R.string.bt_stop);
			// 显示进度条
			pb_time_keeping.setVisibility(View.VISIBLE);
		} else {
			// 停止计时
			mButtonToggle = false;
			// 改变按钮文字
			bt_action.setText(R.string.bt_start);
			// 停止计时，网数据库中插入一条数据
			HabitDBHelper.insert(mStartTime, mValues, mDBHelper);
			// 隐藏进度条
			pb_time_keeping.setVisibility(View.GONE);
		}
	}
}