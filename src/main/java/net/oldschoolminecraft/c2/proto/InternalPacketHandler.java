package net.oldschoolminecraft.c2.proto;

import net.oldschoolminecraft.c2.C2Framework;
import net.oldschoolminecraft.c2.MessageConfig;
import net.oldschoolminecraft.c2.PluginConfig;

public class InternalPacketHandler implements IInternalPacketHandler
{
    protected final PluginConfig config = C2Framework.getInstance().getConfig();
    protected final MessageConfig msgConfig = C2Framework.getInstance().getMsgConfig();

    public void handlePacket(AbstractPacket packet)
    {
        if (packet != null)
        {
            System.err.println("[C2] Received invalid packet from client: " + packet.packetID + ": " + packet.c2handler.c2socket);
        } else System.err.println("[C2] Received malformed packet");
    }

    public void handleDisconnectPacket(DisconnectPacket packet)
    {
        if (packet != null)
        {
            try
            {
                System.out.println("[C2] Client disconnected: " + packet.message);
                packet.c2handler.end();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}
