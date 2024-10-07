package com.itheima;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts=new ArrayList<>();
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;//登录成功的账户
    public void start(){
        while (true) {
            System.out.println("===欢迎您进入ATM系统===");
            System.out.println("1、用户登录");
            System.out.println("2、用户开户");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    login();
                    //登录
                    break ;
                case 2:
                    //开户
                    createAcoount();
                    break;
                default:
                    System.out.println("没有该操作~~~");
            }
        }


    }//系统欢迎页


    /**用户登陆操作 */
    private void login(){
        System.out.println("===系统登录===");
        if(accounts.size()==0){
            System.out.println("当前系统中无账户，请先开户再登录");
            return;//无账户
        }

        while (true) {
            System.out.println("请输入您的卡号");
            String cardId=sc.next();
            Account acc=getAccountBycardId(cardId);
            if(acc==null){
                System.out.println("您输入的卡号不存在");
            }else{
                while (true) {
                    System.out.println("请输入登录密码；");
                    String password=sc.next();
                    if(password.equals(acc.getPssWord())){
                        //密码正确
                        loginAcc=acc;
                        System.out.println("恭喜您，"+acc.getUserName()+"成功登陆了系统，您的卡号是"+cardId);
                        //展示登陆后的操作界面
                        showUserCommand();
                        return;



                    }else{
                        System.out.println("您输入的密码不正确，请确认");
                    }
                }

            }
        }
    }
    /**展示登录页面 */
    private void showUserCommand(){
        while (true) {
            System.out.println(loginAcc.getUserName()+"您可以选择如下功能===");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.密码修改");
            System.out.println("6.退出");
            System.out.println("7.注销当前账户");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    showLoginAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    drawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    updatePassWord();
                    return;//修改后回到欢迎界面
                case 6:
                    System.out.println(loginAcc.getUserName()+"您退出系统成功");
                    return;

                case 7:
                    if(deleteAccount()){
                        //销户成功，回到欢迎界面
                        return;
                    }
                    break;
                default:
                    System.out.println("您当前选择的操作不存在，请确认");
            }
        }
    }
//账户密码修改
    private void updatePassWord() {
        System.out.println("===账户密码修改操作===");
        while (true) {
            System.out.println("请您输入当前账户密码");
            String passWord=sc.next();
            if(loginAcc.getPssWord().equals(passWord)){
                while (true) {
                    System.out.println("请输入新密码");
                    String newPassWord=sc.next();
                    System.out.println("请确认新密码");
                    String okPassWord=sc.next();
                    if(okPassWord.equals(newPassWord)){
                        loginAcc.setPssWord(newPassWord);
                        System.out.println("修改成功");
                        return;
                    }else{
                        System.out.println("两次密码不一致");
                    }
                }
            }else{
                System.out.println("密码不正确");
            }
        }
    }

    //销户操作
    private boolean deleteAccount() {
        System.out.println("===进行销户操作===");
        System.out.println("请问您确认销户码？y/n");
        String command=sc.next();
        switch (command){
            case "y":
                //账户有钱则不能销户
                if(loginAcc.getMoney()==0){
                    accounts.remove(loginAcc);
                    System.out.println("您的账户已销户");
                    return true;
                }else{
                    System.out.println("您的账户有钱，不能销户");
                    return false;
                }

            default:
                System.out.println("好的，您的账户保留");
                return false;
        }
    }

    //转账
    private void transferMoney() {
        System.out.println("===用户转账===");
        if(accounts.size()<2){
            System.out.println("当前系统只有一个账户，无法转账");
            return;
        }
        if(loginAcc.getMoney()==0){
            System.out.println("您自己都没钱，别转了");
            return;
        }
        while (true) {
            System.out.println("请输入对方的卡号：");
            String cardId=sc.next();

            //判断卡号是否存在
            Account acc=getAccountBycardId(cardId);
            if(acc==null){
                System.out.println("您输入的对方卡号不存在");
            }else{
                String name="*"+acc.getUserName().substring(1);
                System.out.println("请输入对方账户【"+name+"】姓氏");
                String preName=sc.next();
                if(acc.getUserName().startsWith(preName)){
                    //真正转账
                    while (true) {
                        System.out.println("输入转账的金额:");
                        double money=sc.nextDouble();
                        if(loginAcc.getMoney()>=money){
                            loginAcc.setMoney(loginAcc.getMoney()-money);
                            acc.setMoney(acc.getMoney()+money);
                            System.out.println("您转账成功");
                            return;//跳出转账方法
                        }else {
                            System.out.println("余额不足，最多可以转"+loginAcc.getMoney());
                        }
                    }
                }else{
                    System.out.println("对不起，您填的姓氏不对");
                }
            }
        }
    }

    //取钱
    private void drawMoney() {
        System.out.println("===取钱操作===");
        //余额是否大于100
        if(loginAcc.getMoney()<100){
            System.out.println("余额不足100，无法取钱");
            return;
        }

        while (true) {
            System.out.println("请您输入取款金额：");
            double money=sc.nextDouble();

            //余额是否足够
            if(loginAcc.getMoney()>=money){
                //判断是否超过限额
                if(money>loginAcc.getLinmit()){
                    System.out.println("超过了取款限额，每次最多可以取款："+loginAcc.getLinmit());
                }else{
                    loginAcc.setMoney(loginAcc.getMoney()-money);
                    System.out.println("您取款"+money+"成功，取款后剩余"+loginAcc.getMoney());
                    break;
                }
            }else{
                System.out.println("余额不足，您的账户余额是；"+loginAcc.getMoney());
            }
        }
    }

    //存钱
    private void depositMoney() {
        System.out.println("===存钱操作===");
        System.out.println("请您输入存款金额");
        double money=sc.nextDouble();

        //更新余额
        loginAcc.setMoney(loginAcc.getMoney()+money);
        System.out.println("恭喜，您存钱"+money+"成功，当前余额是："+loginAcc.getMoney());
    }

    /**
     展示当前登录的账户信息
     */
    private void showLoginAccount(){
        System.out.println("===当前您的账户信息如下===");
        System.out.println("卡号:"+loginAcc.getCardId());
        System.out.println("户主:"+loginAcc.getUserName());
        System.out.println("性别:"+loginAcc.getSex());
        System.out.println("余额:"+loginAcc.getMoney());
        System.out.println("限额:"+loginAcc.getLinmit());


    }


    //用户开户操作
    private void createAcoount(){
        System.out.println("===已经进入系统开户操作===");
        //1.创建一个用户对象
        Account acc=new Account();

        //2.需要用户输入自己的开户对象
        System.out.println("请您输入你的账户名称：");
        String name=sc.next();
        acc.setUserName(name);

        while (true) {
            System.out.println("请输入您的性别");
            char sex=sc.next().charAt(0);//取出字符
            if(sex=='男'||sex=='女'){
            acc.setSex(sex);
            break;
            }else {
                System.out.println("您输入的性别有误");
            }
        }

        while (true) {
            System.out.println("输入您的账户密码：");
            String password=sc.next();
            System.out.println("输入您的确认密码：");
            String okword=sc.next();
            if(okword.equals(password)){
                acc.setPssWord(okword);
                break;
            }else{
                System.out.println("您输入的两次密码不一样");
            }
        }

        System.out.println("请输入您的取现额度：");
        double limit=sc.nextDouble();
        acc.setLinmit(limit);

        //由系统自动生成卡号，且不能与其它账户卡号重复
        String newCardId=createCardId();
        acc.setCardId(newCardId);


        //3.把对象存入集合
        accounts.add(acc);
        System.out.println("恭喜您，"+acc.getUserName()+"开户成功，您的卡号是："+acc.getCardId());
    }

    //返回一个八位的卡号且不重复
    private String createCardId(){
        while (true) {
            //定义String作为卡号；
            String cardId="";
            //产生八位卡号
            Random r=new Random();
            for (int i = 0; i < 8; i++) {
                int data=r.nextInt(10);
                cardId +=data;

            }
            //判断是否重复
            Account acc=getAccountBycardId(cardId);
            if(acc==null){
                //不重复
                return cardId;
            }
        }
    }

//根据卡号查找账户对象  accounts=[];
    private Account getAccountBycardId(String cardId){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc=accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;//卡号不存在
    }

}
