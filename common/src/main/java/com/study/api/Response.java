package com.study.api;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Response extends BaseResponse {

    String respCode;


    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ExceptionResponse extends Response {
        Object msgDetail;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class SuccessResponse extends Response {
        Object data;
    }

    public static Response toOkResponse() {
        return toAPIResponse(ResEnums.SUCCESS.getRespMsg());
    }

    public static Response toAPIResponse(Object data) {
        SuccessResponse response = new SuccessResponse();
        response.setData(data);
        response.setRespCode(ResEnums.SUCCESS.getRespCode());
        response.setRespMsg(ResEnums.SUCCESS.getRespMsg());
        return response;
    }

    public static Response toFailResponse(){
        return toExceptionResponse(ResEnums.FAILURE);
    }

    public static Response toExceptionResponse(Object data) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMsgDetail(data);
        response.setRespMsg(String.valueOf(data));
        response.setRespCode(ResEnums.BUSINESS_FAILURE.getRespCode());
        return response;
    }

    public static Response toExceptionResponse(ResEnums bizEnums){
        ExceptionResponse response = new ExceptionResponse();
        if (bizEnums != null) {
            response.setMsgDetail(bizEnums.getRespMsg());
            response.setRespMsg(bizEnums.getRespMsg());
            response.setRespCode(bizEnums.getRespCode());
        }
        return response;
    }

    public static Response toExceptionResponse(ResEnums bizEnums, Object msgDetail){
        ExceptionResponse response = new ExceptionResponse();
        if (bizEnums != null) {
            response.setRespMsg(bizEnums.getRespMsg());
            response.setRespCode(bizEnums.getRespCode());
        }
        response.setMsgDetail(msgDetail);
        return response;
    }

    @Override
    public boolean isSuccess(){
        return this.respCode.equals(ResEnums.SUCCESS.getRespCode());
    }

    public SuccessResponse toSuccessAPIResponse(){
        if(this instanceof SuccessResponse){
            return (SuccessResponse)this;
        }else{
            return null;
        }

    }

    public ExceptionResponse toExceptionResponse(){
        if(this instanceof ExceptionResponse){
            return (ExceptionResponse)this;
        }else{
            return null;
        }

    }
}
