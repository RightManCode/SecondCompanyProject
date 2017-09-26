package com.wishland.www.xinwanhaotest.utils;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ToolsMthode {


    /**
     * @param username 判断用户名
     * @return
     */
    public  static boolean judgeUsername(String username) {
        if (!username.isEmpty()
//                && firstCharacter(username)
                && username.length() > 3) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @param passwork 判断密码
     * @return
     */
    public static boolean judgePasswork(String passwork, int number ) {
        if (!passwork.isEmpty() && passwork.length() > number) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param passwork
     * @return
     */
    public static boolean judgeQphone(String passwork ,int number) {
        if (!passwork.isEmpty() && passwork.length() == number) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @return 判断username第一个字符是否为字母，并返回结果；
     */
    public static boolean firstCharacter(String username) {
        char x = username.charAt(0);
        if ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z')) {
            return true;
        } else {
            return false;
        }
    }
}
