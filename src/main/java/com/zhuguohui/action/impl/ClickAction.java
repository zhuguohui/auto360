package com.zhuguohui.action.impl;

import com.zhuguohui.action.Function1;
import com.zhuguohui.action.MockAction;
import com.zhuguohui.action.ActionTarget;

import java.awt.event.InputEvent;

/**
 * 执行点击操作
 */
public class ClickAction extends MockAction<Void> {
    public ClickAction(String opName) {
        super(opName);
    }

    @Override
    public void doAction(ActionTarget target, Void unused, Function1<Void> successCallBack, Function1<Throwable> errorCallBack) {

        int k=10;
        //win10上有问题，多调几次确保成功
        while((--k)>0)
        {
            robot.mouseMove(target.getActionX(), target.getActionY());
        }

        robot.delay(1000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        if(successCallBack!=null){
            successCallBack.call(null);
            System.out.println("执行"+opName+"成功");
        }
    }
}
