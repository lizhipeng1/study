[#assign businessNameFirstUpper=table.businessName?cap_first]

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('${menuId1!}', '-1', '${table.tableComment!}', 'menu', '${table.businessName}', '${table.moduleName}/${table.childModuleName}/${table.businessName}/index', '0', '', '1', '', 'list', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('${menuId2!}', '${menuId1!}', '查询-${table.tableComment!}', 'button', '', null, '0', '/api/${table.moduleName}/${table.childModuleName}/${table.businessName}/list/searchList', '1', '${table.moduleName!}:${table.childModuleName!}:${table.businessName!}:list', '#', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('${menuId3!}', '${menuId1!}', '新增-${table.tableComment!}', 'button', '', null, '0', '/api/${table.moduleName}/${table.childModuleName}/${table.businessName}/add', '1', '${table.moduleName!}:${table.childModuleName!}:${table.businessName!}:add', '#', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('${menuId4!}', '${menuId1!}', '修改-${table.tableComment!}', 'button', '', null, '0', '/api/${table.moduleName}/${table.childModuleName}/${table.businessName}/update', '1', '${table.moduleName!}:${table.childModuleName!}:${table.businessName!}:update', '#', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('${menuId5!}', '${menuId1!}', '删除-${table.tableComment!}', 'button', '', null, '0', '/api/${table.moduleName}/${table.childModuleName}/${table.businessName}/delete/deleteById/*', '1', '${table.moduleName!}:${table.childModuleName!}:${table.businessName!}:delete', '#', 1, '', now(), '1');
