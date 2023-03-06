package com.playfun.front.model;

import org.hibernate.annotations.GenericGenerator;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Erupt(
        name = "系统消息",
        primaryKeyCol = "mail_id",
        orderBy = "TotalMail.mail_id desc",
        power = @Power(importable = true, export = true)
)
@Table(name = "total_mail")
@Entity
public class TotalMail {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "identity")
    @EruptField(
            views = @View(title = "通知ID")
    )
    private int mail_id;

    @EruptField(
            views = @View(title = "主题"),
            edit = @Edit(title = "主题", notNull = true, search = @Search)
    )
    private String title;

    @EruptField(
            views = @View(title = "发布时间"),
            edit = @Edit(title = "发布时间")
    )
    private String time;

    @EruptField(
            views = @View(title = "发布日期"),
            edit = @Edit(title = "发布日期")
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

}
