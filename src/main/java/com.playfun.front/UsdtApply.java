package com.playfun.front;

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
        name = "Usdt提现",
        primaryKeyCol = "id",
        orderBy = "UsdtApply.id desc"
)
@Table(name = "usdt_apply")
@Entity
public class UsdtApply {

    @Id
    @Column(name = "id")
    @EruptField(
            views = @View(title = "自增ID"),
            edit = @Edit(title = "自增ID", notNull = true, search = @Search)
    )
    private String id;


    @EruptField(
            views = @View(title = "批次ID"),
            edit = @Edit(title = "批次ID", notNull = true, search = @Search)
    )
    private String batch_id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "链ID"),
            edit = @Edit(title = "链ID", notNull = true, search = @Search)
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
            views = @View(title = "usdt"),
            edit = @Edit(title = "usdt", notNull = true, search = @Search)
    )
    private float usdt;

    @EruptField(
            views = @View(title = "ht"),
            edit = @Edit(title = "ht", notNull = true, search = @Search)
    )
    private int ht;

    @EruptField(
            views = @View(title = "费用"),
            edit = @Edit(title = "费用", notNull = true, search = @Search)
    )
    private int fee;

    @EruptField(
            views = @View(title = "是否当日到账"),
            edit = @Edit(title = "是否当日到账",
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(value = "0", label = "是"),
                            @VL(value = "1", label = "否")
                    }))
    )
    private int is_today;

    @EruptField(
            views = @View(title = "审核"),
            edit = @Edit(title = "审核", notNull = true, search = @Search)
    )
    private int verify;

    @EruptField(
            views = @View(title = "转账Hash"),
            edit = @Edit(title = "转账Hash", notNull = true, search = @Search)
    )
    private String hash_value;

    @EruptField(
            views = @View(title = "是否完成"),
            edit = @Edit(title = "是否完成", readonly = @Readonly, search = @Search)
    )
    private int done;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;

}
