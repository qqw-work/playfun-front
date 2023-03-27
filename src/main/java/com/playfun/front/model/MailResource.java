package com.playfun.front.model;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Erupt(
        name = "空投资源",
        dataProxy = MailResourceDataProxy.class,
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
            edit = @Edit(title = "消息ID", notNull = true, search = @Search, placeHolder = "创建新消息请填0，复用已有请填对应消息ID")
    )
    private Integer mail_id;

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
                    notNull = true,
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

    @Transient
    @EruptField(
            edit = @Edit(title = "主题")
    )
    private String title;

    @Transient
    @EruptField(
            edit = @Edit(title = "附言")
    )
    private String ps;

    @Transient
    @EruptField(
            edit = @Edit(title = "内容")
    )
    private String content;

    public Integer getMailId() {
        return mail_id;
    }

    public void setMailId(Integer mailId) {
        this.mail_id = mailId;
    }

    public String getAcctId() {
        return acct_id;
    }

    public void setAcctId(String acctId) {
        this.acct_id = acctId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
