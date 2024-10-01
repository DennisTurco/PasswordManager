/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class JsonManager implements IJsonManager{
    
    private final String ACCOUNT_FILE = "src/main/resources/saves/accounts.json"; 
    private final String ACCOUNT_DIRECTORY = "src/main/resources/saves/"; 
    private final String AES_STRING = "uQHTkQbIqnHExgfw+wDofg==";
    private final SecretKey secretKey;
    
    public JsonManager(){        
        byte[] decodedKey = Base64.getDecoder().decode(AES_STRING);      
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
    


    @Override
    public boolean ReadAccoutToJSON(String username, String password) {
        String filePath = ACCOUNT_FILE;
        StringBuilder jsonData = new StringBuilder();

        try {

            File file = new File(filePath);

            if (!file.exists()) {
                return false;
            } 
        
            // Leggi il contenuto del file JSON senza tentare di decifrare l'intero file
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            // Itera attraverso l'array e confronta username e password
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Decodifica i campi cifrati da Base64
                byte[] encodedUsername = Base64.getDecoder().decode(jsonObject.getString("username"));
                byte[] encodedPassword = Base64.getDecoder().decode(jsonObject.getString("Password"));

                // Decifra username e password
                String decryptedUsername = new String(EncryptionUtil.decryptData(encodedUsername, secretKey));
                String decryptedPassword = new String(EncryptionUtil.decryptData(encodedPassword, secretKey));

                // Confronta i dati decifrati con quelli inseriti dall'utente
                if ((password==null && username.equals(decryptedUsername)) || (username.equals(decryptedUsername) && password.equals(decryptedPassword))) {
                    return true; // Accesso corretto
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
        return false;
    }



    @Override
    public void saveLoginState(String username) {
        JSONObject loginState = new JSONObject();
        loginState.put("username", username);

        try (FileWriter fileWriter = new FileWriter(ACCOUNT_DIRECTORY+"loginState.json")) {
            fileWriter.write(loginState.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void WritePasswordToJSON(String accountName, String email, String password, String note, String username, javax.swing.JFrame object) {
       String filePath = ACCOUNT_DIRECTORY + username + ".json";

       try {
           

           // Crea un nuovo oggetto JSON per la nuova entry
           JSONObject newEntry = new JSONObject();

           // Cifra i campi sensibili (AccountName, Email, Password e Note)
           byte[] encryptedAccountName = EncryptionUtil.encryptData(accountName.getBytes(), secretKey);
           byte[] encryptedPassword = EncryptionUtil.encryptData(password.getBytes(), secretKey);
           byte[] encryptedEmail = EncryptionUtil.encryptData(email.getBytes(), secretKey);
           byte[] encryptedNote = EncryptionUtil.encryptData(note.getBytes(), secretKey);

           // Codifica in Base64 per renderli leggibili come stringhe nel JSON
           String encryptedAccountNameBase64 = Base64.getEncoder().encodeToString(encryptedAccountName);
           String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);
           String encryptedEmailBase64 = Base64.getEncoder().encodeToString(encryptedEmail);
           String encryptedNoteBase64 = Base64.getEncoder().encodeToString(encryptedNote);

           // Aggiungi i dati cifrati all'oggetto JSON della nuova entry
           newEntry.put("AccountName", encryptedAccountNameBase64);
           newEntry.put("Email", encryptedEmailBase64);
           newEntry.put("Password", encryptedPasswordBase64);
           newEntry.put("Note", encryptedNoteBase64);

           File file = new File(filePath);
           JSONObject jsonObject;

           if (file.exists()) {
               // Leggi il contenuto del file
               String content = new String(Files.readAllBytes(Paths.get(filePath)));

               if (content.isEmpty()) {
                   // Se il file è vuoto, crea un nuovo oggetto JSON con un array vuoto in "EntryList"
                   jsonObject = new JSONObject();
                   jsonObject.put("EntryList", new JSONArray());
               } else {
                   // Se il file ha del contenuto, carica il contenuto esistente come oggetto JSON
                   jsonObject = new JSONObject(content);
               }
           } else {
               // Se il file non esiste, crea un nuovo oggetto JSON con un array vuoto in "EntryList"
               jsonObject = new JSONObject();
               jsonObject.put("EntryList", new JSONArray());
           }

           // Aggiungi la nuova entry all'array "EntryList"
           jsonObject.getJSONArray("EntryList").put(newEntry);

           // Scrivi il file JSON aggiornato
           try (FileWriter fileWriter = new FileWriter(filePath)) {
               fileWriter.write(jsonObject.toString(4));  // Il parametro 4 è per l'indentazione del JSON
           }

           // Verifica se i campi obbligatori sono vuoti e mostra un messaggio di errore
           if (accountName.isEmpty() || email.isEmpty() || password.isEmpty()) {
               JOptionPane.showMessageDialog(object, "Enter the text in the required fields", "Failure", JOptionPane.ERROR_MESSAGE);
               return;
           }

       } catch (Exception e) {
           e.printStackTrace();
           MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
       }
   }


    @Override
    public List<Entry> GetEntryListFromJSON(String accountSearch, String entryToDelete, Account account) {
        String filePath = ACCOUNT_DIRECTORY + account.username + ".json";
        List<Entry> entries = new ArrayList<>();

        try {
           
            // Leggi il contenuto del file JSON senza tentare di decifrare l'intero file
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);

            // Ottieni l'array "EntryList" dal JSON
            JSONArray entryList = jsonObject.getJSONArray("EntryList");

            // Itera attraverso le entry nell'array "EntryList"
            for (int i = 0; i < entryList.length(); i++) {
                JSONObject entryObject = entryList.getJSONObject(i);

                // Decodifica i campi cifrati in Base64
                byte[] encodedAccountName = Base64.getDecoder().decode(entryObject.getString("AccountName"));
                byte[] encodedPassword = Base64.getDecoder().decode(entryObject.getString("Password"));
                byte[] encodedEmail = Base64.getDecoder().decode(entryObject.getString("Email"));
                byte[] encodedNote = Base64.getDecoder().decode(entryObject.getString("Note"));

                // Decifra i campi usando la chiave segreta
                String decryptedAccountName = new String(EncryptionUtil.decryptData(encodedAccountName, secretKey));
                String decryptedPassword = new String(EncryptionUtil.decryptData(encodedPassword, secretKey));
                String decryptedEmail = new String(EncryptionUtil.decryptData(encodedEmail, secretKey));
                String decryptedNote = new String(EncryptionUtil.decryptData(encodedNote, secretKey));

                // Filtra per ricerca (se fornito) e aggiungi l'entry alla lista
                if (accountSearch == null || decryptedAccountName.contains(accountSearch)) {
                    Entry entry = new Entry(decryptedAccountName, decryptedPassword, decryptedEmail, decryptedNote);
                    entries.add(entry);
                }
            }

            return entries;

        } catch (Exception e) {
            e.printStackTrace();
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
        return null;
    }


    // Metodo per salvare le voci aggiornate nel file JSON (opzionale, se necessario)
    @Override
    public void saveEntriesToJson(List<Entry> entries, Account account) {
        String filePath = ACCOUNT_DIRECTORY + account.username + ".json";
        JSONObject jsonObject = new JSONObject();

        try {
            
            
            // Creare un JSONArray per EntryList
            JSONArray entryList = new JSONArray();



            // Popolare l'array con le entries passate alla funzione
            for (Entry entry : entries) {
                JSONObject entryObject = new JSONObject();

                 // Cifra i campi sensibili (AccountName, Email, Password e Note)
               byte[] encryptedAccountName = EncryptionUtil.encryptData(entry.getAccountName().getBytes(), secretKey);
               byte[] encryptedPassword = EncryptionUtil.encryptData(entry.getPassword().getBytes(), secretKey);
               byte[] encryptedEmail = EncryptionUtil.encryptData(entry.getEmail().getBytes(), secretKey);
               byte[] encryptedNote = EncryptionUtil.encryptData(entry.getNote().getBytes(), secretKey);

               // Codifica in Base64 per renderli leggibili come stringhe nel JSON
               String encryptedAccountNameBase64 = Base64.getEncoder().encodeToString(encryptedAccountName);
               String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);
               String encryptedEmailBase64 = Base64.getEncoder().encodeToString(encryptedEmail);
               String encryptedNoteBase64 = Base64.getEncoder().encodeToString(encryptedNote);

                entryObject.put("AccountName", encryptedAccountNameBase64);
                entryObject.put("Password", encryptedPasswordBase64);
                entryObject.put("Email", encryptedEmailBase64);
                entryObject.put("Note", encryptedNoteBase64);
                entryList.put(entryObject);
            }
            
            File file = new File(filePath);

            if (file.exists()) {
                // Se il file esiste, leggerne il contenuto
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                if (!content.isEmpty()) {
                    // Aggiorna il contenuto del file esistente con le nuove entries
                    jsonObject = new JSONObject(content);
                }
            }
            
            byte[] encryptedAccount = EncryptionUtil.encryptData(account.username.getBytes(), secretKey);
            byte[] encryptedPassword = EncryptionUtil.encryptData(account.password.getBytes(), secretKey);

            String encryptedAccountBase64 = Base64.getEncoder().encodeToString(encryptedAccount);
            String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);


            // Imposta i campi principali
            jsonObject.put("AccountName", encryptedAccountBase64);  // Mantieni o aggiorna il nome dell'account principale
            jsonObject.put("Password", encryptedPasswordBase64);  // Mantieni la password dell'account
            jsonObject.put("EntryList", entryList);  // Sostituisci la EntryList con le nuove entries

            // Scrivi l'oggetto JSON aggiornato nel file
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonObject.toString(4)); // Formatta il JSON con 4 spazi
            }

        } 
        
        
        catch (Exception e) {
            e.printStackTrace();
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
    }


    @Override
    public void createUserFile(String username, String password) {
        System.out.println("createUserFile");
        try {
           
            // Cifra il nome utente e la password
            byte[] encryptedUsername = EncryptionUtil.encryptData(username.getBytes(), secretKey);
            byte[] encryptedPassword = EncryptionUtil.encryptData(password.getBytes(), secretKey);

            // Codifica in Base64 per renderli leggibili come stringhe nel JSON
            String encryptedUsernameBase64 = Base64.getEncoder().encodeToString(encryptedUsername);
            String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);

            // Crea un oggetto JSON con le informazioni cifrate e il campo EntryList vuoto
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AccountName", encryptedUsernameBase64);
            jsonObject.put("Password", encryptedPasswordBase64);
            jsonObject.put("EntryList", new JSONArray());  // EntryList vuoto

            // Determina il percorso del file usando il nome dell'account
            String filePath = ACCOUNT_DIRECTORY + username + ".json";

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                // Scrivi l'oggetto JSON nel file
                fileWriter.write(jsonObject.toString(4)); // Il parametro 4 è per l'indentazione del JSON
                System.out.println("Informazioni aggiunte con successo.");
            } catch (Exception e) {
                e.printStackTrace();
                MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
    }


  @Override
    public void addUserToCentralFile(String username, String password) {
        String centralFilePath = ACCOUNT_FILE;
        //String secretKeyPath = ACCOUNT_DIRECTORY + username + "_key.aes";
        JSONArray accountsArray;

        try {
           
            // Verifica se il file centrale esiste, altrimenti crea un nuovo JSONArray
            if (!Files.exists(Paths.get(centralFilePath))) {
               System.out.println("Il file centrale non esiste. Creazione di un nuovo file...");
               accountsArray = new JSONArray(); // Crea un nuovo array se il file non esiste
            } else {
               // Leggi il file JSON centrale, se esiste
               String content = new String(Files.readAllBytes(Paths.get(centralFilePath)));

               // Verifica che il contenuto non sia vuoto e che inizi con '['
               if (content.trim().isEmpty() || !content.trim().startsWith("[")) {
                   accountsArray = new JSONArray(); // Crea un nuovo array se il file è vuoto o non valido
               } else {
                   accountsArray = new JSONArray(content); // Continua se il file contiene un array valido
               }
            }
            
            // Cifra il username e la password
            byte[] encryptedUsername = EncryptionUtil.encryptData(username.getBytes(), secretKey);
            byte[] encryptedPassword = EncryptionUtil.encryptData(password.getBytes(), secretKey);

            // Codifica i dati cifrati in Base64 per memorizzarli nel JSON
            String encryptedUsernameBase64 = Base64.getEncoder().encodeToString(encryptedUsername);
            String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);

            // Crea un nuovo oggetto JSON per l'utente cifrato
            JSONObject newUser = new JSONObject();
            newUser.put("username", encryptedUsernameBase64);
            newUser.put("Password", encryptedPasswordBase64);

            // Aggiungi l'utente all'array
            accountsArray.put(newUser);

            // Scrivi l'array aggiornato nel file
            try (FileWriter fileWriter = new FileWriter(centralFilePath)) {
                fileWriter.write(accountsArray.toString(4)); // Il parametro 4 è per l'indentazione del JSON
                System.out.println("Utente aggiunto al file centrale con successo.");
            }

        } catch (IOException e) {
            // Se il file non esiste o non può essere letto, inizializza un array vuoto
            accountsArray = new JSONArray();
        } catch (JSONException e) {
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
            return;
        }
    }


    @Override
    public void WriteAccountToJSON(String username, String password) {
        System.out.println("WriteAccountToJSON");
        try {
           
            // Cifra il nome utente e la password
            byte[] encryptedUsername = EncryptionUtil.encryptData(username.getBytes(), secretKey);
            byte[] encryptedPassword = EncryptionUtil.encryptData(password.getBytes(), secretKey);

            // Codifica in Base64 per renderli leggibili come stringhe nel JSON
            String encryptedUsernameBase64 = Base64.getEncoder().encodeToString(encryptedUsername);
            String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);

            // Crea un nuovo oggetto JSON con le informazioni cifrate
            JSONObject newEntry = new JSONObject();
            newEntry.put("AccountName", encryptedUsernameBase64);
            newEntry.put("Password", encryptedPasswordBase64);

            // Determina il percorso del file usando il nome dell'account
            String filePath = ACCOUNT_DIRECTORY + username + ".json";

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                // Scrivi il JSONObject nel file
                fileWriter.write(newEntry.toString(4)); // Il parametro 4 è per l'indentazione del JSON
                System.out.println("Informazioni aggiunte con successo.");
            } catch (Exception e) {
                e.printStackTrace();
                MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            MainGUI.OpenExceptionMessage(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
    }  
}