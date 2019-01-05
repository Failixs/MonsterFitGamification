package com.monsterfit.monsterfit.database;

public class Exercise {

    public enum TYPE { ARMS, CHEST, LEGS }

    public static final String TABLE_NAME = "exercises";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_INSTRUCTION = "instruction";
    public static final String COLUMN_DIFFICULTY = "difficulty";

    private int id;
    private String title;
    private TYPE type;
    private String instruction;
    private int difficulty;
    private int repetitions;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_INSTRUCTION + " TEXT,"
                    + COLUMN_DIFFICULTY + " INTEGER"
                    + ")";

    public Exercise() {
    }

    public Exercise(int id, String title, TYPE type, String instruction, int difficulty) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.instruction = instruction;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TYPE getType() {
        return type;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public int getRepetitions() { return repetitions; }

    public void setRepetitions(int repetitions){ this.repetitions = repetitions; }
}
