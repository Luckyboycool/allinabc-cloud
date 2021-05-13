package com.allinabc.cloud.admin.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: QQF
 * @create: 2020-05-20 12:54
 **/
public class BeanDtoVoUtils<V, E> {
    /**
     * TODO  dot ,Do ,entity 相互转换
     * 同：BeanUtils.copyProperties(dtoEntity, newInstance);
     *
     * @param oldClass 原数据--Dto，Vo，entity
     * @param newClass 转换为--Dto，Vo，entity
     * @return
     */
    public static <E> E convert(Object oldClass, Class<E> newClass) {
        // 判断oldClass 是否为空!
        if (oldClass == null) {
            return null;
        }
        // 判断newClass 是否为空
        if (newClass == null) {
            return null;
        }
        try {
            // 创建新的对象实例
            E newInstance = newClass.newInstance();
            // 把原对象数据拷贝到新的对象
            BeanUtils.copyProperties(oldClass, newInstance);
            // 返回新对象
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }


    //TODO  Page<Entity> 分页对象转 Page<Vo>  ( list 循环)
//    public static <T, V> Page<V> pageVo(Page<T> page, Class<V> v) {
//        try {
//            List<T> tList = page.getRecords();
//            List<V> voList = new ArrayList<>();
//            for (T t : tList) {
//                V newv = (V) BeanDtoVoUtils.convert(t, v.newInstance().getClass());
//                voList.add(newv);
//            }
//            //Page<V> vs = new Page<voList>(page.getPageable(), page.getTotalElements());
//            Page<V> vs = new Page<V>();
//            vs.setRecords(voList);
//            vs.setTotal(page.getTotal());
//            vs.setPages(page.getPages());
//            return vs;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    //TODO  Page<Entity> 分页对象转 Page<Vo> （Stream 方式）
//    public static  <T, V> Page<V> pageVoStream(Page<T> page, Class<V> v) {
//        List<V> voList = page.getRecords().stream().map(item -> {
//            try {
//                return (V) BeanDtoVoUtils.convert(item, v.newInstance().getClass());
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }).collect(Collectors.toList());
//        Page<V> vs = new PageImpl<>(voList, page.getPageable(), page.getTotalElements());
//        return vs;
//    }


    //TODO  list<Entity> 集合对象转list<Vo> ( list 循环)
    public static <T, V> List<V> listVo(List<T> oldList, Class<V> v) {
        try {
            List<V> voList = new ArrayList<>();
            for (T t : oldList) {
                V newv = (V) BeanDtoVoUtils.convert(t, v.newInstance().getClass());
                voList.add(newv);
            }
            return voList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //TODO  list<Entity> 集合对象转list<Vo> （Stream 方式）
    public static <T, V> List<V> listVoStream(List<T> oldList, Class<V> v) {
        List<V> voList = oldList.stream().map(item -> {
            try {
                return (V) BeanDtoVoUtils.convert(item, v.newInstance().getClass());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return voList;
    }





}
