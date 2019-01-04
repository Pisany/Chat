package pl.my.client.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    //converting strings to MD5 in java
    public String code(String password) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(), 0, password.length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md == null : "Encryption md = null";
        return (new BigInteger(1, md.digest()).toString(16));
    }


}
