package knowledgeassistant.conversation;

import java.time.Instant;

public class Message
{
    public enum Role
    {
        USER,
        ASSISTANT,
    }

    private final Role role;
    private final Instant createdAt;
    private final String content;

    public Message(Role role, String content)
    {
        this.role = role;
        this.content = content;
        createdAt = Instant.now();
    }

    public Role getRole()
    {
        return role;
    }

    public Instant getCreatedAt()
    {
        return createdAt;
    }

    public String getContent()
    {
        return content;
    }
}