package net.oldschoolminecraft.c2;

import net.oldschoolminecraft.c2.event.MessageWriteEvent;
import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;

import java.io.File;

public class MessageConfig extends Configuration
{
    public MessageConfig(File file)
    {
        super(file);

        reload();
    }

    public void reload()
    {
        load();
        write();
        save();
    }

    public void write()
    {
        generateMessage("authenticated", "&aYou have been authorized!");
        generateMessage("linkSuccess", "&aYour account has been linked successfully!");

        // this is for c2 extensions to be able to write their own messages to this file during the reload process
        MessageWriteEvent event = new MessageWriteEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    private void generateMessage(String key, Object defaultValue)
    {
        if (this.getProperty(key) == null) this.setProperty(key, defaultValue);
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public Object getMessage(String key)
    {
        return this.getProperty(key);
    }

    public Object getMessage(String key, Object defaultValue)
    {
        Object value = getMessage(key);
        if (value == null) value = defaultValue;
        return value;
    }
}
