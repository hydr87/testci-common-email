package org.apache.commons.mail;

import static org.junit.Assert.*;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailConcrete;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.mail.Session;
import java.util.Date;
import java.util.Enumeration;

public class EmailTest {

    private Email email;

    @Before
    public void setUp() {
        // Using EmailConcrete subclass
        email = new EmailConcrete();
    }
    
    @After
    public void tearDownEmailTest() throws Exception {
    	
    }
    

    @Test
    public void testAddBcc() throws Exception {
        email.addBcc("test@example.com");
        assertEquals(1, email.getBccAddresses().size());
    }

    @Test
    public void testAddCc() throws Exception {
        email.addCc("cc@example.com");
        assertEquals(1, email.getCcAddresses().size());
    }

    @Test
    public void testAddHeader() throws Exception {
        email.addHeader("X-Test-Header", "testValue");
        email.setHostName("localhost");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.buildMimeMessage(); // Ensure headers are built

        Enumeration<?> headerNames = email.getMimeMessage().getAllHeaderLines();
        boolean headerExists = false;

        while (headerNames.hasMoreElements()) {
            String header = (String) headerNames.nextElement();
            if (header.contains("X-Test-Header: testValue")) {
                headerExists = true;
                break;
            }
        }
        assertTrue(headerExists);
    }

    @Test
    public void testAddReplyTo() throws Exception {
        email.addReplyTo("reply@example.com", "Reply Name");
        assertEquals(1, email.getReplyToAddresses().size());
    }

    @Test
    public void testBuildMimeMessage() throws Exception {
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.setSubject("Test Email");
        email.setMsg("This is a test email.");
        email.buildMimeMessage();
        assertNotNull(email.getMimeMessage());
    }

    @Test
    public void testGetHostName() {
        email.setHostName("smtp.example.com");
        assertEquals("smtp.example.com", email.getHostName());
    }

    @Test
    public void testGetMailSession() throws Exception {
        email.setHostName("smtp.example.com");
        Session session = email.getMailSession();
        assertNotNull(session);
    }

    @Test
    public void testGetSentDate() {
        Date now = new Date();
        email.setSentDate(now);
        assertEquals(now, email.getSentDate());
    }

    @Test
    public void testGetSocketConnectionTimeout() {
        email.setSocketConnectionTimeout(5000);
        assertEquals(5000, email.getSocketConnectionTimeout());
    }

    @Test
    public void testSetFrom() throws Exception {
        email.setFrom("sender@example.com");
        assertEquals("sender@example.com", email.getFromAddress().getAddress());
    }
}
