package com.weiz.spb.common.constants;

import com.weiz.spb.services.dto.MessageCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConst {

    // Message Code
    public static final MessageCode INTERNAL_SERVER_ERROR = new MessageCode(500, "Internal server error");
    public static final MessageCode BAD_REQUEST = new MessageCode(400, "Bad request");
    public static final MessageCode NOT_FOUND = new MessageCode(404, "Not found");
    public static final MessageCode RESOURCE_CONFLICT = new MessageCode(409, "Resource conflict");
    public static final MessageCode FORBIDDEN = new MessageCode(403, "Forbidden");
    public static final MessageCode UNAUTHORIZED = new MessageCode(401, "Unauthorized");
    public static final MessageCode OK = new MessageCode(200, "Success");
    public static final MessageCode CREATED = new MessageCode(201, "Created");
    public static final MessageCode NO_CONTENT = new MessageCode(204, "No content");

    // Common message
    public static final String GET_SUCCESS = "Get data successfully";
    public static final String GET_FAIL = "Fail to get data";
    public static final String UPDATE_SUCCESS = "Update item successfully";
    public static final String UPDATE_FAIL = "Fail to update item";
    public static final String CREATE_SUCCESS = "Create item successfully";
    public static final String CREATE_FAIL = "Fail to create item";
    public static final String DELETE_SUCCESS = "Delete item successfully";
    public static final String DELETE_FAIL = "Fail to delete item";
    public static final String UNAUTHORIZED_USER = "Please log in to perform this method";


    // Message response
    public static final String AUTH_LOGIN_FAIL = "Invalid email or password";
    public static final String AUTH_LOGIN_SUCCESS = "Login successfully";
    public static final String AUTH_REGISTER_USER_EXISTS = "User has already existed in system";
    public static final String AUTH_TOKEN_INVALID = "Invalid token";
    public static final String AUTH_LOGOUT_SUCCESS = "Logout Successfully";
    public static final String AUTH_REFRESH_TOKEN_SUCCESS = "Refresh token successfully";
    public static final String AUTH_VERIFIED_EMAIL = "Email is already verified";
    public static final String AUTH_UNVERIFIED_EMAIL = "Email has not been verified";
    public static final String AUTH_SEND_VERIFY_CODE_SUCCESS = "Send code to verify successfully";
    public static final String AUTH_VERIFY_EMAIL_SUCCESS = "Verify email successfully";
    public static final String AUTH_RESET_PASSWORD_SUCCESS = "Reset password successfully";

    // Mail
    public static final String EMAIL_SEND = "luutrieuvi2003@gmail.com";
    public static final String VERIFY_EMAIL_SUBJECT = "Verify Email";
    public static final String FORGET_PASSWORD_SUBJECT = "Forgot Password";

}
