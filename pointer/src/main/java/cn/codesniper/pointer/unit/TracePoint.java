package cn.codesniper.pointer.unit;

/**
 * 打点事件实体
 */
public class TracePoint {

    //产生事件的页面名
    private String PageName;
    //产生事件的模块名
    private String ModuleName;
    //根据业务扩展 埋点信息描述
    private String BizName;
    //发生事件的view路径
    private TraceViewRoot ViewPath;
    //发生事件的类型
    private PointerEventType eventType;
    //需要上传的数据路径
    private String DataPath;

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    public String getBizName() {
        return BizName;
    }

    public void setBizName(String bizName) {
        BizName = bizName;
    }

    public TraceViewRoot getViewPath() {
        return ViewPath;
    }

    public void setViewPath(TraceViewRoot viewPath) {
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

    public String  getCompleteViewPath(){
        if(ViewPath!=null){
            return ViewPath.getCompletePath();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Point{" +
                "PageName='" + PageName + '\'' +
                ", ModuleName='" + ModuleName + '\'' +
                ", BizName='" + BizName + '\'' +
                ", ViewPath=" + ViewPath +
                ", eventType=" + eventType +
                ", DataPath='" + DataPath + '\'' +
                '}';
    }
}
