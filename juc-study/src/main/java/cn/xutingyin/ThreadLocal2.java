package cn.xutingyin;

import java.lang.ThreadLocal;
import java.util.concurrent.TimeUnit;

public class ThreadLocal2 {
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // tl.set(new Person());
            // 这里取不到值，返回java.lang.NullPointerException
            // 因为 ThreadLocal里面设置的值是线程独有的，只有自己线程里面才能访问到Person,如果另外的线程需要访问，只有在自己线程里进行设置
            System.out.println(tl.get().name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Person p2 = new Person();
            p2.name = "lisi";
            tl.set(p2);
        }).start();
    }

    static class Person {
        String name = "zhangsan";
    }
}