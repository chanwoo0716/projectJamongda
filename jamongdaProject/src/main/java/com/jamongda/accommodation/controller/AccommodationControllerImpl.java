package com.jamongda.accommodation.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.accommodation.dto.AccommodationDTO;
import com.jamongda.accommodation.dto.AccommodationImageDTO;
import com.jamongda.accommodation.dto.RoomDTO;
import com.jamongda.accommodation.service.AccommodationService;
import com.jamongda.member.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("accommodationController")
public class AccommodationControllerImpl implements AccommodationController {

	private static String ACCOMMODATION_IMAGE_REPO = "D:\\Hwang\\FileuploadAcc";
	private static String ROOM_IMAGE_REPO = "D:\\Hwang\\FileuploadRo";

	@Autowired
	private AccommodationService accommodationService;

	@Autowired
	private AccommodationDTO accommodationDTO;

	@Autowired
	private RoomDTO roomDTO;

	// 숙소/객실등록 페이지로 이동(사업자 로그인 후 첫 화면)
	@Override
	@GetMapping("/accommodation/regAccommodation.do")
	public ModelAndView regAccommodation(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그인한 사업자의 정보만 보여줘야하므로 세션에서 그 사람의 email꺼내기
		HttpSession session = request.getSession(false);
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("host");

		// 로그인 여부 확인
		if (memberDTO == null) {
			// 로그인이 되어 있지 않으면 /main.do로 보내기
			response.sendRedirect("/main.do");
			session.invalidate();
			return null;
		}

		String email = memberDTO.getEmail();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/accommodation/regAccommodation");
		mav.addObject("hostSidebar", "accommodation/hostSidebar");
		mav.addObject("email", email);
		return mav;
	}

	// 숙소/객실관리 페이지로 이동 (*숙소/객실/regCheck/숙소대표이미지 정보 보여줘야함)
	@Override
	@GetMapping("/accommodation/manageAccommodation.do")
	public ModelAndView manageAccommodation(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그인한 사업자의 정보만 보여줘야하므로 세션에서 그 사람의 email꺼내기
		HttpSession session = request.getSession(false);
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("host");

		// 로그인 여부 확인
		if (memberDTO == null) {
			// 로그인이 되어 있지 않으면 /main.do로 보내기
			response.sendRedirect("/main.do");
			session.invalidate();
			return null;
		}

		String email = memberDTO.getEmail();

		// *임시 이메일 테스트용(관리자에게 이메일 보내는 기능에 쓸 이메일. 원래는 로그인한 사업자 이메일로 써야하지만 도용하는 것 같아서 보류)
		//String email2 = "chanwoo6195@naver.com";

		// *숙소(regCheck도 포함),객실,숙소대표이미지 정보 담는 Map(이미지 포함!)
		Map accRoMap = accommodationService.listAccRo(email);

		// 로그인한 사업자 이메일 세팅
		accRoMap.put("email", email);

		// 로그인한 사업자 이메일 세팅2
		//accRoMap.put("email2", email2);

		// accRoMap확인
		System.out.println("manageAccommodation.do의 accRoMap: " + accRoMap);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/accommodation/manageAccommodation");
		mav.addObject("hostSidebar", "accommodation/hostSidebar");
		mav.addObject("accRoMap", accRoMap);
		mav.addObject("email", email);
		return mav;
	}

	// 회원예약관리 페이지로 이동
	@Override
	@GetMapping("/accommodation/manageReservation.do")
	public ModelAndView manageReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인한 사업자의 정보만 보여줘야하므로 세션에서 그 사람의 email꺼내기
		HttpSession session = request.getSession(false);
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("host");

		// 로그인 여부 확인
		if (memberDTO == null) {
			// 로그인이 되어 있지 않으면 /main.do로 보내기
			response.sendRedirect("/main.do");
			session.invalidate();
			return null;
		}
		
		String email = memberDTO.getEmail();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/accommodation/manageReservation");
		mav.addObject("sidebar", "accommodation/hostSidebar");
		mav.addObject("email", email);
		return mav;
	}

	// 리뷰관리 페이지로 이동.
	@Override
	@GetMapping("/accommodation/manageReview.do")
	public ModelAndView manageReview(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/accommodation/manageReview");
		mav.addObject("sidebar", "accommodation/hostSidebar");
		return mav;
	}

	// 숙소/객실등록에서 "등록하기" 눌렀을 때
	@Override
	@PostMapping("/accommodation/addAccommodation.do")
	public ModelAndView addAccommodation(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {

		String accImageFileName = null;
		// String roImageFileName=null;

		multipartRequest.setCharacterEncoding("utf-8");

		// ★전체 Map★ (accMap, roMap, accImageFileList, roImageMap, regCheck)
		Map<String, Object> accRoMap = new HashMap<>();

		// (accMap)숙소 데이터 담을 accMap
		Map<String, Object> accMap = new HashMap<String, Object>();
		// (roMap)객실들 데이터 담을 roMap
		Map<String, List<String>> roMap = new HashMap<String, List<String>>();

		// 로그인할 때 세션에 그 사람의 이메일 담아서 오면 여기서 getAttribute로 꺼내서 accMap에 put해야함
		// 사업자만 여기로 오게 될 거다. 그거는 로그인에서 처리함
		/*
		 * HttpSession session = multipartRequest.getSession(); MemberDTO
		 * memberDTO=(MemberDTO)session.getAttribute("email"); String
		 * email=memberDTO.getEmail(); accMap.put("email", email); // *accMap에
		 * email넣어야함(accommodation테이블,member테이블)
		 */
		// *임시 이메일 테스트용
		String email = "guro@shillastay.com";
		accMap.put("email", email);

		// (accMap, roMap) accRoMap에 accMap과 roMap 담기(숙소,객실 구분).
		Enumeration enu = multipartRequest.getParameterNames(); // name들 열거

		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);

			System.out.println(name);
			System.out.println(value);

			if (name.contains("acc_")) {
				accMap.put(name, value);
			} else if (name.contains("ro_")) {
				// 객실 이름에서 숫자 추출 (예: ro_name1에서 숫자 1 추출)
				String roomNumber = name.replaceAll("[^0-9]", "");

				// 해당 객실 번호의 리스트가 이미 있는지 확인하고 없으면 새로 생성
				List<String> roomAttributes = roMap.computeIfAbsent("room" + roomNumber, k -> new ArrayList<>());
				roomAttributes.add(value); // 해당 객실 번호의 속성 리스트에 값 추가
			}

			System.out.println("accMap: " + accMap); // accMap의 상태 출력
			System.out.println("roMap: " + roMap); // roMap의 상태 출력
		}
		accRoMap.put("accMap", accMap); // accMap: {acc_name=1, acc_tel=1, acc_type=호텔/리조트, acc_area=1, acc_address=1,
										// acc_info=1, email=guro@shillastay.com}
		accRoMap.put("roMap", roMap); // roMap: {room1=[2, 2, 2, 2, 2, 2, 2, 2], room2=[3, 3, 3, 3, 3, 3, 3, 3]}

		// (accImageFileList) 업로드된 '숙소' 파일명을 담을 List 생성 및 accRoMap에 담기
		List<String> accFileList = accMultiFileUpload(multipartRequest); // 여러개 이미지 이름들 가져옴
		List<AccommodationImageDTO> accImageFileList = new ArrayList<AccommodationImageDTO>();

		if (accFileList != null && accFileList.size() != 0) {
			for (String accFileName : accFileList) {
				AccommodationImageDTO accImageDTO = new AccommodationImageDTO();
				accImageDTO.setAcc_image(accFileName);
				accImageFileList.add(accImageDTO);
			}
			accRoMap.put("accImageFileList", accImageFileList);
		}
		// (roImageMap) 업로드된 '객실' 파일명을 담을 roImageMap 생성 및 accRoMap에 담기
		// *roImageMap은 키, 값으로 되어있음. ro_image1 = 객실1의 이미지이름들, ro_image2= 객실2의 이미지이름들..
		Map<String, List<String>> roImageMap = roMultiFileUpload(multipartRequest); // 여러개 이미지 이름들 가져옴
		accRoMap.put("roImageMap", roImageMap); // roImageMap={ro_image1=[hwangsungbin.webp, ...],
												// ro_image2=[hwangsungbin3.webp, ...]}

		// 리스트에 파일이름 들어갔나 확인하기!
		System.out.println("====================================================================");
		System.out.println("accImageFileList:");
		for (AccommodationImageDTO accImageDTO : accImageFileList) {
			System.out.println("acc_image: " + accImageDTO.getAcc_image());
		}
		// roImageMap 확인
		for (Map.Entry<String, List<String>> entry : roImageMap.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			System.out.print(key + "=[");
			for (int i = 0; i < values.size(); i++) {
				System.out.print(values.get(i));
				if (i < values.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println("]");
		}
		System.out.println("====================================================================");

		// (서비스)숙소 등록 요청 및 숙소 고유 ID가져오기 -> accRoMap안의 accMap에 acc_id넣기
		try {
			// 숙소 등록 서비스 호출 및 숙소 고유 ID인 acc_id 가져오기
			int acc_id = accommodationService.addAccommodation(accRoMap);
			// 업로드된 이미지를 해당 디렉터리로 이동
			if (accImageFileList != null && accImageFileList.size() != 0) {
				for (AccommodationImageDTO accImageDTO : accImageFileList) { // 하나의 폴더에 여러장 이미지 넣는 작업
					accImageFileName = accImageDTO.getAcc_image();
					File srcFile = new File(ACCOMMODATION_IMAGE_REPO + "\\temp\\" + accImageFileName);
					File destDir = new File(ACCOMMODATION_IMAGE_REPO + "\\" + acc_id);
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
		} catch (Exception e) {
			// 숙소 등록 중 오류
			System.out.println("숙소 등록 중 오류!!");
			e.printStackTrace();

			if (accImageFileList != null && accImageFileList.size() != 0) {
				for (AccommodationImageDTO accImageDTO : accImageFileList) { // 하나의 폴더에 여러장 이미지 넣는 작업
					accImageFileName = accImageDTO.getAcc_image();
					File srcFile = new File(ACCOMMODATION_IMAGE_REPO + "\\temp\\" + accImageFileName);
					// 글쓰기 수행 중 오류 발생 시 temp폴더의 이미지를 모두 삭제
					srcFile.delete();
				}
			}
		}

		// (서비스)객실 등록 요청 및 객실 고유 ID 리스트 가져오기 -> accRoMap안의 roMap에 ro_id 넣기
		try {
			// 객실 등록 서비스 호출 및 객실 고유 ID 리스트 가져오기
			List<Integer> roIdList = accommodationService.addRoom(accRoMap);

			// 업로드된 이미지를 해당 디렉터리로 이동
			if (roImageMap != null && !roImageMap.isEmpty()) {
				int idx = 0;
				for (Map.Entry<String, List<String>> entry : roImageMap.entrySet()) {
					int ro_id = roIdList.get(idx);
					List<String> roomImages = entry.getValue();
					for (String roImageFileName : roomImages) {
						File srcFile = new File(ROOM_IMAGE_REPO + "\\temp\\" + roImageFileName);
						File destDir = new File(ROOM_IMAGE_REPO + "\\" + ro_id);
						FileUtils.moveFileToDirectory(srcFile, destDir, true);
					}
					idx++;
				}
			}
		} catch (Exception e) {
			// 객실 등록 중 오류
			System.out.println("객실 등록 중 오류!!");
			e.printStackTrace();

			if (roImageMap != null && !roImageMap.isEmpty()) {
				for (Map.Entry<String, List<String>> entry : roImageMap.entrySet()) {
					List<String> roomImages = entry.getValue();
					for (String roImageFileName : roomImages) {
						File srcFile = new File(ROOM_IMAGE_REPO + "\\temp\\" + roImageFileName);
						// 글쓰기 수행 중 오류 발생 시 temp 폴더의 이미지를 모두 삭제
						srcFile.delete();
					}
				}
			}
		}
		// accRoMap에 뭐있는지 확인하기(에러 확인용)
		System.out.println("accRoMap :" + accRoMap);

		// ModelAndView 객체 생성+반환
		// 숙소등록하면 바로 manageAccommodation으로 가지 말고 컨트롤러 한번 거치게하기(숙소,객실,regDate정보담기)
		ModelAndView mav = new ModelAndView("redirect:/accommodation/manageAccommodation.do");
		return mav;

	}

	// 숙소 이미지 파일 업로드 메서드
	private List<String> accMultiFileUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
		List<String> accFileList = new ArrayList<String>();
		Iterator<String> accFileNames = multipartRequest.getFileNames(); // 반복자(Iterator)에 String타입으로 숙소 이미지 파일이름 담기.

		// 요청에서 각 파일명을 반복
		while (accFileNames.hasNext()) {
			String accFileName = accFileNames.next(); // 반복자(Iterator)에서 다음 파일명을 가져옴.(name)

			MultipartFile mFile = multipartRequest.getFile(accFileName); // 파일명으로부터 MultipartFile 객체를 가져옴
			String originalFileName = mFile.getOriginalFilename(); // 첨부한 이미지 이름 얻어오기(원본 파일명 가져오기)

			if (accFileName != null && accFileName.contains("acc_")) {
				accFileList.add(originalFileName); // 업로드된 파일명을 fileList에 추가
				System.out.println(accFileList);
				File file = new File(ACCOMMODATION_IMAGE_REPO + "\\" + accFileName); // 대상 파일을 나타내는 File 객체를 생성

				// 이미지 크기가 있느냐? (텅 빈 이미지 검사하기)
				if (mFile.getSize() != 0) {
					// 이미지를 수정할 때 기존 이미지가 존재하는지 확인
					if (!file.exists()) {
						// 부모 디렉터리가 존재하지 않으면 생성
						if (file.getParentFile().mkdir()) {
							file.createNewFile(); // 새 파일 생성
						}
					}
					// 파일을 전송(일단 temp에 넣기)
					mFile.transferTo(new File(ACCOMMODATION_IMAGE_REPO + "\\temp\\" + originalFileName));
				}
			}
		}
		return accFileList;
	}// accMultiFileUpload() END

	// 객실 이미지 파일 업로드 메서드
	private Map<String, List<String>> roMultiFileUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
		Map<String, List<String>> roImageMap = new HashMap<>();
		Iterator<String> roFileNames = multipartRequest.getFileNames();

		// 요청에서 각 파일명을 반복
		while (roFileNames.hasNext()) {
			String roFileName = roFileNames.next();
			MultipartFile mFile = multipartRequest.getFile(roFileName);
			String originalFileName = mFile.getOriginalFilename();

			// 파일명에 "ro_image"가 포함되어 있을 경우 처리
			if (roFileName != null && roFileName.contains("ro_image")) {
				String[] parts = roFileName.split("_"); // "_"를 기준으로 분리하여 객실 번호와 이미지 번호를 가져옴
				if (parts.length >= 3) {
					String roomKey = parts[0] + "_" + parts[1]; // ro_image1, ro_image2 등을 key로 사용
					String imageNumber = parts[2].substring(0, 1); // 이미지 번호에서 끝의 숫자만 추출

					List<String> roImageList = roImageMap.getOrDefault(roomKey, new ArrayList<>());

					// 이미지 파일명을 리스트에 추가
					roImageList.add(originalFileName);
					roImageMap.put(roomKey, roImageList);

					// 실제 파일을 업로드
					File file = new File(ROOM_IMAGE_REPO + "/" + roFileName);
					if (mFile.getSize() != 0) {
						if (!file.exists()) {
							if (file.getParentFile().mkdirs()) { // 부모 디렉토리들을 생성하도록 수정
								file.createNewFile();
							}
						}
						mFile.transferTo(new File(ROOM_IMAGE_REPO + "/temp/" + originalFileName));
					}
				}
			}
		}
		// roImageMap 출력
		System.out.println("roImageMap:");
		for (Map.Entry<String, List<String>> entry : roImageMap.entrySet()) {
			String roomListKey = entry.getKey();
			List<String> fileList = entry.getValue();

			System.out.println("Key: " + roomListKey);
			System.out.println("Files:");
			for (String fileName : fileList) {
				System.out.println("- " + fileName);
			}
		}
		return roImageMap;
	}// roMultiFileUpload() END

}