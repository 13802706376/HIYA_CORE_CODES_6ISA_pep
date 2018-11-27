package yunnex.pep.common.util.tree;

import java.util.List;

public interface Tree<T extends Tree<T>> {

    String getId();

    String getParentId();

    List<T> getChildren();

    void setChildren(List<T> list);

}
