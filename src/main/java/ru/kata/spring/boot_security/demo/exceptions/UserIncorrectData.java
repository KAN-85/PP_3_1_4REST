package ru.kata.spring.boot_security.demo.exceptions;

public class UserIncorrectData {
    private String info;

    public UserIncorrectData() {}

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
