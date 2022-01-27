package com.example.honeybee.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.honeybee.R;
import com.example.honeybee.utilService.GmailSender;
import com.example.honeybee.view.activity.AuthenticationActivity;
import com.example.honeybee.view.activity.LoginActivity;
import com.google.firebase.inappmessaging.model.Text;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailFragment extends Fragment {

    public static EmailFragment newInstance(){
        return new EmailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email, null);
        Button button = (Button) view.findViewById(R.id.emailAuthBtn);
        EditText studentNumber = view.findViewById(R.id.studentNumber);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                AuthenticationFragment authenticationFragment = new AuthenticationFragment();
                // System.out.println("학생 번호"+sNumber);
                // MailSender(studentNumber.getText().toString());
                // MailSender("184750");
                // GmailSender gmailSender = new GmailSender("mywnajsldkf@gmail.com","wjddls0709!");
                // String AuthenticationKey = gmailSender.getEmailCode();
                String authKey = getAuthKey();
                bundle.putString("authKey", authKey);
                System.out.println("메일 번호"+(studentNumber.getText().toString()).concat("@jnu.ac.kr"));
                System.out.println("authenticationkey는 "+authKey);
                try {
                    // gmailSender.sendMail("인증 번호", AuthenticationKey, "wnajsldkf@naver.com");

                    new Thread(()->{
                        MailSender(studentNumber.getText().toString(), authKey);
                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                authenticationFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_layout, authenticationFragment);
                transaction.commit();
            }
        });
        return view;
    }

    public String getAuthKey(){
        StringBuffer temp = new StringBuffer();
        for(int i = 0; i < 4; i++){
            double random = Math.random();
            int value = (int)(random*10);
            temp.append(value);
        }

        String AuthenticationKey = temp.toString();

        return AuthenticationKey;
    }


    public void MailSender(String studentNumber, String authKey){
        String jnuDomain = "@jnu.ac.kr";

        String to_email = studentNumber.concat(jnuDomain);

        // mail server 설정
        String host = "smtp.naver.com";
        String user = "wnajsldkf";
        String password = "whddnr1123!";

        // SMTP 서버 정보 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        // 메일 전송
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user, "honeybe"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));

            // 메일 제목
            msg.setSubject("HoneyBe 인증 메일입니다.");
            // 메일 내용
            msg.setText("인증 번호는 "+authKey);
            Transport.send(msg);
            System.out.println("이메일 전송");

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}