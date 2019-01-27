package com.monsterfit.monsterfit.database;

public class Score {
    public enum TYPE { ARMS, TORSO, LEGS }

    public static final String TABLE_NAME = "scores";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_LASTENCOUNTER = "lastEncounter";
    public static final String COLUMN_COUNT = "count";

    public static final String TIME_INSTALLED = "Installation date";
    public static final String TIME_WORKED_OUT = "Worked out time";
    public static final String DEFEATED_ARM_MONSTERS = "Defeated arm monsters";
    public static final String DEFEATED_TORSO_MONSTERS = "Defeated torso monsters";
    public static final String DEFEATED_LEG_MONSTERS = "Defeated leg monsters";


    private int id;
    private String name;
    private String description;
    private long score;
    private long lastEncounter;
    private int count;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_SCORE + " BIGINT DEFAULT NULL,"
                    + COLUMN_LASTENCOUNTER + " BIGINT DEFAULT NULL,"
                    + COLUMN_COUNT + " INTEGER DEFAULT NULL"
                    + ")";

    public Score() {
    }

    public Score(int id, String name, String description, long score, long lastEncounter, int count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.score = score;
        this.lastEncounter = lastEncounter;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public String getDescription() {
        return description;
    }

    public long getScore(){
        return score;
    }

    public void setScore(long score){
        this.score = score;
    }

    public long getLastEncounter() {return lastEncounter; }

    public void setLastEncounter(long lastEncounter){this.lastEncounter = lastEncounter; }

    public int getCount(){return count; }

    public void incrementCount(){count += 1; }

    /**
     * Sets the count down to the param, if it's lower than before
     * @param count
     */
    public void setCount(int count) { this.count = Math.min(count, this.count); }
}
