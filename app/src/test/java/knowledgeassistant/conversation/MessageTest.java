package knowledgeassistant.conversation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest
{
    @Test
    void createNewMessage()
    {
        Message message = new Message(Message.Role.USER, "Hello");

        assertEquals("Hello", message.getContent());
        assertEquals(Message.Role.USER, message.getRole());

        assertNotNull(message.getCreatedAt());
    }
}