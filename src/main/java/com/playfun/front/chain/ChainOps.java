package com.playfun.front.chain;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
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

    public Tuple2<Credentials, String> getCredentials(String privateKey) {
        if(WalletUtils.isValidPrivateKey(privateKey)) {
            Credentials credentials = Credentials.create(privateKey);
            //Credentials credentials = WalletUtils.loadCredentials(password, keyStore);

            String fromAddress = credentials.getAddress();

            return Tuples.of(credentials, fromAddress);
        }
        return Tuples.of(null, "Error");
    }

    public BigInteger nonce(String fromAddress) {
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress,
                    DefaultBlockParameterName.LATEST).sendAsync().get();

            return ethGetTransactionCount.getTransactionCount();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("get transaction count error: " + e.getMessage());
        }

        return null;
    }

    public BigInteger gasPrice() {
        try {
            EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();

            return ethGasPrice.getGasPrice();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println();
        }

        return null;
    }

    public BigInteger estimateGas(String from, BigInteger nonce, BigInteger gasPrice, String functionHex) {
        BigInteger gasLimitEst = BigInteger.valueOf(60000L);
        try {
            EthEstimateGas ethEstimateGasFunTrans = web3j.ethEstimateGas(
                            Transaction.createFunctionCallTransaction(from,
                                    nonce,
                                    gasPrice,
                                    gasLimitEst,
                                    contractAddress,
                                    functionHex)
            ).sendAsync().get();

            System.out.println("gas est: " + ethEstimateGasFunTrans.getAmountUsed());

            EthEstimateGas ethEstimateGasEthTrans = web3j.ethEstimateGas(Transaction.createEtherTransaction(from, nonce, gasPrice, gasLimitEst, from, BigInteger.ONE)).sendAsync().get();

            System.out.println("gas est: " + ethEstimateGasEthTrans.getAmountUsed());

            return ethEstimateGasEthTrans.getAmountUsed();
        } catch (InterruptedException| ExecutionException e) {
            System.out.println("estimates gas limit error: " + e.getMessage());
            return BigInteger.ZERO;
        }
    }

    public String functionHex(String toAddress, BigDecimal amount) {
        Address address = new Address(toAddress);

        BigInteger val = amount.multiply(new BigDecimal(ChainTools.getEther())).toBigInteger();
        Uint256 value = new Uint256(val);

        Function function = new Function("transfer",
                Arrays.asList(address, value),
                Collections.singletonList(new TypeReference<Type>() {}));

        return FunctionEncoder.encode(function);
    }

    public String transactionTokenOut(Credentials credentials, BigInteger nonce, String functionHex, BigInteger gasPrice, BigInteger gasLimit) {
            //交易手续费 = gasPrice * gasLimit
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                    gasPrice,
                    gasLimit,
                    this.contractAddress,
                    functionHex);

            String hexValue = Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction, 56L, credentials));

        try {
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            if(!ethSendTransaction.hasError()) {
                return ethSendTransaction.getTransactionHash();
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("transcation error: " + e.getMessage());
        }

        return null;
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





        //String privateKey = "0x81e8adfc28f91dae8e8a33846fda1aa22d9edaab5c23ec7217e34b6de3ab1f19";
        String privateKey = "0x6ee66573e6ec9531886d1241ab7a06ad26d1c8b33c8796caf918742169a53d8e";
        String toAddress = "0x823d04ECADc7B17678A49e89e0E4E0744346f125";

        Tuple2<Credentials, String> c = ops.getCredentials(privateKey);
        BigInteger nonce = ops.nonce(c.getT2());
        BigInteger gasPrice = ops.gasPrice();
        String functionHex = ops.functionHex(toAddress, new BigDecimal(1));
        BigInteger gasLimit = ops.estimateGas(c.getT2(), nonce, gasPrice, functionHex);

        System.out.println(gasLimit);
        //ops.transactionTokenOut(c.getT1(), nonce, functionHex, gasPrice, gasLimit);

    }
}