package ru.minusd.security.cripto;

import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class Crypto {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    SecretKey priKey = keyGenerator.generateKey();
    Cipher cipher = Cipher.getInstance("AES");

    public Crypto() throws NoSuchAlgorithmException, NoSuchPaddingException {
        keyGenerator.init(128);
    }

    /**
     * Зашифровка данныех
     *
     * @param value данные для шифрования
     * @return зашифрованные данные
     */
    public String encrypt(String value) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        byte[] bytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(bytes);
    }

    /**
     * Расшифровка данныех
     *
     * @param value данные ранее зашифрованные методом
     * @return расшифрованные данные
     * @see #encrypt(String)
     */
    public String decrypt(String value) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] bytes1 = cipher.doFinal(DatatypeConverter.parseHexBinary(value));
        return new String(bytes1);
    }
}
