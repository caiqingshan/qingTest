package com.qingshan.mall.qingTest;

import com.qingshan.mall.model.Student;
import com.qingshan.mall.model.StudentInfo;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class demo01 {
    public static void main(String[] args) throws ParseException {
        Student student = new Student();
//        student.setId(1L);
        ArrayList<Student> list = new ArrayList<>();
        list.add(student);
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("dsf");
        student1.setTitle("开心");
        list.add(student1);
        System.err.println(list);
        List<StudentInfo> collect = list.stream().filter(item -> item.getName()!=null).map(item1 -> {
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(item1, studentInfo);
            return studentInfo;
        }).collect(Collectors.toList());
        System.out.println("*************"+collect);
        System.out.println("787878787878787887878787878787878");
    }
}
