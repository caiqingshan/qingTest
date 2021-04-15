package com.qingshan.mall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SerialBusType {
    user("10", "用户"),
    goodOrder("11", "商品订单"),
    goodOrderDetail("12", "商品订单明细"),
    goodRefund("13", "商品退款"),
    coupon("14", "优惠券"),
    account("15", "账本"),
    point("16", "积分"),
    shopOrder("17", "门店订单"),
    shopOrderCode("18", "门店订单核销码"),
    shopRefund("19", "门店退款"),
    shopAccount("20", "门店账本");
    private String prefix;
    private String describe;
}
