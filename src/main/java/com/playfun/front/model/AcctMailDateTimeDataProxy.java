package com.playfun.front.model;

import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.fun.DataProxy;

public class AcctMailDateTimeDataProxy implements DataProxy<AcctMail> {

    @Comment("增加前")
    public void beforeAdd(AcctMail model) {
        if(model.getTime() == null || model.getTime().equals("")) {
            model.setTime(TimeUtil.now());
        }

        if(model.getPubDay() == null || model.getPubDay().equals("")) {
            model.setPubDay(TimeUtil.today());
        }
    }

    @Comment("修改前")
    public void beforeUpdate(AcctMail model) {
        if(model.getTime() == null || model.getTime().equals("")) {
            model.setTime(TimeUtil.now());
        }

        if(model.getPubDay() == null || model.getPubDay().equals("")) {
            model.setPubDay(TimeUtil.today());
        }
    }
}
