package com.ximalaya.m3u8.node;

import java.util.zip.CRC32;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
//        Integer seed = 1234;
        String seed = "cnr中国";
        CRC32 c = new CRC32();
        c.update(String.valueOf(seed).getBytes());
        System.out.println(c.getValue());
//        System.out.println( "Hello World!" );
//        Thread t = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while (true) {
//                    System.out.println(1);
//                    try {
//                        Thread.sleep(1000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, "worker-test");
//        t.start();
//        t
        System.out.println(System.getProperties().getProperty("os.name").equals("Mac OS X"));
        
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[] { "classpath:applicationContext.xml" });
    }
}
