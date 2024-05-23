package vn.dev.tttn.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.dev.tttn.dto.UserModel;
import vn.dev.tttn.entity.User;

@Service
public class TransmitService {
	
	// không muốn có sự tương tác giưa controller và database
	List<UserModel> userEntitiesToModels(List<User> userEntities){
		List<UserModel> users = userEntities.stream()
				.map(userEntity -> new UserModel(
						userEntity.getId(),
						userEntity.getCreateDate(),
						userEntity.getUsername(),
						userEntity.getPassword(),
						userEntity.getNickname(),
						userEntity.getEmail(),
						userEntity.getDescription(),
						userEntity.getAvatar(),
						userEntity.getStatus()
						)).collect(Collectors.toList());
		return users;
	}
	
	UserModel userToModel(User entity, UserModel model) {
				model.setId(entity.getId());
				model.setCreateDate(entity.getCreateDate());
				model.setUsername(entity.getUsername());
				model.setPassword(entity.getPassword());
				model.setNickname(entity.getNickname());
				model.setEmail(entity.getEmail());
				model.setDescription(entity.getDescription());
				model.setAvatar(entity.getAvatar());
				model.setStatus(entity.getStatus());
			return model;
	}

}
