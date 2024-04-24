package org.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyCriteria {	
	private String type;
	private String keyword;
	
	private String userid;	
	private String boardType;
	
	private int pageNum;
	private int amount;	
		
	
	/*검색어 배열생성*/
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
	
	public MyCriteria() {
		this(1, 10);
	}
	
	public MyCriteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
