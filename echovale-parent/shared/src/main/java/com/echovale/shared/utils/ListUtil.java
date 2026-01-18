package com.echovale.shared.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/23 09:10
 */
public class ListUtil {




    public static int getInitialCapacity(int expectedSize) {
        return (int) (expectedSize / 0.75F + 1.0F);
    }


    public static <E> List<E> concat(List<E> list1, List<E> list2) {
        List<E> res = new ArrayList<>(list1.size() + list2.size());
        res.addAll(list1);
        res.addAll(list2);
        return res;
    }


}
