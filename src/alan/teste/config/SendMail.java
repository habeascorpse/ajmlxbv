
package alan.teste.config;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp  
public class SendMail extends Thread {

    private String mailSMTPServer;
    private String mailSMTPServerPort;
    private String from;
    private String mailTo;
    private String subject;
    private String message;

    private String UsuarioCadastro;
    private String SenhaCadastro;


    /* 
     * quando instanciar um Objeto ja sera atribuido o servidor SMTP do GMAIL  
     * e a porta usada por ele 
     */
     public SendMail(String from, String to, String subject, String message) { //Para o GMAIL   
        mailSMTPServer = "smtp.gmail.com";
        mailSMTPServerPort = "465";
        this.from = from;
        this.mailTo = to;
        this.subject = subject;
        this.message = message;
    }
    /* 
     * caso queira mudar o servidor e a porta, so enviar para o contrutor 
     * os valor como string 
     */

    public SendMail(String mailSMTPServer, String mailSMTPServerPort) { //Para outro Servidor  
        this.mailSMTPServer = mailSMTPServer;
        this.mailSMTPServerPort = mailSMTPServerPort;
    }

    private void sendMail() {

        Properties props = new Properties();

        // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado  
                /* 
         props.setProperty("proxySet","true"); 
         props.setProperty("socksProxyHost","192.168.155.1"); // IP do Servidor Proxy 
         props.setProperty("socksProxyPort","1080");  // Porta do servidor Proxy 
         */
        props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP  
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL  
        props.put("mail.smtp.auth", "true"); //ativa autenticacao  
        props.put("mail.smtp.user", from); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)  
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", mailSMTPServerPort); //porta  
        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket  
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtps.ssl.checkserveridentity", "false");
        props.put("mail.smtps.ssl.trust", "*");

        //Cria um autenticador que sera usado a seguir  
        SimpleAuth auth = null;
        auth = new SimpleAuth("no-reply@cloudmessenger.com.br", "mocpadrao");

        //Session - objeto que ira realizar a conexão com o servidor  
        /*Como há necessidade de autenticação é criada uma autenticacao que 
         * é responsavel por solicitar e retornar o usuário e senha para  
         * autenticação */
        Session session = Session.getDefaultInstance(props, auth);
        session.setDebug(true); //Habilita o LOG das ações executadas durante o envio do email  

        //Objeto que contém a mensagem  
        Message msg = new MimeMessage(session);

        try {
            //Setando o destinatário  
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            //Setando a origem do email  
            msg.setFrom(new InternetAddress(from));
            //Setando o assunto  
            msg.setSubject(subject);

            final MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(message, "text/html");

            final MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent("-", "text/plain");

            final Multipart mp = new MimeMultipart("alternative");

            mp.addBodyPart(textPart);
            mp.addBodyPart(htmlPart);

            //Setando o conteúdo/corpo do email  
            //    msg.setContent(message, "text/plain");
            msg.setContent(mp);

        } catch (Exception e) {
            System.out.println(">> Erro: Completar Mensagem");
            e.printStackTrace();
        }

        //Objeto encarregado de enviar os dados para o email  
        Transport tr;
        try {
            tr = session.getTransport("smtp"); //define smtp para transporte  
            /* 
             *  1 - define o servidor smtp 
             *  2 - seu nome de usuario do gmail 
             *  3 - sua senha do gmail 
             */
            tr.connect(mailSMTPServer, "no-reply@cloudmessenger.com.br", "mocpadrao");
            msg.saveChanges(); // don't forget this  
            //envio da mensagem  
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            System.out.println(">> Erro: Envio Mensagem");
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        sendMail();

    }

    public static boolean validEmailInstitucional(String email) {

//    Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@ifro.edu.br");
        Matcher m = p.matcher(email);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
}

class SimpleAuth extends Authenticator {

    public String username = null;
    public String password = null;

    public SimpleAuth(String user, String pwd) {
        username = user;
        password = pwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
