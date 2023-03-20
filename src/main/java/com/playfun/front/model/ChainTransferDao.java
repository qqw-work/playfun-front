package com.playfun.front.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import xyz.erupt.jpa.dao.EruptDao;

import javax.annotation.Resource;
import javax.swing.tree.TreePath;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ChainTransferDao {
    @Resource
    private EruptDao eruptDao;

    public void fillChainTransfer(ChainTransfer chainTransfer) {
        String sql = "select in_wallet, chain_id, usdt from usdt_apply where batch_id = "
                + chainTransfer.getBatch_id() + " and done = 0";
        List<Map<String, Object>> maps = eruptDao.getJdbcTemplate().queryForList(sql);

        if(maps.size() != 1) {
            return;
        }

        chainTransfer.setId(0);
        chainTransfer.setTo_address((String)maps.get(0).get("in_wallet"));
        chainTransfer.setUsdt(BigDecimal.valueOf((double)maps.get(0).get("usdt")));
        chainTransfer.setChain_id((String)maps.get(0).get("chain_id"));
        chainTransfer.setState(0);
        chainTransfer.setDone(0);
        chainTransfer.setUptime(TimeUtil.now());
    }

    public Tuple2<String, String> getChainInfo(String chainId) {
        String sql = "select api_provider, contract_address from usdt_info where chain_id = "
                + chainId + " and state = 0";
        List<Map<String, Object>> maps = eruptDao.getJdbcTemplate().queryForList(sql);

        if(maps.size() != 1) {
            return Tuples.of("", "");
        }

        return Tuples.of((String)maps.get(0).get("api_provider"),
                (String)maps.get(0).get("contract_address"));
    }

    public void updateChargeApply(String batchId) {
        String sql = "update usdt_apply set done = 1, uptime = '"
                + TimeUtil.now() + "' where batch_id = " + batchId;

        eruptDao.getJdbcTemplate().update(sql);
    }

    public void updateChainTransfer(String batchId, String hash) {
        String sql = "update chain_transfer set hash = '" + hash +"', done = 1, uptime = '"
                + TimeUtil.now() + "' where batch_id = " + batchId;

        eruptDao.getJdbcTemplate().update(sql);
    }
}
