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
<<<<<<< HEAD
    public List<Entry> GetEntryListFromJSON(String accountSearch, String username);
    public void saveEntriesToJson(List<Entry> entries, Account account);
=======
    public List<Entry> GetEntryListFromJSON(String accountSearch, String entryToDelete, String username);
    public void saveEntriesToJson(List<Entry> entries, String username);
>>>>>>> 3cc54cb3f622e2904052f1261f37e4a0f12e4998
    public void createUserFile(String username, String password);
    public void addUserToCentralFile(String username, String password);
    public void WriteAccountToJSON(String username, String password);
}