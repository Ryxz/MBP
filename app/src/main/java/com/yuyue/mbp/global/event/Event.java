package com.yuyue.mbp.global.event;

/**
 * Created by Renyx on 2018/6/7
 * 总线事件
 */
public class Event {

    public int what;

    public int arg1;

    public float arg2;

    public Object obj;

    public static Event getEvent() {
        return new Event();
    }

    public static Event getEvent(int what) {
        Event event = getEvent();
        event.what = what;
        return event;
    }
}
