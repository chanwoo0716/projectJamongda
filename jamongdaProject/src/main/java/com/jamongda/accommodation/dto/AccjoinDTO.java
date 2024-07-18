package com.jamongda.accommodation.dto;

import lombok.Data;

@Data
public class AccjoinDTO {
	private AccommodationDTO accommodationDTO;
	private AccommodationImageDTO accommodationImageDTO;
	
	public AccommodationDTO getAccommodationDTO() {
		return accommodationDTO;
	}
	public void setAccommodationDTO(AccommodationDTO accommodationDTO) {
		this.accommodationDTO = accommodationDTO;
	}
	public AccommodationImageDTO getAccommodationImageDTO() {
		return accommodationImageDTO;
	}
	public void setAccommodationImageDTO(AccommodationImageDTO accommodationImageDTO) {
		this.accommodationImageDTO = accommodationImageDTO;
	}
}
