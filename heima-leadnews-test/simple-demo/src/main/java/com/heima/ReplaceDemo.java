package com.heima;

public class ReplaceDemo {
    public static void main(String[] args) {
        String pathUrl = "http://101.34.245.48:9000/hmtt/2023/10/17/2a24867368544723a2686534d1cab105.jpg";
        String result = pathUrl.replace("http://101.34.245.48:9000" + "/", "");
        System.out.println(result);
    }
}
