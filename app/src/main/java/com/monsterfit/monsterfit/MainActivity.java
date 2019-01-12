package com.monsterfit.monsterfit;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monsterfit.monsterfit.database.DatabaseHelper;
import com.monsterfit.monsterfit.database.Exercise;
import com.monsterfit.monsterfit.database.Score;

import java.io.File;
import java.util.List;

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

        ImageView usersPicture = findViewById(R.id.usersPicture);
        params = usersPicture.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.6);
        usersPicture.setLayoutParams(new LinearLayout.LayoutParams(params));

        LinearLayout startPageButtons = findViewById(R.id.startPageButtons);
        params = startPageButtons.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        startPageButtons.setLayoutParams(new LinearLayout.LayoutParams(params));
    }

    private void setStatistics(){

        DatabaseHelper db = new DatabaseHelper(this);

        LinearLayout layout = findViewById(R.id.statisticsLayout);
        layout.removeAllViews();

        TextView statisticsTextView = new TextView(this);
        statisticsTextView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        statisticsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        statisticsTextView.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);
        statisticsTextView.setText(getString(R.string.statistics));
        layout.addView(statisticsTextView);

        List<Score> scores = db.getScores();
        for(Score score : scores){
            // The green Line
            View view = new View(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            view.setBackgroundResource(R.color.colorButton);
            layout.addView(view);

            LinearLayout scoreLayout = new LinearLayout(this);
            scoreLayout.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scoreLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout descriptionLayout = new LinearLayout(this);
            descriptionLayout.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            descriptionLayout.setOrientation(LinearLayout.HORIZONTAL);

            // The little images in front of the monster descriptions
            ImageView image = new ImageView(this);
            image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
            switch(score.getName()){
                case Score.DEFEATED_ARM_MONSTERS:
                    image.setBackgroundResource(R.drawable.nockchan);
                    descriptionLayout.addView(image);
                    break;
                case Score.DEFEATED_TORSO_MONSTERS:
                    image.setBackgroundResource(R.drawable.machomei);
                    descriptionLayout.addView(image);
                    break;
                case Score.DEFEATED_LEG_MONSTERS:
                    image.setBackgroundResource(R.drawable.kicklee);
                    descriptionLayout.addView(image);
                    break;
                    default:
                        break;
            }

            // The description of each score
            TextView descriptionTextView = new TextView(this);
            descriptionTextView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f));
            descriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            descriptionTextView.setText(score.getDescription());
            descriptionLayout.addView(descriptionTextView);

            scoreLayout.addView(descriptionLayout);

            // The score value
            TextView scoreTextView = new TextView(this);
            scoreTextView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            String scoreStr = "";
            switch(score.getName()){
                case Score.TIME_INSTALLED:
                    long difference = System.currentTimeMillis() - score.getScore();
                    int dayCount = (int)(difference / 86400000);
                    scoreStr += (dayCount <= 0) ? "" : String.valueOf(dayCount) + " Tag" + ((dayCount == 1) ? ", " : "en, ");
                    difference %= 86400000;
                    int hourCount = (int)(difference / 3600000);
                    scoreStr += String.valueOf(hourCount) + " Stunde"  + ((hourCount == 1) ? "" : "n");
                    break;
                case Score.TIME_WORKED_OUT:
                    long temp = score.getScore();
                    dayCount = (int)(temp / 86400000);
                    scoreStr += (dayCount <= 0) ? "" : String.valueOf(dayCount) + " Tag" + ((dayCount == 1) ? ", " : "e, ");
                    temp %= 86400000;
                    hourCount = (int)(temp / 3600000);
                    scoreStr += (hourCount <= 0) ? "" : String.valueOf(hourCount) + " Stunde" + ((hourCount == 1) ? ", " : "n, ");
                    temp %= 3600000;
                    int minuteCount = (int)(temp / 60000);
                    scoreStr += (minuteCount <= 0) ? "" : String.valueOf(minuteCount) + " Minute" + ((minuteCount == 1) ? ", " : "n, ");
                    temp %= 60000;
                    int secondCount = (int)(temp / 1000);
                    scoreStr += String.valueOf(secondCount) + " Sekunde"  + ((secondCount == 1) ? "" : "n");
                    break;
                default: //e.g. monsters defeated
                    scoreStr = String.valueOf(score.getScore());
            }
            scoreTextView.setText(scoreStr);
            scoreLayout.addView(scoreTextView);

            layout.addView(scoreLayout);
        }

        List<Exercise> exercises = db.getExerciseByType(Exercise.TYPE.NONE);

        for(Exercise exercise : exercises){
            // The green Line
            View view = new View(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            view.setBackgroundResource(R.color.colorButton);
            layout.addView(view);

            LinearLayout scoreLayout = new LinearLayout(this);
            scoreLayout.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scoreLayout.setOrientation(LinearLayout.HORIZONTAL);

            // The description of each score
            TextView descriptionTextView = new TextView(this);
            descriptionTextView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            descriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            descriptionTextView.setText(exercise.getTitle());
            scoreLayout.addView(descriptionTextView);

            // The score value
            TextView scoreTextView = new TextView(this);
            scoreTextView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            scoreTextView.setText(String.valueOf(exercise.getCount()));
            scoreLayout.addView(scoreTextView);

            layout.addView(scoreLayout);
        }


    }

    public void changeViewToMonsterSelection(View v){
        startActivity(new Intent(this, ChooseTrainingActivity.class));
    }
}
