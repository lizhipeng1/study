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
public class APIBaseResponse {

    String respCode;

    String respMsg;


    public static APIBaseResponse success(){
        return new APIBaseResponse("0000","success");
    }

    public static APIBaseResponse fail(String failMsg){
        return new APIBaseResponse("9999",failMsg);
    }

    public boolean isSuccess(){
        return "0000".equals(respCode);
    }
}
