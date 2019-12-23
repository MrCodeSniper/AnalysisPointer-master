package cn.codesniper.pointer.unit;

/**
 * 打点配置
 */
public class Point {

    //产生事件的页面名
    private String PageName;
    //发生事件的view路径
    private ViewRoot ViewPath;
    //发生事件的类型
    private PointerEventType eventType;
    //需要上传的数据路径
    private String DataPath;

    @Override
    public String toString() {
        return "Point{" +
                "PageName='" + PageName + '\'' +
                ", ViewPath=" + ViewPath.getCompletePath() +
                ", eventType=" + eventType.getEventName() +
                ", DataPath='" + DataPath + '\'' +
                '}';
    }

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
    }

    public ViewRoot getViewPath() {
        return ViewPath;
    }

    public void setViewPath(ViewRoot viewPath) {
        ViewPath = viewPath;
    }

    public PointerEventType getEventType() {
        return eventType;
    }

    public void setEventType(PointerEventType eventType) {
        this.eventType = eventType;
    }

    public String getDataPath() {
        return DataPath;
    }

    public void setDataPath(String dataPath) {
        DataPath = dataPath;
    }


    public String toJson(){
        return "";
    }
}
