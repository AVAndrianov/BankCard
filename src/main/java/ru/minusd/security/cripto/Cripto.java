package ru.minusd.security.cripto;

import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
@Component
public class Cripto {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    SecretKey priKey = keyGenerator.generateKey();
    Cipher cipher = Cipher.getInstance("AES");

    public Cripto() throws NoSuchAlgorithmException, NoSuchPaddingException {
        keyGenerator.init(128);

    }

    public String cripted(String value) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, priKey);

        byte[] bytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));

        String encrypted = DatatypeConverter.printHexBinary(bytes);

        return encrypted;
    }

    public String uncripted(String value) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        byte[] bytes1 = cipher.doFinal(DatatypeConverter.parseHexBinary(value));

        return new String(bytes1);
    }


}
