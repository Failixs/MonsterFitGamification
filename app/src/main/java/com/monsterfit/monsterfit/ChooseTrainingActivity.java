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

    public void changeViewToChestActivity(View v){
        startActivity(new Intent(this, ChestActivity.class));
    }
}
