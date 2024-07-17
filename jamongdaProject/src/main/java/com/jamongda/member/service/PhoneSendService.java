package com.jamongda.member.service;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class PhoneSendService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.from.number}")
    private String fromNumber;

    public void certifiedPhoneNumber(String tel, String cerNum) {
        Message coolsms = new Message(apiKey, apiSecret);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", tel);
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("text", "자몽다(JAMONGDA) 휴대폰인증 메시지 : 인증번호는 [" + cerNum + "] 입니다.");
        params.put("app_version", "test app 1.2");

        try {
            JSONObject result = coolsms.send(params);
            System.out.println(result.toString());
        } catch (CoolsmsException e) {
            System.out.println("에러 메시지: " + e.getMessage());
            System.out.println("에러 코드: " + e.getCode());
        }
    }
}