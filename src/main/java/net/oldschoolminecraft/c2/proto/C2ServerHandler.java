package net.oldschoolminecraft.c2.proto;

import net.oldschoolminecraft.c2.C2Framework;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.util.ArrayList;

public class C2ServerHandler extends Thread
{
    public Socket c2socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean running = true;
    private final ArrayList<PacketHandler> packetHandlers = new ArrayList<>();
    private static final boolean debugMode = (boolean) C2Framework.getInstance().getConfig().getConfigOption("debugMode");

    public C2ServerHandler(Socket c2socket)
    {
        super("C2ServerHandler");

        packetHandlers.add(new InternalPacketHandler());
        packetHandlers.addAll(C2Framework.getInstance().getExtensionPacketHandlers());

        this.c2socket = c2socket;
    }

    public void init()
    {
        try
        {
            running = true;
            dis = new DataInputStream(c2socket.getInputStream());
            dos = new DataOutputStream(c2socket.getOutputStream());
            start();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void run()
    {
        while (running)
        {
            try
            {
                int packetID = dis.readInt();
                if (debugMode) System.out.println("[C2] Received packet: " + packetID);
                Class<? extends AbstractPacket> packetClass = PacketRegistry.getPacketClass(packetID);
                AbstractPacket packet = packetClass.getConstructor().newInstance();
                packet.packetID = packetID;
                packet.setC2Handler(this);
                packet.readData(dis);
                for (PacketHandler handler : packetHandlers) handler.handlePacket(packet);
            } catch (EOFException ignored) {}
            catch (Exception ex) {
                if (debugMode) ex.printStackTrace(System.err);
                try
                {
                    sendPacket(new ErrorPacket(ex.getMessage()));
                } catch (Exception ignored) {}
                end(ex.getMessage());
                return;
            }
        }

        System.out.println("[C2] Connection closed: " + c2socket);
    }

    public void sendPacket(AbstractPacket packet)
    {
        if (!running || c2socket.isClosed() || !c2socket.isConnected()) return;
        try
        {
            packet.writeData(dos);
            dos.flush();
        } catch (Exception ex) {
            if (debugMode) ex.printStackTrace(System.err);
            end(ex.getMessage());
        }
    }

    public void end()
    {
        end("Graceful. Initiated by admin or internal service.");
    }

    public void end(String reason)
    {
        try
        {
            System.out.println("[C2] Client disconnected (" + c2socket + "): " + reason);
            running = false;
            dis.close();
            dos.close();
            c2socket.close();
        } catch (Exception ignored) {}
    }
}
