package com.zhuguohui.action;

import java.awt.*;

public abstract class MockAction<ARG> {
    //模拟点击
   static protected Robot robot;
   protected String opName;

    public MockAction(String opName) {
        this.opName = opName;
    }

    static {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
    }

  public abstract   void doAction(ActionTarget target, ARG arg, Function1<Void> successCallBack, Function1<Throwable> errorCallBack);
}
