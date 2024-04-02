
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1749748806666235905', '-1', '企业应用信息', 'menu', 'account', 'wechat/cp/account/index', '0', '', '1', '', 'list', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1749748806666235906', '1749748806666235905', '查询-企业应用信息', 'button', '', null, '0', '/api/wechat/cp/account/list/searchList', '1', 'wechat:cp:account:list', '#', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1749748806666235907', '1749748806666235905', '新增-企业应用信息', 'button', '', null, '0', '/api/wechat/cp/account/add', '1', 'wechat:cp:account:add', '#', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1749748806666235908', '1749748806666235905', '修改-企业应用信息', 'button', '', null, '0', '/api/wechat/cp/account/update', '1', 'wechat:cp:account:update', '#', 1, '', now(), '1');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1749748806666235909', '1749748806666235905', '删除-企业应用信息', 'button', '', null, '0', '/api/wechat/cp/account/delete/deleteById/*', '1', 'wechat:cp:account:delete', '#', 1, '', now(), '1');
