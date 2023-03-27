package com.playfun.front.model;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Erupt(
        name = "USDT提现",
        orderBy = "UsdtApply.id desc"
)
@Table(name = "usdt_apply")
@Entity
public class UsdtApply {

    @Id
    @Column(name = "id")
    @EruptField
    private String id;


    @EruptField(
            views = @View(title = "批次ID")
    )
    private String batch_id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", readonly = @Readonly, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "链"),
            edit = @Edit(title = "链",
                    notNull = true,
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

//    @EruptField(
//            views = @View(title = "付款钱包"),
//            edit = @Edit(title = "付款钱包", notNull = true, search = @Search)
//    )
    private String in_wallet;

    @EruptField(
            views = @View(title = "用户钱包"),
            edit = @Edit(title = "用户钱包", readonly = @Readonly, search = @Search)
    )
    private String out_wallet;

    @EruptField(
            views = @View(title = "支付USDT"),
            edit = @Edit(title = "支付USDT", readonly = @Readonly, search = @Search)
    )
    private BigDecimal usdt;

    @EruptField(
            views = @View(title = "已消耗HT")
    )
    private Integer ht;

    @EruptField(
            views = @View(title = "费用")
    )
    private Integer fee;

    @EruptField(
            views = @View(title = "是否当日到账"),
            edit = @Edit(title = "是否当日到账",
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(value = "0", label = "否"),
                            @VL(value = "1", label = "是")
                    }))
    )
    private Integer is_today;

    @EruptField(
            views = @View(title = "是否完成"),
            edit = @Edit( title = "是否完成",
                    search = @Search,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(value = "0", label = "未处理"),
                            @VL(value = "1", label = "已完成")
                    })
            )

    )
    private Integer done;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;

    public String getBatch_id() {
        return batch_id;
    }

    public String getChain_id() {
        return chain_id;
    }

    public String getIn_wallet() {
        return in_wallet;
    }

    public BigDecimal getUsdt() {
        return usdt;
    }
}
