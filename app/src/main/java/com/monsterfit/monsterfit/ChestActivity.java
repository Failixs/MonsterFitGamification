package com.monsterfit.monsterfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ChestActivity extends AppCompatActivity {


    public int maxHealth = 1000;
    public int currentHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest);

        final ProgressBar monsterHealthBar = (ProgressBar) findViewById(R.id.monsterHealthBar);
        monsterHealthBar.setMax(maxHealth);
        currentHealth = maxHealth;

        final Button topLeftButton = findViewById(R.id.topLeftButton);
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                currentHealth -= 100;
                monsterHealthBar.setProgress(currentHealth);
            }
        });
    }
}
