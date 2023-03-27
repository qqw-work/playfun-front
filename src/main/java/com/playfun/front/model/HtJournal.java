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
        name = "Ht流水",
        orderBy = "HtJournal.id desc"
)
@Table(name = "ht_journal")
@Entity
public class HtJournal {

    @Id
    @Column(name = "id")
    @EruptField
    private String id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "操作类型"),
            edit = @Edit(title = "操作类型",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "300001", label = "订单操作"),
                                    @VL(value = "500001", label = "提现USDT"),
                                    @VL(value = "600001", label = "充值USDT")
                            }
                    ))
    )
    private String act_code;

    @EruptField(
            views = @View(title = "流向"),
            edit = @Edit(title = "流向",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "0", label = "北向"),
                                    @VL(value = "1", label = "南向")
                            }
                    ))
    )
    private String direct;

    @EruptField(
            views = @View(title = "订单ID")
    )
    private String order_id;

    @EruptField(
            views = @View(title = "交易HT数量")
    )
    private Integer amount;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;
}
