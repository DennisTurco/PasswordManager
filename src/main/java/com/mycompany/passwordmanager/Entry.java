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

        @Override
        public String toString() {
            return "AccountName: " + accountName + ", Password: " + password + ", Email: " + email + ", Note: " + note;
        }
    // Getter e setter se necessario
}
