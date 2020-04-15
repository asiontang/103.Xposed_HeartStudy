package cn.asiontang.xposed.heart_study;

import cn.asiontang.xposed.BaseXposedHookLoadPackage;
import cn.asiontang.xposed.LogEx;
import cn.asiontang.xposed.XposedUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedHookLoadPackage extends BaseXposedHookLoadPackage
{
    public static final String HOOK_PACKAGE_NAME = "com.plagh.heartstudy";
    private static final String TAG = XposedHookLoadPackage.class.getName();

    public boolean handleLoadPackage4release(final XC_LoadPackage.LoadPackageParam loadPackageParam)
    {
        //==========================================================================================
        // 需要 Hook 哪些应用?
        //==========================================================================================
        if (!loadPackageParam.packageName.equalsIgnoreCase(HOOK_PACKAGE_NAME))
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
                if (!param.thisObject.getClass().getName().equalsIgnoreCase("com.plagh.heartstudy.base.MyApplication"))
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
        try
        {
            XposedUtils.realHookAllMethods("android.os.SystemProperties", loadPackageParam.classLoader, "get", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable
                {
                    //if (BuildConfig.DEBUG)
                    //    LogEx.log(TAG, param.args, param.getResult(), param.method);

                    if (param.args != null && param.args.length == 1 && param.args[0].equals("ro.build.hw_emui_api_level"))
                        param.setResult("10");
                }
            });
        }
        catch (Throwable e)
        {
            LogEx.log(TAG, "hook:classLoader", loadPackageParam.classLoader);
            LogEx.log(TAG, "hook Exception", e);
        }
    }
}