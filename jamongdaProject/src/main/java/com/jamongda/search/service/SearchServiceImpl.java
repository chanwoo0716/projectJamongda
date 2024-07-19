package com.jamongda.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.dto.RoomImageDTO;
import com.jamongda.search.dao.SearchDAO;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDAO searchDAO;
	
	//숙소상세보기
	@Override
	public Map detailAccRo(int acc_id) throws DataAccessException {
		
		Map detailAccRoMap = new HashMap();
		
		// acc_id에 해당하는 숙소 가져오기
		List<AccommodationDTO> accList = searchDAO.selectAcc(acc_id);
		detailAccRoMap.put("accList", accList);
		
		// acc_id에 해당하는 객실 가져오기
	    List<List<RoomDTO>> roLists = new ArrayList<>();  // 각 숙소별 객실 리스트를 저장할 리스트
	    for (AccommodationDTO acc : accList) {
	        List<RoomDTO> rooms = searchDAO.selectRo(acc.getAcc_id());  // 숙소별 객실 리스트를 가져옴
	        roLists.add(rooms);  // 로컬 리스트에 추가
	        detailAccRoMap.put("roList" + acc.getAcc_id(), rooms);  // 맵에 추가
	        
	        System.out.println("detailAccRoMap: " + detailAccRoMap.get("roList" + acc.getAcc_id()));
	        
	        /* 객실 정보 넘어오는지 보고싶을 때 주석 풀어
	        for (RoomDTO room : rooms) {
	            System.out.println("Room ID: " + room.getRo_id());
	            System.out.println("Room Name: " + room.getRo_name());
	            System.out.println("Room Min Capacity: " + room.getRo_min());
	            System.out.println("Room Max Capacity: " + room.getRo_max());
	            System.out.println("Room Info: " + room.getRo_info());
	            System.out.println("Room Amenities: " + room.getRo_amenities());
	            System.out.println("Room Price: " + room.getRo_price());
	            System.out.println("Room Check-In: " + room.getRo_checkIn());
	            System.out.println("Room Check-Out: " + room.getRo_checkOut());
	            System.out.println("Accommodation ID: " + room.getAcc_id());
	            System.out.println("-----------------------------");  // 구분선 추가
	        }
	        */
	    }

	    // acc_id에 해당하는 숙소 이미지 가져오기
	    List<AccommodationImageDTO> accImageFileList = searchDAO.selectAccImages(acc_id);
	    detailAccRoMap.put("accImageFileList", accImageFileList);
	    
	    // acc_id에 해당하는 객실 이미지 가져오기(서브쿼리 이용)
	    List<RoomImageDTO> roImageFileList = searchDAO.selectRoImages(acc_id);
	    detailAccRoMap.put("roImageFileList", roImageFileList);

	    // 각 객실의 첫 번째 이미지
	    Map<Integer, String> roThumbnail = new HashMap<>();
	    for (List<RoomDTO> rooms : roLists) {
	        for (RoomDTO room : rooms) {
	            for (RoomImageDTO image : roImageFileList) {
	                if (image.getRo_id() == room.getRo_id()) {
	                    roThumbnail.put(room.getRo_id(), image.getRo_image());
	                    break;  // 첫 번째 이미지를 찾으면 루프 종료
	                }
	            }
	        }
	    }
	    detailAccRoMap.put("roThumbnail", roThumbnail);
		return detailAccRoMap;

	}

	// 대표자명, 사업자번호 추가
	@Override
	public Map<String, Object> getHostInfo(int acc_id) throws Exception {
		return searchDAO.selectHostInfo(acc_id);
	}

}
