package com.qingshan.mall.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressInfo implements Serializable {
    private static final long serialVersionUID = 2475502688423598351L;
    private Long addressId;
    private Integer provinceId;
    private Integer cityId;
    private Integer countryId;
    private Long userId;
    private String receiveUserName;//收件人姓名
    private String receiveMobile;//收件人手机号
    private String area;//所在地区
    private String location;//详细地址
    private String postCode;//邮编
    private Boolean isDefault;//是否默认地址 1-是 0-不是
    private String fullInfo;//完整地址

    public String getFullInfo() {
        String info = null;
        if (area != null) {
            info = area;
        }
        if (location != null) {
            info = info == null ? location : area + location;
        }
        return info;
    }
}
