package com.mycompany.passwordmanager;

public class Entry {
    private final String accountName;
    private final String password;
    private final String email;
    private final String note;

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
<<<<<<< HEAD
    
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
=======
>>>>>>> 3cc54cb3f622e2904052f1261f37e4a0f12e4998
}