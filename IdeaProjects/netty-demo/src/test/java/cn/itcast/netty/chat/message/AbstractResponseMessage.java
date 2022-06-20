package cn.itcast.netty.chat.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class AbstractResponseMessage extends Message {
    private boolean success;
    private String reason;

    public AbstractResponseMessage() {}

    public AbstractResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
