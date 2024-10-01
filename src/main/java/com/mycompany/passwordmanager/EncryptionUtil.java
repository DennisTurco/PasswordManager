package com.mycompany.passwordmanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;
import java.security.SecureRandom;

public class EncryptionUtil {

    private static final int AES_BLOCK_SIZE = 16; // Dimensione del blocco AES

    // Metodo per cifrare i dati
    public static byte[] encryptData(byte[] data, SecretKey secretKey) throws Exception {
        // Crea il cifrario AES in modalità CBC con padding PKCS5
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        // Genera un IV casuale
        byte[] iv = new byte[AES_BLOCK_SIZE];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        // Inizializza il cifrario in modalità ENCRYPT
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);

        // Cifra i dati
        byte[] encryptedData = cipher.doFinal(data);

        // Combina IV e dati cifrati
        byte[] combined = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

        return combined; // Ritorna i dati cifrati insieme all'IV
    }

    // Metodo per decifrare i dati
    public static byte[] decryptData(byte[] encryptedDataWithIV, SecretKey secretKey) throws Exception {
        // Estrai l'IV dai dati cifrati
        byte[] iv = new byte[AES_BLOCK_SIZE];
        System.arraycopy(encryptedDataWithIV, 0, iv, 0, iv.length);
        
        // I dati cifrati seguono l'IV
        byte[] encryptedData = new byte[encryptedDataWithIV.length - AES_BLOCK_SIZE];
        System.arraycopy(encryptedDataWithIV, iv.length, encryptedData, 0, encryptedData.length);

        // Crea il cifrario AES in modalità CBC con padding PKCS5
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        // Inizializza il cifrario in modalità DECRYPT
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);

        // Decifra i dati
        return cipher.doFinal(encryptedData);
    }

    // Metodo per generare una chiave segreta AES
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Chiave a 128 bit
        return keyGenerator.generateKey();
    }

    // Metodo per ottenere la chiave segreta da un file
    public static SecretKey getSecretKeyFromFile(String keyFilePath) throws Exception {
        Path path = Paths.get(keyFilePath);

        // Verifica se il file della chiave esiste
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File della chiave AES non trovato: " + keyFilePath);
        }

        // Leggi il contenuto del file e decodifica la chiave
        byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(path));
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Metodo per salvare la chiave segreta in un file
    public static void saveSecretKeyToFile(SecretKey secretKey, String keyFilePath) throws Exception {
        Path path = Paths.get(keyFilePath);

        // Verifica se la directory esiste, altrimenti creala
        if (!Files.exists(path.getParent())) {
            try {
                Files.createDirectories(path.getParent());
                System.out.println("Directory creata: " + path.getParent().toString());
            } catch (IOException e) {
                System.out.println("Errore durante la creazione della directory: " + e.getMessage());
                throw e;
            }
        }

        // Salva la chiave nel file
        Files.write(path, Base64.getEncoder().encode(secretKey.getEncoded()));
        System.out.println("Chiave AES salvata con successo nel file: " + keyFilePath);
    }

    // Metodo per generare e salvare una chiave AES
    public static void generateAndSaveAESKey(String username) {
        try {
            // Genera la chiave AES
            SecretKey secretKey = generateSecretKey();

            // Usa il percorso relativo ma con gestione dinamica della directory
            String workingDir = System.getProperty("user.dir");
            String keyFilePath = workingDir + "/saves/" + username + "_key.aes";

            // Salva la chiave in un file
            saveSecretKeyToFile(secretKey, keyFilePath);

            System.out.println("Chiave AES generata e salvata con successo per " + username + " nel percorso: " + keyFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
