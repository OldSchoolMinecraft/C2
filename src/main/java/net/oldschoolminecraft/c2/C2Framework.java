package net.oldschoolminecraft.c2;

import net.oldschoolminecraft.c2.proto.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C2Framework extends JavaPlugin
{
    private static final List<Integer> RESERVED_PACKET_IDS = new ArrayList<>();

    private static C2Framework instance;

    private PluginConfig config;
    private MessageConfig msgConfig;
    private C2ServerListener c2listener;
    private final ArrayList<PacketHandler> extensionPacketHandlers = new ArrayList<>();

    public void onEnable()
    {
        instance = this;

        config = new PluginConfig(new File(getDataFolder(), "config.yml"));
        msgConfig = new MessageConfig(new File(getDataFolder(), "messages.yml"));
        c2listener = new C2ServerListener(config.getInt("c2port", 12992));

        registerPacket(99, DisconnectPacket.class);
        registerPacket(111, ErrorPacket.class);

        System.out.println("C2 enabled");
    }

    public void registerPacketHandler(PacketHandler handler)
    {
        extensionPacketHandlers.add(handler);
    }

    public ArrayList<PacketHandler> getExtensionPacketHandlers()
    {
        return extensionPacketHandlers;
    }

    public void registerPacket(int packetID, Class<? extends AbstractPacket> classz)
    {
        if (RESERVED_PACKET_IDS.contains(packetID)) throw new IllegalArgumentException("The packetID provided is reserved.\nReserved ID(s): " + Arrays.toString(RESERVED_PACKET_IDS.toArray()));
        registerPacket(packetID, classz, false);
    }

    private void registerPacket(int packetID, Class<? extends AbstractPacket> classz, boolean reserved)
    {
        if (reserved) RESERVED_PACKET_IDS.add(packetID);
        PacketRegistry.registerPacket(packetID, classz);
    }

    public PluginConfig getConfig()
    {
        return config;
    }

    public MessageConfig getMsgConfig()
    {
        return msgConfig;
    }

    public void onDisable()
    {
        System.out.println("C2 disabled");
    }

    public static C2Framework getInstance()
    {
        return instance;
    }
}
