package com.jamongda.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller("roFileDownload")
public class RoFileDownloadController {
   private static String ROOM_IMAGE_REPO="C:\\Users\\lynli\\OneDrive\\바탕 화면\\project\\fileupload\\FileuploadRo";
   
   @GetMapping("roDownload.do")
   public void fileDown(@RequestParam("ro_id") String ro_id,
         @RequestParam("ro_image") String ro_image,
         HttpServletRequest request,
         HttpServletResponse response) throws Exception {
      
      request.setCharacterEncoding("utf-8");
      response.setContentType("text/html;charset=utf-8");
      
      OutputStream outs = response.getOutputStream();   //input은 이미지 읽어오는 클래스, output은 읽어서 클라이언트에게 보내주는 클래스
      String path = ROOM_IMAGE_REPO + "\\" + ro_id + "\\" + ro_image;
      File imageFile = new File(path);
      response.setHeader("Cache-Control", "no-cache");   //캐시 사용 안한다.
      
      //이미지 파일을 내려받는데(다운로드) 필요한 response헤더 정보를 설정
      ro_image=URLEncoder.encode(ro_image, "utf-8");
      response.addHeader("Content-disposition", "attachment; fileName=" + ro_image);   //attachment:추가하는 명령어
      FileInputStream fis = new FileInputStream(imageFile);   //input은 이미지 읽어오는 클래스
      byte[] buffer = new byte[1024*8];   //buffer를 이용해서 한번에 8kbyte씩 클라이언트에게 전송
      while(true) {
         int count=fis.read(buffer);   //
         if(count==-1) break;   //더 이상 넘겨줄게 없을 경우.
         outs.write(buffer, 0, count);
      }
      fis.close();
      outs.close();
   }
}
