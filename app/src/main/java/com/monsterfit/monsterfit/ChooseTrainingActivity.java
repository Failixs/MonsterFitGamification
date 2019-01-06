package com.monsterfit.monsterfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseTrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_training);
    }

    public void changeViewToTrainingActivity(View v){
        finish();
        startActivity(new Intent(this, TrainingActivity.class).putExtra("tag", v.getTag().toString()));
    }
}
