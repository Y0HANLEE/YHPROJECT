package org.project.domain.User;

import java.util.Date;
import java.util.List;

import org.project.domain.ProfileImageVO;
import org.project.domain.Album.AlbumReplyVO;
import org.project.domain.Album.AlbumVO;
import org.project.domain.Board.BoardReplyVO;
import org.project.domain.Board.BoardVO;

import lombok.Data;

@Data
public class UserVO {
	private String uno;
	private String userid;
	private String userpw;
	private String name;
	private String gender;
	private String phone;
	private String email;
	private String zonecode;
	private String address;
	private String addressDetail;
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
