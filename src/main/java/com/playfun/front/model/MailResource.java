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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Erupt(
        name = "账户消息",
        primaryKeyCol = "mail_id",
        orderBy = "MailResource.mail_id desc",
        power = @Power(importable = true, export = true)
)
@Table(name = "mail_resource")
@Entity
public class MailResource {
    @Id
    @EruptField(
            views = @View(title = "消息ID"),
            edit = @Edit(title = "消息ID", notNull = true, search = @Search)
    )
    private int mail_id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "投放资源ID"),
            edit = @Edit(title = "投放资源ID",
                    notNull = true,
                    search = @Search,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "30000001", label = "HT"),
                                    @VL(value = "30000002", label = "体力药水")
                            }
                    ))
    )
    private String item_id;

    @EruptField(
            views = @View(title = "数量"),
            edit = @Edit(title = "数量", notNull = true)
    )
    private Integer number;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",
                    search = @Search,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "0", label = "未领取"),
                                    @VL(value = "1", label = "已领取")
                            }
                    )
            )
    )
    private Integer res_flag;
}
