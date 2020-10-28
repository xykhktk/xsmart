package org.xsmart.system.entity;

import java.util.HashMap;

public class Data {

    private String code;
    private String message;
    private Boolean success;
    private HashMap<String, Object> data;

    private void setCode(String code) {
        this.code = code;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public static Data success(String msg) {
        return success(msg, "200");
    }

    public static Data success(String msg, String code) {
        Data returnData = new Data();
        returnData.setSuccess(true);
        returnData.setCode(code);
        returnData.setMessage(msg);
        returnData.setData(new HashMap<>());
        return returnData;
    }

    public Data putData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public static Data error(String msg) {
        return error(msg, "400");
    }

    public static Data error(String msg, String code) {
        Data returnData = new Data();
        returnData.setSuccess(false);
        returnData.setCode(code);
        returnData.setMessage(msg);
        returnData.setData(new HashMap<>());
        return returnData;
    }

}
