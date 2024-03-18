package com.cipher.vigenere;

public class CipherServiceTest {

    public static void main(String args[]) {
        CipherService cs = new CipherServiceImpl();
        String keyFile = "src/com/cipher/vigenere/key.txt"; // The path to the key file
        String messageFile = "src/com/cipher/vigenere/message.txt"; // The path to the message/input file
        String encryptedMessageFile = "src/com/cipher/vigenere/encrypted_message.txt"; // The path to the encrypted message file

        // Encrypt the message
        cs.encrypt(keyFile, messageFile, encryptedMessageFile);
        System.out.println("Message encrypted successfully.");

        // Decrypt the encrypted message
        String decryptedMessageFile = "src/com/cipher/vigenere/decrypted_message.txt"; // The path to the decrypted message file
        cs.decrypt(keyFile, encryptedMessageFile, decryptedMessageFile);
        System.out.println("Message decrypted successfully.");
    }
}

