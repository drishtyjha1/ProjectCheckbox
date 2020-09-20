package com.example.projectcheckbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    private static final String TABLE_NAME = "task_items";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_ITEMS = "items";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " TEXT,"
            + COL_ITEMS + " TEXT)";


    public DBHelper(@Nullable Context context) {
        super(context, "Task.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertDataToDatabase(SQLiteDatabase db, Task taskItem) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, taskItem.taskTitle);
        cv.put(COL_ITEMS, taskItem.taskItemString);
        db.insert(TABLE_NAME, null, cv);
    }


    public ArrayList<Task> getTasksFromDB(SQLiteDatabase db) {
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.taskID = cursor.getInt(cursor.getColumnIndex(COL_ID));
                task.taskTitle = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                task.taskItemString = cursor.getString(cursor.getColumnIndex(COL_ITEMS));

                tasks.add(task);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return tasks;
    }

    public void updateDataToDatabse(SQLiteDatabase db, Task taskItem) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, taskItem.taskTitle);
        cv.put(COL_ITEMS, taskItem.taskItemString);
        db.update(TABLE_NAME, cv, COL_ID + "=" + taskItem.taskID, null);
    }
}
