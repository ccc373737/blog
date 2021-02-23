package com.ccc.fizz;

import java.io.File;

public class Temp2 {
    public static void main(String[] args) {
        String path = "D:\\music";
        File file = new File(path);

        File[] files = file.listFiles();

        for (File file1 : files) {
            if (file1.getName().matches("^[y][2].*$")) {
                String name = file1.getName().substring(file1.getName().indexOf("-") + 2, file1.getName().indexOf("_")) + ".mp3";
                File newFile = new File(path + File.separator + name);
                System.out.println(newFile);
                file1.renameTo(newFile);
            }

        }

        /*File oldFile = new File("D:\\music" + File.separator + "y2mate.com - 08.他们 - 李志丨live 2009 工体东路没有人_Hnxjxx3i3nU_320kbps.mp3");
        File newFile = new File("D:\\music" + File.separator + "他们 - 李志.mp3");

        oldFile.renameTo(newFile);*/


       /* String name = "y2mate.com - 11.这个世界会好吗 - 李志丨live 2009 工体东路没有人_cycunRMALZk_320kbps.mp3";
        //System.out.println(name.substring(name.indexOf("-") + 2, name.indexOf("_")) + ".mp3");

        System.out.println(name.matches("^[y][2].*$"));*/
    }
}
