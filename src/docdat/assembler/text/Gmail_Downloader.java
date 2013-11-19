/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.assembler.text;

import docdat.io.names.ProcessLogger;
import java.util.Properties;
import javax.mail.*;

/**
 *
 * @author wilfongj
 */
public class Gmail_Downloader {

    public Gmail_Downloader(String project_PathName) {
        setProjectPathName(project_PathName);


    }
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "wilfongjt@gmail.com";
    private static final String SMTP_AUTH_PWD = "ghostdog";
    private static final String SUBJECT_LINE_TARGET = "Map for you";
    private static final String DIGITS = "0123456789";
    private static final String MINDJET = "Powered by Mindjet";
    private static final String QUOTED = "quoted-printable";
    private static final String EXTSTUB = ".mmap.txt.";
    private static final String EXTDEFAULT = "requirement";
    private static final String TMPFILENAME = "ProcessLogger.log";
    private static final String LOADDATE = "load-date";
    private static final String LOGFOLDERNAME = "logs";
    private String projectPathandFileName = "";

    public String getProjectPathName() {
        return projectPathandFileName;
    }

    public void setProjectPathName(String projectPathName) {
        this.projectPathandFileName = projectPathName;
    }

    public String getLogPathandFileName() {

        return getProjectPathName() + LOGFOLDERNAME;

    }
    private Session MailSession = null;
    private Store Store = null;

    public Store getStore() {
        return Store;
    }

    public void setStore(Store store) throws MessagingException {
        //Connect to the current host using the specified username and password.
        store.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
        this.Store = store;
    }

    public Session getMailSession() {

        return MailSession;
    }

    public void setMailSession() throws NoSuchProviderException, MessagingException {

        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");

        this.MailSession = Session.getDefaultInstance(props);
        this.MailSession.setDebug(false);
        // Get a Store object that implements the specified protocol.
        setStore(this.MailSession.getStore("pop3"));

    }

    public void download() throws Exception {

        System.out.println("log folder name: " + getLogPathandFileName());

        String firstline = "";

        // props.put("mail.smtps.quitwait", "false");



        try {
            setMailSession();

            //Create a Folder object corresponding to the given name.
            Folder folder = getStore().getFolder("inbox");

            // Open the Folder.
            folder.open(Folder.READ_ONLY);

            Message[] message = folder.getMessages();
            Part part = null;
            String contentType = null;
            System.out.println("Messages: " + message.length);


            ProcessLogger log = new ProcessLogger(getLogPathandFileName(), TMPFILENAME);
            log.KillLog();

            Object messagecontentObject = null;
            Multipart multipart = null;
            // Display message.
            for (int i = message.length - 1; i > -1; i--) {



                if (message[i].getSubject().startsWith(SUBJECT_LINE_TARGET)) {

                    System.out.println(" 2 " + message[i].getSubject());

                    // Retrieve the message content
                    messagecontentObject = message[i].getContent();
                    if (messagecontentObject instanceof Multipart) {

                        multipart = (Multipart) message[i].getContent();

                        for (int k = 1; k < multipart.getCount(); k++) {

                            part = multipart.getBodyPart(k);
                            contentType = part.getContentType();
                            if (contentType.startsWith("text/plain")) {
                                System.out.println(" 6");
                                System.out.println("---------reading content type text/plain mail -------------");
                            } else {
                                System.out.println("--------- attachment part -------------");
                            }
                        }

                    }

                }

                /*
                if (!message[i].getSubject().startsWith(SUBJECT_LINE_TARGET)) {
                System.out.println("------------ Skipping " + (i + 1) + " ------------");
                }
                System.out.println(" 2 "  + message[i].getSubject() );
                 */
                /*
                if (message[i].getSubject().startsWith(SUBJECT_LINE_TARGET)) {
                System.out.println(" 3");
                
                System.out.println("Subject: " + message[i].getSubject());
                
                
                // Retrieve the message content
                messagecontentObject = message[i].getContent();
                if (messagecontentObject instanceof Multipart) {
                System.out.println(" 4");
                multipart = (Multipart) message[i].getContent();
                
                System.out.println(" has attachement " + multipart.getCount());
                for (int k = 1; k < multipart.getCount(); k++) {
                System.out.println(" 5");
                part = multipart.getBodyPart(k);
                contentType = part.getContentType();
                if (contentType.startsWith("text/plain")) {
                System.out.println(" 6");
                System.out.println("---------reading content type text/plain mail -------------");
                } else {
                System.out.println("--------- attachment part -------------");
                }
                }
                
                }
                
                
                }
                 */



            }
            folder.close(true);
            getStore().close();

        } catch (Exception e) {
            System.err.println(e.toString());
            if (getStore() != null) {
                getStore().close();
            }
        }


        /*  
        public void download() throws Exception {
        
        System.out.println("log folder name: " + getLogPathandFileName());
        
        String firstline = "";
        
        // props.put("mail.smtps.quitwait", "false");
        
        
        
        try {
        setMailSession();
        
        //Create a Folder object corresponding to the given name.
        Folder folder = getStore().getFolder("inbox");
        
        // Open the Folder.
        folder.open(Folder.READ_ONLY);
        
        Message[] message = folder.getMessages();
        Part part = null;
        String contentType = null;
        System.out.println("Messages: " + message.length);
        
        
        ProcessLogger log = new ProcessLogger(getLogPathandFileName(), TMPFILENAME);
        log.KillLog();
        
        Object messagecontentObject = null;
        Multipart multipart = null;
        // Display message.
        for (int i = 0; i < message.length && i < 15; i++) {
        
        
        if (!message[i].getSubject().startsWith(SUBJECT_LINE_TARGET)) {
        System.out.println("------------ Skipping " + (i + 1) + " ------------");
        }
        
        
        if (message[i].getSubject().startsWith(SUBJECT_LINE_TARGET)) {
        System.out.println("Subject: " + message[i].getSubject());
        // Retrieve the message content
        messagecontentObject = message[i].getContent();
        if (messagecontentObject instanceof Multipart) {
        multipart = (Multipart) message[i].getContent();
        
        System.out.println(" has attachement " + multipart.getCount());
        for (int k = 1; k < multipart.getCount(); k++) {
        part = multipart.getBodyPart(i);
        contentType = part.getContentType();
        if (contentType.startsWith("text/plain")) {
        System.out.println("---------reading content type text/plain mail -------------");
        } else {
        }
        }
        // System.out.println("------------ Message " + (i + 1) + " ------------");
        
        //System.out.println("SentDate : " + message[i].getSentDate());
        // System.out.println("From : " + message[i].getFrom()[0]);
        //System.out.println("Subject : " + message[i].getSubject());
        // System.out.print("Message : ");
        
        
        String line = "";
        String line2 = "";
        InputStream stream = message[i].getInputStream();
        char prevCh = 'x';
        
        
        log.Write(line2);
        ProcessLogger pl = new ProcessLogger(getProjectPathName(), TMPFILENAME);
        pl.KillLog();
        System.out.println("------------ A ------------");
        // move past opening junk
        while (stream.available() != 0) {
        char ch = (char) stream.read();
        line += ch;
        if (line.contains(QUOTED)) {
        log.Write(line);
        line = "";
        break;
        }
        System.out.println("------------ A1 ----------- " + ch);
        }
        System.out.println("------------ B ------------");
        // start reading good stuff
        while (stream.available() != 0) {
        
        char ch = (char) stream.read();
        
        switch (ch) {
        case ' ':
        case '\t':
        if (prevCh != '\n') {
        line += ch;
        } else {
        
        if (firstline.length() == 0) {
        firstline = line + "[" + LOADDATE + ": " + new Date() + "]";
        line = line + "[" + LOADDATE + ": " + new Date() + "]";
        }
        
        line = line.replaceAll("=94", "'");
        line = line.replaceAll("=93", "'");
        line = line.replaceAll("=92", "'");
        //line.replaceAll(" = ", "");
        pl.WriteNoDate(line);
        System.out.println(line);
        line = "";
        line += ch;
        }
        break;
        
        case '.':
        case '-':
        case ',':
        case '!':
        case '@':
        case '#':
        case '$':
        case '%':
        case '^':
        case '&':
        case '*':
        case '(':
        case ')':
        case '_':
        case '+':
        case '=':
        case '{':
        case '}':
        case '[':
        case ']':
        case ':':
        case ';':
        case '?':
        case '~':
        case '|':
        line += ch;
        break;
        default:
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) {
        line += ch;
        } else {
        line += " ";  // add a space for the ignored characters
        log.WriteNoDate("[ignored:" + ch + "]");
        }
        // skip it
        
        }
        if (line.contains(MINDJET)) {
        line = line.substring(0, line.indexOf(MINDJET));
        pl.WriteNoDate(line);
        
        break;
        }
        
        prevCh = ch;
        }
        
        }
        
        }
        
        folder.close(true);
        getStore().close();
        
        }
        
        
        }    catch (Exception e) {
        System.err.println(e.toString());
        if (getStore() != null) {
        getStore().close();
        }
        }
        
        
        
        
        // prepare the file name
        String filename = "";
        String ext = "";
        
        
        PseudoElements elems = new PseudoElements();
        
        LoadPseudoElements loader = new LoadPseudoElements(elems, firstline);
        
        PseudoElement elem = elems.getElement(0);
        
        filename = elem.getName();
        System.out.println("Name: " + filename);
        if (elem.getAttributes().getAttribute(Constants.Attributes.EXT) != null) {
        filename = filename + EXTSTUB + elem.getAttributes().getAttribute(Constants.Attributes.EXT).getValue();
        } else {
        filename = filename + EXTSTUB + EXTDEFAULT;
        }
        
        File tof = new File(getProjectPathName(), filename);
        if (tof.exists()) {
        tof.delete();
        }
        File pf = new File(getProjectPathName(), TMPFILENAME);
        pf.renameTo(tof);
        
        System.out.println("out");
        
        }
         */
        /*
        
        // prepare the file name
        String filename = "";
        String ext = "";
        
        
        PseudoElements elems = new PseudoElements();
        
        LoadPseudoElements loader = new LoadPseudoElements(elems, firstline);
        
        PseudoElement elem = elems.getElement(0);
        
        filename = elem.getName();
        System.out.println("Name: " + filename);
        if (elem.getAttributes().getAttribute(Constants.Attributes.EXT) != null) {
        filename = filename + EXTSTUB + elem.getAttributes().getAttribute(Constants.Attributes.EXT).getValue();
        } else {
        filename = filename + EXTSTUB + EXTDEFAULT;
        }
        
        File tof = new File(getProjectPathName(), filename);
        if (tof.exists()) {
        tof.delete();
        }
        File pf = new File(getProjectPathName(), TMPFILENAME);
        pf.renameTo(tof);
        
        System.out.println("out");
         */
    }
}
