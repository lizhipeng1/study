package ${package.SERVICEIMPL};

import ${package.ENTITY}.${fileNameInfo.ENTITY};
import ${package.MAPPER}.${fileNameInfo.MAPPER};
import ${package.ISERVICE}.${fileNameInfo.ISERVICE};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.tableComment!} 服务实现类
 * </p>
 *
 * @author ${table.codeAuthor}
 * @since ${date}
 */
@Service
public class ${fileNameInfo.SERVICEIMPL} extends ${superServiceImplClass}<${fileNameInfo.MAPPER}, ${fileNameInfo.ENTITY}> implements ${fileNameInfo.ISERVICE} {

    /**
     * 是否存在子节点
     * @param ${pkColumn}
     * @return
     */
    public boolean hadChild(Long ${pkColumn}){
        QueryWrapper qw = new QueryWrapper();
        qw.eq("parent_id",${pkColumn});
        List list = list(qw);
        if(list.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void update${table.className}Children(Long ${pkColumn}, String newAncestors, String oldAncestors) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("${pkColumnName}",${pkColumn});
        List<${fileNameInfo.ENTITY}> childrenList = list(qw);
        if(!childrenList.isEmpty()){
            for (${fileNameInfo.ENTITY} children : childrenList) {
                children.setAncestors(children.getAncestors().replace(oldAncestors, newAncestors));
            }
            updateBatchById(childrenList);
        }
     }
}
