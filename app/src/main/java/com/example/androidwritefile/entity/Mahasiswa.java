package com.example.androidwritefile.entity;


public class Mahasiswa {

    private String name;
    private String nim;
    private String password;

    public Mahasiswa() {
    }

    public Mahasiswa(String name, String nim, String password) {
        this.name = name;
        this.nim = nim;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
