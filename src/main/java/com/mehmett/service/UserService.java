package com.mehmett.service;

import com.mehmett.dto.request.UserRequestDTO;
import com.mehmett.dto.response.UserResponseDTO;
import com.mehmett.entity.Comment;
import com.mehmett.entity.User;
import com.mehmett.entity.Video;
import com.mehmett.repository.UserRepository;
import com.mehmett.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;

public class UserService  {
	private final UserRepository userRepository;
	private static UserService instance;
	
	private UserService() {
		this.userRepository = UserRepository.getInstance();
	}
	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	
	
	public Optional<UserResponseDTO> save(UserRequestDTO userRequestDTO) {
		User user;
		Optional<User> userOptional;
		UserResponseDTO responseDTO=new UserResponseDTO();
		try {
			if (userRepository.isExistUsername(userRequestDTO.getUsername())&& userRepository.isExistEmail(userRequestDTO.getEmail())) {
				ConsoleTextUtils.printErrorMessage("Kullanıcı adı veya email zaten mevcut.");
			}
			user=new User();
			user.setName(userRequestDTO.getName());
			user.setSurname(userRequestDTO.getSurname());
			user.setEmail(userRequestDTO.getEmail());
			user.setUsername(userRequestDTO.getUsername());
			user.setPassword(userRequestDTO.getPassword());
			userOptional=userRepository.save(user);
			ConsoleTextUtils.printSuccessMessage(user.getUsername()+" kullanıcı adlı kullanıcı kaydedildi.");
			responseDTO.setEmail(userOptional.get().getEmail());
			responseDTO.setUsername(userOptional.get().getUsername());
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Kullanıcı kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.of(responseDTO);
	}
	
	
	public void delete(Long id) {
		try{
			userRepository.delete(id);
			ConsoleTextUtils.printSuccessMessage("Kullanıcı silindi.");
		}
		catch (Exception e){
			ConsoleTextUtils.printErrorMessage("Service : Kullanıcı silinirken hata oluştu : "+e.getMessage());
		}
	}
	
	
	public void update(UserRequestDTO userRequestDTO) {
		User user;
		try {
			if (userRepository.isUsernameAndMailExist(userRequestDTO.getUsername(), userRequestDTO.getEmail())) {
				Optional<User> optionalUser = userRepository.findByUsername(userRequestDTO.getUsername());
				if (optionalUser.isPresent()){
					user = optionalUser.get();
					user.setName(userRequestDTO.getName());
					user.setSurname(userRequestDTO.getSurname());
					user.setEmail(userRequestDTO.getEmail());
					user.setUsername(userRequestDTO.getUsername());
					user.setPassword(userRequestDTO.getPassword());
					userRepository.update(user);
					ConsoleTextUtils.printSuccessMessage(user.getUsername()+" kullanıcı adlı kullanıcı bilgileri güncellendi.");
				}
			}
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Kullanıcı bilgileri güncellenirken hata oluştu : "+e.getMessage());
		}
	
	}
	
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> findByUsernameAndPassword(String username,String password){
		
		Optional<User> optUser = userRepository.findByUsernameAndPassword(username, password);
		if (optUser.isEmpty()){
			ConsoleTextUtils.printErrorMessage("Kullanıcı adı veya sifre hatalı.");
		}
		else {
			ConsoleTextUtils.printSuccessMessage("Hoşgeldiniz, "+username);
		}
		return optUser;
	}
	
	public boolean isExistUsername(String username){
		if (userRepository.isExistUsername(username))
		{
			ConsoleTextUtils.printErrorMessage("Bu kullanıcı adı zaten kayıtlı.");
		}
		return userRepository.isExistUsername(username);
	}
	public boolean isExistEmail(String email){
		if (userRepository.isExistEmail(email))
		{
			ConsoleTextUtils.printErrorMessage("Bu mail adresi zaten kayıtlı.");
		}
		return userRepository.isExistEmail(email);
	}
	public List<Video>  getLikedVideosOfUser(User user){
		List<Video> likedVideosOfUser = userRepository.getLikedVideosOfUser(user);
		if (likedVideosOfUser.isEmpty()){
			ConsoleTextUtils.printErrorMessage("Kullanıcının beğendiği video yok.");
		}
		return likedVideosOfUser;
	}
	
	public Optional<User> findByUsername(String username){
		Optional<User> user = userRepository.findByUsername(username);
		return user;
	}
	
	public List<Comment> getAllCommentsOfUser(User user) {
		return userRepository.getAllCommentsOfUser(user);
	}
}