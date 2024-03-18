package com.cipher.vigenere;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CipherServiceImpl extends CipherService {

	@Override
	public void encrypt(String keyFile, String messageFile, String encryptedMessageFile) {
	    try {
	        // Read the key from the key file
	        String key = readFileAsString(keyFile);

	        // Read the message from the message file
	        String message = readFileAsString(messageFile);

	        // Encrypt the message using the Vigenere cipher algorithm
	        String encryptedMessage = vigenereCipher(message, key, false); // Specify 'false' for encryption

	        // Write the encrypted message to the encrypted message file
	        writeStringToFile(encryptedMessage, encryptedMessageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void decrypt(String keyFile, String encryptedMessageFile, String messageFile) {
	    try {
	        // Read the key from the key file
	        String key = readFileAsString(keyFile);

	        // Read the encrypted message from the encrypted message file
	        String encryptedMessage = readFileAsString(encryptedMessageFile);

	        // Decrypt the message using the Vigenere cipher algorithm
	        String decryptedMessage = vigenereCipher(encryptedMessage, key, true); // Specify 'true' for decryption

	        // Write the decrypted message to the message file
	        writeStringToFile(decryptedMessage, messageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	// Read the contents of a file as a string
	private String readFileAsString(String filePath) throws IOException {
	    StringBuilder content = new StringBuilder();
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
	        int character;
	        while ((character = reader.read()) != -1) {
	            content.append((char) character);
	        }
	    }
	    return content.toString().trim(); // Trim any leading or trailing whitespace
	}

    // Write a string to a file
    private void writeStringToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    private String vigenereCipher(String message, String key, boolean decrypt) {
        StringBuilder processedMessage = new StringBuilder();
        int keyLength = key.length();
        int keyIndex = 0;
        for (char ch : message.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int shift = key.charAt(keyIndex % keyLength) - base;
                if (decrypt) {
                    // For decryption, invert the shift
                    shift = (26 - shift) % 26;
                }
                processedMessage.append((char) ((ch - base + shift + 26) % 26 + base));
                keyIndex++;
            } else {
                processedMessage.append(ch);
            }
        }
        return processedMessage.toString();
    }
}

