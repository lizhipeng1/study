package com.study.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lzp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    String respCode;

    String respMsg;


    public static BaseResponse success(){
        return new BaseResponse("0000","success");
    }

    public static BaseResponse fail(String failMsg){
        return new BaseResponse("9999",failMsg);
    }

    public boolean isSuccess(){
        return "0000".equals(respCode);
    }
}
