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

    public void setAccountName(String newAccountName) {
        this.accountName = newAccountName;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }

    @Override
    public String toString() {
        return accountName + password + email + note;
    }
}
