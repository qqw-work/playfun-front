package com.playfun.front.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.fun.DataProxy;

@Service
public class ChainTransferDataProxy implements DataProxy<ChainTransfer> {
    @Autowired
    private ChainTransferDao chainTransferDao;

    @Comment("数据新增行为，可对数据做初始化等操作")
    public void addBehavior(ChainTransfer model) {

    }

    @Comment("增加前")
    public void beforeAdd(ChainTransfer model) {
        chainTransferDao.fillChainTransfer(model);
        chainTransferDao.updateChargeApply(model.getBatch_id());
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
