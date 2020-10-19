package com.honnalmanja.javamvvmpractice.utils;

public class CommonUtils {

    public static final String TASK_ID_KEY = "task_id_bundle_key";
    public static final String IS_NEW_TASK_KEY = "is_new_task_bundle_key";
    public static final String TASK_POSITION_KEY = "task_position";

    public static final String LOGIN_API = "login";
    public static final String SIGN_UP_API = "sign_up";
    public static final String LOGOUT_API = "logout";

    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static String composeToken(String token){
        return "Bearer "+token;
    }

}
