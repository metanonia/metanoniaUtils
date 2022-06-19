package com.metanonia.utils;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class createKey {
    public static void main(String[] args) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        if(args.length != 4) {
            System.out.println("USage: createKey <minAddress> <maxAddress> <minAmt> <maxAmt>");
            System.exit(0);
        }
        int minV = Integer.valueOf(args[0]);
        int maxV = Integer.valueOf(args[1]);
        int min_val = Integer.valueOf(args[2]);
        int max_val = Integer.valueOf(args[3]);
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int cnt = tlr.nextInt(minV, maxV + 1);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        File file = new File("address_"+sdf.format(now)+"_"+String.valueOf(cnt)+".csv");
        FileOutputStream fOut = new FileOutputStream(file);
        OutputStreamWriter out = new OutputStreamWriter(fOut);
        int sum = 0;
        for(int i=0; i<cnt; i++) {
            SecureRandom rand = new SecureRandom();
            rand.setSeed(now.getTime());
            int randomNum = rand.nextInt((max_val - min_val) + 1) + min_val;
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            String address = "0x"+Keys.getAddress(ecKeyPair);
            byte[] pk = Numeric.toBytesPadded(ecKeyPair.getPrivateKey(), 32);
            String priKey = Hex.toHexString(pk);

            //Credentials cr = Credentials.create(priKey);
            //String newaddr = cr.getAddress();
            sum += randomNum;
            out.write(address+", "+String.valueOf(randomNum)+", "+priKey + "\r\n");
        }
        out.flush();
        out.close();
        System.out.println("Total address: "+ String.valueOf(cnt) + "\ttotalAmt: " + String.valueOf(sum));
    }
}
