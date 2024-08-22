package com.jamongda.accommodation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.accommodation.dao.AccommodationDAO;
import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;
import com.jamongda.booking.dto.BookingDTO;
import com.jamongda.review.dto.ReviewDTO;
import com.jamongda.review.dto.ReviewImageDTO;

@Service("accommodationService")
public class AccommodationServiceImpl implements AccommodationService {

	@Autowired
	private AccommodationDAO accDAO;

	// 숙소 등록 요청 및 숙소 고유 ID 생성 (getNewAccId(), insertNewAcc(), insertNewImages())
	@Override
	public int addAccommodation(Map accRoMap) throws DataAccessException {

		int acc_id = accDAO.getNewAccId(); // 숙소 고유 ID 생성
		// accRoMap에서 accMap을 꺼내서 사용
		Map<String, Object> accMap = (Map<String, Object>) accRoMap.get("accMap");
		accMap.put("acc_id", acc_id); // accMap에 acc_id 추가

		accDAO.insertNewAcc(accMap); // 숙소 정보 추가

		accRoMap.put("acc_id", acc_id);
		accDAO.insertNewAccImages(accRoMap); // 숙소 이미지 추가

		return acc_id;
	}

	// 여러 객실 등록 요청 및 객실 고유 ID 생성 (getNewRoId(), insertNewRo(), insertNewRoImages())
	// 한 숙소당 객실이 여러개이므로 ro_id가 여러개 나옴 -> ro_id를 리스트에 담아서 리턴해야함-> ro_id는 객실의 개수만큼
	// 생성되어야함->반복문
	@Override
	public List<Integer> addRoom(Map accRoMap) throws DataAccessException {

		int acc_id = (int) accRoMap.get("acc_id");
		// 객실 정보들(객실당 정보들)
		Map<String, List<String>> roMap = (Map<String, List<String>>) accRoMap.get("roMap");
		// 객실 이미지들(객실당 이미지들)
		Map<String, List<String>> roImageMap = (Map<String, List<String>>) accRoMap.get("roImageMap");
		// 객실 ID들 넣는 리스트
		List<Integer> roIdList = new ArrayList<>();

		// *채워져야할 내용*
		for (Map.Entry<String, List<String>> entry : roMap.entrySet()) {
			String roomName = entry.getKey(); // 객실 이름 (room1, room2, ...)
			List<String> roomAttributes = entry.getValue();

			// 새로운 객실 ID 생성
			int newRoId = accDAO.getNewRoId();
			roIdList.add(newRoId);

			RoomDTO roomDTO = new RoomDTO();
			roomDTO.setRo_id(newRoId);
			roomDTO.setRo_name(roomAttributes.get(0));
			roomDTO.setRo_min(Integer.parseInt(roomAttributes.get(1)));
			roomDTO.setRo_max(Integer.parseInt(roomAttributes.get(2)));
			roomDTO.setRo_info(roomAttributes.get(3));
			roomDTO.setRo_amenities(roomAttributes.get(4));
			roomDTO.setRo_price(Integer.parseInt(roomAttributes.get(5)));
			roomDTO.setRo_checkIn(roomAttributes.get(6));
			roomDTO.setRo_checkOut(roomAttributes.get(7));
			roomDTO.setAcc_id(acc_id);

			// 객실 정보 추가
			accDAO.insertNewRo(roomDTO);

			// 객실 이미지 DTO 객체 리스트 생성
			List<RoomImageDTO> roomImageDTOList = new ArrayList<>();
			List<String> roomImages = roImageMap.get("ro_image" + roomName.substring(4));
			for (String imageName : roomImages) {
				RoomImageDTO roomImageDTO = new RoomImageDTO();
				roomImageDTO.setRo_image(imageName);
				roomImageDTO.setRo_id(newRoId);
				roomImageDTOList.add(roomImageDTO);
			}
			// 객실 이미지 추가
			accDAO.insertNewRoImages(roomImageDTOList);
		}
		return roIdList;
	}

	// 숙소/객실관리 (숙소/객실 정보, 숙소/객실 이미지, regCheck 가져오기)
	@Override
	public Map listAccRo(String email) throws DataAccessException {
		Map accRoMap = new HashMap();

		// 숙소 가져오기(regCheck포함) (한 사업자가 여러개 숙소 등록할 수 있으니 List 로 받음)
		List<AccommodationDTO> accList = accDAO.selectAllAcc(email);
		accRoMap.put("accList", accList);

		// 객실 가져오기
		List<List<RoomDTO>> roLists = new ArrayList<>(); // 각 숙소별 객실 리스트를 저장할 리스트
		for (AccommodationDTO acc : accList) {
			List<RoomDTO> rooms = accDAO.selectAllRo(acc.getAcc_id()); // 숙소별 객실 리스트를 가져옴
			roLists.add(rooms); // 로컬 리스트에 추가
			accRoMap.put("roList" + acc.getAcc_id(), rooms); // 맵에 추가
		}
		// 숙소 이미지 가져오기
		List<AccommodationImageDTO> accImageFileList = accDAO.selectAccImages(email);
		accRoMap.put("accImageFileList", accImageFileList);

		// 객실 이미지 가져오기
		List<RoomImageDTO> roImageFileList = accDAO.selectRoImages(email);
		accRoMap.put("roImageFileList", roImageFileList);

		// 각 숙소의 대표 이미지
		Map<Integer, String> accThumbnail = new HashMap<>();
		for (AccommodationDTO acc : accList) {
			for (AccommodationImageDTO image : accImageFileList) {
				if (image.getAcc_id() == (acc.getAcc_id())) {
					accThumbnail.put(acc.getAcc_id(), image.getAcc_image());
					break;
				}
			}
		}
		accRoMap.put("accThumbnail", accThumbnail);

		return accRoMap;
	}

	// 회원 예약 리스트 가져오기
	@Override
	public List<Map<String, Object>> getAccommodationBookingInfo(String email) throws DataAccessException {
		List<Map<String, Object>> reservationList = new ArrayList<>();

		// 이메일로 숙소 정보 조회
		List<AccommodationDTO> accommodations = accDAO.getAccommodationsByEmail(email);

		if (accommodations != null && !accommodations.isEmpty()) {
			List<Integer> accIds = new ArrayList<>();
			for (AccommodationDTO accommodation : accommodations) {
				accIds.add(accommodation.getAcc_id());
			}
			// acc_id들로 객실 정보 조회
			List<RoomDTO> rooms = accDAO.getRoomsByAccIds(accIds);

			if (rooms != null && !rooms.isEmpty()) {
				List<Integer> roIds = new ArrayList<>();
				for (RoomDTO room : rooms) {
					roIds.add(room.getRo_id());
				}

				// ro_id들로 예약 정보 조회
				List<BookingDTO> bookings = accDAO.getBookingsByRoIds(roIds);

				// 숙소, 객실, 예약 정보를 조합하여 결과 생성
				for (AccommodationDTO accommodation : accommodations) {
					List<Map<String, Object>> roomList = new ArrayList<>();
					for (RoomDTO room : rooms) {
						if (room.getAcc_id() == accommodation.getAcc_id()) {
							List<BookingDTO> roomBookings = new ArrayList<>();
							for (BookingDTO booking : bookings) {
								if (booking.getRo_id() == room.getRo_id()) {
									roomBookings.add(booking);
								}
							}
							// 객실에 예약이 있는 경우에만 추가
							if (!roomBookings.isEmpty()) {
								Map<String, Object> roomMap = new HashMap<>();
								roomMap.put("room", room);
								roomMap.put("bookings", roomBookings);
								roomList.add(roomMap);
							}
						}
					}
					// 숙소에 유효한 객실이 있는 경우에만 추가
					if (!roomList.isEmpty()) {
						Map<String, Object> accommodationMap = new HashMap<>();
						accommodationMap.put("accommodation", accommodation);
						accommodationMap.put("rooms", roomList);
						reservationList.add(accommodationMap);
					}
				}
			}
		}
		return reservationList;
	}

	//리뷰 데이터 가져오기(acc_name, ro_name, rev_content, rev_date, email)
	@Override
	public List<Map<String, Object>> getReviewsByHostEmail(String email) throws DataAccessException {
		return accDAO.selectReviewsByHostEmail(email);
	}
	//리뷰 등록하기
	@Override
	public void updateReviewComment(int rev_id, String rev_comment) throws DataAccessException {
		accDAO.updateReviewComment(rev_id, rev_comment);
	}
	//리뷰 이미지 삭제하기
	@Override
	public void delReviewImage(int rev_id) throws DataAccessException {
		accDAO.delReviewImage(rev_id);
	}	
	//리뷰 삭제하기
	@Override
	public void delReview(int rev_id) throws DataAccessException {
		accDAO.delReview(rev_id);
	}
	//리뷰 상세보기 내용가져오기
	@Override
	public ReviewDTO getReviewById(Long revId) throws DataAccessException {
		return accDAO.findReviewById(revId);
	}
	//리뷰 상세보기 이미지가져오기
	@Override
	public List<ReviewImageDTO> getReviewImagesByReviewId(Long revId) throws DataAccessException {
		return accDAO.findReviewImagesByReviewId(revId);
	}

}
