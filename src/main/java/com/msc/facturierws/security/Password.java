package com.msc.facturierws.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Micky
 */
public class Password {

    private String password;
    private String key;

    /**
     * Set le password.
     *
     * @param password en clair ou crypter en hexa
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void setKey(String secretKeyEncrypted) {
        this.key = secretKeyEncrypted;
    }

    /**
     * Permet de convertir un string en clair en cle hexadecimal. C'est ce que
     * l'ont mettra dans le fichier properties. Sert d'helper pour la 1ere fois.
     *
     * @return [0] => la cle qui sert a securiser la phrase<br>
     * [1] => la passphrase
     */
    public String[] getConvertedHexaPassword() {
        KeyGenerator keyGen;
        String[] res = new String[2];
        try {
            keyGen = KeyGenerator.getInstance("DESede");
            keyGen.init(168);
            SecretKey cle = keyGen.generateKey();
            byte[] enc = encrypter(password, cle);
            byte[] cleb = convertSecretKey(cle);
            res[0] = DatatypeConverter.printHexBinary(cleb);
            res[1] = DatatypeConverter.printHexBinary(enc);
            return res;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SecretKey getSecretKey() {
        byte[] keyEnc = hexStringToByteArray(this.key);
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(keyEnc));
            return (SecretKey) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private byte[] convertSecretKey(SecretKey sk) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(sk);
        } catch (IOException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (null != oos) {
                    oos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return baos.toByteArray();
    }

    private byte[] hexStringToByteArray(String str) {
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                    + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Convertie la chaine crypte en clair.
     *
     * @return le password decrypter
     */
    public String getConvertedClearPassword() {
        byte[] penc = hexStringToByteArray(this.password);
        SecretKey sk = getSecretKey();
        try {
            return decrypter(penc, sk);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private byte[] encrypter(final String message, SecretKey cle)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, cle);
        byte[] donnees = message.getBytes();

        return cipher.doFinal(donnees);
    }

    private String decrypter(final byte[] donnees, SecretKey cle)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, cle);

        return new String(cipher.doFinal(donnees));
    }

}
