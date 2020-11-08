/**
 * fshows.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author CoderMa
 * @version MyClassLoader.java, v 0.1 2020-11-07 6:37 下午 CoderMa
 */
public class MyClassLoader extends ClassLoader {

    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 将文件转换成字节数组
     */
    public static byte[] getFileByte(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream((int) file.length());
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            fis.close();
            bos.close();
        }
    }

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/coderma/coders/fshows/lifecircle/deep-in-java/week-01/Hello.xlass";
        MyClassLoader myClassLoader = new MyClassLoader(filePath);
        Class<?> clazz = myClassLoader.loadClass("Hello");
        Method method = clazz.getMethod("hello");
        method.invoke(clazz.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //获取文件数组
            byte[] fileByte = getFileByte(classPath);
            //解码
            byte[] classByte = new byte[fileByte.length];
            for (int i = 0; i < fileByte.length; i++) {
                classByte[i] = (byte) ((byte) 255 - fileByte[i]);
            }
            Class<?> c = this.defineClass(name, classByte, 0, classByte.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

}