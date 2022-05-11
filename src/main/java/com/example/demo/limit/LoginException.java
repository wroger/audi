package com.example.demo.limit;

/**
 * @Author:wroger
 * @time：2022/05/10
 * @Discription：
 */
public class LoginException extends RuntimeException  {
    private static final long serialVersionUID = 1364225358754654702L;

    public LoginException(){
        super("用户名或者密码错误！");
    }

    public LoginException(String message){
        super(message);
    }
}