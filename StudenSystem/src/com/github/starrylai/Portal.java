package com.github.starrylai;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Portal {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        //登录注册
        System.out.println("欢迎进入学生管理系统");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入数字选择：1登录；2注册；3忘记密码；4退出系统");
            int choose = sc.nextInt();
            switch (choose) {
                case 1:
                    login(list);
                    break;
                case 2:
                    register(list);
                    break;
                case 3:
                    resetPassword(list);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("无此选项，请重新输入");
            }
        }
    }

        //登录
        private static void login(ArrayList<User> list){
            Scanner sc = new Scanner(System.in);
            int loginNum = 3;
            for (int i = 0; i < loginNum; i++) {//3次登录机会
                System.out.println("请输入用户名");
                String userName = sc.next();
                boolean flag = contains(list, userName);
                if (!flag) {
                    System.out.println("用户名"+userName+"不存在，请先注册");
                    return;
                }
                System.out.println("请输入密码");
                String password = sc.next();

                while(true){
                    String VerificationCode = getCode();
                    System.out.println("验证码为："+VerificationCode);
                    System.out.println("请输入验证码");
                    String inputCode = sc.next();
                    if(VerificationCode.equals(inputCode)){
                        System.out.println("验证码正确");break;
                    }else{
                        System.out.println("验证码错误，请重新输入验证码");
                    }
                }

                boolean result = checkUserInfo(userName, password, list);
                if(result){
                    System.out.println("登录成功");
                    StudentSystem ss = new StudentSystem();//启动学生管理系统
                    ss.startStudentSystem();
                    break;
                }else{
                    if(i == loginNum-1){
                        System.out.println("当前账户"+userName+"已锁定");
                        return;
                    }
                    System.out.println("用户名或密码错误，账户将在"+(loginNum-1-i)+"次失败后锁定");
                }
            }
        }

        private static void register(ArrayList<User> list){
            Scanner sc = new Scanner(System.in);
            //用户名设置
            while(true){
                System.out.println("请输入用户名");
                String userName = sc.next();
                boolean flag = checkUsername(userName);//校验用户名格式
                if(!flag){
                    continue;
                }

                boolean flag1 = contains(list, userName);//校验用户名唯一性
                if(flag1){
                    System.out.println("用户名"+userName+"已存在，请重新输入");
                }else{
                    System.out.println("用户名"+userName+"可用");
                    break;
                }
            }
            //密码设置
            while(true){
                System.out.println("请输入新密码");
                String password = sc.next();
                System.out.println("请再次输入密码");
                String secondPassword = sc.next();
                if(password.equals(secondPassword)){//两次密码设置需一致
                    System.out.println("密码设置成功");break;
                }else{
                    System.out.println("两次输入的密码不一致，请重新输入");
                }
            }
            //身份证验证
            while(true){
                System.out.println("请输入身份证号");
                String citizenID = sc.next();
                boolean flag = checkCitizenID(citizenID);
                if(flag){
                    System.out.println("身份证号验证成功");break;
                }
            }

        }

        private static boolean checkCitizenID(String citizenID){
            if(citizenID.length() != 18){
                System.out.println("身份证号必须为18位");
                return false;
            }
            if(citizenID.startsWith("0")){
                System.out.println("身份证号不能以0开头");
                return false;
            }

            for (int i = 0; i < citizenID.length()-1; i++) {
                
            }
        }

        private static boolean checkUsername(String userName){
            int len = userName.length();
            if(len<3 || len>15){
                System.out.println("用户名长度必须在3~15位");
                return false;
            }

            for (int i = 0; i < len; i++) {
                char ch = userName.charAt(i);
                if(!((ch >= 'a' && ch <= 'z' ) || (ch >= 'A' && ch <= 'Z') ||  (ch >= '0' && ch <= '9'))) {
                    System.out.println("用户名只能由数字和字母构成");
                    return false;
                }
            }

            int count = 0;
            for (int i = 0; i < len; i++) {
                char ch = userName.charAt(i);
                if((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z') ) {
                    count++;
                    break;
                }
            }
            if(count == 0){
                System.out.println("用户名不能为纯数字");
                return false;
            }
            return true;
        }

        private static void resetPassword(ArrayList){}

        private static boolean checkUserInfo(String userName, String password, ArrayList<User> list){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getUsername().equals(userName) && list.get(i).getPassword().equals(password)){
                    return true;
                }
            }
            return false;
        }

        private static String getCode(){
        char[] chars = new char[26*2];//存储字母的数组
            for (int i = 0; i < 26; i++) {
                chars[2*i] = (char)(i+'a');
                chars[2*i+1] = (char)(i+'A');
            }

            StringBuilder sb = new StringBuilder();
            Random r = new Random();
            for(int i = 0; i < 4; i++){//4个字母
                char c = chars[r.nextInt(26*2)];
                sb.append(c);
            }

            sb.append(r.nextInt(10));//1个数字

            //随机打乱数字的位置
            char[] chars1 = sb.toString().toCharArray();
            int randomIndex = r.nextInt(chars1.length);
            char temp = chars1[randomIndex];
            chars1[randomIndex] = chars1[chars1.length-1];
            chars1[chars1.length-1] = temp;
            return new String(chars1);
        }

        private static boolean contains(ArrayList<User> list, String userName) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUsername().equals(userName)) {
                    return true;
                }
            }
            return false;
        }
}