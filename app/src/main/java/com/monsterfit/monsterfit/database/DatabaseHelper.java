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
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "exercises_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        createExerciseTable(db);
        createScoreTable(db);
    }

    private void createExerciseTable(SQLiteDatabase db){
        // create notes table
        db.execSQL(Exercise.CREATE_TABLE);
        insertExercise(db, "Liegestütz", Exercise.TYPE.ARMS, "Die Hände befinden sich etwas über schulterbreit voneinander entfernt am Boden. Die Finger zeigen nach vorne, die Daumen nach innen. Durch gleichzeitiges Anspannen der Arme werden diese gestreckt und der Oberkörper hebt vom Boden ab. Das Gewicht wird gleichmäßig auf Zehenspitzen und Händen verteilt. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt. Nun werden beide Arme gleichzeitig gebeugt und der Oberkörper somit abgesenkt, bis die Nasenspitze fast den Boden berührt. Der Körper bleibt dabei gestreckt.", 10);
        insertExercise(db, "Liegestütz", Exercise.TYPE.CHEST, "Die Hände befinden sich etwas über schulterbreit voneinander entfernt am Boden. Die Finger zeigen nach vorne, die Daumen nach innen. Durch gleichzeitiges Anspannen der Arme werden diese gestreckt und der Oberkörper hebt vom Boden ab. Das Gewicht wird gleichmäßig auf Zehenspitzen und Händen verteilt. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt. Nun werden beide Arme gleichzeitig gebeugt und der Oberkörper somit abgesenkt, bis die Nasenspitze fast den Boden berührt. Der Körper bleibt dabei gestreckt.", 10);
        insertExercise(db, "Kniebeugen", Exercise.TYPE.LEGS, "Tief in die Hocke gehen, Knie nicht vor die Zehenspitzen schieben. Bis 90°-Beugung im Knie.", 15);
        insertExercise(db, "Sit-ups", Exercise.TYPE.CHEST, "Mit geradem Rücken nach oben richtung Decke schieben. Kopf in Verlängerung der Wirbelsäule. Zwischen den Übungen nicht ablegen.", 20);
        insertExercise(db, "Einarmige Liegestütz", Exercise.TYPE.ARMS, "Beine breit, ein Arm unter dem Körper. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt.", 43);
        insertExercise(db, "Breite Liegestütz", Exercise.TYPE.CHEST, "Die Hände befinden sich doppelte Schulterbreite voneinander entfernt am Boden. Die Finger zeigen nach vorne, die Daumen nach innen. Durch gleichzeitiges Anspannen der Arme werden diese gestreckt und der Oberkörper hebt vom Boden ab. Das Gewicht wird gleichmäßig auf Zehenspitzen und Händen verteilt. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt. Nun werden beide Arme gleichzeitig gebeugt und der Oberkörper somit abgesenkt, bis die Nasenspitze fast den Boden berührt. Der Körper bleibt dabei gestreckt.", 33);
        insertExercise(db, "Dips", Exercise.TYPE.ARMS, "Hände stützen sich rücklings auf ein erhöhtes Objekt. Der Hintern bewegt sich richtung Boden und wird wieder durch Anspannen der Armmuskulatur in die Ausgangslage gedrückt.", 25);
        insertExercise(db, "Scheibenwischer", Exercise.TYPE.CHEST, "Rücken liegt auf dem Boden, kein Hohlkreuz, Beine 90° in die Luft. Arme dürfen unterstützen. Beine nun nach links und nach rechts fallen lassen, ohne sie abzulegen. 90° in der Hüfte werden beibehalten.", 23);
        insertExercise(db, "Stepper", Exercise.TYPE.LEGS, "Ein Fuß auf einer kleinen Erhöhung, der andere unten. Zwei Fußwechsel bilden einen Durchgang.", 7);
    }

    private void createScoreTable(SQLiteDatabase db){
        db.execSQL(Score.CREATE_TABLE);
        insertScore(db, Score.TIME_INSTALLED, System.currentTimeMillis());
        insertScore(db, Score.TIME_WORKED_OUT, 0);
        insertScore(db, Score.KILLED_ARM_MONSTERS, 0);
        insertScore(db, Score.KILLED_CHEST_MONSTERS, 0);
        insertScore(db, Score.KILLED_LEG_MONSTERS, 0);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Exercise.TABLE_NAME);

        // Create tables again
        createExerciseTable(db);

        if (oldVersion < 2){
            db.execSQL("DELETE FROM " + Exercise.TABLE_NAME
                    + " WHERE (" + Exercise.COLUMN_TITLE + "='Liegestütz' AND "
                    + Exercise.COLUMN_TYPE + "='LEGS');");
            insertExercise(db, "Kniebeugen", Exercise.TYPE.LEGS, "Tief in die Hocke gehen, Knie nicht vor die Zehenspitzen schieben. Bis 90°-Beugung im Knie.", 15);
            insertExercise(db, "Sit-ups", Exercise.TYPE.CHEST, "Mit geradem Rücken nach oben richtung Decke schieben. Kopf in Verlängerung der Wirbelsäule. Zwischen den Übungen nicht ablegen.", 20);
            insertExercise(db, "Einarmige Liegestütz", Exercise.TYPE.ARMS, "Beine breit, ein Arm unter dem Körper. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt.", 43);
            insertExercise(db, "Breite Liegestütz", Exercise.TYPE.CHEST, "Die Hände befinden sich doppelte Schulterbreite voneinander entfernt am Boden. Die Finger zeigen nach vorne, die Daumen nach innen. Durch gleichzeitiges Anspannen der Arme werden diese gestreckt und der Oberkörper hebt vom Boden ab. Das Gewicht wird gleichmäßig auf Zehenspitzen und Händen verteilt. Kopf, Hals, Wirbelsäule, Gesäß und Knie bilden eine Linie und die Bauchmuskulatur ist angespannt. Nun werden beide Arme gleichzeitig gebeugt und der Oberkörper somit abgesenkt, bis die Nasenspitze fast den Boden berührt. Der Körper bleibt dabei gestreckt.", 33);
            insertExercise(db, "Dips", Exercise.TYPE.ARMS, "Hände stützen sich rücklings auf ein erhöhtes Objekt. Der Hintern bewegt sich richtung Boden und wird wieder durch Anspannen der Armmuskulatur in die Ausgangslage gedrückt.", 25);
            insertExercise(db, "Scheibenwischer", Exercise.TYPE.CHEST, "Rücken liegt auf dem Boden, kein Hohlkreuz, Beine 90° in die Luft. Arme dürfen unterstützen. Beine nun nach links und nach rechts fallen lassen, ohne sie abzulegen. 90° in der Hüfte werden beibehalten.", 23);
            insertExercise(db, "Stepper", Exercise.TYPE.LEGS, "Ein Fuß auf einer kleinen Erhöhung, der andere unten. Zwei Fußwechsel bilden einen Durchgang.", 7);
        }
    }

    // Methods for exercises
    private void insertExercise(SQLiteDatabase db, String title, Exercise.TYPE type, String instruction, int difficulty){
        ContentValues values = new ContentValues();
        values.put(Exercise.COLUMN_TITLE, title);
        values.put(Exercise.COLUMN_TYPE, type.name());
        values.put(Exercise.COLUMN_INSTRUCTION, instruction);
        values.put(Exercise.COLUMN_DIFFICULTY, difficulty);
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

    // Methods for scores
    private void insertScore(SQLiteDatabase db, String description, long score){
        ContentValues values = new ContentValues();
        values.put(Score.COLUMN_DESCRIPTION, description);
        values.put(Score.COLUMN_SCORE, score);
        db.insert(Score.TABLE_NAME, null, values);
    }

    public Score getScore (String description){
        String selectQuery = "SELECT * FROM " + Score.TABLE_NAME
                + " WHERE " + Score.COLUMN_DESCRIPTION
                + "='" + description + "';";

        Score score = new Score();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
                score = new Score(cursor.getInt(cursor.getColumnIndex(Score.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Score.COLUMN_DESCRIPTION)),
                        cursor.getLong(cursor.getColumnIndex(Score.COLUMN_SCORE)));
        }

        db.close();

        return score;
    }

    public void updateScore(Score score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Score.COLUMN_SCORE, score.getScore());

        db.update(Score.TABLE_NAME, values, Score.COLUMN_ID + " = ?", new String[]{String.valueOf(score.getId())});
    }


}

