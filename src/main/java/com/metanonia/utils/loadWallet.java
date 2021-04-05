package com.metanonia.utils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;

import static com.metanonia.utils.CommonUtils.compressPubKey;

public class loadWallet {

    public static void main(String[] args) {
        try {
            Credentials credentials = WalletUtils.loadCredentials(args[0], args[1]);

            ECKeyPair ecKeyPair = credentials.getEcKeyPair();
            String privateKey = ecKeyPair.getPrivateKey().toString(16);
            String publicKey = ecKeyPair.getPublicKey().toString(16);
            String address = credentials.getAddress();

            System.out.println("Address = " + address);
            System.out.println("PublicKey = 0x" + publicKey);
            System.out.println("CompressPubKey = 0x" + compressPubKey(ecKeyPair.getPublicKey()));
            System.out.println("PrivateKey = 0x" + privateKey);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
