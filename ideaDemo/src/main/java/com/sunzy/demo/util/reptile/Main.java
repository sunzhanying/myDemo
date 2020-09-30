package com.sunzy.demo.util.reptile;

/**
 * @author sunzy
 * @date 2020/9/30
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Main {
    public static  void  main(String [] args) {

        for (int start = 0; start < 2; start ++)  {
            try {
                //String address = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=" + start;
                String address = "http://oa.senshishui.com:20000/default/order/customerlist/page/1";
                String currentStr = new GetJson().getHttpJson(address);
                System.out.println("currentStr:" + currentStr);

                //如果是json格式
                //JSONObject jsonObject = JSON.parseObject(currentStr);
                 System.out.println("已经爬取到底了");
                //System.out.println("正在爬取中---共抓取:" + total + "条数据");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
