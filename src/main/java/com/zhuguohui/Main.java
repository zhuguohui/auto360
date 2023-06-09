package com.zhuguohui;

import com.zhuguohui.action.OffsetTarget;
import com.zhuguohui.action.impl.ClickAction;
import com.zhuguohui.action.impl.EnterAction;
import com.zhuguohui.action.impl.InputAction;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.InputEvent;
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


    }

    public static void doSelectedApk() throws Exception {
        String path="C:\\Users\\yhtx\\AndroidStudioProjects\\nmip_android\\app\\build\\outputs\\apk\\official\\release";
        String fileName="温度新闻_official_v6.1.1_time_202303022239_sign.apk";
        FoundResult result = ImageUtil.foundImageInScreen("img/win11_path_input.png", -1);
        InputAction action=new InputAction("输入地址");
        action.doAction(result,path,null,null);

        EnterAction enterAction=new EnterAction("回车");
        enterAction.doAction(null,null,null,null);
        Thread.sleep(500);

        FoundResult result2 = ImageUtil.foundImageInScreen("img/win11_file_input.png", -1);
        InputAction action2=new InputAction("输入文件名");
        action2.doAction(result2,fileName,null,null);

        FoundResult result3 = ImageUtil.foundImageInScreen("img/win11_file_open.png", -1);
        ClickAction clickAction=new ClickAction("点击打开文件");
        clickAction.doAction(result3,null,null,null);
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
        System.out.println("关闭360助手");
        RunUtil.executeCmd("taskkill /FI \"WINDOWTITLE eq 360加固助手\" /T /F");
        System.out.println("启动360加固助手");
        //RunUtil.executeCmd("start E:\\360jiagubao_windows_64\\360加固助手.exe");
        RunUtil.executeCmd("start E:\\360jiagubao_windows_64\\360加固助手.exe");


        long timeOut=10*1000;
        long startTime = System.currentTimeMillis();
        String path= Main.class.getClassLoader().getResource("img/btn_login.png").toURI().getPath();
        FoundResult result = ImageUtil.foundImageInScreen(path);
        while (!result.isFound()){
            result = ImageUtil.foundImageInScreen(path);
            Thread.sleep(1000);
        }


        //找到了

//        showFoundResult(result);


//        long useTime=System.currentTimeMillis()-startTime;
//        System.out.println("耗时"+useTime+"毫秒");

      //  System.out.println("开始登陆");

        ClickAction action=new ClickAction("登录操作");
        action.doAction(result,null,null,null);

        doAddApk();

        doSelectedApk();

        //System.out.println(path);

       // openFile(new File(path));
    }

    private static void doAddApk() throws Exception {
        FoundResult result = ImageUtil.foundImageInScreen("img/btn_add_apk.png",10*1000);
        if(result.isFound()){
            new ClickAction("添加apk").doAction(new OffsetTarget(result,-0.5f,0),null,null,null);
        }
    }

    private static void showFoundResult(FoundResult result) throws IOException {
        //保存
        String saveDir = getDesktop() + "\\auto360Image";
        String fileName="found.png";
        String savePath = ImageUtil.saveImageToPath(result.getFoundImage(), saveDir, fileName);
        ImageUtil.showImage(new File(savePath));
    }

    private static String getDesktop(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();
        //桌面真实路径
        return com.getPath();
    }



}