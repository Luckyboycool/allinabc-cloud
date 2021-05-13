package com.allinabc.cloud.common.core.utils;

import java.util.Collections;
import java.util.List;

public class ListHelper {

    public static List<String> singleton(List<String> stringList){
        if(null == stringList || stringList.size() == 0)
            return stringList;
        stringList.removeAll(Collections.singleton(null));
        return stringList;
    }

}
