package cn.asiontang.xposed.heart_study;

import cn.asiontang.xposed.LogEx;
import cn.asiontang.xposed.XposedUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Auto.js Pro(org.autojs.autojspro)vPro 7.0.4-1[Auto.js Pro(f5b1e878)]
 */
public class XposedHookClass_v7_0_4_1
{
    private static final String TAG = XposedHookClass_v7_0_4_1.class.getName();

    /**
     * <pre>
     * </pre>
     * <p><b>测试用例如下:</b></p>
     * <pre>
     * </pre>
     */
    public static void hook(final XC_LoadPackage.LoadPackageParam loadPackageParam)
    {
        try
        {
            XposedUtils.realHookAllMethods("android.os.SystemProperties", loadPackageParam.classLoader, "get", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable
                {
                    if (BuildConfig.DEBUG)
                        LogEx.log(TAG, param.args, param.getResult(), param.method);

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
