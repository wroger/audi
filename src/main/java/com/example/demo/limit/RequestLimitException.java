package com.example.demo.limit;

/**
 * @Author:wroger
 * @time：2022/05/10
 * @Discription：
 */
public class RequestLimitException extends Exception {
    private static final long serialVersionUID = 1364225358754654702L;

    public RequestLimitException(){
        super("访问过于频繁！");
    }

    public RequestLimitException(String message){
        super(message);
    }
}