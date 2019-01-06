package com.monsterfit.monsterfit;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monsterfit.monsterfit.database.DatabaseHelper;
import com.monsterfit.monsterfit.database.Score;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setLayout();

        setStatistics();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setStatistics();
    }

    /**
     * Sets the layout of the starting page:
     * Image and Buttons take the whole page, statistics follow beneath
     */
    private void setLayout(){
        // Get height of the display for layout issues
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        ViewGroup.LayoutParams params;

        // resize the views
        TextView titleView = findViewById(R.id.titleView);
        params = titleView.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(params));
        titleView.invalidate();

        ImageView usersPicture = findViewById(R.id.usersPicture);
        params = usersPicture.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.6);
        usersPicture.setLayoutParams(new LinearLayout.LayoutParams(params));
        usersPicture.invalidate();

        LinearLayout startPageButtons = findViewById(R.id.startPageButtons);
        params = startPageButtons.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        startPageButtons.setLayoutParams(new LinearLayout.LayoutParams(params));
        startPageButtons.invalidate();
    }

    private void setStatistics(){

        DatabaseHelper db = new DatabaseHelper(this);

        // Time installed
        long score = db.getScore(Score.TIME_INSTALLED).getScore();
        long difference = System.currentTimeMillis() - score;
        int dayCount = (int)(difference / 86400000);
        String days = (dayCount <= 0) ? "" : String.valueOf(dayCount) + " Tag" + ((dayCount == 1) ? ", " : "e, ");
        difference %= 86400000;
        int hourCount = (int)(difference / 3600000);
        String hours = String.valueOf(hourCount) + " Stunde"  + ((hourCount == 1) ? "" : "n");

        TextView timeInstalled = findViewById(R.id.timeInstalled);
        timeInstalled.setText(days + hours);

        // Worked out time
        score = db.getScore(Score.TIME_WORKED_OUT).getScore();

        dayCount = (int)(score / 86400000);
        days = (dayCount <= 0) ? "" : String.valueOf(dayCount) + " Tag" + ((dayCount == 1) ? ", " : "e, ");
        score %= 86400000;
        hourCount = (int)(score / 3600000);
        hours = (hourCount <= 0) ? "" : String.valueOf(hourCount) + " Stunde" + ((hourCount == 1) ? ", " : "n, ");
        score %= 3600000;
        int minuteCount = (int)(score / 60000);
        String minutes = (minuteCount <= 0) ? "" : String.valueOf(minuteCount) + " Minute" + ((minuteCount == 1) ? ", " : "n, ");
        score %= 60000;
        int secondCount = (int)(score / 1000);
        String seconds = String.valueOf(secondCount) + " Sekunde"  + ((secondCount == 1) ? "" : "n");

        TextView timeWorkedOut = findViewById(R.id.timeWorkedOut);
        timeWorkedOut.setText(days + hours + minutes + seconds);

        // Killed arm monsters
        score = db.getScore(Score.KILLED_ARM_MONSTERS).getScore();
        TextView killedArmMonsters = findViewById(R.id.killedArmMonster);
        killedArmMonsters.setText(String.valueOf(score));

        // Killed chest monsters
        score = db.getScore(Score.KILLED_CHEST_MONSTERS).getScore();
        TextView killedChestMonsters = findViewById(R.id.killedChestMonster);
        killedChestMonsters.setText(String.valueOf(score));

        // Killed leg monsters
        score = db.getScore(Score.KILLED_LEG_MONSTERS).getScore();
        TextView killedLegMonsters = findViewById(R.id.killedLegMonster);
        killedLegMonsters.setText(String.valueOf(score));
    }

    public void changeViewToMonsterSelection(View v){
        startActivity(new Intent(this, ChooseTrainingActivity.class));
    }
}
