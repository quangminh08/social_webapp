package vn.dev.tttn.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.dev.tttn.entity.Comment;

@Service
public class CommentService extends BaseService<Comment>{

	@Override
	public Class<Comment> clazz() {
		// TODO Auto-generated method stub
		return Comment.class;
	}
	
	@Transactional
	public Comment saveOrUpdate(Comment comment) {
		return super.saveOrUpdate(comment);
	}

}
