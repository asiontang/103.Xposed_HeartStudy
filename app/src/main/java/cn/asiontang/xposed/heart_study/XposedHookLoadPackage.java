package cn.asiontang.xposed.heart_study;

import cn.asiontang.xposed.BaseXposedHookLoadPackage;
import cn.asiontang.xposed.LogEx;
import cn.asiontang.xposed.XposedUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedHookLoadPackage extends BaseXposedHookLoadPackage
{
    private static final String TAG = XposedHookLoadPackage.class.getName();

    public boolean handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam)
    {
        //==========================================================================================
        // 需要 Hook 哪些应用?
        //==========================================================================================
        if (!loadPackageParam.packageName.equalsIgnoreCase("com.plagh.heartstudy"))
        {
            LogEx.log(TAG, loadPackageParam.packageName, "Hooked:NoMatched");
            return false;
        }

        //==========================================================================================
        // 需要解决的问题:
        //　　1. 直接在`handleLoadPackage` 里直接 `Hook`真正APP的某个特定类时会出现`java.lang.ClassNotFoundException: Didn't find class`错误
        //   　　> 原因是:默认首次加载的 `ClassLoader` 是加固厂家的固定 Application.只是负责解密用的,里面当然还没有APP真正的类相关信息的.
        //
        // 解决方案:
        //　　1. 既然Hook的时机默认调用太早了,那么想办法延后一点, 比如等到APP真正的Application.onCreate之后再 Hook 特定类.
        //==========================================================================================
        XposedUtils.realHookAllConstructors("android.app.Application", loadPackageParam.classLoader, new XC_MethodHook()
        {
            @Override
            protected void afterHookedMethod(final MethodHookParam param)
            {
                if (!param.thisObject.getClass().getName().equalsIgnoreCase("org.autojs.autojs.App"))
                {
                    LogEx.log(TAG, loadPackageParam.packageName, "Application", param.thisObject, "NoMatched");
                    return;
                }
                LogEx.log(TAG, loadPackageParam.packageName, "Application", param.thisObject, "Matched", "Hooking");

                handleLoadPackage4releaseWhenOnPerApplicationInit(loadPackageParam);

                LogEx.log(TAG, loadPackageParam.packageName, "Application", param.thisObject, "Matched", "Hooked");
            }
        });
        return true;
    }

    /**
     * 每个Application实例化时都会触发此Hook函数.
     */
    private void handleLoadPackage4releaseWhenOnPerApplicationInit(final XC_LoadPackage.LoadPackageParam loadPackageParam)
    {
        XposedHookClass_v7_0_4_1.hook(loadPackageParam);
    }
}