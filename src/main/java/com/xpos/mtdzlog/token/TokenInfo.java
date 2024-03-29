package com.xpos.mtdzlog.token;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.groovy.parser.antlr4.util.StringUtils;

import lombok.Data;

@Data
@Entity(name = "TOKEN_INFO")
public class TokenInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "TOKEN_ID")
	private Integer tokenId;
	
	@Column(name = "imageUrl")
	private String imageUrl;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "grade")
	private String grade;
	
	@Column(name = "description")
	private String description;

	@Column(name = "owner")
	private String owner;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOKEN_INFO_ID", referencedColumnName = "TOKEN_ID")
	private List<TokenAttribute> tokenAttributes;
	
	public String getMaskingOwnerAddress() {
		if (StringUtils.isEmpty(this.owner)) {
			return "";
		} else {
			return this.owner.substring(0, 5) + "***********" + this.owner.substring(37, 42);
		}
	}
}
