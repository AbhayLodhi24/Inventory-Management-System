package com.project.ims.Service;

import com.project.ims.Model.User;
import com.project.ims.Dto.UserDto;
import com.project.ims.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
 
    private Long userId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    @Autowired
    private UserRepository userRepository;
    
    public List<UserDto> getAllUsers(){
    	List<User> users = userRepository.findAll();
    	List<UserDto> userDtos = new ArrayList<>();
    	for(User user : users) {
    		userDtos.add(convertToDto(user));
    	}
    	return userDtos;
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public void addUser(UserDto userDto) {
        if (userRepository.findByusername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        userRepository.save(convertToEntity(userDto));
    }


    public List<UserDto> searchUsers(String query) {
    	List<User> users = userRepository.findByUsernameContainingIgnoreCase(query);
    	List<UserDto> userDtos = new ArrayList<>();
    	for(User user : users) {
    		userDtos.add(convertToDto(user));
    	}
    	return userDtos;
    	
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
        return dto;
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setRole(userDto.getRole());
        return user;
    }
    

}