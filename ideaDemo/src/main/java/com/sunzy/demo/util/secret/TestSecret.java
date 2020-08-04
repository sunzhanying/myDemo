package com.sunzy.demo.util.secret;

/**
 * @author sunzy
 * @date 2020/8/4
 */




public class TestSecret {
    //设置的签名校验时间,超过当前时间签名无效,单位毫秒
    private static final long SIGN_FAIL_TIME = 15 * 60 * 1000;

    public static void main(String[] args) {
        //String timestamp = paramJson.getString("timestamp");
        boolean boo = chackTime();

    }

    private static boolean chackTime() {
        String timestamp = "1231231231231";
        if (timestamp == null || "".equals(timestamp)) {
            return false;
        }
        long timeMillis = System.currentTimeMillis();
        long timeDiff = timeMillis - Long.parseLong(timestamp);
        //判断入参时间戳和当前时间进行比较,超过一定时间失效
        if ((timeDiff < 0 ? -timeDiff : timeDiff) > SIGN_FAIL_TIME) {
            return false;
        }
        return false;
    }
}
