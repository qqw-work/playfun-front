package com.playfun.front.model;


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
        name = "订单查询",
        primaryKeyCol = "order_id",
        orderBy = "OrderList.order_id desc"
)
@Table(name = "order_list")
@Entity
public class OrderList {

    @Id
    @EruptField(
            views = @View(title = "订单ID")
    )
    private String order_id;

    @EruptField(
            views = @View(title = "账户ID"),
            edit = @Edit(title = "账户ID", notNull = true, search = @Search)
    )
    private String acct_id;

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
            views = @View(title = "商品规格")
    )
    private String item_spec;

    @EruptField(
            views = @View(title = "订单类型"),
            edit = @Edit(title = "订单类型",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "100001", label = "购买"),
                                    @VL(value = "500001", label = "退款"),
                                    @VL(value = "600001", label = "免费")
                            }
                    ))
    )
    private String account_code;

    @EruptField(
            views = @View(title = "商品刊例价")
    )
    private Integer rate_card;

    @EruptField(
            views = @View(title = "销售价格")
    )
    private Integer sale_price;

    @EruptField(
            views = @View(title = "购买数量")
    )
    private Integer item_count;

    @EruptField(
            views = @View(title = "支付HT")
    )
    private Integer payment;

    @EruptField(
            views = @View(title = "订单状态"),
            edit = @Edit(title = "订单状态",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "0", label = "未支付"),
                                    @VL(value = "1", label = "已支付"),
                                    @VL(value = "2", label = "已取消")
                            }
                    ))
    )
    private Integer state;

    @EruptField(
            views = @View(title = "创建时间")
    )
    private String create_time;

    @EruptField(
            views = @View(title = "更新时间")
    )
    private String uptime;
}
