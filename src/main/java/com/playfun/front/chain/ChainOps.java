package com.playfun.front.chain;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ChainOps {
    private final Web3j web3j;
    private final String contractAddress;

    public ChainOps(String apiProvider, String contractAddress) {
        this.web3j = Web3j.build(new HttpService(apiProvider));
        this.contractAddress = contractAddress;
    }

    public Optional<TransactionReceipt> transactionReceipt(String hash) throws IOException, InterruptedException {
        return web3j.ethGetTransactionReceipt(hash)
                .send()
                .getTransactionReceipt();
    }

    //18 位 = 1u
    public BigInteger getTokenBalance(String address) {
        Function function = new Function("balanceOf",
                Arrays.asList(new Address(address)),
                Arrays.asList(new TypeReference<Address>() {
                }));

        String encode = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(
                address, contractAddress, encode);

        EthCall ethCall = null;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return Numeric.decodeQuantity(ethCall.getResult());
    }

    public void transactionTokenOut(String privateKey) {


    }

    public static BigInteger getEther() {
        return BigInteger.TEN.pow(18);
    }

    public static BigInteger getPWei() {
        return BigInteger.TEN.pow(15);
    }

    public static BigInteger getGWei() {
        return BigInteger.TEN.pow(9);
    }

    public static void main(String[] args) {
        ChainOps ops = new ChainOps("https://bscrpc.com", "0x55d398326f99059ff775485246999027b3197955");
        BigInteger tokenBalance = ops.getTokenBalance("0x823d04ECADc7B17678A49e89e0E4E0744346f125");
        BigInteger tokenBalance1 = ops.getTokenBalance("0x2ABe55cCbE8F4A34eC585c2d4dB1ae26AFAB0233");

        System.out.println("token balance : " + tokenBalance);
        System.out.println("token balance 1 : " + tokenBalance1);

        BigInteger tokenBalance2 = ops.getTokenBalance("0x24cbd50a268ce7a9a4338f84aa381c3e7ba5a2d3");
        BigInteger tokenBalance3 = ops.getTokenBalance("0x55d398326f99059ff775485246999027b3197955");

        System.out.println("token balance 2 : " + tokenBalance2);
        System.out.println("token balance 3 : " + tokenBalance3);

        BigInteger tokenBalance4 = ops.getTokenBalance("0xcB8A989E2e59d494f8276E128eE10e089a2c9832");

        System.out.println("token balance 4 : " + tokenBalance4);


    }
}