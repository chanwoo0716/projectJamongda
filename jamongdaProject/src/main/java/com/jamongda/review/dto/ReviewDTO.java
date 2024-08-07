package com.jamongda.review.dto;

import java.sql.Date;
import java.util.List;

public class ReviewDTO {
    private int rev_id;
    private String rev_content;
    private Date rev_date;
    private String email;
    private int ro_id;
    private String ro_name;
    private String acc_name;
    private List<ReviewImageDTO> images;

    public int getRev_id() {
        return rev_id;
    }

    public void setRev_id(int rev_id) {
        this.rev_id = rev_id;
    }

    public String getRev_content() {
        return rev_content;
    }

    public void setRev_content(String rev_content) {
        this.rev_content = rev_content;
    }

    public Date getRev_date() {
        return rev_date;
    }

    public void setRev_date(Date rev_date) {
        this.rev_date = rev_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRo_id() {
        return ro_id;
    }

    public void setRo_id(int ro_id) {
        this.ro_id = ro_id;
    }

    public String getRo_name() {
        return ro_name;
    }

    public void setRo_name(String ro_name) {
        this.ro_name = ro_name;
    }

    public String getAcc_name() {
    	return acc_name;
    }
    
    public void setAcc_name(String acc_name) {
    	this.acc_name = acc_name;
    }

    public List<ReviewImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ReviewImageDTO> images) {
        this.images = images;
    }
}