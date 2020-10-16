package com.website.website.dao;

import com.website.website.model.User;
import com.website.website.vo.UserDetailsModifyRequest;

import java.util.List;

public interface IUserDao {
    User findById(String userId);
    Object addCategoryAccessToUser(String userId, String categoryId);
    User getUserById(String userId);
    Object deleteCategoryAccess(String categoryId);

}
