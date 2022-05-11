package com.example.demo.advice;

import lombok.Data;

@Data
public class ResultData<T> {
  /** 结果状态 ,具体状态码参见ResultData.java*/
  private int code;
  private String message;
  private T data;
  private long timestamp ;
 
 
  public ResultData (){
    this.timestamp = System.currentTimeMillis();
  }
 
  public void setCode(int value) {
    code = value;
  }

  public int getCode() {
    return code;
  }


  public void setMessage(String value) {
    message = value;
  }  
  public String getMessage() {
    return message;
  }
  public void setData(T value) {
    data = value;
  }

  public T getData() {
    return data;
  }

  public void setTimestamp(long value) {
    timestamp = value;
  }  
  public long getTimestamp() {
    return timestamp;
  }

  public static <T> ResultData<T> success(T data) {
    ResultData<T> resultData = new ResultData<>();
    resultData.setCode(ResultCode.RC100.getCode());
    resultData.setMessage(ResultCode.RC100.getMessage());
    resultData.setData(data);
    return resultData;
  }
 
  public static <T> ResultData<T> fail(int code, String message) {
    ResultData<T> resultData = new ResultData<>();
    resultData.setCode(code);
    resultData.setMessage(message);
    return resultData;
  }
}
 
