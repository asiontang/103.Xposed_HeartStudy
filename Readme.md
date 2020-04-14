[TOC]

# 103.Xposed_HeartStudy



## 破解步骤



### vPro 7.0.4-1 成功破解相关代码

```java

```



### 破解 验证失败

1. 定位到字符串ID`2131755136`

## 参考资料

1. [Xposed笔记.md](https://note.wiz.cn/web/web?dc=3a5a5503-87c8-4357-84fc-6bbd8ece9671&kb=&cmd=km%2C)

2. [XposedBridge可以替换非Java原生CPP方法吗？ ·问题＃52·rovo89 / XposedBridge·GitHub](https://github.com/rovo89/XposedBridge/issues/52) 

3. [[Q] Can Xposed hook native methods?](https://forum.xda-developers.com/xposed/xposed-hook-native-methods-t2817927) 

   > Yes, native methods can be hooked. However, in case this is for an app's code, it has to be done after System.loadLibrary(), otherwise the latter overwrites the hook. Ideally, the framework should take care of this itself, but it's not straight-forward and the has been vey little need for this.
   >
   > 
   >
   > Correct. Only JNI functions can be hooked, i.e. those which are declared in and called by Java code.
