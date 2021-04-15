package com.qingshan.mall.qingTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingshan.mall.model.RedisKey;
import com.qingshan.mall.model.RedisUtils;
import com.qingshan.mall.model.Student;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class demo01 {

    public static void main(String[] args) throws ParseException {

        ArrayList<Student> list1 = new ArrayList<>();
        Student student = new Student();
        student.setTitle("12");
        student.setId(1L);
        student.setName("张三");
        student.setNum(5);
        list1.add(student);
        Student student1 = new Student();
        student1.setTitle("13");
        student1.setId(1L);
        student1.setNum(6);
        student1.setName("李四");
        list1.add(student1);
        Student student3 = new Student();
        student3.setTitle("15");
        student3.setId(2L);
        student3.setNum(1);
        student3.setName("王五");
        list1.add(student3);
        ArrayList<Student> list = new ArrayList<>();
        for (Student stu : list1) {
            Student student2 = list
                    .stream()
                    .filter(item -> {
                        if (item.getId().equals(stu.getId())) {
                            return true;
                        }
                        return false;
                    })
                    .findAny()
                    .orElse(null);
            if(student2==null){
                    student2 = new Student();
                    student2.setName(stu.getName());
                    student2.setTitle(stu.getTitle());
                    student2.setId(stu.getId());
                    student2.setNum(stu.getNum());
                    list.add(student2);
                }else {
                student2.setNum(student2.getNum()+stu.getNum());
            }
        }
        System.out.println(list);


    }
}
