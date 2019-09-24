package integrationHelpers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.ListThreadsResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.Thread;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.net.URLDecoder;

public class GmailIntegrator {
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailIntegrator.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    private static Gmail getService() throws GeneralSecurityException, IOException{
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }
    
    public static void replyEmail(String threadSubject, String replyBody) throws GeneralSecurityException, IOException, MessagingException{
    	Gmail service = getService();
    	String user = "Me";
    	Message msg = listMessagesMatchingQuery(service, user, threadSubject);
    	String threadId = msg.getThreadId();
    	MimeMessage receievedEmail = getMimeMessage(service,user,msg.getId());
    	MimeMessage reEmail = createEmailFromMimeMessage(receievedEmail,replyBody);
    	sendMessage(service, user, reEmail, threadId);
    }
    
    public static String getLatestEmailContent(String threadSubject) throws IOException, MessagingException, GeneralSecurityException{
    	Gmail service = getService();
    	String user = "Me";
    	Message msg = listMessagesMatchingQuery(service, user, threadSubject);
    	return msg.getPayload().getParts().get(1).getBody().getData().toString();
    }
    
    public static String getEchoSignLink(String tsmcRecordName) throws IOException, MessagingException, GeneralSecurityException{
    	String emailSubject = "Please sign "+tsmcRecordName+" Acknowledgement Letter";
    	Gmail service = getService();
    	String user = "Me";
    	Message msg = listMessagesMatchingQuery(service, user, emailSubject);
    	MimeMessage mimMsg = getMimeMessage(service,user,msg.getId());
    	String msgText = getText(mimMsg);
    	String echoSignLink = msgText.substring(msgText.indexOf("href=\"")+6);
    	echoSignLink = echoSignLink.substring(0, echoSignLink.indexOf("\""));
    	return echoSignLink;
    }

    private static List<Thread> listThreadsMatchingQuery(Gmail service, String userId, String query) throws IOException {
    	
	    ListThreadsResponse response = service.users().threads().list(userId).setQ(query).execute();
	    List<Thread> threads = new ArrayList<Thread>();
	    while(response.getThreads() != null) {
	      threads.addAll(response.getThreads());
	      if(response.getNextPageToken() != null) {
	        String pageToken = response.getNextPageToken();
	        response = service.users().threads().list(userId).setQ(query).setPageToken(pageToken).execute();
	      } else {
	        break;
	      }
	    }

	    return threads;
    }
    
    private static Message listMessagesMatchingQuery(Gmail service, String userId, String query) throws IOException, MessagingException {
	    List<String> labels = new ArrayList<String>();
	    labels.add("INBOX");
    	ListMessagesResponse response = service.users().messages().list(userId).setLabelIds(labels).setQ(query).execute();
    	List<Message> messages = new ArrayList<Message>();
	    while (response.getMessages() != null) {
	      messages.addAll(response.getMessages());
	      if (response.getNextPageToken() != null) {
	        String pageToken = response.getNextPageToken();
	        response = service.users().messages().list(userId).setLabelIds(labels).setQ(query)
	            .setPageToken(pageToken).execute();
	      } else {
	        break;
	      }
	    }
	    List<Message> msgs = new ArrayList<Message>();
	    for (Message message : messages) {
	    	Message msg = service.users().messages().get(userId, message.getId()).setFormat("full").execute();
	    	if(!msg.getLabelIds().contains("SENT")){
	    		msgs.add(msg);
	    	}
	    		
	    }
	    Message msg = getLatestMessageInthread(service,userId,msgs);
	    System.out.println("msg -- : " + msg);
	    MimeMessage mim = getMimeMessage(service, userId, msg.getId());
	    System.out.println("Mime message -- : "+getText(mim));
	    return msg;
    }
    
    private static MimeMessage getMimeMessage(Gmail service, String userId, String messageId) throws IOException, MessagingException {
	    Message message = service.users().messages().get(userId, messageId).setFormat("raw").execute();
	    byte[] emailBytes = Base64.decodeBase64(message.getRaw());

	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	    MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
	    return email;
    }    
    
    private static Message getLatestMessageInthread(Gmail service, String userId, List<Message> messages) throws IOException, MessagingException{
    	Map<Long, Message> msgMap = new HashMap<Long, Message>();
    	for(Message msg : messages){
    		msgMap.put(msg.getInternalDate(), msg);
    	}
    	return msgMap.get(Collections.max(msgMap.keySet()));
    }
    private static MimeMessage createEmail(Address to, Address from, String subject, String bodyText) throws AddressException, MessagingException, IOException, GeneralSecurityException{
    	Properties props = new Properties();
    	
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(from);
        email.addRecipient(javax.mail.Message.RecipientType.TO, to);
        email.setSubject("Re: "+subject);
        email.setText(bodyText);
        return email;
    }
    
    private static MimeMessage createEmailFromMimeMessage(MimeMessage receievedEmail, String bodyText) throws MessagingException{
    	Properties props = new Properties();
    	
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(receievedEmail.getRecipients(javax.mail.Message.RecipientType.TO)[0]);
        email.addRecipient(javax.mail.Message.RecipientType.TO, receievedEmail.getFrom()[0]);
        email.setReplyTo(receievedEmail.getReplyTo());
        String headerValue = null;
        for(Address header: receievedEmail.getReplyTo())
        {
        	if(headerValue == null)
        		headerValue = header.toString();
        	headerValue = headerValue+" "+header.toString();
        }
        email.setHeader("References", headerValue);
        email.setSubject("Re: "+receievedEmail.getSubject());
        email.setText(bodyText);
        return email;
    }
    
    private static Message createMessageWithEmail(MimeMessage emailContent, String threadId)
            throws MessagingException, IOException, GeneralSecurityException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        message.setThreadId(threadId);
        return message;
    }    
    
	public static String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent(); 
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null) {
						text = getText(bp);
					}
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null) {
						return s;
					}
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null) {
					return s;
				}
			}
		}

		return null;
	}    
    
    public static Message sendMessage(Gmail service, String userId, MimeMessage emailContent, String threadId) throws MessagingException, IOException, GeneralSecurityException {
		Message message = createMessageWithEmail(emailContent, threadId);
		message = service.users().messages().send(userId, message).execute();
		
		return message;
    }
    public static void main(String... args) throws IOException, GeneralSecurityException, AddressException, MessagingException {
        //Gmail service = getService();
        //String user = "me";
        //String subject = "Please sign TSMC-00018433 Acknowledgement Letter";
        System.out.println("*&*&* - "+getEchoSignLink("TSMC-00018433"));
        //listMessagesMatchingQuery(service, user, subject);
    	//replyEmail(subject,"Tere aage pichhe dol ke :P ");
    }
}
