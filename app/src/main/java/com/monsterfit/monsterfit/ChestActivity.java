package com.monsterfit.monsterfit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class ChestActivity extends AppCompatActivity {


    public int maxHealth = 1000;
    public int currentHealth;



    //function to create random number from 0 to max
    public int random(int max){
        int tmp = 0;
        tmp = 0 + (int)(Math.random() * ((max - 1) + 1));
        return tmp;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest_training);


        //strings
        final String pushUps = getString(R.string.pushUps);
        final String wideGripPushUps = getString(R.string.wideGripPushUps);
        final String handsPressing = getString(R.string.handsPressing);
        final String oneArmPushUps = getString(R.string.oneArmPushUps);
        final String pushUpsInstruction = getString(R.string.pushUpsInstruction);
        final String exercisesEasy[] = {pushUps,wideGripPushUps,handsPressing,oneArmPushUps};
        final String exercisesMiddle[] = {pushUps,wideGripPushUps,handsPressing,oneArmPushUps};
        final String exercisesHard[] = {pushUps,wideGripPushUps,handsPressing,oneArmPushUps};

        //buttons,bars etc.
        final ProgressBar monsterHealthBar = findViewById(R.id.monsterHealthBar);
        final TextView monsterHealthNumber = findViewById(R.id.monsterHealthNumber);
        final TextView popupText = findViewById(R.id.popupText);
        final Button topLeftButton = findViewById(R.id.topLeftButton);
        final Button topRightButton = findViewById(R.id.topRightButton);
        final Button bottomLeftButton = findViewById(R.id.bottomLeftButton);
        final Button bottomRightButton = findViewById(R.id.bottomRightButton);
        final FloatingActionButton floatingButtonTopLeft = findViewById(R.id.floatingActionButtonTopLeft);







        monsterHealthBar.setMax(maxHealth);
        currentHealth = maxHealth;


        //initialize random buttons
        topLeftButton.setText(exercisesEasy[random(exercisesEasy.length)]);
        topRightButton.setText(exercisesEasy[random(exercisesEasy.length)]);
        bottomLeftButton.setText(exercisesEasy[random(exercisesEasy.length)]);
        bottomRightButton.setText(exercisesEasy[random(exercisesEasy.length)]);






        topLeftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                currentHealth -= 100;
                monsterHealthBar.setProgress(currentHealth);
                monsterHealthNumber.setText(" " + String.valueOf(currentHealth) + "/1000 HP");
            }
        });

        floatingButtonTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
               //
                //
                // die kacke geht nicht
                //popupText.setText("penis");
                //
                //
                //
                //
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });


       /* floatingButtonTopLeft.setOnClickListener(
                popup.setText(getResources().getValue(get););
        );*/
    }


}
