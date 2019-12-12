package com.elegant.essay.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @description: 比较两个List对象是否相同
 * @author: xiaoxu.nie
 * @date: 2018-12-18 13:42
 */
@Slf4j
public class CompareCollectionUtil {

    private CompareCollectionUtil() {
    }

    /**
     * 比较两个List是否相同
     *
     * @param listOne
     * @param listTwo
     * @return 相同返回true
     */
    public static boolean compareListsMd5Hex(Collection listOne, Collection listTwo) {
        // 如果两个都为空则相等
        if (CollectionUtils.isEmpty(listOne) && CollectionUtils.isEmpty(listTwo)) {
            return true;
        }
        // 任意一个为空，另一个不为空则不相等
        if (CollectionUtils.isEmpty(listOne) || CollectionUtils.isEmpty(listTwo)) {
            return false;
        }
        // 两个都不为空
        int oneSize = listOne.size();
        int twoSize = listTwo.size();
        // 尺寸不相等则不相等
        if (oneSize != twoSize) {
            return false;
        }
        StringBuilder builderOne = new StringBuilder();
        listOne.stream().forEach(obj -> builderOne.append(JSON.toJSONString(obj)));
        StringBuilder builderTwo = new StringBuilder();
        listTwo.stream().forEach(obj -> builderTwo.append(JSON.toJSONString(obj)));

        String md5HexOne = DigestUtils.md5Hex(builderOne.toString());
        String md5HexTwo = DigestUtils.md5Hex(builderTwo.toString());
        return md5HexOne.equals(md5HexTwo);
    }

    /**
     * 比较两个list并返回不同元素
     *
     * @param colOne
     * @param colTwo
     * @return
     */
    public static Collection compareListsGetDif(Collection colOne, Collection colTwo) {

        if (CollectionUtils.isEmpty(colOne) && CollectionUtils.isEmpty(colTwo)) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isNotEmpty(colOne) && CollectionUtils.isEmpty(colTwo)) {
            return colOne;
        }
        if (CollectionUtils.isEmpty(colOne) && CollectionUtils.isNotEmpty(colTwo)) {
            return colOne;
        }
        Collection difElements = Lists.newArrayList();
        Collection maxList = null;
        Collection minList = null;
        if (colOne.size() > colTwo.size()) {
            maxList = colOne;
            minList = colTwo;
        } else {
            maxList = colTwo;
            minList = colOne;
        }
        // 将元素最大的数量放入map,减少放入小元素list时的判空次数循环
        Map<Object, Integer> maxMap = new HashMap<>();
        maxList.stream().forEach(i -> maxMap.put(i, 1));
        // 将元素小的list放入map中
        minList.stream().forEach(i -> {
            Integer num = maxMap.get(i);
            if (num == null) {
                maxMap.put(i, 1);
            } else {
                ++num;
                maxMap.put(i, num);
            }
        });
        // 遍历map中value中值大于1的，表示重复，等于1的表示不重复的
        maxMap.forEach((k, v) -> {
            if (v == 1) {
                difElements.add(k);
            }
        });
        return difElements;
    }

    /**
     * 比较两个list对象，如果相同返回true，不同返回false
     *
     * @param collection1
     * @param collection2
     * @return
     */
    public static boolean compareLists(Collection collection1, Collection collection2) {
        Collection collection = compareListsGetDif(collection1, collection2);
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 比较两个List集合，返回相同元素索引和不同元素的索引
     *
     * @param list1
     * @param list2
     * @return
     */
    public static Map<Integer, List<List<Integer>>> compareListGetIndexAndValue(List<Integer> list1, List<Integer> list2) {
        // 两个list不能为空
        Assert.notEmpty(list1, "Param1 element is null Error");
        Assert.notEmpty(list2, "Param2 element is null Error");

        Map<Integer, List<List<Integer>>> maxMap = new HashMap<>(list1.size());
        for (int i = 0; i < list1.size(); i++) {
            Integer curEle = list1.get(i);
            List<List<Integer>> listList = maxMap.get(curEle);
            if (CollectionUtils.isEmpty(listList)) {
                List<List<Integer>> lists = Lists.newArrayList();
                lists.add(Lists.newArrayList(i));
                maxMap.put(curEle, lists);
            } else {
                List<Integer> list = listList.get(0);
                list.add(i);
                listList.set(0, list);
                maxMap.put(curEle, listList);
            }
        }

        for (int i = 0; i < list2.size(); i++) {
            Integer curEle = list2.get(i);
            List<List<Integer>> listList = maxMap.get(curEle);
            if (CollectionUtils.isEmpty(listList)) {
                List<List<Integer>> lists = Lists.newArrayList();
                lists.add(Lists.newArrayList(i));
                maxMap.put(curEle, lists);
            } else {
                // 长度为1时，表明第二个集合中的重复元素并没有放置到档期LIST中
                if (listList.size() == 1) {
                    List<Integer> list = Lists.newArrayList(i);
                    listList.add(list);
                } else {
                    List<Integer> list = listList.get(1);
                    // 放置索引
                    list.add(i);
                    listList.set(1, list);
                    maxMap.put(curEle, listList);
                }
            }
        }
        return maxMap;
    }

    /**
     * 比较两个list返回不同元素和对应的索引
     *
     * @param list1
     * @param list2
     * @return
     */
    public static Map<Integer, List<List<Integer>>> compareListGetDifIndexAndValue(List<Integer> list1, List<Integer> list2) {
        Map<Integer, List<List<Integer>>> difEleMap = Maps.newHashMap();
        Map<Integer, List<List<Integer>>> listMap = CompareCollectionUtil.compareListGetIndexAndValue(list1, list2);
        listMap.forEach((k, v) -> {
            // value的list尺寸为1时表明是不同元素
            if (v.size() == 1) {
                difEleMap.put(k, v);
            }
        });
        return difEleMap;
    }

    /**
     * 比较两个list返回相同元素和对应的索引
     *
     * @param list1
     * @param list2
     * @return
     */
    public static Map<Integer, List<List<Integer>>> compareListGetCommonIndexAndValue(List<Integer> list1, List<Integer> list2) {
        Map<Integer, List<List<Integer>>> commonEleMap = Maps.newHashMap();
        Map<Integer, List<List<Integer>>> listMap = CompareCollectionUtil.compareListGetIndexAndValue(list1, list2);
        listMap.forEach((k, v) -> {
            // value的list尺寸为2时表明是不同元素
            if (v.size() > 1) {
                commonEleMap.put(k, v);
            }
        });
        return commonEleMap;
    }

}
