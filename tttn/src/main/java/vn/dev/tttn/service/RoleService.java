package vn.dev.tttn.service;

import org.springframework.stereotype.Service;

import vn.dev.tttn.entity.Role;


@Service
public class RoleService extends BaseService<Role>{
	
	
	
	public Role saveAddRole(Role role) {
		return super.saveOrUpdate(role);
	}
	
	
	public Role getRoleByName(String name) {
		String sql = "select * from tbl_role where role_name='" + name + "'";
		return this.getEntityByNativeSql(sql);
	}


	@Override
	public Class<Role> clazz() {
		return Role.class;
	}

}
