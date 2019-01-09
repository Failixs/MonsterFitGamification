package com.monsterfit.monsterfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.monsterfit.monsterfit.database.DatabaseHelper;
import com.monsterfit.monsterfit.database.Exercise;
import com.monsterfit.monsterfit.database.Score;

import java.util.ArrayList;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private Score killedMonsters;
    private int maxHealth;
    private int currentHealth;

    private Exercise.TYPE tag;
    private Exercise[] selectedExercises;

    private ProgressBar monsterHealthBar;
    private TextView monsterHealthNumber;

    private long workOutStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        workOutStarted = System.currentTimeMillis();

        db = new DatabaseHelper(this);

        this.tag = getTag();
        setMonsterImageView();

        setMaxHealth(db);

        List<Exercise> exerciseList = getExerciseListByType(db);
        selectedExercises = getSelectedExercises(exerciseList);

        //buttons,bars etc.
        monsterHealthBar = findViewById(R.id.monsterHealthBar);
        monsterHealthNumber = findViewById(R.id.monsterHealthNumber);

        final Button topLeftButton = findViewById(R.id.topLeftButton);
        final Button topRightButton = findViewById(R.id.topRightButton);
        final Button bottomLeftButton = findViewById(R.id.bottomLeftButton);
        final Button bottomRightButton = findViewById(R.id.bottomRightButton);

        topLeftButton.setText(selectedExercises[0].getRepetitions() + "x " + selectedExercises[0].getTitle());
        topRightButton.setText(selectedExercises[1].getRepetitions() + "x " + selectedExercises[1].getTitle());
        bottomLeftButton.setText(selectedExercises[2].getRepetitions() + "x " + selectedExercises[2].getTitle());
        bottomRightButton.setText(selectedExercises[3].getRepetitions() + "x " + selectedExercises[3].getTitle());

        monsterHealthBar.setMax(maxHealth);
        currentHealth = maxHealth;
        monsterHealthBar.setProgress(currentHealth);
        monsterHealthNumber.setText(" " + String.valueOf(currentHealth) + "/" + String.valueOf(maxHealth) + "HP");
    }

    @Override
    protected void onPause(){
        super.onPause();

        // Update worked out time ion dataBase
        long workOutTime = System.currentTimeMillis() - workOutStarted;
        Score workedOutTime = db.getScore(Score.TIME_WORKED_OUT);
        workedOutTime.setScore(workedOutTime.getScore() + workOutTime);
        db.updateScore(workedOutTime);
    }

    public void instructionClick(View v){
        Exercise exerciseDone = selectedExercises[Integer.valueOf(v.getTag().toString())];

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);
        final TextView popupWindowText = popupView.findViewById(R.id.popupText);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindowText.setText(exerciseDone.getInstruction());

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void trainingDoneClick(View v){
        Exercise exerciseDone = selectedExercises[Integer.valueOf(v.getTag().toString())];
        currentHealth = Math.max(0, currentHealth - exerciseDone.getDifficulty() * exerciseDone.getRepetitions());

        monsterHealthBar.setProgress(currentHealth);
        monsterHealthNumber.setText(" " + String.valueOf(currentHealth) + "/" + String.valueOf(maxHealth) + "HP");

        if(currentHealth <= 0){

            killedMonsters.setScore(killedMonsters.getScore() + 1);

            db.updateScore(killedMonsters);

            finish();
        }
    }

    /**
     * Gets the tag (Training type) from the intent
     * @return training type
     */
    private Exercise.TYPE getTag(){
        Bundle b = getIntent().getExtras();
        String tag = "ARMS"; // or other values
        if(b != null)
            tag = b.getString("tag");
        return Exercise.TYPE.valueOf(tag);
    }

    /**
     * Sets the enemy monster image considering the tag
     */
    //TODO: Change drawings
    private void setMonsterImageView(){
        ImageView monster = findViewById(R.id.monster);
        switch(tag){
            case LEGS:
                monster.setImageResource(R.drawable.kicklee);
                break;
            case CHEST:
                monster.setImageResource(R.drawable.machomei);
                break;
            case ARMS:
                monster.setImageResource(R.drawable.nockchan);
                break;
            default:
                monster.setImageResource(R.drawable.machomei);
                break;
        }
    }

    private void setMaxHealth(DatabaseHelper db){
        switch(this.tag){
            case ARMS:
                killedMonsters = db.getScore(Score.KILLED_ARM_MONSTERS);
                break;
            case CHEST:
                killedMonsters = db.getScore(Score.KILLED_CHEST_MONSTERS);
                break;
            case LEGS:
                killedMonsters = db.getScore(Score.KILLED_LEG_MONSTERS);
                break;
        }

        this.maxHealth = (int)((0.98 + Math.random() * 0.04)*(500 + 10 * (int)killedMonsters.getScore()));

    }
    /**
     * Gets all exercises from database with the tag as exercise type
     * @return exerciseList
     */
    private List<Exercise> getExerciseListByType(DatabaseHelper db){
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList = db.getExerciseByType(this.tag);
        return exerciseList;
    }

    /**
     * Chooses randomly four Exercises of different levels of difficulty
     * @param exerciseList
     * @return array of four selected exercises
     */
    private Exercise[] getSelectedExercises(List<Exercise> exerciseList){
        Exercise[] selectedExercises = new Exercise[4];
        for(int i = 0; i < 4; i++){
            selectedExercises[i] = getExercise(exerciseList, 6 - i);
        }
        return  selectedExercises;
    }

    /**
     * Selects randomly an exercise out of the given list
     * when monster can be defeated with iterationsToDo iterations.
     * @param exerciseList list to be selected from
     * @param iterationsToDo level of difficulty (higher = easier)
     * @return an exercise of the given level of difficulty
     */
    private Exercise getExercise(List<Exercise> exerciseList, int iterationsToDo){
        if(exerciseList.size() > 0) {
            Exercise randomExercise = exerciseList.get((int) (Math.random() * exerciseList.size()));
            Exercise exercise = new Exercise(randomExercise.getId(), randomExercise.getTitle(), randomExercise.getType(),randomExercise.getInstruction(),randomExercise.getDifficulty()); //randomExercise needs to be copied here!
            if (4 * exercise.getDifficulty() >= maxHealth / iterationsToDo)
                getExercise(exerciseList, iterationsToDo); // Chosen exercise too difficult
            else {
                int rep = 5;
                while (rep * exercise.getDifficulty() < maxHealth / iterationsToDo) {
                    rep++;
                }
                exercise.setRepetitions(rep);
            }
            return exercise;
        }
        return null;
    }
}
