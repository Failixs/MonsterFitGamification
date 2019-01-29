package com.monsterfit.monsterfit;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterfit.monsterfit.database.DatabaseHelper;
import com.monsterfit.monsterfit.database.Exercise;
import com.monsterfit.monsterfit.database.Score;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private int index;      // For the ids of the statistik text views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        setLayout();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setTrainingCounts(Score.DEFEATED_ARM_MONSTERS);
        setTrainingCounts(Score.DEFEATED_TORSO_MONSTERS);
        setTrainingCounts(Score.DEFEATED_LEG_MONSTERS);
        setImage();
        setStatistics();
    }


    /**
     * Resetting count if last training is to long away
     */
    private void setTrainingCounts(String type) {
        // Arm Monsters
        Score score = db.getScore(type);
        long diff = System.currentTimeMillis() - score.getLastEncounter(); // Zeit seit dem letzten Training
        if (diff > 6.048e+8) // eine Woche in ms
        {
            score.setCount(0);
        }
        else if (diff > 5.184e+8){ // Länger als 6 Tage
            score.setCount(1);
        }
        else if (diff > 4.32e+8){ // Länger als 5 Tage
            score.setCount(2);
        }
        else if (diff > 3.456e+8){ // Länger als 4 Tage
            score.setCount(3);
        }
        else if (diff > 2.592e+8){ // Länger als 3 Tage
            score.setCount(4);
        }

        db.updateScore(score);
    }

    /**
     * setting the image according to the trainings count
     */
    private void setImage(){
        ImageView usersPicture = findViewById(R.id.usersPicture);
        usersPicture.setImageResource(getResources().getIdentifier(getImageResourceName(),"drawable", getPackageName()));

        int resource = getBadgeResource(db.getScore(Score.DEFEATED_ARM_MONSTERS).getCount());
        ImageView lacertBadge = findViewById(R.id.lacertBadge);
        lacertBadge.setImageResource(resource);
        TextView lacertBadgeText = findViewById(R.id.lacertBadgeText);
        if(resource != android.R.color.transparent)
            lacertBadgeText.setVisibility(View.VISIBLE);

        resource = getBadgeResource(db.getScore(Score.DEFEATED_TORSO_MONSTERS).getCount());
        ImageView truncBadge = findViewById(R.id.truncBadge);
        truncBadge.setImageResource(resource);
        TextView truncBadgeText = findViewById(R.id.truncBadgeText);
        if(resource != android.R.color.transparent)
            truncBadgeText.setVisibility(View.VISIBLE);

        resource = getBadgeResource(db.getScore(Score.DEFEATED_LEG_MONSTERS).getCount());
        ImageView crusBadge = findViewById(R.id.crusBadge);
        crusBadge.setImageResource(resource);
        TextView crusBadgeText = findViewById(R.id.crusBadgeText);
        if(resource != android.R.color.transparent)
            crusBadgeText.setVisibility(View.VISIBLE);
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
        int width = size.x;

        ConstraintLayout.LayoutParams params;

        // resize the views
        TextView titleView = findViewById(R.id.titleView);
        params = (ConstraintLayout.LayoutParams) titleView.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        titleView.setLayoutParams(new ConstraintLayout.LayoutParams(params));

        View titleShadowView = findViewById(R.id.titleShadowView);
        params = (ConstraintLayout.LayoutParams) titleShadowView.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        titleShadowView.setLayoutParams(new ConstraintLayout.LayoutParams(params));

        ImageView lacertBadge = findViewById(R.id.lacertBadge);
        lacertBadge.setImageResource(getBadgeResource(db.getScore(Score.DEFEATED_ARM_MONSTERS).getCount()));
        params = (ConstraintLayout.LayoutParams) lacertBadge.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        params.width = (int)Math.ceil(width * 0.2);
        lacertBadge.setLayoutParams(new ConstraintLayout.LayoutParams(params));

        ImageView truncBadge = findViewById(R.id.truncBadge);
        truncBadge.setImageResource(getBadgeResource(db.getScore(Score.DEFEATED_TORSO_MONSTERS).getCount()));
        params = (ConstraintLayout.LayoutParams) truncBadge.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        params.width = (int)Math.ceil(width * 0.2);
        truncBadge.setLayoutParams(new ConstraintLayout.LayoutParams(params));

        ImageView crusBadge = findViewById(R.id.crusBadge);
        crusBadge.setImageResource(getBadgeResource(db.getScore(Score.DEFEATED_LEG_MONSTERS).getCount()));
        params = (ConstraintLayout.LayoutParams) crusBadge.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        params.width = (int)Math.ceil(width * 0.2);
        crusBadge.setLayoutParams(new ConstraintLayout.LayoutParams(params));

        ImageView usersPicture = findViewById(R.id.usersPicture);
        usersPicture.setImageResource(getResources().getIdentifier(getImageResourceName(),"drawable", getPackageName()));
        params = (ConstraintLayout.LayoutParams) usersPicture.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.6);
        params.width = (int)Math.ceil(width * 0.8 - 30);
        usersPicture.setLayoutParams(new ConstraintLayout.LayoutParams(params));

        ConstraintLayout startPageButtons = findViewById(R.id.startPageButtons);
        params = (ConstraintLayout.LayoutParams) startPageButtons.getLayoutParams();
        params.height = (int)Math.ceil(height * 0.2);
        startPageButtons.setLayoutParams(new ConstraintLayout.LayoutParams(params));
    }

    /**
     * Gets the badge style
     * @param count the count of the defeated monsters
     * @return badge resource as int
     */
    private int getBadgeResource(int count){
        if(count >= 250)
            return R.drawable.gold;
        else if(count >= 100)
            return R.drawable.silver;
        else if(count >= 10)
            return R.drawable.bronze;
        else return android.R.color.transparent;
    }

    /**
     * gets the name of the image:
     * @return img + first digit for arms + second digit for torso + third digit for legs
     */
    private String getImageResourceName() {
        return "img"
                + getImageIndex(db.getScore(Score.DEFEATED_ARM_MONSTERS).getCount())
                + getImageIndex(db.getScore(Score.DEFEATED_TORSO_MONSTERS).getCount())
                + getImageIndex(db.getScore(Score.DEFEATED_LEG_MONSTERS).getCount());
    }

    /**
     * gets an index for each type
     * @param count of a specified type
     * @return index
     */
    private String getImageIndex(int count) {
        if(count == 0) return "1";
        else if(count <= 4) return "2";
        else return "3";
    }

    /**
     * sets all statistics
     */
    private void setStatistics(){

        ConstraintLayout layout = findViewById(R.id.statisticsLayout);
        layout.removeAllViews();

        ConstraintSet set = new ConstraintSet();

        index = 42;

        TextView statisticsTextView = getTextView(24, getString(R.string.statistics), Gravity.START);
        statisticsTextView.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);
        layout.addView(statisticsTextView);

        View greenline = setGreenLine(layout, statisticsTextView, 3);

        TextView installedTextView = getTextView(18, db.getScore(Score.TIME_INSTALLED).getDescription(), Gravity.START);
        layout.addView(installedTextView);

        TextView installedScoreView = getTextView(18, formatInstalledScoreString(), Gravity.END);
        layout.addView(installedScoreView);

        View smallGreenline = setGreenLine(layout, statisticsTextView, 1);

        TextView workOutTextView = getTextView(18, db.getScore(Score.TIME_WORKED_OUT).getDescription(), Gravity.START);
        layout.addView(workOutTextView);

        TextView workOutScoreView = getTextView(18, formatWorkoutScoreString(), Gravity.END);
        layout.addView(workOutScoreView);

        View greenLine1 = setGreenLine(layout, workOutScoreView, 3);

        TextView lacertmonDescriptionView = getTextView(18, db.getScore(Score.DEFEATED_ARM_MONSTERS).getDescription(), Gravity.START);
        layout.addView(lacertmonDescriptionView);

        ImageView lacertmonImage = new ImageView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            lacertmonImage.setId(View.generateViewId());
        } else lacertmonImage.setId(index);
        index += 1;
        lacertmonImage.setAdjustViewBounds(true);
        lacertmonImage.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        lacertmonImage.setBackgroundResource(R.drawable.nockchan);
        layout.addView(lacertmonImage);

        TextView lacertmonScoreView = getTextView(18, String.valueOf(db.getScore(Score.DEFEATED_ARM_MONSTERS).getScore()), Gravity.END);
        layout.addView(lacertmonScoreView);

        View lastMonsterTextView = setMonsterStatistics(layout, lacertmonImage, Exercise.TYPE.ARMS);

        View greenLine2 = setGreenLine(layout, lastMonsterTextView, 3);

        TextView truncmonDescriptionView = getTextView(18, db.getScore(Score.DEFEATED_TORSO_MONSTERS).getDescription(), Gravity.START);
        layout.addView(truncmonDescriptionView);

        ImageView truncmonImage = new ImageView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            truncmonImage.setId(View.generateViewId());
        } else truncmonImage.setId(index);
        index += 1;
        truncmonImage.setAdjustViewBounds(true);
        truncmonImage.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        truncmonImage.setBackgroundResource(R.drawable.machomei);
        layout.addView(truncmonImage);

        TextView truncmonScoreView = getTextView(18, String.valueOf(db.getScore(Score.DEFEATED_TORSO_MONSTERS).getScore()), Gravity.END);
        layout.addView(truncmonScoreView);

        lastMonsterTextView = setMonsterStatistics(layout, truncmonImage, Exercise.TYPE.TORSO);

        View greenLine3 = setGreenLine(layout, lastMonsterTextView, 3);

        TextView crusmonDescriptionView = getTextView(18, db.getScore(Score.DEFEATED_LEG_MONSTERS).getDescription(), Gravity.START);
        layout.addView(crusmonDescriptionView);

        ImageView crusmonImage = new ImageView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            crusmonImage.setId(View.generateViewId());
        } else crusmonImage.setId(index);
        index += 1;
        crusmonImage.setAdjustViewBounds(true);
        crusmonImage.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        crusmonImage.setBackgroundResource(R.drawable.kicklee);
        layout.addView(crusmonImage);

        TextView crusmonScoreView = getTextView(18, String.valueOf(db.getScore(Score.DEFEATED_LEG_MONSTERS).getScore()), Gravity.END);
        layout.addView(crusmonScoreView);

        setMonsterStatistics(layout, crusmonImage, Exercise.TYPE.LEGS);

        set.clone(layout);
        set.connect(statisticsTextView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
        set.connect(statisticsTextView.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);

        set.connect(installedTextView.getId(), ConstraintSet.TOP, greenline.getId(), ConstraintSet.BOTTOM);
        set.connect(installedScoreView.getId(), ConstraintSet.TOP, installedTextView.getId(), ConstraintSet.TOP);
        set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, new int[]{installedTextView.getId(), installedScoreView.getId()}, new float[]{1,1}, ConstraintSet.CHAIN_SPREAD_INSIDE );

        set.connect(smallGreenline.getId(),  ConstraintSet.TOP, installedTextView.getId(), ConstraintSet.BOTTOM);

        set.connect(workOutTextView.getId(), ConstraintSet.TOP, smallGreenline.getId(), ConstraintSet.BOTTOM);
        set.connect(workOutScoreView.getId(), ConstraintSet.TOP, workOutTextView.getId(), ConstraintSet.TOP);
        set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, new int[]{workOutTextView.getId(), workOutScoreView.getId()}, new float[]{1,1}, ConstraintSet.CHAIN_SPREAD_INSIDE );

        set.connect(lacertmonImage.getId(), ConstraintSet.TOP, greenLine1.getId(), ConstraintSet.BOTTOM);
        set.connect(lacertmonDescriptionView.getId(), ConstraintSet.TOP, lacertmonImage.getId(), ConstraintSet.TOP);
        set.connect(lacertmonDescriptionView.getId(), ConstraintSet.BOTTOM, lacertmonImage.getId(), ConstraintSet.BOTTOM);
        set.connect(lacertmonScoreView.getId(), ConstraintSet.TOP, lacertmonImage.getId(), ConstraintSet.TOP);
        set.connect(lacertmonScoreView.getId(), ConstraintSet.BOTTOM, lacertmonImage.getId(), ConstraintSet.BOTTOM);
        set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, new int[]{lacertmonDescriptionView.getId(), lacertmonImage.getId(), lacertmonScoreView.getId()}, new float[]{4, 2, 2}, ConstraintSet.CHAIN_SPREAD_INSIDE );

        set.connect(truncmonImage.getId(), ConstraintSet.TOP, greenLine2.getId(), ConstraintSet.BOTTOM);
        set.connect(truncmonDescriptionView.getId(), ConstraintSet.TOP, truncmonImage.getId(), ConstraintSet.TOP);
        set.connect(truncmonDescriptionView.getId(), ConstraintSet.BOTTOM, truncmonImage.getId(), ConstraintSet.BOTTOM);
        set.connect(truncmonScoreView.getId(), ConstraintSet.TOP, truncmonImage.getId(), ConstraintSet.TOP);
        set.connect(truncmonScoreView.getId(), ConstraintSet.BOTTOM, truncmonImage.getId(), ConstraintSet.BOTTOM);
        set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, new int[]{truncmonDescriptionView.getId(), truncmonImage.getId(), truncmonScoreView.getId()}, new float[]{4, 2, 2}, ConstraintSet.CHAIN_SPREAD_INSIDE );

        set.connect(crusmonImage.getId(), ConstraintSet.TOP, greenLine3.getId(), ConstraintSet.BOTTOM);
        set.connect(crusmonDescriptionView.getId(), ConstraintSet.TOP, crusmonImage.getId(), ConstraintSet.TOP);
        set.connect(crusmonDescriptionView.getId(), ConstraintSet.BOTTOM, crusmonImage.getId(), ConstraintSet.BOTTOM);
        set.connect(crusmonScoreView.getId(), ConstraintSet.TOP, crusmonImage.getId(), ConstraintSet.TOP);
        set.connect(crusmonScoreView.getId(), ConstraintSet.BOTTOM, crusmonImage.getId(), ConstraintSet.BOTTOM);
        set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, new int[]{crusmonDescriptionView.getId(), crusmonImage.getId(), crusmonScoreView.getId()}, new float[]{4, 2, 2}, ConstraintSet.CHAIN_SPREAD_INSIDE );
        set.applyTo(layout);
    }

    /**
     * Get a new TextView with specified items
     * @param textSize textsize of the view
     * @param text to be set text
     * @param gravity gravity of the textview
     * @return generated TextView
     */
    private TextView getTextView(int textSize, String text, int gravity) {
        TextView tv = new TextView(this);
        tv.setLayoutParams( new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setId(View.generateViewId());
        } else tv.setId(index);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv.setGravity(gravity);
        tv.setText(text);

        index += 1;

        return tv;
    }

    private String formatInstalledScoreString() {
        String scoreStr = "";
        long difference = System.currentTimeMillis() - db.getScore(Score.TIME_INSTALLED).getScore();
        int dayCount = (int)(difference / 86400000);
        scoreStr += (dayCount <= 0) ? "" : String.valueOf(dayCount) + " Tag" + ((dayCount == 1) ? ", " : "en, ");
        difference %= 86400000;
        int hourCount = (int)(difference / 3600000);
        scoreStr += String.valueOf(hourCount) + " Stunde"  + ((hourCount == 1) ? "" : "n");

        return scoreStr;
    }

    private String formatWorkoutScoreString() {
        String scoreStr = "";

        long temp = db.getScore(Score.TIME_WORKED_OUT).getScore();
        int dayCount = (int)(temp / 86400000);
        scoreStr += (dayCount <= 0) ? "" : String.valueOf(dayCount) + " Tag" + ((dayCount == 1) ? ", " : "e, ");
        temp %= 86400000;
        int hourCount = (int)(temp / 3600000);
        scoreStr += (hourCount <= 0) ? "" : String.valueOf(hourCount) + " Stunde" + ((hourCount == 1) ? ", " : "n, ");
        temp %= 3600000;
        int minuteCount = (int)(temp / 60000);
        scoreStr += (minuteCount <= 0) ? "" : String.valueOf(minuteCount) + " Minute" + ((minuteCount == 1) ? ", " : "n, ");
        temp %= 60000;
        int secondCount = (int)(temp / 1000);
        scoreStr += String.valueOf(secondCount) + " Sekunde"  + ((secondCount == 1) ? "" : "n");

        return scoreStr;
    }

    private View setGreenLine(ConstraintLayout parent, View viewToAttach, int height) {
        View view = new View(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setId(View.generateViewId());
        } else view.setId(index);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        view.setBackgroundResource(R.color.colorAccent);
        parent.addView(view);

        index += 1;

        ConstraintSet set = new ConstraintSet();
        set.clone(parent);
        set.connect(view.getId(), ConstraintSet.TOP, viewToAttach.getId(), ConstraintSet.BOTTOM);
        set.applyTo(parent);

        return view;
    }

    private View setMonsterStatistics(ConstraintLayout parent, View viewToAttach, Exercise.TYPE type) {
        List<Exercise> exercises = db.getExerciseByType(type);

        View lastView = new TextView(this);
        for(Exercise exercise : exercises) {
            View greenLine = setGreenLine(parent, viewToAttach, 1);

            TextView descriptionTextView = getTextView(18, exercise.getTitle(), Gravity.START);
            parent.addView(descriptionTextView);

            TextView scoreTextView = getTextView(18, String.valueOf(exercise.getCount()), Gravity.END);
            parent.addView(scoreTextView);

            ConstraintSet set = new ConstraintSet();
            set.clone(parent);
            set.connect(descriptionTextView.getId(), ConstraintSet.TOP, greenLine.getId(), ConstraintSet.BOTTOM);
            set.connect(scoreTextView.getId(), ConstraintSet.TOP, descriptionTextView.getId(), ConstraintSet.TOP);
            set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, new int[]{descriptionTextView.getId(), scoreTextView.getId()}, new float[]{3, 1}, ConstraintSet.CHAIN_SPREAD_INSIDE );
            set.applyTo(parent);

            lastView = descriptionTextView;
            viewToAttach = descriptionTextView;
        }

        return lastView;
    }

    public void changeViewToMonsterSelection(View v){
        startActivity(new Intent(this, ChooseTrainingActivity.class));
    }
}
