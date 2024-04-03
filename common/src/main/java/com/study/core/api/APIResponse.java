package com.study.core.api;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class APIResponse extends APIBaseResponse{

    String respCode;


    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ExceptionAPIResponse extends APIResponse {
        Object msgDetail;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class SuccessAPIResponse extends APIResponse {
        Object data;
    }

    public static APIResponse toOkResponse() {
        return toAPIResponse(ApiResEnums.SUCCESS.getRespMsg());
    }

    public static APIResponse toAPIResponse(Object data) {
        SuccessAPIResponse response = new SuccessAPIResponse();
        response.setData(data);
        response.setRespCode(ApiResEnums.SUCCESS.getRespCode());
        response.setRespMsg(ApiResEnums.SUCCESS.getRespMsg());
        return response;
    }

    public static APIResponse toFailResponse(){
        return toExceptionResponse(ApiResEnums.FAILURE);
    }

    public static APIResponse toExceptionResponse(Object data) {
        ExceptionAPIResponse response = new ExceptionAPIResponse();
        response.setMsgDetail(data);
        response.setRespMsg(String.valueOf(data));
        response.setRespCode(ApiResEnums.BUSINESS_FAILURE.getRespCode());
        return response;
    }

    public static APIResponse toExceptionResponse(ApiResEnums bizEnums){
        ExceptionAPIResponse response = new ExceptionAPIResponse();
        if (bizEnums != null) {
            response.setMsgDetail(bizEnums.getRespMsg());
            response.setRespMsg(bizEnums.getRespMsg());
            response.setRespCode(bizEnums.getRespCode());
        }
        return response;
    }

    public static APIResponse toExceptionResponse(ApiResEnums bizEnums, Object msgDetail){
        ExceptionAPIResponse response = new ExceptionAPIResponse();
        if (bizEnums != null) {
            response.setRespMsg(bizEnums.getRespMsg());
            response.setRespCode(bizEnums.getRespCode());
        }
        response.setMsgDetail(msgDetail);
        return response;
    }

    @Override
    public boolean isSuccess(){
        return this.respCode.equals(ApiResEnums.SUCCESS.getRespCode());
    }

    public SuccessAPIResponse toSuccessAPIResponse(){
        if(this instanceof SuccessAPIResponse){
            return (SuccessAPIResponse)this;
        }else{
            return null;
        }

    }

    public ExceptionAPIResponse toExceptionResponse(){
        if(this instanceof ExceptionAPIResponse){
            return (ExceptionAPIResponse)this;
        }else{
            return null;
        }

    }
}
