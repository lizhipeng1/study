
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1750839251752243201', '-1', '客户联系我方式', 'menu', 'contactWay', 'wechat/cp/contactWay/index', '0', '', '1', '', 'list', 1, '', now(), '1');





INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1500202208042100438', '1500202208042100437', '查询-微信公众平台配置', 'button', '', null, '0', '/api/wechat/account/list/searchList', '1', 'wechat:account:list', '#', 1, '', now(), '1');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1500202208042100439', '1500202208042100437', '新增-微信公众平台配置', 'button', '', null, '0', '/api/wechat/account/add/addWechatAccount', '1', 'wechat:account:add', '#', 1, '', now(), '1');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1500202208042100440', '1500202208042100437', '修改-微信公众平台配置', 'button', '', null, '0', '/api/wechat/account/update/updateWechatAccount', '1', 'wechat:account:update', '#', 1, '', now(), '1');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show, permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1500202208042100441', '1500202208042100437', '删除-微信公众平台配置', 'button', '', null, '0', '/api/wechat/account/delete/deleteById/*', '1', 'wechat:account:delete', '#', 1, '', now(), '1');
