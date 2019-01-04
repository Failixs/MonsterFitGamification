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

import java.io.File;

public class MainActivity extends AppCompatActivity {

   // public String installedTime = String.valueOf(daysInstalled());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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


        //TextView timeInstalled = (TextView) findViewById(R.id.timeInstalledValue);
        //timeInstalled.setText(installedTime);
    }

    public void changeViewToMonsterSelection(View v){
        startActivity(new Intent(this, ChooseTrainingActivity.class));
    }

   /* public long daysInstalled(){
        long installed = 0;
        try{
            long installedTime = this.getPackageManager().getPackageInfo("com.monsterfit.monsterfit", 0).firstInstallTime;
            installed = installedTime;
            PackageManager pm = this.getPackageManager();
            PackageInfo packInfo = pm.getPackageInfo("com.monsterfit.monsterfit", 0);
            installed = packInfo.firstInstallTime;
        }catch (PackageManager.NameNotFoundException e){
        }
        return installed;
    }*/
}
