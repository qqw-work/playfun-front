package com.playfun.front.model;


import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import javax.persistence.*;
@Erupt(
        name = "账户",
        primaryKeyCol = "wallet_id",
        orderBy = "InnerAccount.acct_id desc",
        power = @Power(importable = true, export = true)
)
@Table(name = "account")
@Entity
public class InnerAccount {

    @Id
    @EruptField(
            views = @View(title = "钱包ID"),
            edit = @Edit(title = "钱包ID")
    )
    private String wallet_id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "昵称")
    )
    private String nick_name;

    @EruptField(
            views = @View(title = "邀请ID")
    )
    private String invite_id;

    @EruptField(
            views = @View(title = "邀请人ID"),
            edit = @Edit(title = "邀请人ID", search = @Search)
    )
    private String inviter;


    @EruptField(
            views = @View(title = "账户状态"),
            edit = @Edit(title = "账户状态",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "0", label = "有效"),
                                    @VL(value = "1", label = "过期")
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
