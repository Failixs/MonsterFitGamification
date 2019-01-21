package com.monsterfit.monsterfit.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.monsterfit.monsterfit.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "exercises_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        createExerciseTable(db);
        createScoreTable(db);
    }

    private void createExerciseTable(SQLiteDatabase db){
        db.execSQL(Exercise.CREATE_TABLE);
        Resources system = Resources.getSystem();

        // Exercises for ARMS
        insertExercise(db, context.getString(R.string.pushUp), Exercise.TYPE.ARMS, context.getString(R.string.pushUpInstruction), 10);
        insertExercise(db, context.getString(R.string.oneArmedPushUp), Exercise.TYPE.ARMS, context.getString(R.string.oneArmedPushUpInstruction), 43);
        insertExercise(db, context.getString(R.string.dips), Exercise.TYPE.ARMS, context.getString(R.string.dipsInstruction), 25);
        insertExercise(db, context.getString(R.string.highPushUp), Exercise.TYPE.ARMS, context.getString(R.string.highPushUpInstruction), 20);
        insertExercise(db, context.getString(R.string.wideGripPushUp), Exercise.TYPE.ARMS, context.getString(R.string.wideGripPushUpInstruction), 10);
        insertExercise(db, context.getString(R.string.circlePushUps), Exercise.TYPE.ARMS, context.getString(R.string.circlePushUpsInstruction), 30);
        insertExercise(db, context.getString(R.string.bicepsCurl), Exercise.TYPE.ARMS, context.getString(R.string.bicepsCurlInstruction), 10);
        insertExercise(db, context.getString(R.string.clapPushUp), Exercise.TYPE.ARMS, context.getString(R.string.clapPushUpInstruction), 35);


        // Exercises for LEGS
        insertExercise(db, context.getString(R.string.squat), Exercise.TYPE.LEGS, context.getString(R.string.squatInstruction), 7);
        insertExercise(db, context.getString(R.string.squatJump), Exercise.TYPE.LEGS, context.getString(R.string.squatJumpInstruction), 15);
        insertExercise(db, context.getString(R.string.wallsitting), Exercise.TYPE.LEGS, context.getString(R.string.wallsittingInstruction), 5);
        insertExercise(db, context.getString(R.string.becklift), Exercise.TYPE.LEGS, context.getString(R.string.beckliftLegInstruction), 10);
        insertExercise(db, context.getString(R.string.lunges), Exercise.TYPE.LEGS, context.getString(R.string.lungesInstruction), 15);
        insertExercise(db, context.getString(R.string.lungesJumping), Exercise.TYPE.LEGS, context.getString(R.string.lungesJumpingInstruction), 20);
        insertExercise(db, context.getString(R.string.lungesToTheSide), Exercise.TYPE.LEGS, context.getString(R.string.lungesToTheSideInstruction), 20);
        insertExercise(db, context.getString(R.string.calfRaise), Exercise.TYPE.LEGS, context.getString(R.string.calfRaiseInstruction), 7);
        insertExercise(db, context.getString(R.string.scissor), Exercise.TYPE.LEGS, context.getString(R.string.scissorInstruction), 15);
        insertExercise(db, context.getString(R.string.fireHydrant), Exercise.TYPE.LEGS, context.getString(R.string.fireHydrantInstruction), 15);


        // Exercises for TORSO
        insertExercise(db, context.getString(R.string.superman), Exercise.TYPE.TORSO, context.getString(R.string.supermanInstruction), 15);
        insertExercise(db, context.getString(R.string.steps), Exercise.TYPE.TORSO, context.getString(R.string.stepsInstruction) , 7);
        insertExercise(db, context.getString(R.string.plankSuperman), Exercise.TYPE.TORSO, context.getString(R.string.plankSupermanInstruction) , 12);
        insertExercise(db, context.getString(R.string.plank), Exercise.TYPE.TORSO, context.getString(R.string.plankInstruction) , 7);
        insertExercise(db, context.getString(R.string.sitUp), Exercise.TYPE.TORSO, context.getString(R.string.sitUpInstruction) , 7);
        insertExercise(db, context.getString(R.string.jackknife), Exercise.TYPE.TORSO, context.getString(R.string.jackknifeInstruction) , 20);
        insertExercise(db, context.getString(R.string.sideSupport), Exercise.TYPE.TORSO, context.getString(R.string.sideSupportInstruction) , 7);
        insertExercise(db, context.getString(R.string.reverseCrunch), Exercise.TYPE.TORSO, context.getString(R.string.reverseCrunchInstruction) , 12);
        insertExercise(db, context.getString(R.string.scissor), Exercise.TYPE.TORSO, context.getString(R.string.scissorInstruction) , 12);
        insertExercise(db, context.getString(R.string.mountainClimbers), Exercise.TYPE.TORSO, context.getString(R.string.mountainClimbersInstruction) , 10);
        insertExercise(db, context.getString(R.string.bicycleCrunches), Exercise.TYPE.TORSO, context.getString(R.string.bicycleCrunchesInstruction) , 10);
        insertExercise(db, context.getString(R.string.becklift), Exercise.TYPE.TORSO, context.getString(R.string.beckliftInstruction) , 12);
        insertExercise(db, context.getString(R.string.beckliftLeg), Exercise.TYPE.TORSO, context.getString(R.string.beckliftLegInstruction) , 15);


    }

    private void createScoreTable(SQLiteDatabase db){
        db.execSQL(Score.CREATE_TABLE);
        Resources system = Resources.getSystem();
        insertScore(db, Score.TIME_INSTALLED, context.getString(R.string.timeInstalled), System.currentTimeMillis());
        insertScore(db, Score.TIME_WORKED_OUT, context.getString(R.string.timeWorkedOut), 0);
        insertScore(db, Score.DEFEATED_ARM_MONSTERS, context.getString(R.string.defeatedLacertmons), 0);
        insertScore(db, Score.DEFEATED_TORSO_MONSTERS, context.getString(R.string.defeatedTruncmons), 0);
        insertScore(db, Score.DEFEATED_LEG_MONSTERS, context.getString(R.string.defeatedCrusmons), 0);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Exercise.TABLE_NAME);

        // Create tables again
        createExerciseTable(db);
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

        String whereStatement = type == Exercise.TYPE.NONE ? "" : " WHERE " + Exercise.COLUMN_TYPE + "='" + type.name() + "'";
        String selectQuery = "SELECT * FROM " + Exercise.TABLE_NAME + whereStatement + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                exercises.add(new Exercise(cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_TITLE)),
                        Exercise.TYPE.valueOf(cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_TYPE))),
                        cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_INSTRUCTION)),
                        cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_DIFFICULTY)),
                        cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_COUNT))));
            } while (cursor.moveToNext());
        }

        db.close();

        return exercises;
    }

    public void updateCount(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Exercise.COLUMN_COUNT, exercise.getCount());

        db.update(Exercise.TABLE_NAME, values, Exercise.COLUMN_ID + " = ?", new String[]{String.valueOf(exercise.getId())});
        db.close();
    }

    // Methods for scores
    private void insertScore(SQLiteDatabase db, String name, String description, long score){
        ContentValues values = new ContentValues();
        values.put(Score.COLUMN_NAME, name);
        values.put(Score.COLUMN_DESCRIPTION, description);
        values.put(Score.COLUMN_SCORE, score);
        db.insert(Score.TABLE_NAME, null, values);
    }

    public Score getScore (String description){
        String selectQuery = "SELECT * FROM " + Score.TABLE_NAME
                + " WHERE " + Score.COLUMN_NAME
                + "='" + description + "';";

        Score score = new Score();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
                score = new Score(cursor.getInt(cursor.getColumnIndex(Score.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Score.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(Score.COLUMN_DESCRIPTION)),
                        cursor.getLong(cursor.getColumnIndex(Score.COLUMN_SCORE)));
        }

        db.close();

        return score;
    }

    public List<Score> getScores(){

        List<Score> scores = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + Score.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                scores.add(new Score(cursor.getInt(cursor.getColumnIndex(Score.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Score.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(Score.COLUMN_DESCRIPTION)),
                        cursor.getLong(cursor.getColumnIndex(Score.COLUMN_SCORE))));
            } while (cursor.moveToNext());
        }

        db.close();

        return scores;
    }

    public void updateScore(Score score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Score.COLUMN_SCORE, score.getScore());

        db.update(Score.TABLE_NAME, values, Score.COLUMN_ID + " = ?", new String[]{String.valueOf(score.getId())});
        db.close();
    }


}

