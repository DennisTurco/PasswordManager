/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class JsonManager implements IJsonManager{

    @Override
    public boolean ReadAccoutToJSON(String username, String password) {
        String filePath = "accounts.json";
        StringBuilder jsonData = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }

            // Parsing the JSON array
            JSONArray jsonArray = new JSONArray(jsonData.toString());

            // Iterate through the array and get the values
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String accountName = jsonObject.getString("username");
                String accountPassword = jsonObject.getString("Password");
                
                if(username.equals(accountName) && password.equals(accountPassword)){
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void saveLoginState(String username) {
        JSONObject loginState = new JSONObject();
        loginState.put("username", username);

        try (FileWriter fileWriter = new FileWriter("loginState.json")) {
            fileWriter.write(loginState.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void WritePasswordToJSON(String accountName, String email, String password, String note, String username, javax.swing.JFrame object) {
       String filePath = username + ".json";

        JSONObject newEntry = new JSONObject();
        newEntry.put("AccountName", accountName);
        newEntry.put("Email", email);
        newEntry.put("Password", password);
        newEntry.put("Note", note);

        try {
            File file = new File(filePath);
            JSONArray jsonArray;

            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                if (content.isEmpty()) {
                    jsonArray = new JSONArray();
                } else {
                    jsonArray = new JSONArray(content);
                }
            } else {
                jsonArray = new JSONArray();
            }

            jsonArray.put(newEntry);

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonArray.toString(4));
            }

            if (accountName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(object, "Enter the text in the required fields", "Failure", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Entry> GetEntryListFromJSON(String accountSearch, String entryToDelete, String username) {
        String filePath = username + ".json";
        StringBuilder jsonData = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonData.toString());
            List<Entry> entries = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String accountName = jsonObject.optString("AccountName");
                String password = jsonObject.optString("Password");
                String email = jsonObject.optString("Email");
                String note = jsonObject.optString("Note");

                Entry entry = new Entry(accountName, password, email, note);

                if (!(entryToDelete != null && !entryToDelete.isEmpty() && accountName.equals(entryToDelete))) {
                    if (accountSearch == null || accountSearch.isEmpty() || accountName.toLowerCase().contains(accountSearch.toLowerCase())) {
                        entries.add(entry);
                    }
                }
            }

            return entries;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Metodo per salvare le voci aggiornate nel file JSON (opzionale, se necessario)
    @Override
    public void saveEntriesToJson(List<Entry> entries, String username) {
        String filePath = username + ".json";
        JSONArray jsonArray = new JSONArray();

        for (Entry entry : entries) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AccountName", entry.getAccountName());
            jsonObject.put("Password", entry.getPassword());
            jsonObject.put("Email", entry.getEmail());
            jsonObject.put("Note", entry.getNote());
            jsonArray.put(jsonObject);
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonArray.toString(4)); // Indentazione di 4 spazi per formattare il JSON
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    @Override
    public void createUserFile(String username, String password) {
        // Crea un nuovo oggetto JSON con le informazioni da aggiungere
        JSONObject newEntry = new JSONObject();
        newEntry.put("AccountName", username);
        newEntry.put("Password", password);

        // Crea un array JSON e aggiungi l'oggetto utente
        JSONArray userArray = new JSONArray();
        userArray.put(newEntry);

        // Determina il percorso del file usando il nome dell'account
        String filePath = username + ".json";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // Scrivi il JSONArray nel file
            fileWriter.write(userArray.toString(4)); // Il parametro 4 è per l'indentazione del JSON
            System.out.println("Informazioni aggiunte con successo.");
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    @Override
    public void addUserToCentralFile(String username, String password) {
        String centralFilePath = "accounts.json";
        JSONArray accountsArray;

        // Leggi il file JSON centrale, se esiste
        try {
            String content = new String(Files.readAllBytes(Paths.get(centralFilePath)));
            accountsArray = new JSONArray(content);
        } catch (IOException e) {
            accountsArray = new JSONArray(); // Crea un nuovo array se il file non esiste
        }

        // Crea un nuovo oggetto JSON per l'utente
        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("Password", password);

        // Aggiungi l'utente all'array
        accountsArray.put(newUser);

        // Scrivi l'array aggiornato nel file
        try (FileWriter fileWriter = new FileWriter(centralFilePath)) {
            fileWriter.write(accountsArray.toString(4)); // Il parametro 4 è per l'indentazione del JSON
            System.out.println("Utente aggiunto al file centrale con successo.");
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    @Override
    public void WriteAccountToJSON(String username, String password) {
        // Crea un nuovo oggetto JSON con le informazioni da aggiungere
        JSONObject newEntry = new JSONObject();
        newEntry.put("AccountName", username);
        newEntry.put("Password", password);

        // Determina il percorso del file usando il nome dell'account
        String filePath = username + ".json";
        

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // Scrivi il JSONObject nel file
            fileWriter.write(newEntry.toString(4)); // Il parametro 4 è per l'indentazione del JSON
            System.out.println("Informazioni aggiunte con successo.");
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
}
