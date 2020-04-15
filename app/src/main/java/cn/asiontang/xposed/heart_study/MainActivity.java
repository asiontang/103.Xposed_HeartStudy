package cn.asiontang.xposed.heart_study;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.widget.Toast;

import cn.asiontang.xposed.DebugModeUtils;

public class MainActivity extends Activity
{
    public synchronized boolean hasPermission(String permission)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        //在小米手机Android 6.0系统上测试'定位'权限时,单纯使用checkSelfPermission无法检测到正确的值,结合checkOp则刚好可以正确判断.
        int checkOp = ((AppOpsManager) getSystemService(Context.APP_OPS_SERVICE)).checkOp(AppOpsManager.permissionToOp(permission), Process.myUid(), getPackageName());
        return checkOp != AppOpsManager.MODE_IGNORED && checkPermission(permission, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            // 权限已经申请成功，在这里执行想要的权限操作
            updateNewApkFullPath();
        else
            //权限申请失败
            Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG)
        {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                updateNewApkFullPath();
            else if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                updateNewApkFullPath();
            else
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        else
            Toast.makeText(this, "PUBLIC MODE", Toast.LENGTH_SHORT).show();
    }

    private void updateNewApkFullPath()
    {
        if (DebugModeUtils.updateNewApkFullPathByRoot(getPackageName(), XposedHookLoadPackage.HOOK_PACKAGE_NAME))
            Toast.makeText(this, "DEBUG MODE: AllIsOk.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "DEBUG MODE: Error!!!!", Toast.LENGTH_LONG).show();
    }
}