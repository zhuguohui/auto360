package com.zhuguohui.action;

import com.zhuguohui.FoundResult;

public class OffsetTarget implements ActionTarget {
    FoundResult foundResult;
    //偏移量，0表示中间。1表示最右边。-1表示最左边
    float xOffset;
    //偏移量 0表示中间，1表示最下边。-1表示最上边
    float yOffset;

    int actionX;
    int actionY;

    /**
     *
     * @param foundResult 查找的结果
     * @param xOffset 偏移量，0表示中间。1表示最右边。-1表示最左边
     * @param yOffset   偏移量 0表示中间，1表示最下边。-1表示最上边
     */
    public OffsetTarget(FoundResult foundResult, float xOffset, float yOffset) {
        this.foundResult = foundResult;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        float offsetX=(xOffset+1)/2;
        actionX= (int) (foundResult.getFoundX()+foundResult.getWidth()*offsetX);

        float offsetY=(yOffset+1)/2;
        actionY= (int) (foundResult.getFoundY()+foundResult.getHeight()*offsetY);
    }

    @Override
    public int getActionX() {
        return actionX;
    }

    @Override
    public int getActionY() {
        return actionY;
    }
}
