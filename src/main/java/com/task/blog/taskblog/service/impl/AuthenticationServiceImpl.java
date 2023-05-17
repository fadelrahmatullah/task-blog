package com.task.blog.taskblog.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.task.blog.taskblog.common.exception.ValidationException;
import com.task.blog.taskblog.entity.User;
import com.task.blog.taskblog.repository.UserRepository;
import com.task.blog.taskblog.request.UserRequest;
import com.task.blog.taskblog.response.LoginResponse;
import com.task.blog.taskblog.response.UserResponse;
import com.task.blog.taskblog.service.AuthenticationService;
import com.task.blog.taskblog.util.UtilJwt;
import com.task.blog.taskblog.vo.TokenVo;

import jakarta.transaction.Transactional;

@Service
@Repository
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{
    
    @Autowired
	private UtilJwt jwtUtil;
    
	@Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
	PasswordEncoder passwordEncoder;


    @Override
    public UserResponse registerUser(UserRequest req) {
        User user = userRepository.findById(req.getUsername()).orElse(null);
        if (user != null) {
            throw new ValidationException(String.format("Username : [%s] Is Already Exist", req.getUsername()));
        }
        User entity = new User();
        BeanUtils.copyProperties(req, entity);
        String passEncode = Base64.getEncoder().encodeToString(req.getPassword().getBytes());
		byte[] passDecoded = Base64.getDecoder().decode(passEncode);
		String passDecodedStr = new String(passDecoded, StandardCharsets.UTF_8);
		String finalPass = passwordEncoder.encode(passDecodedStr);
        entity.setPassword(finalPass);
        entity.setCreateBy(req.getUsername());
        entity.setCreateDt(new Date(System.currentTimeMillis()));
        userRepository.save(entity);

        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public LoginResponse login(String username, String password) {

        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            throw new ValidationException( String.format("Username : [%s] Is Not Found", username));
        }

        try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException ex) {
			throw new ValidationException("Forbidden");
		}

        TokenVo accessTokenInfo = jwtUtil.generateToken(username);

		LoginResponse response = new LoginResponse();
		response.setTokenType(jwtUtil.getTokenType());
		response.setToken(accessTokenInfo.getToken());
		response.setLoginDate(accessTokenInfo.getIssuedAt());
        return response;
    }
    
}
