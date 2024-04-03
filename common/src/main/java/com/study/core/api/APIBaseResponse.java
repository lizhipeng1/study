package com.study.core.api;


import lombok.Data;

@Data
public class APIBaseResponse {

    String respCode;

    String respMsg;

    public APIBaseResponse() {
    }

    public APIBaseResponse(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

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
