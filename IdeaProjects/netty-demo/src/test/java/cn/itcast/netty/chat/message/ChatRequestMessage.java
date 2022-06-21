package cn.itcast.netty.chat.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ChatRequestMessage extends Message{
    private String content;
    private String to;
    private String from;

    public ChatRequestMessage() {
    }

    public ChatRequestMessage(String from, String to, String content) {
        this.content = content;
        this.to = to;
        this.from = from;
    }

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}