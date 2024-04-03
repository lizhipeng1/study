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

}
