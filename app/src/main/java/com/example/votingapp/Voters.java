package com.example.votingapp;

public class Voters {
    private  String Name;
    private  String VoterId;
    private  String EmailId;
    private  String DOB;
    private  String state;
    public Voters(){
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVoterId() {
        return VoterId;
    }

    public void setVoterId(String voterid) {
        VoterId = voterid;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
