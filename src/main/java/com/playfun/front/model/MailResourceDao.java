package com.playfun.front.model;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;

import javax.annotation.Resource;

@Service
public class MailResourceDao {
    @Resource
    private EruptDao eruptDao;

    public Integer add(AcctMail acctMail) {
        String insertSql = "insert into acct_mail(mail_id, acct_id, title, time, pub_day, ps, content) " +
                "values (:mail_id, :acct_id, :title, :time, :pub_day, :ps, :content)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        eruptDao.getNamedParameterJdbcTemplate().update(insertSql, new BeanPropertySqlParameterSource(acctMail), keyHolder);

        return keyHolder.getKey().intValue();
    }
}
