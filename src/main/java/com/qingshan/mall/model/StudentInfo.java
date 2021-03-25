package com.qingshan.mall.model;



import lombok.Data;

import java.io.Serializable;

@Data
public class StudentInfo  implements Serializable {

    private int cardId;
    private int num;
    private int goodId;
    private int skuId;
}
