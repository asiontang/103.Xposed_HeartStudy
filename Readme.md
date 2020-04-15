[TOC]

# 103.Xposed_HeartStudy

解决了非华为手机无法使用新版本《心脏健康研究(com.plagh.heartstudy)》APP的问题.



##  如何解决 在非华为手机上使用心脏健康研究APP



### 成功的解决方案3: Root后直接修改系统文件模拟华为手机

#### 使用方案

1. **ROOT**手机
2. 安装`Solid Explorer`
3. 打开`/system/build.prop `编辑
4. 在首行添加并保存`ro.build.hw_emui_api_level=10`
5. 重启手机即可.

#### 参考资料

1. [adb获取Android系统属性（adb shell getprop ***）_移动开发_hqzxsc2006的专栏-CSDN博客](https://blog.csdn.net/hqzxsc2006/article/details/55254391) 

   > 1. /system/build.prop 

2. [adb shell ——通过adb开启应用、获取SystemProperties值_移动开发_忘冻鱼的博客-CSDN博客](https://blog.csdn.net/wdyshowtime/article/details/77852912) 

   > 定义的这些字段名：ro.开头的属性将不能被更改属性值，persist.开头的属性会被永久纪录，其他属性值在重新开机后均将被丢弃。

3. [Android系统开发之Systemproperties的反射用法及如何自定义名称 - 仗剑长行 - SegmentFault 思否](https://segmentfault.com/a/1190000006191769) 

   > 编译不会出错，但什么也写不进去，当然也读不出来。
   > 我们的方法没有错，错的是规则，那么我们就来修改规则。
   > 打开external/sepolicy/property_contexts，加上一句：



### 成功的解决方案2: 使用别的已有的Xposed模块模拟华为手机

既然能安装Xposed环境那么则理论上用别的Xposed模块模拟华为手机也是可以成功的.

#### 使用方法

1. **ROOT**手机

2. 安装面具Magisk

3. 安装面具模块  `Riru - Core`

4. 安装面具模块  `Riru - EdXposed(YAHFA)`

5. 安装EdXposed Manager

6. 在EdXposed Manager里应用白名单勾选"心脏健康研究"

7. 安装以下任意版本激活并重启

   > * 应用变量
   > * Device Emulator Pro(com.device.emulator.pro)v3.53[URET(a5f7ac1a)]
   > * Device Faker(com.devicefaker.free)v1.0.0[Lam Phuong(e6112ac3)]

8. 在对应的APP里模拟厂商=`HUAWEI`

9. 即可正常使用



### 成功的解决方案1:使用自定义Xposed模块模拟华为手机

#### 使用方法

1. **ROOT**手机

2. 安装面具Magisk

3. 安装面具模块  `Riru - Core`

4. 安装面具模块  `Riru - EdXposed(YAHFA)`

5. 安装EdXposed Manager

6. 安装`103.Xposed_HeartStudy`

   > * 包名:cn.asiontang.xposed.heart_study
   > * 版本:v200415.01.03.003
   > * 签名:AsionTang(10f65e31)

7. 在EdXposed Manager里应用白名单勾选"心脏健康研究"

8. 在EdXposed Manager里模块列表勾选"103.Xposed_HeartStudy"

9. 重启手机后即可正常使用

#### 兼容性

测试条件:

1. 手环固件版本:`1.1.0.122`
1. 使用运动健康APP能正常同步数据

以下版本在Android 9.0 AospExtended 系统的手机测试成功

1. 心脏健康研究(com.plagh.heartstudy)v2.4.0[HeartStudy(28206cd0)] 

#### 原理

1. 模拟华为手机的系统属性`ro.build.hw_emui_api_level` >=9 即可

```java

private boolean b(String p0){   
   p0 = ("HUAWEI".equals(p0) || (p0 = "HONOR".equals(p0)))? true : false;  
return p0;
}
private void d(){
String v0 = Build.BRAND.toUpperCase(Locale.ENGLISH);
a.c( this .String, new StringBuilder()+ "brand:" +v0);
if (! this .e() && ! this .b(v0)) {
this .f();
return ;
} else if (v.b( "first_income" , false )){
this .h();
} else {
this .startActivityForResult( new Intent( this , GuideActivity. class ), 10 );
}
return ;
}

private boolean e(){
Class v2 = Class.forName( "android.os.SystemProperties" );
Class[] v4 = new Class[ 1 ];
v4[ 0 ]=String. class ;
Object[] v4_1 = new Object[ 1 ];
v4_1[ 0 ]= "ro.build.hw_emui_api_level" ;
v2 = Integer.parseInt(v2.getDeclaredMethod( "get" , v4).invoke(v2, v4_1));
a.c( this .String, new StringBuilder()+ "emuiApiLevel: " +v2);
if (v2 >= 9 ) {
return true ;
}
return false ;
}

```

### 部分成功方案:直接通过启动页面

1. ROOT手机
1. 安装` 运动健康`
1. 安装` HMS Core`
1. 安装` 心脏健康研究`
1. 通过ROOT权限直接跳过Splash页面进入登录页面即可
1. 每次打开都需要通过ROOT权限直接打开Main界面.
1. 直接使用AutoJS创建一个快捷打开脚本
   
    ```js
    //cn.asiontang.autojs.js002
    var options = ["登录界面", "主页面"]
    var i = dialogs.select("请选择要强制打开的界面", options);
    switch (i) //
    {
        case 0:
            let a1 = "am start -n com.plagh.heartstudy/com.plagh.heartstudy.view.activity.LoginRegistActivity";
            shell(a1, true);
            break;
        case 1:
            let a2 = "am start -n com.plagh.heartstudy/com.plagh.heartstudy.view.activity.MainActivity"
            shell(a2, true);
            break;
        default:
            toast("您取消了选择");
            break;
    }
    ```
    
1. **可惜**最终无法正常和手环同步数据,各种尝试都**提示同步失败.**

#### 尝试2:安装 `v2.4.0`最新 版本APP试试

1. 结果:失败

1. 能正常登录和配对

1. 点击我的设备 提示` 已连接` 

1. 点击设备后 ` 智能适时测量开关` 状态显示 `已开启`

1. 点击`心脏健康研究项目`后提示`开启失败`

   > ` 智能适时测量开关` 为 灰色 ` 未启用`   状态

1. 就是提示`数据同步失败,请下拉刷新再次同步`

1. 进入 "心率失常检测" - 点击开始测试 - 重新测试 后 出现`数据传输异常,此次测量失败.请稍后尝试`



#### 尝试1:安装 `v1.0.19` 旧版本APP试试

1. 结果:失败

1. 能正常登录和配对

1. 就是提示`数据同步失败,请下拉刷新再次同步`

1. 点击开始测试 - 重新测试 后 出现空白信号质量之后就没有任何响应了

1. 点击我的设备 提示` 已连接` 

1. 点击设备后 提示`开启失败`

   > 智能适时测量开关 为 灰色 ` 未启用` 状态

### 个人尝试使用各种分身APP无效

1. 尝试过几款支持模拟机型的分身APP
2. 尝试安装过旧版本的心脏健康研究APP
3. 最终结果都是无法正常使用






## 参考资料

1. [Xposed笔记.md](https://note.wiz.cn/web/web?dc=3a5a5503-87c8-4357-84fc-6bbd8ece9671&kb=&cmd=km%2C)

2. [XposedBridge可以替换非Java原生CPP方法吗？ ·问题＃52·rovo89 / XposedBridge·GitHub](https://github.com/rovo89/XposedBridge/issues/52) 

3. [[Q] Can Xposed hook native methods?](https://forum.xda-developers.com/xposed/xposed-hook-native-methods-t2817927) 

   > Yes, native methods can be hooked. However, in case this is for an app's code, it has to be done after System.loadLibrary(), otherwise the latter overwrites the hook. Ideally, the framework should take care of this itself, but it's not straight-forward and the has been vey little need for this.
   >
   > 
   >
   > Correct. Only JNI functions can be hooked, i.e. those which are declared in and called by Java code.
