package com.mycompany.passwordmanager;

public class Entry {
    private String accountName;
    private String password;
    private String email;
    private String note;

    public Entry(String accountName, String password, String email, String note) {
        this.accountName = accountName;
        this.password = password;
        this.email = email;
        this.note = note;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return accountName + password + email + note;
    }
    
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setNote(String note){
        this.note = note;
    }
}