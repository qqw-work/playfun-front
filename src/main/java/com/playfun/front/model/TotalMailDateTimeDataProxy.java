package com.playfun.front.model;

import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.fun.DataProxy;

public class TotalMailDateTimeDataProxy implements DataProxy<TotalMail> {
    @Comment("增加前")
    public void beforeAdd(TotalMail model) {
        if(model.getTime() == null || model.getTime().equals("")) {
            model.setTime(TimeUtil.now());
        }

        if(model.getPubDay() == null || model.getPubDay().equals("")) {
            model.setPubDay(TimeUtil.today());
        }
    }

    @Comment("修改前")
    public void beforeUpdate(TotalMail model) {
        if(model.getTime() == null || model.getTime().equals("")) {
            model.setTime(TimeUtil.now());
        }

        if(model.getPubDay() == null || model.getPubDay().equals("")) {
            model.setPubDay(TimeUtil.today());
        }
    }
}
