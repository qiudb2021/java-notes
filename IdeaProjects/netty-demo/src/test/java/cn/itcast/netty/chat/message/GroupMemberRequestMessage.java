package cn.itcast.netty.chat.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupMemberRequestMessage extends Message {
    private String groupName;

    public GroupMemberRequestMessage() {
    }

    public GroupMemberRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupMemberRequestMessage;
    }
}
