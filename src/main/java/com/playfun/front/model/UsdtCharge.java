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
        name = "USDT充值",
        orderBy = "UsdtCharge.id desc"
)
@Table(name = "usdt_charge")
@Entity
public class UsdtCharge {

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
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
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

    @EruptField(
            views = @View(title = "付款钱包"),
            edit = @Edit(title = "付款钱包", notNull = true, search = @Search)
    )
    private String in_wallet;

    @EruptField(
            views = @View(title = "收款钱包"),
            edit = @Edit(title = "收款钱包", notNull = true, search = @Search)
    )
    private String out_wallet;

    @EruptField(
            views = @View(title = "收款Hash码")
    )
    private String hash_value;

    @EruptField(
            views = @View(title = "充值USDT")
    )
    private BigDecimal usdt;

    @EruptField(
            views = @View(title = "到账HT")
    )
    private Integer ht;

    @EruptField(
            views = @View(title = "费用")
    )
    private BigDecimal fee;


    @EruptField(
            views = @View(title = "确认状态"),
            edit = @Edit(title = "确认状态",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "0", label = "未确认"),
                                    @VL(value = "1", label = "确认中"),
                                    @VL(value = "2", label = "已充值"),
                                    @VL(value = "3", label = "失败")
                            }
                    )
            )
    )
    private Integer state;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;

}
