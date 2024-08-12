package com.jamongda.booking.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	// 환불 처리
	private static final String IMP_API_URL = "https://api.iamport.kr";
    private static final String IMP_API_KEY = "3412245855314472";
    private static final String IMP_API_SECRET = "UjOAlFD4CGtejQmw3GPWmD4TCxA5R98ek95NaqtCA7jzv59cWJyZVNrmmZmfobeDBeXqG4crs1XiWTca";
    private String authToken;

    public boolean refundPayment(String imp_uid, int amount) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("imp_uid", imp_uid);
        requestBody.put("amount", amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAuthToken());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(IMP_API_URL + "/payments/cancel", request, String.class);

        System.out.println("API Response Status: " + response.getStatusCode());
        System.out.println("API Response Body: " + response.getBody());

        return response.getStatusCode().is2xxSuccessful() && response.getBody().contains("\"status\":\"cancelled\"");
    }

    public boolean cancelBooking(Long bo_number) throws Exception {
        return bookingDAO.cancelBooking(bo_number);
    }

    private String getAuthToken() throws Exception {
        if (authToken == null) {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("imp_key", IMP_API_KEY);
            requestBody.put("imp_secret", IMP_API_SECRET);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(IMP_API_URL + "/users/getToken", request, String.class);

            System.out.println("Token API Response Status: " + response.getStatusCode());
            System.out.println("Token API Response Body: " + response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                authToken = parseTokenFromResponse(responseBody);
                System.err.println(authToken);
            } else {
                throw new Exception("Failed to get auth token. Response: " + response.getBody());
            }
        }

        return authToken;
    }

    private String parseTokenFromResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("response").path("access_token").asText();
    }
}