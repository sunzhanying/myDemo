package com.sunzy.demo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author sunzy
 * @date 2020/8/13
 * LinkedBlockingQueue 的妙用，在异步执行时，可以设置最长等待时间
 */
public class MainTest {
    public static void main(String[] args) {
        try{
            long time1 = System.currentTimeMillis();
            LinkedBlockingQueue queue = new LinkedBlockingQueue();//如果队列在异步线程中，可以设置poll的最长等待时间
            Map<String,String> map = new HashMap<>();
            map.put("a","aa");
            /*for(int i = 0;i< 30;i++){
                if(i == 3){//模拟从redis中获取数据，如果能获取到
                    queue.put(map);//如果放值，不break跳出循环的话，也要阻塞到循环结束
                    break;//获取到值之后跳出循环
                }else{
                    Thread.sleep(100);//如果没有获取到就等待100毫秒
                }
            }*/
            new Thread(new Runnable() {//正确使用应该使用异步线程，如果queue中放了数据，下面最长等待3000毫秒
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);//模拟业务逻辑操作消耗时间，如果超过下面poll等待的3000毫秒，则最长3000毫秒
                        queue.put(map);//如果放值，不break跳出循环的话，也要阻塞到循环结束
                        Thread.sleep(5000);//等待多长都无所谓了，阻塞队列中已经put过，下面poll直接获取
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            Map m = (Map)queue.poll(3000, TimeUnit.MILLISECONDS);//timeout参数为等待时间，如果是-1的话不阻塞
            //System.out.println(m);
            long time2 = System.currentTimeMillis();
            //System.out.println("时间差：" + (time2 - time1));
        }catch (Exception e){

        }


    }
}
