package com.whk.match.rule;


import com.whk.match.entity.MatchQueue;
import com.whk.match.entity.Room;
import com.whk.match.entity.Team;

public class RulePVE {

    public void match(Room room, MatchQueue matchQueue) {
        // 匹配
        for (Room waiting : matchQueue.getWaitingRoom().values()) {
            // 改成优先队列，二分查找等提高速度
            if (room.getScore() - 10 <= waiting.getScore() && room.getScore() + 10 >= waiting.getScore()) {
                int num = room.getOneTeams().size() + waiting.getOneTeams().size();
                if (num <= 5) {
                    // 迁移
                    for (Team team : room.getOneTeams().values()) {
                        team.setRoomId(waiting.getId());
                        waiting.getOneTeams().put(team.getId(), team);
                    }
                    room.setAbandon(true);
                }
                if (num == 5) {
                    // 匹配完成
                    matchQueue.getWaitingRoom().remove(waiting.getId());
                    matchQueue.getReadyRoom().put(waiting.getId(), waiting);
                }
                break;
            }
        }

        // 匹配失败
        if (!room.isAbandon()) {
            matchQueue.getWaitingRoom().put(room.getId(), room);
        }
    }

    public void noticeMatchOver(MatchQueue matchQueue) {
        for (Room ready : matchQueue.getReadyRoom().values()) {
            for (Team team : ready.getOneTeams().values()) {
                sendMatcherOver(team);
            }
        }
    }

    private void sendMatcherOver(Team team) {
        if (!team.isNotice()) {
            // 通知小队玩家
            System.out.println("team match " + team.getId());
        }
    }
}
