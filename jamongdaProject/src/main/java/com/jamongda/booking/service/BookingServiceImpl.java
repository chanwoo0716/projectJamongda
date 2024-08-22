package com.jamongda.booking.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.booking.dao.BookingDAO;
import com.jamongda.booking.dto.BookingDTO;

@Service("bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingDAO bookingDAO;

    @Value("${imp.api.key}")
    private String IMP_API_KEY;

    @Value("${imp.api.secret}")
    private String IMP_API_SECRET;

    private static final String IMP_API_URL = "https://api.iamport.kr";
    private String authToken;
    private long tokenExpiryTime;  // 토큰 만료 시간을 저장하기 위한 필드 추가

    @Override
    public Long createBoNumber() throws Exception {
        Random r = new Random();
        int rNumber = r.nextInt(8888) + 1111;
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Long bo_number = Long.parseLong(dateFormat.format(today) + rNumber);
        // 예약번호가 중복인지 확인
        while (bookingDAO.isExistNumber(bo_number)) {
            rNumber = r.nextInt(8888) + 1111;
            bo_number = Long.parseLong(dateFormat.format(today) + rNumber);
        }
        return bo_number;
    }

    @Override
    public void insertBoInfo(BookingDTO bookingDTO) throws Exception {
        // room 테이블에 ro_id가 유효한지 확인해야함
        RoomDTO roomDTO = bookingDAO.showRoInfo(bookingDTO.getRo_id());
        if (roomDTO == null) {
            throw new Exception("Invalid room ID: " + bookingDTO.getRo_id());
        }
        bookingDAO.insertBoInfo(bookingDTO);
    }

    @Override
    public BookingDTO showBoInfo(Long bo_number) throws Exception {
        BookingDTO bookingDTO = bookingDAO.showBoInfo(bo_number);
        return bookingDTO;
    }

    @Override
    public String showAccInfo(int acc_id) throws Exception {
        String acc_name = bookingDAO.showAccNameById(acc_id);
        return acc_name;
    }

    @Override
    public RoomDTO showRoInfo(int ro_id) throws Exception {
        RoomDTO roomDTO = bookingDAO.showRoInfo(ro_id);
        return roomDTO;
    }

    public boolean refundPayment(String imp_uid, int amount) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("imp_uid", imp_uid);
        requestBody.put("amount", amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAuthToken());
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(IMP_API_URL + "/payments/cancel", request, String.class);

            // 응답 상태 코드와 내용 검증
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody().contains("\"status\":\"cancelled\"");
            } else {
                throw new Exception("Payment refund failed. Status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception("HTTP error occurred during payment refund: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Error occurred during payment refund: " + e.getMessage(), e);
        }
    }

    public boolean cancelBooking(Long bo_number) throws Exception {
        return bookingDAO.cancelBooking(bo_number);
    }

    private String getAuthToken() throws Exception {
        if (authToken == null || isTokenExpired()) {
            RestTemplate restTemplate = new RestTemplate();

            String jsonRequest = "{\"imp_key\":\"" + IMP_API_KEY + "\",\"imp_secret\":\"" + IMP_API_SECRET + "\"}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(IMP_API_URL + "/users/getToken", request, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    String responseBody = response.getBody();
                    authToken = parseTokenFromResponse(responseBody);
                    saveTokenExpiryTime(responseBody);
                } else {
                    throw new Exception("Failed to get auth token. Response: " + response.getBody());
                }
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                throw new Exception("HTTP error occurred during token retrieval: " + e.getMessage(), e);
            } catch (Exception e) {
                throw new Exception("Error occurred during token retrieval: " + e.getMessage(), e);
            }
        }
        return authToken;
    }

    
    private boolean isTokenExpired() {
        return System.currentTimeMillis() > tokenExpiryTime;
    }

    private void saveTokenExpiryTime(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        int expiresIn = rootNode.path("response").path("expires_in").asInt();
        this.tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
    }

    private String parseTokenFromResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("response").path("access_token").asText();
    }
}
