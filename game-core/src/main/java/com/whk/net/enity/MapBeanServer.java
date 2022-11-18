package com.whk.net.enity;

public class MapBeanServer extends MapBean{

    private int seqId;

    private EnumMessageType messageType;

    @Override
    public MapBeanServer clone() {
        var mapBeanServer = (MapBeanServer)super.clone();
        mapBeanServer.setMessageType(messageType);
        mapBeanServer.setSeqId(seqId);
        return mapBeanServer;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public EnumMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(EnumMessageType messageType) {
        this.messageType = messageType;
    }

    public String getPlayerId() {
        return getString("playerId");
    }

    public int getServerId(){
        return getInt("serverId");
    }
}
