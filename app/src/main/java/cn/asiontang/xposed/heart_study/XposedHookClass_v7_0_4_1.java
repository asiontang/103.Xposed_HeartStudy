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
            XposedUtils.realHookAllMethods("m.c.a.f.c.c.c", loadPackageParam.classLoader, "d", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable
                {
                    if (BuildConfig.DEBUG)
                        LogEx.log(TAG, "成功重置m.c.a.f.c.c.c.d()的返回结果=true；原始结果=" + param.getResult());
                    param.setResult(true);

                    LogEx.log(TAG, "Hook.Point.1", "OK");
                }
            });

            XposedUtils.realHookAllMethods("org.autojs.autojs.tool.Security", loadPackageParam.classLoader, "a", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable
                {
                    if (param.method.getName().equals("a") && (param.args == null || param.args.length == 0))
                    {
                        param.setResult(new byte[0]);

                        if (BuildConfig.DEBUG)
                            LogEx.log(TAG
                                    , "param.method=", param.method
                                    , "param.getResult新结果=new byte[0]");

                        LogEx.log(TAG, "Hook.Point.2", "OK");
                    }
                    else
                        LogEx.log(TAG
                                , "param.method=", param.method
                                , "param.method.getName()=", param.method.getName()
                                , "param.args=", param.args
                                , "param.getResult原始结果=", param.getResult());
                }
            });

            XposedUtils.realHookAllMethods("org.autojs.autojs.tool.Security", loadPackageParam.classLoader, "isValidData", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable
                {
                    if (BuildConfig.DEBUG)
                        LogEx.log(TAG
                                , "param.method=", param.method
                                , "param.method.getName()=", param.method.getName()
                                , "param.args=", param.args
                                , "param.getResult原始结果=", param.getResult());
                }
            });
            XposedUtils.realHookAllMethods("org.autojs.autojs.tool.Security", loadPackageParam.classLoader, "anyServicesAvailable", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable
                {
                    if (BuildConfig.DEBUG)
                        LogEx.log(TAG
                                , "param.method=", param.method
                                , "param.method.getName()=", param.method.getName()
                                , "param.args=", param.args
                                , "param.getResult原始结果=", param.getResult()
                                , "param.getResult新结果=true");
                    param.setResult(true);
                    LogEx.log(TAG, "Hook.Point.3", "OK");
                }
            });

            //通过str, " must not be null 来获取程序进度.
            XposedUtils.realHookAllMethods("i.d.b.i", loadPackageParam.classLoader, "a", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable
                {
                    if (param.args != null && param.args.length == 2
                            && param.method.toString().equals("public static boolean i.d.b.i.a(java.lang.Object,java.lang.Object)"))
                        if ("org.autojs.autojs.ui.main.MainActivity$b".equals(param.args[1])
                                || "m.c.a.j.b.K".equals(param.args[1])
                                || "m.c.a.j.b.O".equals(param.args[1]))
                        {
                            param.setResult(true);

                            LogEx.log(TAG, "Hook.Point.4", "OK");

                            return;
                        }

                    if (BuildConfig.DEBUG)
                        LogEx.log(TAG
                                , "param.method=", param.method
                                , "param.args=", param.args
                                , "param.thisObject=", param.thisObject);
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
