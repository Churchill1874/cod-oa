package com.ent.codoa.common.tools;

import com.ent.codoa.common.exception.DataException;

//校验工具类
public class CheckReqTools {

    public static void account(String account) {
        if (!account.matches("^[a-zA-Z0-9]+$")){
            throw new DataException("账号只能输入英文和数字");
        }
    }

    public static void name(String name) {
        if (!name.matches("^(?!.*[._-]{2})(?!.*([._-]).*\\1)[\\u4e00-\\u9fa5a-zA-Z0-9]+[._-]?[\\u4e00-\\u9fa5a-zA-Z0-9]*$")) {
            throw new DataException("昵称仅支持一位.或_或-的特殊符号");
        }
    }

    public static void password(String password) {
        if (!password.matches("^[a-zA-Z0-9]+$")){
            throw new DataException("密码只能输入英文和数字");
        }
    }

}
