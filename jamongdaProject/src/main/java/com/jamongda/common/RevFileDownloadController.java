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

@Controller("revFileDownload")
public class RevFileDownloadController {
    private static final String REVIEW_IMAGE_REPO = "C:\\Users\\lynli\\OneDrive\\바탕 화면\\project\\fileupload\\FileuploadRev";

    @GetMapping("/review/downloadImage")
    public void fileDown(@RequestParam("rev_image") String rev_image,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        OutputStream out = response.getOutputStream();
        String path = REVIEW_IMAGE_REPO + "\\" + rev_image;
        File imageFile = new File(path);
        if (!imageFile.exists()) {
            // 파일이 존재하지 않는 경우 예외 처리
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + rev_image);
            return;
        }

        response.setHeader("Cache-Control", "no-cache");

        String encodedFilename = URLEncoder.encode(rev_image, "utf-8").replaceAll("/+", "%20");
        response.addHeader("Content-disposition", "attachment; fileName=\"" + encodedFilename + "\"");

        try (FileInputStream fis = new FileInputStream(imageFile)) {
            byte[] buffer = new byte[1024 * 8];
            int count;
            while ((count = fis.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing the file");
        } finally {
            out.close();
        }
    }
}
