package net.oldschoolminecraft.c2.proto;

import net.oldschoolminecraft.c2.C2Framework;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class DefaultPacket extends AbstractPacket
{
    public DefaultPacket(int packetID)
    {
        super(packetID);
    }

    public void readData(DataInputStream dis)
    {
        try
        {
            if (keyRequired)
            {
                c2key = dis.readUTF();
                if (!c2key.equals(C2Framework.getInstance().getConfig().getConfigOption("c2key")))
                {
                    c2handler.sendPacket(new DisconnectPacket("Invalid C2 key"));
                    c2handler.end("Invalid C2 key");
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void writeData(DataOutputStream dos)
    {
        try
        {
            dos.writeInt(packetID);
            dos.writeUTF("C2");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void handlePacket(InternalPacketHandler handler)
    {
        handler.handlePacket(this);
    }
}
