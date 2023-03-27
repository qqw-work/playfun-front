package com.playfun.front.model;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Erupt(
        name = "英雄收益",
        orderBy = "GoodsItem.id desc"
)
@Table(name = "goods_item")
@Entity
public class GoodsItem {

    @Id
    @Column(name = "id")
    @EruptField
    private String id;

    @EruptField(
            views = @View(title = "商品编码"),
            edit = @Edit(title = "商品编码",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "10000001", label = "英雄盲盒"),
                                    @VL(value = "20000001", label = "英雄空间"),
                                    @VL(value = "20000002", label = "英雄体力"),
                                    @VL(value = "20000003", label = "英雄升级"),
                                    @VL(value = "30000001", label = "空投HT"),
                                    @VL(value = "30000002", label = "空投体力"),
                                    @VL(value = "50000001", label = "每日收益(HT)")
                            }
                    ))
    )
    private String item_code;

    @EruptField(
            views = @View(title = "商品规格"),
            edit = @Edit(title = "商品规格", readonly = @Readonly)
    )
    private String item_spec;

    @EruptField(
            views = @View(title = "商品刊例价"),
            edit = @Edit(title = "商品刊例价")
    )
    private Integer rate_card;

    @EruptField(
            views = @View(title = "购买数量限制")
    )
    private Integer fix_count;

    @EruptField(
            views = @View(title = "商品描述")
    )
    private String detail;
}
