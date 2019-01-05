package com.monsterfit.monsterfit.database;

public class Score {
    public enum TYPE { ARMS, CHEST, LEGS }

    public static final String TABLE_NAME = "scores";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SCORE = "score";

    public static final String TIME_INSTALLED = "Installation date";
    public static final String TIME_WORKED_OUT = "Worked out time";
    public static final String KILLED_ARM_MONSTERS = "Defeated arm monsters";
    public static final String KILLED_CHEST_MONSTERS = "Defeated chest monsters";
    public static final String KILLED_LEG_MONSTERS = "Defeated leg monsters";


    private int id;
    private String description;
    private long score;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_SCORE + " BIGINT"
                    + ")";

    public Score() {
    }

    public Score(int id, String description, long score) {
        this.id = id;
        this.description = description;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getScore(){
        return score;
    }

    public void setScore(long score){
        this.score = score;
    }
}
