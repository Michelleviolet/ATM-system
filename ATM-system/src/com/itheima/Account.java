package com.itheima;

public class Account {
    private String cardId;
    private String userName;
    private char sex;
    private String pssWord;
    private double money;
    private double linmit;//限额


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName+(sex=='男'?"先生":"女士");
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getPssWord() {
        return pssWord;
    }

    public void setPssWord(String pssWord) {
        this.pssWord = pssWord;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLinmit() {
        return linmit;
    }

    public void setLinmit(double linmit) {
        this.linmit = linmit;
    }
}
