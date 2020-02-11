package com.canny.snowflakemigration.service.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PasswordProtector
{
   private static SecretKey key;
   private static Cipher ecipher;
   private static Cipher dcipher;
   public static String encrypt(String password)
   {
      String enc_pass = "";
      try
      {
         key = KeyGenerator.getInstance("DES").generateKey();  
         ecipher = Cipher.getInstance("DES");
         ecipher.init(Cipher.ENCRYPT_MODE, key);
         byte[] utf8 = password.getBytes("UTF8");
         byte[] enc = ecipher.doFinal(utf8);
         byte encodedbkey[] = key.getEncoded();
         enc_pass=Base64.getEncoder().encodeToString((Base64.getEncoder().encodeToString(enc)+","+Base64.getEncoder().encodeToString(encodedbkey)).getBytes());
         return enc_pass;
   }
   catch(Exception e)
      {
         return e.toString();
      }
   }


 public static String decrypt(String enc_pass)
 {
   String password="" , temp="";
    try
    {
   byte[] decoded = Base64.getDecoder().decode(enc_pass);
   temp = new String(decoded);
   String[] pass_key=temp.split(",");
   byte[] decodedKey = Base64.getDecoder().decode(pass_key[1]);
    SecretKey secretKey = new SecretKeySpec(decodedKey, 0,
        decodedKey.length, "DES"); 
   dcipher = Cipher.getInstance("DES");
   dcipher.init(Cipher.DECRYPT_MODE, secretKey);
   byte[] dutf8 = dcipher.doFinal(Base64.getDecoder().decode(pass_key[0]));
   password = new String(dutf8, "UTF8");
   return password;
   }
   catch(Exception e)
   {
      return e.toString();
   }
}
}