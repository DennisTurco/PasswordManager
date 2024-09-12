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
            JSONObject jsonObject;

            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                if (content.isEmpty()) {
                    // Crea un nuovo oggetto JSON se il file è vuoto
                    jsonObject = new JSONObject();
                    jsonObject.put("AccountName", username);  // Imposta il nome dell'account principale
                    jsonObject.put("Password", password);           // Puoi modificare o settare la password se necessario
                    jsonObject.put("EntryList", new JSONArray()); // Crea un array vuoto di EntryList
                } else {
                    // Leggi l'oggetto JSON esistente
                    jsonObject = new JSONObject(content);
                }
            } else {
                // Crea un nuovo oggetto JSON se il file non esiste
                jsonObject = new JSONObject();
                jsonObject.put("AccountName", username);
                jsonObject.put("Password",password);
                jsonObject.put("EntryList", new JSONArray());
            }

            // Ottieni l'array EntryList esistente e aggiungi il nuovo entry
            JSONArray entryList = jsonObject.getJSONArray("EntryList");
            entryList.put(newEntry);

            // Scrivi il file aggiornato
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonObject.toString(4));  // Formatta con indentazione
            }

            // Controlla i campi obbligatori
            if (accountName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(object, "Enter the text in the required fields", "Failure", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Entry> GetEntryListFromJSON(String accountSearch, String username) {
        String filePath = username + ".json";
        StringBuilder jsonData = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }

            // Parsing the main JSON object
            JSONObject jsonObject = new JSONObject(jsonData.toString());

            // Extract the EntryList array
            JSONArray entryList = jsonObject.getJSONArray("EntryList");
            List<Entry> entries = new ArrayList<>();

            // Loop through the entries in EntryList
            for (int i = 0; i < entryList.length(); i++) {
                JSONObject entryObject = entryList.getJSONObject(i);
                String accountName = entryObject.optString("AccountName");
                String password = entryObject.optString("Password");
                String email = entryObject.optString("Email");
                String note = entryObject.optString("Note");

                Entry entry = new Entry(accountName, password, email, note);
                
                entries.add(entry);

            }

            return entries;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Metodo per salvare le voci aggiornate nel file JSON (opzionale, se necessario)
    @Override
    public void saveEntriesToJson(List<Entry> entries, Account account) {
        String filePath = account.username + ".json";
        JSONObject jsonObject = new JSONObject();

        // Creare un JSONArray per EntryList
        JSONArray entryList = new JSONArray();

        // Popolare l'array con le entries passate alla funzione
        for (Entry entry : entries) {
            JSONObject entryObject = new JSONObject();
            entryObject.put("AccountName", entry.getAccountName());
            entryObject.put("Password", entry.getPassword());
            entryObject.put("Email", entry.getEmail());
            entryObject.put("Note", entry.getNote());
            entryList.put(entryObject);
        }

        try {
            File file = new File(filePath);

            if (file.exists()) {
                // Se il file esiste, leggerne il contenuto
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                if (!content.isEmpty()) {
                    // Aggiorna il contenuto del file esistente con le nuove entries
                    jsonObject = new JSONObject(content);
                }
            }

            // Imposta i campi principali
            jsonObject.put("AccountName", account.username);  // Mantieni o aggiorna il nome dell'account principale
            jsonObject.put("Password", jsonObject.optString("Password", account.password));  // Mantieni la password se esiste
            jsonObject.put("EntryList", entryList);  // Sostituisci la EntryList con le nuove entries

            // Scrivi l'oggetto JSON aggiornato nel file
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonObject.toString(4)); // Formatta il JSON con 4 spazi
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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