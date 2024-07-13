/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        return "Account: " + accountName + ", Email: " + email + ", Password: " + password + ", Note: " + note;
    }
}
