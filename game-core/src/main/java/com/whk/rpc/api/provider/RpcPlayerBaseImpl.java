package com.whk.rpc.api.provider;

import com.whk.mongodb.Dbo;
import com.whk.mongodb.dao.UserAccountDao;
import com.whk.net.enity.MapBean;
import com.whk.rpc.api.IRpcPlayerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public class RpcPlayerBaseImpl implements IRpcPlayerBase {

    @Override
    public List<MapBean> getPlayers(String userId) {
        var user = Dbo.userAccountDao.findByUser(userId);
        if (user.isPresent()){
            var players = user.get().getPlayers();
            return players.stream().map(player -> {
                var m = new MapBean();
                m.putAll(Map.of("id", player.id, "career", player.kind, "sex", player.sex, "lastLogin", player.lastLogin));
                return m;
            }).toList();
        }
        return List.of();
    }
}
