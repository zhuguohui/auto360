package com.zhuguohui;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageUtil {
    private static   Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将图片转化为对应的像素值
     * @param img
     * @return
     */
    public static int[][] getPixels(BufferedImage img){

        int [][] data = new int[img.getHeight()][img.getWidth()];
        //方式一：通过getRGB()方式获得像素矩阵
        //此方式为沿Height方向扫描
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                data[y][x]=img.getRGB(x,y);
            }
        }
        return data;
    }

    /**
     * 获取屏幕截图
     * @return
     */
    public static BufferedImage getScreen(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        return robot.createScreenCapture(screenRectangle);
    }

    /**
     *
     * @param screenPixels
     * @param targetPixels
     * @param startX
     * @param startY
     * @param minMatchRatio 最低的对比差异。0-1 1表示100%匹配
     * @return
     */
    public static boolean matchImage(int[][] screenPixels, int[][] targetPixels, int startX, int startY,float minMatchRatio) {
        int height=targetPixels.length;
        int width=targetPixels[0].length;
        //使用关键点匹配来减少计算量。提高性能
//        KeyPoint p1=new KeyPoint(screenPixels,startX,startY,width,height);
//        KeyPoint p2=new KeyPoint(targetPixels,0,0,width,height);
//        if(!p1.equals(p2)){
//            return false;
//        }
        int pixelsCount=targetPixels[0].length*targetPixels.length;
        int maxMisCount= (int) ((1-minMatchRatio)*pixelsCount);
        int misMatchCount=0;
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                int targetP=targetPixels[y][x];
                int screenP=screenPixels[y+startY][x+startX];
                if(targetP!=screenP){
                    misMatchCount++;
                    if(misMatchCount>=maxMisCount) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param resPath
     * @param timeOut 超时时间，-1表示一直查找 ,单位毫秒
     * @return
     * @throws Exception
     */
    public static FoundResult foundImageInScreen(String resPath,long timeOut) throws Exception{
        System.out.println("查找图片:"+resPath);
        String path= Main.class.getClassLoader().getResource(resPath).toURI().getPath();
        long statTime=System.currentTimeMillis();
        FoundResult result = ImageUtil.foundImageInScreen(path);
        while (!result.isFound()){
            long useTime=System.currentTimeMillis()-statTime;
            boolean isTimeOut=timeOut>=0&&useTime>timeOut;
            if(isTimeOut){
                throw new RuntimeException("在"+timeOut+"毫秒内没有在屏幕中找到图片:"+resPath);
            }

            result = ImageUtil.foundImageInScreen(path);
            Thread.sleep(1000);
        }
        return result;
    }

    public static FoundResult foundImageInScreen(String path) throws IOException {
        BufferedImage targetImage = ImageIO.read(new File(path));
        BufferedImage screen = ImageUtil.getScreen();
        int[][] targetPixels = ImageUtil.getPixels(targetImage);
        int[][] screenPixels = ImageUtil.getPixels(screen);
        int sh = screen.getHeight();
        int sw = screen.getWidth();
        int targetHeight=targetImage.getHeight();
        int targetWidth=targetImage.getWidth();
        int scanHeight=sh-targetHeight;
        int scanWidth=sw-targetWidth;
        int foundX=-1;
        int foundY=-1;
        for(int y=0;y<scanHeight;y++){
            for(int x=0;x<scanWidth;x++){
                //完全匹配
                boolean isMath=ImageUtil.matchImage(screenPixels,targetPixels,x,y,0.95f);
                if(isMath){
                    foundX=x;
                    foundY=y;
                    x=scanWidth;
                    y=scanHeight;
                }
            }
        }
        if(foundX!=-1){
            BufferedImage foundImage = createFoundImage(screen, foundX, foundY, targetWidth, targetHeight);
            return new FoundResult(foundX,foundY,targetWidth,targetHeight,foundImage);
        }else{
            return FoundResult.notFound();
        }
    }

    private static BufferedImage createFoundImage(BufferedImage screen,int foundX,int foundY,int targetWidth,int targetHeight){
        //画出来
        Graphics2D graphics = screen.createGraphics();
        graphics.setColor(Color.RED);
        graphics.drawRect(foundX,foundY,targetWidth,targetHeight);

        int centerX=foundX+targetWidth/2;
        int centerY=foundY+targetHeight/2;
        graphics.fillOval(centerX,centerY,10,10);
        graphics.dispose();
        return screen;
    }



    private static class KeyPoint{
        int[] points=new int[5];
        public KeyPoint(int[][] pixels,int x,int y,int width,int height) {
            //左上
            points[0]= pixels[y][x];
            //右上
            points[1]=pixels[y][x+width-1];
            //右下
            points[2]=pixels[y+height-1][x+width-1];
            //左下
            points[3]=pixels[y+height-1][x];
            //中间
            points[4]=pixels[y+height/2][x+width/2];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeyPoint keyPoint = (KeyPoint) o;
            return Arrays.equals(points, keyPoint.points);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(points);
        }
    }

    public static String saveImageToPath(BufferedImage image,String folderName,String fileName){
        //保存路径
        File screenFile = new File(folderName);
        if (!screenFile.exists()) {
            screenFile.mkdir();
        }
        File f = new File(screenFile, fileName);

        try {
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return f.getPath();
    }

    public static void showImage(File f) throws IOException {
        //自动打开
        if (Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
            Desktop.getDesktop().open(f);
    }
}
