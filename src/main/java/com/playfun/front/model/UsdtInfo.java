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

@Erupt(
        name = "usdt信息管理",
        orderBy = "UsdtInfo.id desc"
)
@Table(name = "usdt_info")
@Entity
public class UsdtInfo {

    @Id
    @Column(name = "id")
    @EruptField
    private String id;

    @EruptField(
            views = @View(title = "链ID"),
            edit = @Edit(title = "链ID", notNull = true, search = @Search)
    )
    private String chain_id;

    @EruptField(
            views = @View(title = "USDT兑换HT"),
            edit = @Edit(title = "USDT兑换HT")
    )
    private Integer rate;

    @EruptField(
            views = @View(title = "提现费用"),
            edit = @Edit(title = "提现费用")
    )
    private Integer fee;

    @EruptField(
            views = @View(title = "收款钱包"),
            edit = @Edit(title = "收款钱包", notNull = true)
    )
    private String in_wallet;

    @EruptField(
            views = @View(title = "支付钱包"),
            edit = @Edit(title = "支付钱包", notNull = true)
    )
    private String out_wallet;

    @EruptField(
            views = @View(title = "当天到账上限"),
            edit = @Edit(title = "当天到账上限")
    )
    private Integer instant_transfer;

    @EruptField(
            views = @View(title = "合约地址"),
            edit = @Edit(title = "合约地址", notNull = true)
    )
    private String contract_address;

    @EruptField(
            views = @View(title = "服务商地址"),
            edit = @Edit(title = "服务商地址", notNull = true)
    )
    private String api_provider;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "0", label = "有效"),
                                    @VL(value = "1", label = "失效")
                            }
                    )
            )
    )
    private Integer state;


}

