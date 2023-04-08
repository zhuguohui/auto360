package com.zhuguohui;

import com.zhuguohui.action.ActionTarget;

import java.awt.image.BufferedImage;

public class FoundResult implements ActionTarget {
  private    int foundX=-1;
    private    int foundY=-1;
    private   int width;
    private   int height;

    private BufferedImage foundImage;

    private static final FoundResult notFoundInstance=new FoundResult(-1,-1,0,0,null);
    public FoundResult(int foundX, int foundY, int width, int height,BufferedImage foundImage) {
        this.foundX = foundX;
        this.foundY = foundY;
        this.width = width;
        this.height = height;
        this.foundImage=foundImage;
    }

    public static FoundResult notFound(){
        return notFoundInstance;
    }

    public BufferedImage getFoundImage() {
        return foundImage;
    }

    public boolean isFound(){
        return foundX!=-1;
    }

    public int getFoundX() {
        return foundX;
    }

    public int getCenterX(){
        return foundX+width/2;
    }

    public int getCenterY(){
        return foundY+height/2;
    }

    public int getFoundY() {
        return foundY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int getActionX() {
        return getCenterX();
    }

    @Override
    public int getActionY() {
        return getCenterY();
    }
}
