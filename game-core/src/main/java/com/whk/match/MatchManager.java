package com.whk.match;


import com.whk.match.entity.MatchQueue;
import com.whk.match.entity.Room;
import com.whk.match.entity.Team;
import com.whk.match.id.UIDUtil;
import com.whk.match.rule.RulePVE;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.whk.match.id.IDConst.CACHE;

@Slf4j
public class MatchManager {

    private MatchQueue matchQueue = new MatchQueue();


    private static MatchManager INSTANCE = new MatchManager();


    private MatchManager() {
    }

    public static MatchManager getInstance() {
        return INSTANCE;
    }

    public void testMatch() {
        // 数据准备
        Team team = new Team();
        team.setId(UIDUtil.getId(CACHE));
        Room room = new Room();
        room.setId(UIDUtil.getId(CACHE));
        team.setRoomId(room.getId());
        room.getOneTeams().put(team.getId(), team);
        matchPVE(room);
    }

    public void matchPVE(Room room) {
        // 匹配过程
        RulePVE pve = new RulePVE();
        pve.match(room, matchQueue);
        pve.noticeMatchOver(matchQueue);

    }

    public void matchPVECancel(Team team) {
        Room room = matchQueue.getReadyRoom().get(team.getRoomId());
        if (Objects.isNull(room)) {
            room = matchQueue.getReadyRoom().get(team.getRoomId());
        }

        if (Objects.isNull(room)) {
            // notice(team)
            log.info("notice(team) team removed");
            return;
        }
        room.getOneTeams().remove(team.getId());
        // notice(team)
        log.info("notice(team) remove");
    }

}
