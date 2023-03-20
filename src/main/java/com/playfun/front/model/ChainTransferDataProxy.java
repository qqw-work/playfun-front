package com.playfun.front.model;

import com.playfun.front.chain.ChainOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.fun.DataProxy;

import java.math.BigInteger;

@Service
public class ChainTransferDataProxy implements DataProxy<ChainTransfer> {
    @Autowired
    private ChainTransferDao chainTransferDao;

    @Comment("增加前")
    public void beforeAdd(ChainTransfer model) {
        chainTransferDao.fillChainTransfer(model);
        chainTransferDao.updateChargeApply(model.getBatch_id());
    }

    @Comment("数据编辑行为，对待编辑的数据做预处理")
    public void editBehavior(ChainTransfer model) {
        model.setGasLimit(BigInteger.valueOf(30000L));
        System.out.println("before update: " + model.getGasLimit());

        String privateKey = "0x6ee66573e6ec9531886d1241ab7a06ad26d1c8b33c8796caf918742169a53d8e";
        Tuple2<String, String> chainInfo = chainTransferDao.getChainInfo(model.getChain_id());

        //todo 余额检查
        //todo 报错处理
        model.setChainOps(new ChainOps(chainInfo.getT1(), chainInfo.getT2()));
        String fromAddress = model.getChainOps().getCredentials(privateKey).getT2();
        System.out.println("from address: " + fromAddress);
        BigInteger nonce = model.getChainOps().nonce(fromAddress);
        System.out.println("nonce: " + nonce);
        BigInteger gasPrice = model.getChainOps().gasPrice();
        System.out.println("gas price: " + gasPrice);
        String func = model.getChainOps().functionHex(model.getTo_address(), model.getUsdt());

        BigInteger gasLimit = model.getChainOps().estimateGas(fromAddress, nonce, gasPrice, func);
        System.out.println("gas limit is : " + gasLimit);
        model.setGasLimit(gasLimit);
    }

    @Comment("修改后")
    public void afterUpdate(ChainTransfer model) {
        String hash = null;
        //请转+未完成
        if(model.getState() == 1 && model.getDone() == 0) {
            System.out.println("transfer: " + model.getBatch_id());
            //todo 调用链端转账
            hash = "chain_todo";
            //标记已完成
            chainTransferDao.updateChainTransfer(model.getBatch_id(), hash);
        }

    }
}
