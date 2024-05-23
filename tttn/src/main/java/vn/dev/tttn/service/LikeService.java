package vn.dev.tttn.service;


import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import vn.dev.tttn.entity.Like;

@Service
public class LikeService extends BaseService<Like>{

	@Override
	public Class<Like> clazz() {
		// TODO Auto-generated method stub
		return Like.class;
	}
	
	public Like getLikeBySql(Integer spostId, Integer userId) {
		String sql = "select * from tttnsql.tbl_like where spost_id=" + spostId + " and user_id=" + userId +";";
		return super.getEntityByNativeSql(sql);
	}
	
	
	@Transactional
	public void deleteLike(Like like) {
		super.delete(like);
	}



}
