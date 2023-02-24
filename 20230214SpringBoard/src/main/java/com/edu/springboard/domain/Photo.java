package com.edu.springboard.domain;

import lombok.Data;

@Data
public class Photo {
	private int photo_idx;
	private String filename;
	Gallery gallery;
}
