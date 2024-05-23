package vn.dev.tttn.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.dev.tttn.entity.Remember;

@Service
public class RememberService extends BaseService<Remember>{

	@Override
	public Class<Remember> clazz() {
		return Remember.class;
	}

	@Transactional
	public void remember(Integer spostId, Integer id) {
		Remember newRemember = new Remember();
		newRemember.setCreateDate(new Date());
		newRemember.setSpostId(spostId);
		newRemember.setUserId(id);
		super.saveOrUpdate(newRemember);
	}
	
	public List<Remember> getRemembers(Integer loginedId){
		String sql = "SELECT * FROM tttnsql.tbl_remember where user_id=" + loginedId;
		return super.executeNativeSql(sql);
	}
	
	public Remember getRemember(Integer loginedId, Integer spostId){
		String sql = "SELECT * FROM tttnsql.tbl_remember where user_id=" + loginedId +" and spost_id=" + spostId;
		return super.getEntityByNativeSql(sql);
	}

	@Transactional
	public void delete(Remember remember) {
		super.delete(remember);
	}
}
