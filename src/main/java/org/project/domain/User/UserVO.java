package org.project.domain.User;

import java.util.Date;
import java.util.List;

import org.project.domain.Album.AlbumReplyVO;
import org.project.domain.Album.AlbumVO;
import org.project.domain.Board.BoardReplyVO;
import org.project.domain.Board.BoardVO;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UserVO {
	private String uno;		//seq
	private String userid;	//pk, not null
	private String userpw;	//not null
	private String name;	//not null
	private String gender;	
	private String phone;	
	private String email;	//not null >> id/비번찾기 활용
	private String checkStr; //본인인증 메일발송용 난수
	private String zonecode;
	private String address;
	private String addressDetail;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	private Date regdate;
	private Date updateDate;
	private boolean enabled;
	private List<AuthVO> authList;
	private List<AlbumVO> albumList;
	private List<AlbumReplyVO> albumReplyList;
	private List<BoardVO> boardList;
	private List<BoardReplyVO> boardReplyList;	
	private ProfileImageVO profileImg;	
}
