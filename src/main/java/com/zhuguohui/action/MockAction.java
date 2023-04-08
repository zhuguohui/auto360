package com.zhuguohui.action;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public abstract class MockAction<ARG> {
    //模拟点击
   static protected Robot robot;
   protected String opName;

    public MockAction(String opName) {
        this.opName = opName;
        System.out.println("执行"+opName+"操作");
    }

    static {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
    }

  public abstract   void doAction(ActionTarget target, ARG arg, Function1<Void> successCallBack, Function1<Throwable> errorCallBack);

    /**
     * 按下组合键，如 ctrl + c、ctrl + v、alt + tab 等等
     *
     * @param keycode：组合健数组，如 {KeyEvent.VK_CONTROL,KeyEvent.VK_V}
     */
    protected void pressMultipleKeyByNumber(int... keycode) {
        try {
            Robot robot = new Robot();

            //按顺序按下健
            for (int i = 0; i < keycode.length; i++) {
                robot.keyPress(keycode[i]);
                robot.delay(50);
            }

            //按反序松开健
            for (int i = keycode.length - 1; i >= 0; i--) {
                robot.keyRelease(keycode[i]);
                robot.delay(50);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟按下键盘单个按键，比如文档下一页：PgDn，上一页是PgUp等
     *
     * @param keycode：按键的值,如：KeyEvent.VK_PAGE_UP
     */
    protected  final void pressSingleKeyByNumber(int keycode) {
        try {
            /** 创建自动化测试对象  */
            Robot robot = new Robot();
            /**按下按键*/
            robot.keyPress(keycode);
            /**松开按键*/
            robot.keyRelease(keycode);
            /**可以稍作延时处理*/
            robot.delay(500);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    /**
     * 把文本设置到剪贴板（复制）
     */
    protected  final void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }


}
