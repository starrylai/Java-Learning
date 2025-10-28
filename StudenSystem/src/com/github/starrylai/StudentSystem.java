package com.github.starrylai;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    public static void startStudentSystem() {
        ArrayList<Student> list = new ArrayList<Student>();
        System.out.println("欢迎进入学生管理系统");
        Scanner sc = new Scanner(System.in);
        while(true){
            System.err.println("请输入数字选择：");
            System.out.println("1添加学生信息；");
            System.out.println("2删除学生信息；");
            System.out.println("3修改学生信息；");
            System.out.println("4查询学生信息；");
            System.out.println("5退出系统；");
            int choose = sc.nextInt();
            switch (choose){
                case 1:
                    addInfo(list);
                    break;
                case 2:
                    deleteInfo(list);
                    break;
                case 3:
                    modifyInfo(list);
                    break;
                case 4:
                    searchInfo(list);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("无此选项，请重新输入");
            }
        }
    }

    private static void addInfo(ArrayList<Student> list){
        Student s = new Student();
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("请输入新增学生的ID");
            String id = sc.next();
            boolean flag = contains(list, id);
            if(flag){
                System.out.println("ID已存在，请重新输入");
            }else{
                s.setId(id);
                break;
            }
        }

        System.out.println("请输入学生的姓名");
        String name = sc.next();
        s.setName(name);

        System.out.println("请输入学生的年龄");
        int age = sc.nextInt();
        s.setAge(age);

        System.out.println("请输入学生的家庭地址");
        String Address = sc.next();
        s.setAddress(Address);

        list.add(s);
        System.out.println("学生信息添加成功");


    }

    private static boolean contains(ArrayList<Student> list, String id){
        return getIndex(list, id)>= 0;
    }

    private static void deleteInfo(ArrayList<Student> list){
        System.out.println("请输入需要删除学生的ID");
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        int flag = getIndex(list, id);
        if(flag >=0 ){
            list.remove(flag);
            System.out.println("已删除ID为"+id+"的学生信息");
        }else{
            System.out.println("该学生ID不存在，删除失败");
        }
    }

    private static int getIndex(ArrayList<Student> list, String id){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private static void modifyInfo(ArrayList<Student> list){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入需要修改学生的ID");
        String id = sc.next();
        int flag = getIndex(list, id);
        if(flag >= 0){
            System.out.println("现在开始修改该学生的信息");
        }else{
            System.out.println("该学生ID不存在，修改失败");
            return;
        }

        Student s = list.get(flag);
        System.out.println("请输入修改的学生姓名");
        String newName = sc.next();
        s.setName(newName);

        System.out.println("请输入修改的学生年龄");
        int newAge = sc.nextInt();
        s.setAge(newAge);

        System.out.println("请输入修改的学生家庭住址");
        String newAddress = sc.next();
        s.setAddress(newAddress);

        System.out.println("学生信息修改成功");
    }

    private static void searchInfo(ArrayList<Student> list){
        if(list.size() == 0){
            System.out.println("当前无学生信息，请添加后再查看");
            return;
        }

        System.out.println("ID\t\t姓名\t\t年龄\t\t家庭住址");
        for(int i = 0; i < list.size(); i++){
            Student s = list.get(i);
            System.out.println(s.getId()+"\t\t"+s.getName()+"\t\t"+s.getAge()+"\t\t"+s.getAddress());
        }
    }
}
