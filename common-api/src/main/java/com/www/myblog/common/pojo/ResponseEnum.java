package com.www.myblog.common.pojo;

/**
 * <p>@Description 响应码枚举值 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:22 </p>
 */
public enum ResponseEnum {
    /** 成功 **/
    SUCCESS(200, "请求成功"),
    /** 未找到 **/
    NOT_FOUND(404, "未找到"),
    /** 失败 **/
    FAIL(500, "请求失败"),
    /**  未知异常 **/
    UNDEFINE(-1, "未定义异常信息");
    /** 响应码 **/
    private Integer code;
    /** 响应信息 **/
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
