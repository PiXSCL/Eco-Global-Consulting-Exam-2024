package com.cipher.vigenere;

public abstract class CipherService {

	public abstract void encrypt(String keyFile, String messageFile, String encryptedMessageFile);

	public abstract void decrypt(String keyFile, String encryptedMessageFile, String messageFile);
	
}
