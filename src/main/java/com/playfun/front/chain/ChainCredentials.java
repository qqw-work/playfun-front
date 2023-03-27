package com.playfun.front.chain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;

@Slf4j
public class ChainCredentials {

    @Getter
    public Credentials credentials;

    public ChainCredentials(String privateKey) {
        makeCredentials(privateKey);
    }

    public ChainCredentials(String password, String keyStoreFileName) {
        makeCredentials(password, keyStoreFileName);
    }

    private void makeCredentials(String privateKey) {
        if(WalletUtils.isValidPrivateKey(privateKey)) {
            this.credentials = Credentials.create(privateKey);
        }
    }

    private void makeCredentials(String password, String keyStoreFileName) {
        try {
            this.credentials = WalletUtils.loadCredentials(password, keyStoreFileName);
        } catch (IOException|CipherException e) {
            log.warn("get credentials error: {}", e.getMessage());
        }
    }
}
