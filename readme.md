
1.监听事件源
2.统一处理
3.多种事件类型兼容
4.下发配置DSL自动收集
5.





## 数据类型定义

### 抽象打点事件类型

PointerEventType

```java
    APP_START("_AppStart", 1 << 0),
    APP_END("_AppEnd", 1 << 1),
    APP_CLICK("_AppClick", 1 << 2),
    APP_VIEW_SCREEN("_AppViewScreen", 1 << 3);
```

四种事件类型 APP启动 APP销毁 APP点击事件 APP屏幕事件


### 抽象点位

```java
    //产生事件的页面名
    private String PageName;
    //发生事件的view路径
    private ViewRoot ViewPath;
    //发生事件的类型
    private PointerEventType eventType;
    //需要上传的数据路径
    private String DataPath;
```


### 抽象唯一视图（保证每个视图唯一性和视图树改变时相对不变性）

```
    private String viewType; //view的类型
    private String itemPath; //view的路径
    private int index;  //view在父布局的index
    private View mView; //view本身
    private String completePath; //view完整路径
    private String clz;//view的class
```


## 事件源


### APP启动 APP销毁

问题：1.如何保证多进程问题 2.如何监听崩溃闪退或内存不足杀掉进程

ContentProvider+SP

Session概念
一个APP当一个页面退出 30S内没有新页面打开 认为处于后台
一个新页面显示 与上个页面间隔30S 表示返回前台

registerActivityLifecycle页面onPause倒计时30S 如果没有新页面触发 触发APP销毁事件
如果30S内有新Activity（这里可能是不同进程主线程的ACTIVITY）所以需要使用ContentProvider存标志位表示新界面进来 并清倒计时


页面启动判断是否与上一个超过30S 没有超过触发viewscreen


### APP屏幕事件

registerActivityCallback

registerFragmentCallback

问题：类似hometab的fragment showhide埋点如何兼容？

### APP点击事件

setonClickListener

onClick(View view)

兼容


AOP 缺点 编译时有效 对第三方或者依赖库无效

gradle transform 以gradle插件形式 可以遍历所有文件

.class->.dex hook

ASM-》处理class字节码 visit 也是gradle插件形式


javasisit plugin


### 事件解析



### 数据收集-DSL解析