package cn.codesniper.pointer.process;


import java.util.HashMap;

import cn.codesniper.pointer.constant.TraceBiz;

/**
 * 从ViewID到埋点事件的转换
 */
public class EventParser {

    /**
     * TraceBiz:ViewPath
     */
    private HashMap<String, String> traceMap;

    private static volatile EventParser singleton;

    private EventParser() {
       traceMap=new HashMap<>();
       traceMap.put("ContentView/ClickTraceActivity[0]/FrameLayout[0]/LinearLayout[0]/Button[0]",TraceBiz.EVENT_CLICK_MEETING_DETAIL);
    }

    public static EventParser getInstance() {
        if (singleton == null) {
            synchronized (EventParser.class) {
                if (singleton == null) {
                    singleton = new EventParser();
                }
            }
        }
        return singleton;
    }


    public void addTracePath(String biz,String viewPath){
        traceMap.put(biz,viewPath);
    }


    public String getTraceBiz(String viewPath){
        return traceMap.get(viewPath);
    }


}
