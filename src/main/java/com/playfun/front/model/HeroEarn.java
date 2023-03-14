package com.playfun.front.model;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Erupt(
        name = "英雄收益",
        orderBy = "HeroEarn.earn_day desc"
)
@Table(name = "hero_earn")
@Entity
public class HeroEarn {

    @Id
    @Column(name = "id")
    @EruptField
    private String id;

    @EruptField(
            views = @View(title = "日期"),
            edit = @Edit(title = "日期",
                    type = EditType.DATE,
                    search = @Search,
                    notNull = true,
                    dateType = @DateType,
                    placeHolder = "请选择日期")
    )
    private String earn_day;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

    @EruptField(
            views = @View(title = "每日收益(HT)")
    )
    private Integer ht_total;

    @EruptField(
            views = @View(title = "收益状态"),
            edit = @Edit(title = "收益状态",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "0", label = "未领取"),
                                    @VL(value = "1", label = "已领取")
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
