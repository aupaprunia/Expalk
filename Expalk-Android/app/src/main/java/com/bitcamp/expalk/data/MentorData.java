package com.bitcamp.expalk.data;

public class MentorData {

    String uid, status;

    public MentorData(String uid, String status) {
        this.uid = uid;
        this.status = status;
    }

    public MentorData(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
