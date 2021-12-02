package com.www.myblog.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>@Description 响应报文类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:21 </p>
 */
@Data
@Accessors(chain = true)
public class ResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 响应码 **/
    private Integer code;
    /** 当前页数 **/
    private Integer pageNum;
    /** 页面条数 **/
    private Integer pageSize;
    /** 总数 **/
    private Long totalNum;
    /** 响应消息 **/
    private String msg;
    /** 响应数据 **/
    private T data;
    /**
     * <p>@Description 有其他有参构造方法，则必须有无参数的构造方法，否则反序列的时候会报错 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:21 </p>
     */
    public ResponseDTO() {}
    /**
     * <p>@Description 响应报文构造方法(默认成功状态) </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:22 </p>
     * @param data 数据
     */
    public ResponseDTO(T data) {
        this.code = ResponseEnum.SUCCESS.getCode();
        this.msg = ResponseEnum.SUCCESS.getMsg();
        this.data = data;
    }
    /**
     * <p>@Description 响应报文构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:22 </p>
     * @param code 响应码
     * @param data 数据
     */
    public ResponseDTO(ResponseEnum code, T data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }
    /**
     * <p>@Description 响应报文构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:22 </p>
     * @param code 响应码
     * @param msg 响应信息
     * @param data 数据
     */
    public ResponseDTO(ResponseEnum code, String msg, T data) {
        this.code = code.getCode();
        this.msg = msg == null ? code.getMsg() : msg;
        this.data = data;
    }
    /**
     * <p>@Description 设置响应码值 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 21:14 </p>
     * @param code 码值
     * @return void
     */
    public void setResponseCode(ResponseEnum code){
        this.code = code.getCode();
        this.msg = msg == null ? code.getMsg() : msg;
    }
    /**
     * <p>@Description 设置响应码值及响应信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 21:14 </p>
     * @param code 码值
     * @param data 响应信息
     * @return void
     */
    public void setResponseCode(ResponseEnum code, T data){
        this.code = code.getCode();
        this.msg = msg == null ? code.getMsg() : msg;
        this.data = data;
    }
}
