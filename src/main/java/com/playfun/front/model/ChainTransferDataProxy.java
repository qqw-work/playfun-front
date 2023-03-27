package com.playfun.front.model;

import com.playfun.front.chain.ChainCredentials;
import com.playfun.front.chain.ChainPayment;
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

        String privateKey = "0x6ee66573e6ec9531886d1241ab7a06ad26d1c8b33c8796caf918742169a53d8e";
        ChainCredentials credentials = new ChainCredentials(privateKey);

        //todo 余额检查
        //todo 错误处理
        Tuple2<String, String> chainInfo = chainTransferDao.getChainInfo(model.getChain_id());
        ChainPayment payment = new ChainPayment(chainInfo.getT1(), chainInfo.getT2(), credentials.getCredentials());
        model.setChainPayment(payment);


        payment.makeGasPrice();
        payment.makeNonce();
        payment.estimateGasLimit();
        payment.transferFuncHex(model.getTo_address(), model.getUsdt());

        if(payment.estGasLimitOk(model.getUsdt())) {
            payment.estimateGasLimit();
        }

        model.setGasLimit(payment.getGasLimit());
    }

    @Comment("修改后")
    public void afterUpdate(ChainTransfer model) {
        String hash = "NeedTransFromChain";
        //请转+未完成
        if(model.getState() == 1 && model.getDone() == 0) {
            System.out.println("transfer: " + model.getBatch_id());
            ChainPayment payment = model.getChainPayment();

            payment.adjustGasLimit(model.getGasLimit());
            if(payment.transOk(model.getUsdt())) {
                //hash = payment.transTokenOut();
            }

            //标记已完成
            chainTransferDao.updateChainTransfer(model.getBatch_id(), hash);
        }
    }
}
