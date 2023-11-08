package com.xoxoharsh.codershub.model;

public class UserModel {
    String Leetcode,Codeforces,Geekforgeeks;

    public String getLeetcode() {
        return Leetcode;
    }

    public UserModel(String leetcode, String codeforces, String geekforgeeks) {
        Leetcode = leetcode;
        Codeforces = codeforces;
        Geekforgeeks = geekforgeeks;
    }

    public void setLeetcode(String leetcode) {
        Leetcode = leetcode;
    }

    public void setCodeforces(String codeforces) {
        Codeforces = codeforces;
    }

    public void setGeekforgeeks(String geekforgeeks) {
        Geekforgeeks = geekforgeeks;
    }

    public String getCodeforces() {
        return Codeforces;
    }

    public String getGeekforgeeks() {
        return Geekforgeeks;
    }

    public UserModel() {
    }

}
