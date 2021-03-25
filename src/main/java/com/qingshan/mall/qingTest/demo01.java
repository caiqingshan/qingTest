package com.qingshan.mall.qingTest;

import com.qingshan.mall.model.ValidatorUtils;

import java.text.ParseException;

public class demo01 {


    public static void main(String[] args) throws ParseException {
//        String emoji = "－";
        String emoji = "?";
//        int num = emoji.length();
//        System.out.println(num);
        //--−
        Boolean bool = ValidatorUtils.isSpecialCharacter(emoji);
        System.out.println(bool);
    }

    }
