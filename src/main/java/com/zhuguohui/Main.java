package com.zhuguohui;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    public static void captureScreen(String folderName, String fileName) throws Exception {

        Thread.sleep(3000);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.RED);
        graphics.drawRect(200,200,300,300);
        graphics.dispose();
        //保存路径
        File screenFile = new File(folderName);
        if (!screenFile.exists()) {
            screenFile.mkdir();
        }
        File f = new File(screenFile, fileName);

        ImageIO.write(image, "png", f);

        openFile(f);
    }

    public static void main(String[] args) throws Exception {
      /*  try {
            captureScreen(getDesktop()+"\\auto360Image","1.png");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/

       // File file=new File(Main.class.getResource("img/btn_login.png").getPath());
     //   openFile(file);
        String path= Main.class.getClassLoader().getResource("img/btn_login.png").toURI().getPath();
        System.out.println(path);
        openFile(new File(path));
    }

    private static String getDesktop(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();
        //桌面真实路径
        return com.getPath();
    }

    private static void openFile(File f) throws IOException {
        //自动打开
        if (Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
            Desktop.getDesktop().open(f);
    }

}