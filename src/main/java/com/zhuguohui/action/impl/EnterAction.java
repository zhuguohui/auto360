package com.zhuguohui.action.impl;

import com.zhuguohui.action.ActionTarget;
import com.zhuguohui.action.Function1;
import com.zhuguohui.action.MockAction;

import java.awt.event.KeyEvent;

/**
 * 执行回车
 */
public class EnterAction extends MockAction<Void> {
    public EnterAction(String opName) {
        super(opName);
    }

    @Override
    public void doAction(ActionTarget target, Void unused, Function1<Void> successCallBack, Function1<Throwable> errorCallBack) {
        pressSingleKeyByNumber(KeyEvent.VK_ENTER);
    }
}
