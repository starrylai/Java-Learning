package com.github.starrylai;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    public static void startStudentSystem() {
        ArrayList<Student> list = new ArrayList<Student>();
        System.out.println("欢迎进入学生管理系统");
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("请输入数字选择：");
            System.out.println("1添加学生；");
            System.out.println("2删除学生；");
            System.out.println("3修改学生；");
            System.out.println("4查询学生；");
            System.out.println("5退出系统；");
            int choose = sc.nextInt();
            switch (choose){
                case 1:
                    addInfo(list);
                case 2:
                    deleteInfo(list);
                case 3:
                    modifyInfo(list);
                case 4:
                    searchInfo(list);
                case 5:
                    return;
            }
        }
    }

    private static void addInfo(ArrayList<Student> list){}

    private static void deleteInfo(ArrayList<Student> list){}

    private static void modifyInfo(ArrayList<Student> list){}

    private static void searchInfo(ArrayList<Student> list){}
}
