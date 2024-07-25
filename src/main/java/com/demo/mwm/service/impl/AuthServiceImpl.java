package com.demo.mwm.service.impl;

import com.demo.mwm.dto.UserDto;
import com.demo.mwm.entity.RoleEntity;
import com.demo.mwm.entity.UserEntity;
import com.demo.mwm.exception.CustomException;
import com.demo.mwm.repository.IRoleRepository;
import com.demo.mwm.repository.IUserRepository;
import com.demo.mwm.service.IAuthService;
import com.demo.mwm.service.mapper.IUserEntityMapper;
import com.demo.mwm.utils.AuthoritiesConstants;
import com.demo.mwm.utils.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements IAuthService {

    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final IUserEntityMapper userEntityMapper;

    public AuthServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, IUserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userEntityMapper = userEntityMapper;
    }

    /**
     * Registers a new user.
     * If userDto.roleIds is null, the default role is USER.
     *
     * @param userDto the object containing the new user's information.
     * @return UserDto if registration is successful.
     * @throws IllegalArgumentException if the userDto object is null.
     * @throws CustomException          if the username already exists in the system.
     */

    @Override
    public UserDto register(UserDto userDto) {
        if (Objects.isNull(userDto)) {
            throw new IllegalArgumentException("Param is null");
        }
        Optional<UserEntity> userEntity = userRepository.findByUsername(userDto.getUsername());
        if (userEntity.isPresent()) {
            throw new CustomException("Username is exited");
        }
        // If userDto.roleIds is null, the default role is USER.
        Set<RoleEntity> roleEntitySet;
        if (Objects.nonNull(userDto.getRoleIds())) {
            roleEntitySet = roleRepository.findAllByIds(userDto.getRoleIds());
        }else {
            roleEntitySet = Set.of(roleRepository.findByName(AuthoritiesConstants.USER));
        }
        logger.info(roleEntitySet);
        UserEntity newUser = new UserEntity();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRoles(roleEntitySet);
        userRepository.save(newUser);
        return userEntityMapper.toDto(newUser);
    }

    /**
     * Authenticates the user's login information and generates a JWT token if successful.
     *
     * @param userDto the object containing the user's login credentials.
     * @return the JWT token string if login is successful.
     * @throws IllegalArgumentException if the userDto object is null.
     * @throws CustomException          if the user is not found or the login information is incorrect.
     */

    @Override
    public String login(UserDto userDto) {
        if (Objects.isNull(userDto)) {
            throw new IllegalArgumentException("Param is null");
        }
        UserEntity userEntity = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new CustomException("User not found"));
        if (passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            return jwtUtils.generateToken(userEntity);
        }
        throw new CustomException("Login information is incorrect");

    }
}
