package com.wishland.www.xinwanhaotest.utils;



import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gerry on 2017/5/2. 0
 */

public class NetUtils {
    public static Map<String, String> getParamsPro() {
        Map<String, String> map = new HashMap<>();
        return getParamsPro(map);
    }

    public static Map<String, String> getParamsPro(Map<String, String> map) {
        String s1 = UserSP.getSPInstance().getToken(UserSP.LOGIN_TOKEN);
        if (!s1.isEmpty()) {
            map.put("token", s1);
        }
        map.put("time", Utils_time.getSeconds());
        map.put("version", BaseApi.VERSION);
        String sign = encryption(map);
        String s = MD5Utils.toMD5(sign);
        map.put("signature", s);
        return map;
    }

    private static String encryption(Map<String, String> map) {
        Set<String> strings = map.keySet();
        List<String> list = new ArrayList<>(strings);
        Collections.sort(list);
        StringBuilder stringBuffer = new StringBuilder();
        for (String s :
                list) {
            stringBuffer.append(s).append(map.get(s));
        }
        stringBuffer.append(BaseApi.KEYSTORE);
        return stringBuffer.toString();
    }
}
