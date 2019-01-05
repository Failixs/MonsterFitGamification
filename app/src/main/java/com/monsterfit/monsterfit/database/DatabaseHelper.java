package com.monsterfit.monsterfit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "exercises_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Exercise.CREATE_TABLE);
        insertExercise(db, "Liegestütz", Exercise.TYPE.ARMS, "Die Hände befinden sich etwas über schulterbreit voneinander entfernt am Boden. Die Finger zeigen nach vorne, die Daumen nach innen. Durch gleichzeitiges Anspannen der Arme werden diese gestreckt und der Oberkörper hebt vom Boden ab. Das Gewicht wird gleichmäßig auf Zehenspitzen und Händen verteilt. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt. Nun werden beide Arme gleichzeitig gebeugt und der Oberkörper somit abgesenkt, bis die Nasenspitze fast den Boden berührt. Der Körper bleibt dabei gestreckt.", 10);
        insertExercise(db, "Liegestütz", Exercise.TYPE.LEGS, "Die Hände befinden sich etwas über schulterbreit voneinander entfernt am Boden. Die Finger zeigen nach vorne, die Daumen nach innen. Durch gleichzeitiges Anspannen der Arme werden diese gestreckt und der Oberkörper hebt vom Boden ab. Das Gewicht wird gleichmäßig auf Zehenspitzen und Händen verteilt. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt. Nun werden beide Arme gleichzeitig gebeugt und der Oberkörper somit abgesenkt, bis die Nasenspitze fast den Boden berührt. Der Körper bleibt dabei gestreckt.", 1);
        insertExercise(db, "Liegestütz", Exercise.TYPE.CHEST, "Die Hände befinden sich etwas über schulterbreit voneinander entfernt am Boden. Die Finger zeigen nach vorne, die Daumen nach innen. Durch gleichzeitiges Anspannen der Arme werden diese gestreckt und der Oberkörper hebt vom Boden ab. Das Gewicht wird gleichmäßig auf Zehenspitzen und Händen verteilt. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt. Nun werden beide Arme gleichzeitig gebeugt und der Oberkörper somit abgesenkt, bis die Nasenspitze fast den Boden berührt. Der Körper bleibt dabei gestreckt.", 10);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Exercise.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    private void insertExercise(SQLiteDatabase db, String title, Exercise.TYPE type, String instruction, int difficulty){
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("type", type.name());
        values.put("instruction", instruction);
        values.put("difficulty", difficulty);
        db.insert(Exercise.TABLE_NAME, null, values);
    }

    public List<Exercise> getExerciseByType(Exercise.TYPE type){

        List<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getCursorByType(type, db);

        if(cursor.moveToFirst()){
            do{
                exercises.add(new Exercise(cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_TITLE)),
                        Exercise.TYPE.valueOf(cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_TYPE))),
                        cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_INSTRUCTION)),
                        cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_DIFFICULTY))));
            } while (cursor.moveToNext());
        }

        db.close();

        return exercises;
    }

    public int getExercisesCountByType(Exercise.TYPE type){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = getCursorByType(type, db);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    private Cursor getCursorByType(Exercise.TYPE type, SQLiteDatabase db){

        String selectQuery = "SELECT * FROM " + Exercise.TABLE_NAME
                + " WHERE " + Exercise.COLUMN_TYPE
                + "='" + type.name() + "';";

        return db.rawQuery(selectQuery, null);
    }


}

