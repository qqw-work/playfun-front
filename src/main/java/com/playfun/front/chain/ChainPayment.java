package com.playfun.front.chain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ChainPayment {
    private final Web3j web3j;
    private final String contractAddress;

    @Getter
    private String fromAddress;

    @Getter
    private BigInteger balance;

    @Getter
    private BigInteger nonce;

    @Getter
    private BigInteger gasPrice;

    @Getter
    private String funcStr;

    @Getter private BigInteger gasLimit;


    private Credentials credentials;

    public ChainPayment(String apiProvider, String contractAddress, Credentials credentials) {
        this.web3j = Web3j.build(new HttpService(apiProvider));
        this.contractAddress = contractAddress;
        this.credentials = credentials;
        this.fromAddress = credentials.getAddress();

        this.balance = BigInteger.ZERO;
        this.gasPrice = BigInteger.ZERO;
        this.nonce = BigInteger.ZERO;
        this.gasLimit = BigInteger.ZERO;

        this.funcStr = null;
    }

    public void makeGasPrice() {
        try {
            EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
            this.gasPrice = ethGasPrice.getGasPrice();
        } catch (InterruptedException | ExecutionException e) {
            log.warn("get gas price error: {}", e.getMessage());
        }
    }

    //18 位 = 1u
    public void getTokenBalance() {

        Function function = new Function("balanceOf",
                Arrays.asList(new Address(fromAddress)),
                Arrays.asList(new TypeReference<Address>() {
                }));

        String encode = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(
                fromAddress, contractAddress, encode);

        try {
            EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();

            this.balance = Numeric.decodeQuantity(ethCall.getResult());

        } catch (InterruptedException|ExecutionException e) {
            log.warn("get {} balance error: {}", fromAddress, e.getMessage());
        }
    }

    public void makeNonce() {
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress,
                    DefaultBlockParameterName.LATEST).sendAsync().get();

            this.nonce = ethGetTransactionCount.getTransactionCount();
        } catch (InterruptedException | ExecutionException e) {
            log.warn("get {} nonce error: {}", fromAddress, e.getMessage());
        }
    }

    public void transferFuncHex(String toAddress, BigDecimal usdt) {
        Address address = new Address(toAddress);

        BigInteger val = usdt.multiply(new BigDecimal(ChainTools.getEther())).toBigInteger();
        Uint256 value = new Uint256(val);

        Function function = new Function("transfer",
                Arrays.asList(address, value),
                Collections.singletonList(new TypeReference<Type>() {}));

        this.funcStr = FunctionEncoder.encode(function);
    }

    private boolean isBalanceOk(BigDecimal usdt) {
        this.getTokenBalance();
        log.info("address {} balance is {}, need {}", this.fromAddress, this.balance, usdtToEther(usdt));

        if(usdtToEther(usdt).compareTo(this.balance) > 0) {
            return true;
        }

        return false;
    }

    public boolean estGasLimitOk(BigDecimal usdt) {
        if(isBalanceOk(usdt)
            && this.fromAddress != null
            && this.funcStr != null
            && this.gasPrice.compareTo(BigInteger.ZERO) > 0
            && this.nonce.compareTo(BigInteger.ZERO) > 0) {
            return true;
        }

        return false;
    }

    public void adjustGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public void estimateGasLimit() {
        try {
            BigInteger gasLimitEst = BigInteger.valueOf(60000L);
            EthEstimateGas ethEstimateGasUsdtTrans = web3j.ethEstimateGas(
                            Transaction.createFunctionCallTransaction(this.fromAddress,
                                    this.nonce,
                                    this.gasPrice,
                                    gasLimitEst,
                                    contractAddress,
                                    this.funcStr)
            ).sendAsync().get();
            //this.gasLimit = ethEstimateGasUsdtTrans.getAmountUsed();

            log.info("usdt trans gas limit est is : {}, error is : {}", ethEstimateGasUsdtTrans.getAmountUsed(), ethEstimateGasUsdtTrans.getError());

            EthEstimateGas ethEstimateGasEthTrans = web3j.ethEstimateGas(
                    Transaction.createEtherTransaction(this.fromAddress,
                            this.nonce,
                            this.gasPrice,
                            gasLimitEst,
                            this.fromAddress,
                            BigInteger.ONE)
            ).sendAsync().get();
            this.gasLimit = ethEstimateGasEthTrans.getAmountUsed();

            log.info("eth trans gas limit est is : {}, error is : {} ", ethEstimateGasEthTrans.getAmountUsed(), ethEstimateGasEthTrans.getError());
        } catch (InterruptedException| ExecutionException e) {
            log.info("est gas limit error: {}", e.getMessage());
        }
    }

    public boolean transOk(BigDecimal usdt) {
        if(estGasLimitOk(usdt)
            && this.gasLimit.compareTo(BigInteger.ZERO) > 0) {
            return true;
        }

        return false;
    }

    public String transTokenOut() {
        //交易手续费 = gasPrice * gasLimit
        RawTransaction rawTransaction = RawTransaction.createTransaction(this.nonce,
                this.gasPrice,
                this.gasLimit,
                this.contractAddress,
                this.funcStr);

        String hexValue = Numeric.toHexString(TransactionEncoder
                .signMessage(rawTransaction, 56L, this.credentials));

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

    private BigInteger usdtToEther(BigDecimal usdt) {
        return usdt.multiply(new BigDecimal(getEther())).toBigInteger();
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

    public static BigInteger getTokenBalance(String address, String contractAddress) {

        Function function = new Function("balanceOf",
                Arrays.asList(new Address(address)),
                Arrays.asList(new TypeReference<Address>() {
                }));

        Transaction transaction = Transaction.createEthCallTransaction(
                address, contractAddress, FunctionEncoder.encode(function));

        try {
            EthCall ethCall = Web3j.build(new HttpService("https://bscrpc.com"))
                    .ethCall(transaction, DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();

            return Numeric.decodeQuantity(ethCall.getResult());

        } catch (InterruptedException|ExecutionException e) {
            System.out.println(e.getMessage());
        }

        return BigInteger.ZERO;
    }

    public static void main(String[] args) {
//        String privateKey = "0x6ee66573e6ec9531886d1241ab7a06ad26d1c8b33c8796caf918742169a53d8e";
//        ChainCredentials credentials = new ChainCredentials(privateKey);
//
        String usdtCA = "0x55d398326f99059ff775485246999027b3197955";
//
//        ChainPayment ops = new ChainPayment("https://bscrpc.com",
//                usdtCA,
//                credentials.getCredentials());

        //黑账户
        BigInteger tokenBalance = getTokenBalance("0x29ab475b1ee13d6d9c643713fe93ecff17e21e1f", usdtCA);


        log.info("address: {}, balance: {}", "0x29ab475b1ee13d6d9c643713fe93ecff17e21e1f", new BigDecimal(tokenBalance).divide(new BigDecimal(ChainPayment.getEther())));

//        String toAddress = "0x823d04ECADc7B17678A49e89e0E4E0744346f125";
//
//        BigDecimal usdt = new BigDecimal(1);
//        ops.makeGasPrice();
//        ops.makeNonce();
//        ops.estimateGasLimit();
//        ops.transferFuncHex(toAddress, usdt);
//
//        if(ops.estGasLimitOk(usdt)) {
//            ops.estimateGasLimit();
//        }
//        System.out.println(ops.getGasLimit());
//
//        if(ops.transOk(usdt)) {
//            //ops.transOk(usdt);
//        }
    }
}