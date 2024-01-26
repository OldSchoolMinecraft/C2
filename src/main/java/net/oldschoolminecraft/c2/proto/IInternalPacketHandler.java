package net.oldschoolminecraft.c2.proto;

public interface IInternalPacketHandler extends PacketHandler
{
    void handlePacket(AbstractPacket packet);
    void handleDisconnectPacket(DisconnectPacket packet);
}
