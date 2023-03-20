package com.playfun.front.model;


import com.playfun.front.chain.ChainOps;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Erupt(
        name = "链端转账",
        dataProxy = ChainTransferDataProxy.class,
        orderBy = "ChainTransfer.id desc"
)
@Table(name = "chain_transfer")
@Entity
public class ChainTransfer {

    @Transient
    public ChainOps chainOps;

    public ChainOps getChainOps() {
        return chainOps;
    }

    public void setChainOps(ChainOps chainOps) {
        this.chainOps = chainOps;
    }

    @Id
    @Column(name = "id")
    @EruptField
    private int id;

    @EruptField(
            views = @View(title = "批次ID"),
            edit = @Edit(title = "批次ID", notNull = true, search = @Search)
    )
    private String batch_id;

    public String getChain_id() {
        return chain_id;
    }

    public void setChain_id(String chain_id) {
        this.chain_id = chain_id;
    }

    @EruptField(
            views = @View(title = "链"),
            edit = @Edit(title = "链",
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "56", label = "BSC"),
                                    @VL(value = "1", label = "Eth")
                            }
                    ))
    )
    private String chain_id;

    @EruptField(
            views = @View(title = "收款钱包")
    )
    private String to_address;

    @EruptField(
            views = @View(title = "到账USDT")
    )
    private BigDecimal usdt;

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    //todo 改为实体字段
    @Transient
    @EruptField(
            edit = @Edit(title = "gasLimit")
    )
    private BigInteger gasLimit;

    @EruptField(
            views = @View(title = "转账Hash")
    )
    private String hash;

    @EruptField(
            views = @View(title = "启动"),
            edit = @Edit( title = "启动",
                    search = @Search,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(value = "0", label = "未转"),
                            @VL(value = "1", label = "请转")
                    })
            )

    )
    private Integer state;

    @EruptField(
            views = @View(title = "完成"),
            edit = @Edit( title = "完成",
                    search = @Search,
                    type = EditType.CHOICE,
                    readonly = @Readonly,
                    choiceType = @ChoiceType(vl = {
                            @VL(value = "0", label = "未完成"),
                            @VL(value = "1", label = "已完成")
                    })
            )

    )
    private Integer done;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public BigDecimal getUsdt() {
        return usdt;
    }

    public void setUsdt(BigDecimal usdt) {
        this.usdt = usdt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
