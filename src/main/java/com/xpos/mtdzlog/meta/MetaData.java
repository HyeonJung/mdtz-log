package com.xpos.mtdzlog.meta;

import java.util.List;

import lombok.Data;

@Data
public class MetaData {

	private String name;
	private String description;
	private String image;
	private List<MetaAttribute> attributes;
}
