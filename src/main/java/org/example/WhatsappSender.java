package org.example;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.time.Month;

public class WhatsappSender {
    private final static String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String kk = System.getenv("KK_MOBILE");
    public static final String cv = System.getenv("CV_MOBILE");;
    public static final String twilioWhatsapp = System.getenv("TWILIO_WHATSAPP_NUM");
    public static final String twilioVoiceNum = System.getenv("TWILIO_VOICE_NUM");

    public void sendMessage(String message){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
         Message.creator(
                new PhoneNumber("whatsapp:"+kk),
                new PhoneNumber("whatsapp:"+twilioWhatsapp),
                message)
                .create();
        Message.creator(
                new PhoneNumber("whatsapp:"+cv),
                new PhoneNumber("whatsapp:"+twilioWhatsapp),
                message)
                .create();

    }

    public void sendVoiceCall(String message) {
        String voiceMessage;
        if(message.length() == 0) {
            voiceMessage = "<Response><Say>Hi! We are sorry to inform you that no Sydney flights are available for " + Month.of(App.month).name() + " now. Please check the website for more details.</Say></Response>";
        }else{
            voiceMessage = "<Response><Say>Hi! Great News! Sydney flights are available for " + Month.of(App.month).name() + " now. Please check your whatsapp message for more details.</Say></Response>";
        }
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Call call = Call.creator(new PhoneNumber(kk), new PhoneNumber(twilioVoiceNum),
                new com.twilio.type.Twiml(voiceMessage)).create();

        System.out.println(call.getSid());
        Call callToCv = Call.creator(new PhoneNumber(cv), new PhoneNumber(twilioVoiceNum),
                new com.twilio.type.Twiml(voiceMessage)).create();

        System.out.println(callToCv.getSid());
    }


}
