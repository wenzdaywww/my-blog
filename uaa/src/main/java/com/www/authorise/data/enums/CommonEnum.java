package com.www.authorise.data.enums;

/**
 * <p>@Description 通用枚举类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/2 21:04 </p>
 */
public enum CommonEnum {
    /** 菜单类型：页面菜单 **/
    MENU_TYPE_1("1","页面菜单"),
    /** 菜单类型：请求路径 **/
    MENU_TYPE_2("2","请求路径"),
    /** 性别：男 **/
    SEX_1("1","男"),
    /** 性别：女 **/
    SEX_0("0","女"),
    /** 是标志：1 **/
    YES_1("1","是"),
    /** 否标志：0 **/
    NO_0("0","否"),
    /** 用户状态：有效 **/
    STATE_CD_1("1","有效"),
    /** 用户状态：注销 **/
    STATE_CD_2("2","注销"),
    /** 用户状态：封号 **/
    STATE_CD_3("3","封号")
    ;


    /** 名称 **/
    private String name;
    /** 码值 **/
    private String code;
    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 21:05 </p>
     * @param code 码值
     * @param name 名称
     * @return
     */
    CommonEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
