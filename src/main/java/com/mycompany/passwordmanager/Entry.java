package com.mycompany.passwordmanager;

import java.awt.Component;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.AbstractTableModel;


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


    // Table Model for Entries
    public static class PasswordTableModel extends AbstractTableModel {
        private final List<Entry> entries;
        private final String[] columnNames = {"Account Name", "Password", "Email", "Note", "Actions"};

        public PasswordTableModel(List<Entry> entries) {
            this.entries = new ArrayList<>(entries);
        }

        @Override
        public int getRowCount() {
            return entries.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Entry entry = entries.get(rowIndex);
            switch (columnIndex) {
                case 0: return entry.getAccountName();
                case 1: return entry.getPassword();
                case 2: return entry.getEmail();
                case 3: return entry.getNote();
                case 4: return "Actions";  // Placeholder for actions
                default: return null;
            }
        }

        
    }
}

