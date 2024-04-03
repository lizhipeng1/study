package ${package.ISERVICE};

import ${package.ENTITY}.${fileNameInfo.ENTITY};
import ${superServiceClassPackage};

/**
 * <p>
 * ${table.tableComment!} 服务类
 * </p>
 *
 * @author ${table.codeAuthor}
 * @since ${date}
 */
public interface ${fileNameInfo.ISERVICE} extends ${superServiceClass}<${fileNameInfo.ENTITY}> {
    /**
     * 是否存在子节点
     * @param ${pkColumn}
     * @return
     */
    boolean hadChild(Long ${pkColumn});

    /**
     * 修改子元素关系
     * @param ${pkColumn} 被修改的ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    void update${table.className}Children(Long ${pkColumn}, String newAncestors, String oldAncestors);
}
