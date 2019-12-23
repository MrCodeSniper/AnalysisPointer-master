package cn.codesniper.pointer.unit;

import android.text.TextUtils;

/**
 * 采集的事件类型
 */
public enum PointerEventType {

    APP_START("_AppStart", 1 << 0),
    APP_END("_AppEnd", 1 << 1),
    APP_CLICK("_AppClick", 1 << 2),
    APP_VIEW_SCREEN("_AppViewScreen", 1 << 3);

    private final String eventName;
    private final int eventValue;

    public static PointerEventType getEventTypeByName(String eventName) {
        if (TextUtils.isEmpty(eventName)) {
            return null;
        }

        if ("$AppStart".equals(eventName)) {
            return APP_START;
        } else if ("$AppEnd".equals(eventName)) {
            return APP_END;
        } else if ("$AppClick".equals(eventName)) {
            return APP_CLICK;
        } else if ("$AppViewScreen".equals(eventName)) {
            return APP_VIEW_SCREEN;
        }

        return null;
    }

    PointerEventType(String eventName, int eventValue) {
        this.eventName = eventName;
        this.eventValue = eventValue;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventValue() {
        return eventValue;
    }
}