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
import com.jamongda.search.dao.SearchDAO;
import com.jamongda.search.dto.SearchDTO;

@Service("searchService")
public class SearchServiceImpl implements SearchService{
   
   @Autowired
   private SearchDAO searchDAO;
   
   @Autowired
   private AccommodationDTO accommodationDTO;

   @Override
   public List selectAll() throws DataAccessException {
      List accList=searchDAO.selectAllAccsList();
      return accList;
   }
   
   @Override
   public List search(AccommodationDTO acc) throws DataAccessException {
      List accListOne=searchDAO.searchAccList(acc);
      return accListOne;
   }
   
   @Override
   public List searchType(AccommodationDTO acc) throws DataAccessException {
      List accListType=searchDAO.searchAccType(acc);
      return accListType;
   }
   
   @Override
   public AccommodationDTO detailSearch(int acc_id) throws DataAccessException {
      AccommodationDTO accommodationDTO=searchDAO.selectAccId(acc_id);
      return accommodationDTO;
   }
   
   @Override
   public AccommodationDTO radio(AccommodationDTO acc) throws DataAccessException{
      AccommodationDTO accommodationDTO=searchDAO.selectRadio(acc);
      return accommodationDTO;
   }

   /*
   @Override
   public List searchImage(AccommodationDTO acc) throws DataAccessException {
      List accImageList = searchDAO.searchImage(acc);

      return accImageList;
   }
   */
   @Override
   public List<SearchDTO> rangePrice(int minPrice, int maxPrice) throws DataAccessException{
      List<SearchDTO> accRangePrice=searchDAO.searchPriceRange(minPrice, maxPrice);
        return accRangePrice;
        
   }
   
   //숙소상세보기
      @Override
      public Map detailAccRo(int acc_id) throws DataAccessException {
         
         Map detailAccRoMap = new HashMap();
         
         // acc_id에 해당하는 숙소 가져오기
         List<AccommodationDTO> accList = searchDAO.selectAcc(acc_id);
         detailAccRoMap.put("accList", accList);
         
         // acc_id에 해당하는 객실 가져오기
          List<List<RoomDTO>> roLists = new ArrayList();  // 각 숙소별 객실 리스트를 저장할 리스트
          for (AccommodationDTO acc : accList) {
              List<RoomDTO> rooms = searchDAO.selectRo(acc.getAcc_id());  // 숙소별 객실 리스트를 가져옴
              roLists.add(rooms);  // 로컬 리스트에 추가
              detailAccRoMap.put("roList" + acc.getAcc_id(), rooms);  // 맵에 추가
              
              // rooms 리스트의 내용을 개별 필드 값으로 출력
              for (RoomDTO room : rooms) {
                  System.out.println("Room ID: " + room.getRo_id() + ", Room Name: " + room.getRo_name() + ", Room Price: " + room.getRo_price());
                  // 필요한 다른 필드도 출력 가능
              }
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
