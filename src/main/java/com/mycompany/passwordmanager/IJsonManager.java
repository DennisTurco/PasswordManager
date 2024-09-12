/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.passwordmanager;

import java.util.List;

/**
 *
 * @author Lorenzo
 */
public interface IJsonManager {
    public boolean ReadAccoutToJSON(String username, String password);
    public void saveLoginState(String username);
    public void WritePasswordToJSON(String accountName, String email, String password, String note, String username, javax.swing.JFrame object);
    public List<Entry> GetEntryListFromJSON(String accountSearch, String entryToDelete, String username);
    public void saveEntriesToJson(List<Entry> entries, String username);
    public void createUserFile(String username, String password);
    public void addUserToCentralFile(String username, String password);
    public void WriteAccountToJSON(String username, String password);
}