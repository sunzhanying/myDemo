package com.sunzy.demo.util.reptile;

/**
 * @author sunzy
 * @date 2020/9/30
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ReptileMain {
    private static String urlNoSort = "http://oa.senshishui.com:20000/default/order/customerlist/page/";
    private static String url = "http://oa.senshishui.com:20000/default/order/customerlist/keywords//search/0/jointime/%E5%BC%80%E6%88%B7%E6%97%B6%E9%97%B4/sorttype/jointime/sort/ASC/page/";

    public static  void  main(String [] args) {

        for (int start = 1; start <= 2; start ++)  {
            try {
                //String address = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=" + start;
                String address = url + start;
                String currentStr = new GetJson().getHttpJson(address);
                //System.out.println("currentStr:" + currentStr);
                List<SenInfo> list = getDocFromStr(currentStr);
                //System.out.println(list);
                //如果是json格式
                //JSONObject jsonObject = JSON.parseObject(currentStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //String[] title = {"开户日期","客户编号","客户名称","电话","地址"};
    public static List<Object[]> getObjArr() {
        List<Object[]> list = new ArrayList<>();
        for (int start = 1; start <= 2; start ++) {
            try {

                String address = url + start;
                String currentStr = new GetJson().getHttpJson(address);
                List<SenInfo> listEntity = getDocFromStr(currentStr);
                for(SenInfo senInfo:listEntity){
                    String[] text = new String[5];
                    text[0] = senInfo.getBeginDate();
                    text[1] = senInfo.getCode();
                    text[2] = senInfo.getName();
                    text[3] = senInfo.getPhone();
                    text[4] = senInfo.getAddress();
                    list.add(text);
                }
            } catch (Exception e) {

            }
        }
        return list;
    }

    private static List<SenInfo> getDocFromStr(String str) throws Exception{
        Document document = Jsoup.parse(str);
        //解析HTML文件
        Elements td = document.getElementsByTag("td");
        int size = td.size();

        List<String> listStr = new ArrayList<>();
        for(int i = 0; i < size; i++){
            if(i < 6 || i == 96){//去掉前六个和最后一个
                continue;
            }
            Element element = td.get(i);
            String innerStr = element.html();
            listStr.add(innerStr);
            //System.out.println(innerStr);
        }
        List<String> listStr2 = new ArrayList<>();
        /*for(int m = 0; m < listStr.size(); m++ ){
            if(m % 6 == 0){
                continue;
            }
            String ss2 = listStr.get(m);
            listStr2.add(ss2);
        }*/

        List<List<String>> lists = averageAssign(listStr,15);

        List<SenInfo> list = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            SenInfo senInfo = new SenInfo();
            List<String> list1 = lists.get(i);
            senInfo.setBeginDate(list1.get(0));
            senInfo.setCode(list1.get(1));
            String nameTemp = getRemoveTagValue(list1.get(2));
            senInfo.setName(nameTemp);
            senInfo.setPhone(list1.get(3));
            String addressTemp = getRemoveTagValue(list1.get(4));
            senInfo.setAddress(addressTemp);
            //senInfo.setHref(list1.get(5));
            list.add(senInfo);
        }
        System.out.println(listStr);
        System.out.println(listStr2);
        System.out.println(lists);
        System.out.println(list);
        return list;
    }

    private static String getRemoveTagValue(String s) {
        try{
            Document document = Jsoup.parse(s);
            Elements elements = document.getElementsByTag("a");
            Element element = elements.get(0);
            String innerStr = element.html();
            return innerStr;
        }catch (Exception e){

        }
        return s;
    }

    /**
     * 将一组数据平均分成n组
     *
     * @param source 要分组的数据源
     * @param n      平均分成n组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }


    public static List<SenInfo> initDb(int start,int end) {
        List<SenInfo> listEntity = new ArrayList<>();
        for (int current = start; current <= end; current ++) {
            try {
                String address = url + current;
                String currentStr = new GetJson().getHttpJson(address);
                listEntity.addAll(getDocFromStr(currentStr));
            } catch (Exception e) {

            }
        }
        return listEntity;
    }
}
