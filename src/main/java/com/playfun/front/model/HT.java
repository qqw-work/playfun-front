package com.playfun.front.model;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Erupt(
        name = "HT账户",
        primaryKeyCol = "acct_id",
        orderBy = "HT.acct_id desc"
)
@Table(name = "ht")
@Entity
public class HT {

    @Id
    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "余额")
    )
    private Integer balance;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;
}
