package knowledgeassistant.conversation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatRoomTest 
{
    @Test
    void newRoomIsActiveAndEmpty() {
        ChatRoom room = new ChatRoom(1L, 100L);

        assertEquals(ChatRoom.Status.ACTIVE, room.getStatus());
        assertEquals(0, room.getMessages().size());
    }

    @Test 
    void addMessageKeepsOrder()
    {
        ChatRoom room = new ChatRoom(1L, 100L);
        room.addMessage(new Message(Message.Role.USER, "q"));
        room.addMessage(new Message(Message.Role.ASSISTANT, "a"));
        assertEquals(2, room.getMessages().size());
        assertEquals(Message.Role.USER, room.getMessages().get(0).getRole());
    }

    @Test 
    void archiveChangesStatus()
    {
        ChatRoom room = new ChatRoom(1L, 100L);
        room.archive();
        assertEquals(ChatRoom.Status.ARCHIVED, room.getStatus());
    }

    @Test
    void messagesCannotBeModifiedFromOutside()
    {
        ChatRoom room = new ChatRoom(1L, 100L);
        Message msg = new Message(Message.Role.USER, "x");
        assertThrows(UnsupportedOperationException.class, () -> room.getMessages().add(msg));
    }
}
