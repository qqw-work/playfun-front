package com.playfun.front;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Erupt(
        name = "账户",
        //primaryKeyCol = "wallet_id",
        //orderBy = "Account.acct_id desc",
        power = @Power(importable = true, export = true)
)
@Table(name = "account")
@Entity
public class InnerAccount {

    @Id
    @Column(name = "wallet_id")
    @EruptField(
            views = @View(title = "钱包ID"),
            edit = @Edit(title = "钱包ID", notNull = true, search = @Search)
    )
    private String wallet_id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "昵称"),
            edit = @Edit(title = "昵称", notNull = true, search = @Search)
    )
    private String nick_name;

    @EruptField(
            views = @View(title = "邀请ID"),
            edit = @Edit(title = "邀请ID", notNull = true, search = @Search)
    )
    private String invite_id;

    @EruptField(
            views = @View(title = "邀请人ID"),
            edit = @Edit(title = "邀请人ID", notNull = true, search = @Search)
    )
    private String inviter;


    @EruptField(
            views = @View(title = "账户状态"),
            edit = @Edit(title = "账户状态", notNull = true)
    )
    private int state;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;

}