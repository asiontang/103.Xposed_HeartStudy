package cn.asiontang.xposed.heart_study;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import cn.asiontang.xposed.DebugModeUtils;

public class MainActivity extends Activity
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG)
        {
            if (DebugModeUtils.updateNewApkFullPath())
                Toast.makeText(this, "DEBUG MODE: AllIsOk.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "DEBUG MODE: Error!!!!", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "PUBLIC MODE", Toast.LENGTH_SHORT).show();
    }
}