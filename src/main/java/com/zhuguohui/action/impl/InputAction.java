package com.zhuguohui.action.impl;

import com.zhuguohui.action.ActionTarget;
import com.zhuguohui.action.Function1;
import com.zhuguohui.action.MockAction;

import java.awt.event.KeyEvent;

public class InputAction extends MockAction<String> {

    public InputAction(String opName) {
        super(opName);
    }

    @Override
    public void doAction(ActionTarget target, String s, Function1<Void> successCallBack, Function1<Throwable> errorCallBack) {
        //先点击
        ClickAction clickAction=new ClickAction("点击文本框");
        clickAction.doAction(target,null,null,null);
        //ctrl+a 全选
        pressMultipleKeyByNumber(KeyEvent.VK_CONTROL,KeyEvent.VK_A);
        //删除键 删除
        pressSingleKeyByNumber(KeyEvent.VK_DELETE);
        //将文本放入剪贴板
        setClipboardString(s);
        //ctrl+v
        pressMultipleKeyByNumber(KeyEvent.VK_CONTROL,KeyEvent.VK_V);
        //结束

    }
}
