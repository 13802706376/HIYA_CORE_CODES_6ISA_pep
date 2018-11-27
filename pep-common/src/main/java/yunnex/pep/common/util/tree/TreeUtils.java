package yunnex.pep.common.util.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import yunnex.pep.common.constant.Constant;

/**
 * 树型结构数据工具类
 */
public abstract class TreeUtils {

    /**
     * 构建树形结构数据
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends Tree<T>> List<T> tree(List<T> list) {
        return tree(list, Constant.Num.ZERO.toString());
    }

    /**
     * 构建树形结构数据
     *
     * @param list
     * @param topParentId 最顶层的父ID
     * @param <T>
     * @return
     */
    public static <T extends Tree<T>> List<T> tree(List<T> list, String topParentId) {
        List<T> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }

        Map<String, List<T>> treeMap = new HashMap<>();
        list.forEach(tree -> {
            String parentId = tree.getParentId();
            List<T> trees = treeMap.get(parentId);
            if (CollectionUtils.isEmpty(trees)) {
                trees = new ArrayList<>();
                treeMap.put(parentId, trees);
            }
            trees.add(tree);

            if (parentId.equals(topParentId)) {
                result.add(tree);
            }
        });

        tree(result, treeMap);

        return result;
    }

    /**
     * 递归构建树型结构
     *
     * @param result
     * @param treeMap
     * @param <T>
     */
    private static <T extends Tree<T>> void tree(List<T> result, Map<String, List<T>> treeMap) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        result.forEach(tree -> {
            tree.setChildren(treeMap.get(tree.getId()));
            if (CollectionUtils.isNotEmpty(tree.getChildren())) {
                tree(tree.getChildren(), treeMap);
            }
        });
    }

}
