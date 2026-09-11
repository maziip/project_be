package knowledgeassistant.conversation;
import java.time.Instant;
import java.util.List;

import java.util.ArrayList;
public class ChatRoom
{
    public enum Status {
        ACTIVE, 
        ARCHIVED,
    }
    
    private Status status;
    private final long id;
    private final long ownerId;
    private final Instant createdAt;
    private final List<Message> messages = new ArrayList<Message>();
    public ChatRoom(long id, long ownerUserId)
    {
        status = Status.ACTIVE;
        createdAt = Instant.now();
        this.id = id;
        this.ownerId = ownerUserId;
    }

    public Status getStatus(){
        return  status;
    }

    public long getId(){
        return id;
    }

    public long getOwnerId(){
        return ownerId;
    }

    public Instant getCreatedAt(){
        return createdAt;
    }

    public List<Message> getMessages(){
        return List.copyOf(messages);
    }

    public void addMessage(Message message)
    {
        messages.add(message);
    }

    public void archive()
    {
        status = Status.ARCHIVED;
    }
}