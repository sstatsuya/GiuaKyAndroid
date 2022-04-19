package schedulemail;

/**
 * Project: GiuaKyAndroid
 * Author: Tran Van Tu
 * Date: 4/19/2022 2:47 PM
 * Desc:
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.Properties;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMailAPI extends AsyncTask<Void, Void, Void> {

    //Variables
    private Context mContext;
    private Session mSession;

    private String mEmail;
    private String mSubject;
    private String mMessage;

    private ProgressDialog mProgressDialog;

    //Constructor
    public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
//        this.fileName = fileName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show progress dialog while sending email
        mProgressDialog = ProgressDialog.show(mContext, "Sending message", "Please wait...", false, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismiss progress dialog when message successfully send
        mProgressDialog.dismiss();

        //Show success toast
        Toast.makeText(mContext, "Message Sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();
        final String USERNAME = "tutranvan154@gmail.com";
        final String PASSWORD = "laptrinhandroid";

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(mSession);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mEmail));
            message.setSubject(mSubject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hoa don _2");

            Multipart multipart = new MimeMultipart();
//
            multipart.addBodyPart(messageBodyPart);
//
            messageBodyPart = new MimeBodyPart();

            File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "hoadon_2.pdf");
            //get Source file
            DataSource source = new FileDataSource(file.getPath());
            System.out.println(file.getPath());
//
            messageBodyPart.setDataHandler(new DataHandler(source));
//
            messageBodyPart.setFileName(file.getName());
//
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Send successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}