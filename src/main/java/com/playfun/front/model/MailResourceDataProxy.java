package com.playfun.front.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.fun.DataProxy;

@Service
public class MailResourceDataProxy implements DataProxy<MailResource> {
    @Autowired
    private MailResourceDao mailResourceDao;

    @Comment("增加前")
    public void beforeAdd(MailResource model) {
        if(model.getMailId().equals(0)
                && model.getTitle().length() > 0
                && model.getPs().length() > 0
                && model.getContent().length() > 0) {
            AcctMail acctMail = new AcctMail(model.getAcctId(),
                    model.getTitle(), model.getPs(), model.getContent());

            model.setMailId(mailResourceDao.add(acctMail));
        }
    }

}
