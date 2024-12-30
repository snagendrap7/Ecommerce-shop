package com.dailycodework.Ecommerce_shop.service.user;

import com.dailycodework.Ecommerce_shop.model.User;

public interface IUserService {

    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    USer updateUser(UserUpdateRequest request , Long userId);

}
