package com.xoxoharsh.codershub.model;

import java.util.Map;

public class UserModel {
    Map<String, Object> geeksforgeekMap,leetcodeMap,codeforcesMap;

    public UserModel(Map<String, Object> geeksforgeekMap, Map<String, Object> leetcodeMap, Map<String, Object> codeforcesMap) {
        this.geeksforgeekMap = geeksforgeekMap;
        this.leetcodeMap = leetcodeMap;
        this.codeforcesMap = codeforcesMap;
    }

    public UserModel() {
    }

    public Map<String, Object> getGeeksforgeekMap() {
        return geeksforgeekMap;
    }

    public void setGeeksforgeekMap(Map<String, Object> geeksforgeekMap) {
        this.geeksforgeekMap = geeksforgeekMap;
    }

    public Map<String, Object> getLeetcodeMap() {
        return leetcodeMap;
    }

    public void setLeetcodeMap(Map<String, Object> leetcodeMap) {
        this.leetcodeMap = leetcodeMap;
    }

    public Map<String, Object> getCodeforcesMap() {
        return codeforcesMap;
    }

    public void setCodeforcesMap(Map<String, Object> codeforcesMap) {
        this.codeforcesMap = codeforcesMap;
    }
}
