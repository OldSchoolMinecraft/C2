package net.oldschoolminecraft.c2.event;

import net.oldschoolminecraft.c2.MessageConfig;
import org.bukkit.event.Event;

public class MessageWriteEvent extends Event
{
    private MessageConfig messageConfig;

    public MessageWriteEvent(MessageConfig messageConfig)
    {
        super(Type.CUSTOM_EVENT);
        this.messageConfig = messageConfig;
    }

    public MessageConfig getMessageConfig()
    {
        return messageConfig;
    }
}
