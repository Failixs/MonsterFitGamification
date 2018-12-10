package com.monsterfit.monsterfit;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

   // public String installedTime = String.valueOf(daysInstalled());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
