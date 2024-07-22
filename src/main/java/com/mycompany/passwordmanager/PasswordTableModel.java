/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PasswordTableModel  extends AbstractTableModel{
     private List<Entry> entries;
    private final String[] columnNames = {"Account Name", "Password", "Email", "Note"};

    public PasswordTableModel(List<Entry> entries) {
        this.entries = entries;
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
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Entry entry = entries.get(rowIndex);
        switch (columnIndex) {
            case 0: return entry.getAccountName();
            case 1: return entry.getPassword();
            case 2: return entry.getEmail();
            case 3: return entry.getNote();
            default: return null;
        }
    }
}
