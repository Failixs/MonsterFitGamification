package com.monsterfit.monsterfit;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class ChooseTrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_training);

        setLayout();

    }

    private void setLayout(){
        // Get display width and height
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int)Math.ceil(size.x * 0.5);
        int height = (int)Math.ceil(size.y * 0.5);

        //Setting the imagebuttons relative to the screen
        ViewGroup.MarginLayoutParams params;

        ImageButton torsoMonsterButton = findViewById(R.id.torsoMonsterButton);
        params = (ViewGroup.MarginLayoutParams) torsoMonsterButton.getLayoutParams();
        params.width = width;
        params.height = height;
        params.setMargins(0, 0, width, height);
        torsoMonsterButton.setLayoutParams(new RelativeLayout.LayoutParams(params));

        ImageButton armMonsterButton = findViewById(R.id.armMonsterButton);
        params = (ViewGroup.MarginLayoutParams) armMonsterButton.getLayoutParams();
        params.width = width * 2;
        params.height = height;
        params.setMargins(width, (int)(height/2), 0, (int)(height/2));
        armMonsterButton.setLayoutParams(new RelativeLayout.LayoutParams(params));

        ImageButton legMonsterButton = findViewById(R.id.legMonsterButton);
        params = (ViewGroup.MarginLayoutParams) legMonsterButton.getLayoutParams();
        params.width = width;
        params.height = height;
        params.setMargins(0, height, width, 0);
        legMonsterButton.setLayoutParams(new RelativeLayout.LayoutParams(params));
    }

    public void changeViewToTrainingActivity(View v){
        finish();
        startActivity(new Intent(this, TrainingActivity.class).putExtra("tag", v.getTag().toString()));
    }
}
