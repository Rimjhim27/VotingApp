package com.example.votingapp;

public class Candidate {
    private String Name;
    private String Constituency;
    private String Party;
    private String file;
    private String image_path;
    private String user_id;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getConstituency() {
        return Constituency;
    }

    public void setConstituency(String constituency) {
        Constituency = constituency;
    }

    public String getParty() {
        return Party;
    }

    public void setParty(String party) {
        Party = party;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
