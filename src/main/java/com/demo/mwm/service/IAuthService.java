package com.demo.mwm.service;

import com.demo.mwm.dto.UserDto;

public interface IAuthService {

    UserDto register(UserDto userDto);

    String login(UserDto userDto);

}
