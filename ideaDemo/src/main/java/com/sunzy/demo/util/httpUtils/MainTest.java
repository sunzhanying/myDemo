package com.sunzy.demo.util.httpUtils;

public class MainTest {
    public static void main(String[] args) {
        HTTPRequest httpRequest = new HTTPRequest();
        String s = httpRequest.httpRequestString("http://oa.senshishui.com:20000/default/order/customerlist/keywords//search/0/jointime/%E5%BC%80%E6%88%B7%E6%97%B6%E9%97%B4/sorttype/0/sort/ASC/page/3");

        //System.out.println(s);
    }
}
