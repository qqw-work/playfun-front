package com.playfun.front.model;

import org.hibernate.annotations.GenericGenerator;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;

import javax.persistence.*;

@Erupt(
        name = "账户消息",
        dataProxy = AcctMailDateTimeDataProxy.class,
        primaryKeyCol = "mail_id",
        orderBy = "AcctMail.mail_id desc",
        power = @Power(importable = true, export = true)
)
@Table(name = "acct_mail")
@Entity
public class AcctMail {

    public AcctMail() {}

    public AcctMail(String acctId, String title, String ps, String content) {
        this.acct_id = acctId;
        this.title = title;
        this.time = TimeUtil.now();
        this.pub_day = TimeUtil.today();
        this.ps = ps;
        this.content = content;
    }

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "identity")
    @EruptField(
            views = @View(title = "通知ID")
    )
    private Integer mail_id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "主题"),
            edit = @Edit(title = "主题", notNull = true, search = @Search)
    )
    private String title;

    @EruptField(
            views = @View(title = "发布时间")
    )
    private String time;

    @EruptField(
            views = @View(title = "发布日期")
    )
    private String pub_day;

    @EruptField(
            views = @View(title = "附言"),
            edit = @Edit(title = "附言")
    )
    private String ps;

    @EruptField(
            views = @View(title = "内容"),
            edit = @Edit(title = "内容")
    )
    private String content;


    //getter setter

    public void setTime(String time) {
        this.time = time;
    }

    public void setPubDay(String pub_day) {
        this.pub_day = pub_day;
    }

    public Integer getMail_id() {
        return mail_id;
    }

    public String getAcct_id() {
        return acct_id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getPub_day() {
        return pub_day;
    }

    public String getPs() {
        return ps;
    }

    public String getContent() {
        return content;
    }
}
