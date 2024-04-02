package ${package.CONTROLLER};

import ${package.REQ_ENTITY}.${fileNameInfo.REQ_ENTITY};
import ${package.ENTITY}.${fileNameInfo.ENTITY};
import ${package.BUSINESS_SERVICE}.${fileNameInfo.BUSINESS_SERVICE};
import cn.xluobo.core.api.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * ${table.tableComment!} Controller
 * </p>
 *
 * @author ${table.codeAuthor}
 * @since ${date}
 */
@RestController
@RequestMapping("/api/${table.moduleName}/${table.childModuleName}/${table.businessName}")
public class ${fileNameInfo.CONTROLLER} {
    @Autowired
    private ${fileNameInfo.BUSINESS_SERVICE} ${firstLowerEntityName}Service;

    /**
     * 列表
     *
     * @param ${fileNameInfo.FIRST_LOWER_REQ_ENTITY}
     * @return
     */
    @GetMapping("/list/searchList")
    public APIResponse searchList(${fileNameInfo.REQ_ENTITY} ${fileNameInfo.FIRST_LOWER_REQ_ENTITY}) {
        return ${firstLowerEntityName}Service.searchList(${fileNameInfo.FIRST_LOWER_REQ_ENTITY});
    }

    /**
     * 详情
     *
     * @param ${pkColumn}
     * @return
     */
    @GetMapping("/info/detailById/{${pkColumn}}")
    public APIResponse detailById(@PathVariable("${pkColumn}") String ${pkColumn}) {
        return ${firstLowerEntityName}Service.detailById(${pkColumn});
    }

    /**
     * 添加
     *
     * @param ${firstLowerEntityName}
     * @return
     */
    @PostMapping("/add")
    public APIResponse add${table.className}(@RequestBody ${table.className} ${firstLowerEntityName}) {
        return ${firstLowerEntityName}Service.add${table.className}(${firstLowerEntityName});
    }

    /**
     * 修改
     *
     * @param ${firstLowerEntityName}
     * @return
     */
    @PutMapping("/update")
    public APIResponse update${table.className}(@RequestBody ${table.className} ${firstLowerEntityName}) {
        return ${firstLowerEntityName}Service.update${table.className}(${firstLowerEntityName});
    }

    /**
     * 删除
     *
     * @param ${pkColumn}s
     * @return
     */
    @DeleteMapping("/delete/deleteById/{${pkColumn}s}")
    public APIResponse deleteById(@PathVariable("${pkColumn}s") String[] ${pkColumn}s) {
        return ${firstLowerEntityName}Service.deleteById(${pkColumn}s);
    }
}
