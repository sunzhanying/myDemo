package com.sunzy.demo.util.reptile;

/**
 * @author sunzy
 * @date 2020/9/30
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Main {
    public static  void  main(String [] args) {

        /*int start;
        int total = 0;
        int end = 10;*/
        for (int start = 0; start < 2; start ++)  {
            try {
                //String address = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=" + start;
                String address = "http://oa.senshishui.com:20000/default/order/customerlist/page/1";
                JSONObject dayLine = new GetJson().getHttpJson(address, 1);

                System.out.println("start:" + start);
                JSONArray json = dayLine.getJSONArray("data");
                //List<Movie> list = JSON.parseArray(json.toString(), Movie.class);

                 System.out.println("已经爬取到底了");
                //total += list.size();
                //System.out.println("正在爬取中---共抓取:" + total + "条数据");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
