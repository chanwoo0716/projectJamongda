package com.jamongda.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;
import com.jamongda.booking.dto.BookingDTO;
import com.jamongda.search.dao.SearchDAO;
import com.jamongda.search.dto.SearchDTO;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDAO searchDAO;

	@Override
	public List selectAll() throws DataAccessException {
		List accList = searchDAO.selectAllAccsList();
		return accList;
	}

	@Override
	public List search(AccommodationDTO acc) throws DataAccessException {
		List accListOne = searchDAO.searchAccList(acc);
		return accListOne;
	}

	@Override
	public List searchType(SearchDTO acc) throws DataAccessException {
		List accListType = searchDAO.searchAccType(acc);
		return accListType;
	}

	@Override
	public List<SearchDTO> rangePrice(int minPrice, int maxPrice) throws DataAccessException {
		List<SearchDTO> accRangePrice = searchDAO.searchPriceRange(minPrice, maxPrice);
		return accRangePrice;
	}

	@Override
	public Map<String, Object> detailAccRo(int acc_id, String bo_checkIn, String bo_checkOut) throws DataAccessException {
	    Map<String, Object> detailAccRoMap = new HashMap<>();

	    // 숙소 및 객실 정보 가져오기
	    List<AccommodationDTO> accList = searchDAO.selectAcc(acc_id);
	    detailAccRoMap.put("accList", accList);

	    // 객실 정보 가져오기
	    List<List<RoomDTO>> roLists = new ArrayList<>();
	    for (AccommodationDTO acc : accList) {
	        List<RoomDTO> rooms = searchDAO.selectRo(acc.getAcc_id());
	        roLists.add(rooms);
	        detailAccRoMap.put("roList" + acc.getAcc_id(), rooms);
	    }

	    // 숙소 이미지 정보 가져오기
	    List<AccommodationImageDTO> accImageFileList = searchDAO.selectAccImages(acc_id);
	    detailAccRoMap.put("accImageFileList", accImageFileList);

	    // 객실 이미지 정보 가져오기
	    List<RoomImageDTO> roImageFileList = searchDAO.selectRoImages(acc_id);
	    detailAccRoMap.put("roImageFileList", roImageFileList);

	    // 각 객실의 첫 번째 이미지
	    Map<Integer, String> roThumbnail = new HashMap<>();
	    for (List<RoomDTO> rooms : roLists) {
	        for (RoomDTO room : rooms) {
	            for (RoomImageDTO image : roImageFileList) {
	                if (image.getRo_id() == room.getRo_id()) {
	                    roThumbnail.put(room.getRo_id(), image.getRo_image());
	                    break;
	                }
	            }
	        }
	    }
	    detailAccRoMap.put("roThumbnail", roThumbnail);

	    // 예약 상태 확인
	    SearchDTO searchDTO = new SearchDTO();
	    searchDTO.setAcc_id(acc_id);
	    searchDTO.setCheckIn(bo_checkIn);
	    searchDTO.setCheckOut(bo_checkOut);
	    List<BookingDTO> bookings = searchDAO.checkRoomAvailability(searchDTO);
	    System.out.println("Bookings: " + bookings);  // 로그 확인용
	    
	    // 객실 가용성 확인
	    Map<Integer, Boolean> roomAvailability = new HashMap<>();
	    for (List<RoomDTO> rooms : roLists) {
	        for (RoomDTO room : rooms) {
	            boolean isAvailable = true;
	            for (BookingDTO booking : bookings) {
	                if (booking.getRo_id() == room.getRo_id()) {
	                    isAvailable = false;
	                    break;
	                }
	            }
	            roomAvailability.put(room.getRo_id(), isAvailable);
	        }
	    }
	    detailAccRoMap.put("roomAvailability", roomAvailability);

	    return detailAccRoMap;
	}



	// 대표자명, 사업자번호 추가
	@Override
	public Map<String, Object> getHostInfo(int acc_id) throws Exception {
		return searchDAO.selectHostInfo(acc_id);
	}

}