package com.yuyue.mbp.ui.base;

import com.yuyue.mbp.global.event.Event;

/**
 * Created by Renyx on 2018/6/7
 * 总线事件监听
 */
public interface EventListener {

    public void onEventMainThread (Event event);
}
