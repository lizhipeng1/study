create table wechat_cp_account
(
    cp_account_id    int auto_increment
        primary key,
    tenant_id        varchar(30)                           not null comment '所属租户',
    company_name     varchar(100)                          null comment '企业名称',
    corp_id          varchar(64)                           null comment '企业ID',
    agent_id         integer                               null comment '应用id',
    agent_secret     varchar(64)                           null comment '应用密钥',
    call_back_url    varchar(255)                          null comment '回调url',
    call_back_token  varchar(32)                           null comment '回调token',
    call_back_key    varchar(255)                          null comment '回调Key',
    in_use           char        default '1'               null comment '状态 1在用 0停用',
    create_user      varchar(30)                           null comment '创建者',
    create_time      datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    last_update_user varchar(30) default ''                null comment '更新者',
    last_update_time datetime    default CURRENT_TIMESTAMP null comment '更新时间'
) comment '企业应用信息';

insert into wechat_cp_account(tenant_id, company_name, corp_id, agent_id, agent_secret)
values ('-1', '企业名称', '企业id', 100000, '输入应用秘钥'),
       ('-1', '企业名称', '企业id', 1, '通讯录秘钥');

create table wechat_cp_contact
(
    external_user_id varchar(64) primary key               not null comment '外部联系人的userid',
    tenant_id        varchar(30) not null comment '所属租户',
    corp_id          varchar(64) comment '企业ID',
    name             varchar(100) comment '外部联系人的名称',
    avatar           varchar(200) comment '外部联系人头像',
    type             tinyint comment '外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户',
    gender           tinyint(1)  default 0 comment '外部联系人性别 0-未知 1-男性 2-女性',
    unionid          varchar(32) comment '外部联系人在微信开放平台的唯一身份标识（微信unionid）',
    position         varchar(100) comment '外部联系人的职位',
    corp_name        varchar(100) comment '外部联系人所在企业的简称',
    corp_full_name   varchar(200) comment '外部联系人所在企业的主体名称',
    create_user      varchar(30)                           null comment '创建者',
    create_time      datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    last_update_user varchar(30) default ''                null comment '更新者',
    last_update_time datetime    default CURRENT_TIMESTAMP null comment '更新时间'
) comment '企业微信客户信息';


create table wechat_cp_contact_follow
(
    external_user_id varchar(64) comment '外部联系人的userid',
    cp_user_id       varchar(64) comment '添加了此外部联系人的企业成员userid',
    remark           varchar(64) comment '此外部联系人的备注',
    description      varchar(64) comment '外部联系人的描述',
    create_time      bigint comment '外部联系人的描述',
    remark_corp_name varchar(64) comment '该成员对此微信客户备注的企业名称（仅微信客户有该字段）',
    remark_mobiles   varchar(64) comment '该成员对此客户备注的手机号码',
    add_way          varchar(64) comment '该成员添加此客户的来源',
    oper_userid      varchar(64) comment '发起添加的userid，如果成员主动添加，为成员的userid；如果是客户主动添加，则为客户的外部联系人userid；如果是内部成员共享/管理员分配，则为对应的成员/管理员userid',
    state            varchar(64) comment '企业自定义的state参数，用于区分客户具体是通过哪个「联系我」或获客链接添加',
    primary key (external_user_id, cp_user_id)
) comment '外部联系人对应大员工信息';

create table wechat_cp_contact_follow_tag
(
    follow_tag_id    bigint primary key comment '主键',
    external_user_id varchar(64) comment '外部联系人的userid',
    cp_user_id       varchar(64) comment '添加了此外部联系人的企业成员userid',
    group_name       varchar(64) comment '该成员添加此外部联系人所打标签的分组名称',
    tag_name         varchar(64) comment '该成员添加此外部联系人所打标签名称',
    type             tinyint comment '该成员添加此外部联系人所打标签类型, 1-企业设置，2-用户自定义，3-规则组标签',
    tag_id           varchar(64) comment '该成员添加此外部联系人所打企业标签的id，用户自定义类型标签（type=2）不返回'
) comment '员工给外部联系人添加的标签';

create table wechat_cp_group
(
    group_id         varchar(64) primary key               not null comment '标签组id',
    tenant_id        varchar(30) not null comment '所属租户',
    group_name       varchar(50)                           null comment '标签组名',
    group_tag_type   tinyint     default 1                 null comment '标签分组类型(1:客户企业标签;2:群标签;3:客户个人标签)',
    group_order      bigint                                null comment '标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)',
    delete_flag      char        default '0'               null comment '标签组是否已经被删除，只在指定tag_id进行查询时返回',
    create_user      varchar(30)                           null comment '创建者',
    create_time      bigint                                null comment '创建时间',
    last_update_user varchar(30) default ''                null comment '更新者',
    last_update_time datetime    default CURRENT_TIMESTAMP null comment '更新时间'
) comment '企业微信标签组';

create table wechat_cp_group_tag
(
    tag_id           varchar(64) primary key               not null comment '微信端返回的id',
    tenant_id        varchar(30) not null comment '所属租户',
    group_id         varchar(64)                           null comment '标签组id',
    name             varchar(50)                           null comment '标签名',
    tag_order        bigint                                null comment '标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)',
    delete_flag      char        default '0'               null comment '标签组是否已经被删除，只在指定tag_id进行查询时返回',
    create_user      varchar(30)                           null comment '创建者',
    create_time      bigint                                null comment '创建时间',
    last_update_user varchar(30) default ''                null comment '更新者',
    last_update_time datetime    default CURRENT_TIMESTAMP null comment '更新时间'
) comment '企业微信标签';

create table wechat_cp_contact_way
(
    config_id           varchar(64) primary key               not null comment '新增联系方式的配置id',
    tenant_id        varchar(30) not null comment '所属租户',
    qr_code             varchar(200) comment '联系我二维码链接，仅在scene为2时返回',
    contact_way_name    varchar(30)                           not null comment '名称',
    type                tinyint(1) comment '联系方式类型,1-单人, 2-多人',
    scene               tinyint(1) comment '场景，1-在小程序中联系，2-通过二维码联系',
    style               tinyint(1) comment '在小程序中联系时使用的控件样式',
    remark              varchar(30) comment '联系方式的备注信息，用于助记，不超过30个字符',
    skip_verify         tinyint(1) comment '外部客户添加时是否无需验证，默认为true',
    state               varchar(30) comment '企业自定义的state参数，用于区分不同的添加渠道',
    user                text comment '使用该联系方式的用户userID列表，在type为1时为必填，且只能有一个',
    party               text comment '使用该联系方式的部门id列表，只在type为2时有效',
    add_tag             text comment '需要自动添加的标签id',
    is_temp             tinyint(1) comment '是否临时会话模式，true表示使用临时会话模式，默认为false',
    expires_in          integer comment '临时会话二维码有效期，以秒为单位。该参数仅在is_temp为true时有效，默认7天，最多为14天',
    chat_expires_in     integer comment '临时会话有效期，以秒为单位。该参数仅在is_temp为true时有效，默认为添加好友后24小时，最多为14天',
    unionid             varchar(32) comment '可进行临时会话的客户unionid，该参数仅在is_temp为true时有效，如不指定则不进行限制',
    unionidis_exclusive tinyint(1) comment '是否开启同一外部企业客户只能添加同一个员工，默认为否，开启后，同一个企业的客户会优先添加到同一个跟进人',
    conclusions         json comment '结束语，会话结束时自动发送给客户，可参考“结束语定义”，仅在is_temp为true时有效',
    create_user         varchar(30)                           null comment '创建者',
    create_time         datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    last_update_user    varchar(30) default ''                null comment '更新者',
    last_update_time    datetime    default CURRENT_TIMESTAMP null comment '更新时间'
) comment '客户联系我方式';

create table wechat_cp_user
(
    userid           varchar(64) primary key comment '成员UserID',
    tenant_id        varchar(30) not null comment '所属租户',
    name             varchar(30) comment '成员名称',
    mobile           varchar(30) comment '手机号码',
    department       varchar(100) comment '成员所属部门id列表',
    user_order       varchar(100) comment '部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)',
    position         varchar(30) comment '职务信息',
    gender           tinyint(1)  default 0 comment '性别 0表示未定义，1表示男性，2表示女性',
    email            varchar(30) comment '邮箱',
    biz_mail         varchar(30) comment '企业邮箱',
    avatar           varchar(30) comment '头像url',
    status           varchar(30) comment '激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业',
    main_department  varchar(30) comment '主部门，仅当应用对主部门有查看权限时返回',
    create_user      varchar(30)                           null comment '创建者',
    create_time      datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    last_update_user varchar(30) default ''                null comment '更新者',
    last_update_time datetime    default CURRENT_TIMESTAMP null comment '更新时间'
) comment '企业微信成员';

# 渠道活码菜单
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show,
                      permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1500202208042100436', '-1', '微信生态接入', 'dir', 'wechat', null, '0', '', '1', null, 'system', 1, '', now(),
        '1');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show,
                      permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1500202208042100437', '1500202208042100436', '企业微信', 'dir', 'cp', 'ParentView', '0', '', '1', null, 'job',
        1, '', now(), '1'),
       ('1500202208042100438', '1500202208042100437', '渠道活码', 'menu', 'contactWay', 'wechat/cp/contactWay/index',
        '0', '', '1', '', 'peoples', 1, '', now(), '1');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, router_path, component, out_url, request_url, is_show,
                      permission_meta, icon, sort, remark, create_time, enable)
VALUES ('1750839251752243202', '1500202208042100438', '查询-渠道活码', 'button', '', null, '0',
        '/api/wechat/cp/contactWay/list/searchList', '1', 'wechat:cp:contactWay:list', '#', 1, '', now(), '1'),
       ('1750839251752243203', '1500202208042100438', '新增-渠道活码', 'button', '', null, '0',
        '/api/wechat/cp/contactWay/add', '1', 'wechat:cp:contactWay:add', '#', 2, '', now(), '1'),
       ('1750839251752243204', '1500202208042100438', '修改-渠道活码', 'button', '', null, '0',
        '/api/wechat/cp/contactWay/update', '1', 'wechat:cp:contactWay:update', '#', 3, '', now(), '1'),
       ('1750839251752243205', '1500202208042100438', '删除-渠道活码', 'button', '', null, '0',
        '/api/wechat/cp/contactWay/delete/deleteById/*', '1', 'wechat:cp:contactWay:delete', '#', 4, '', now(), '1');



INSERT INTO sys_dict_type (dict_type_id, dict_name, dict_type, in_use, can_change, create_user, create_time,
                           last_update_user, last_update_time, remark)
VALUES ('1750839251752243205', '企业微信联系我-联系方式类型', 'cp_contact_way_type', '1', '0', null, now(), '', now(),
        null);
INSERT INTO sys_dict_data (dict_data_id, dict_label, dict_value, dict_type, dict_sort)
VALUES ('1750839251752243206', '单人', '1', 'cp_contact_way_type', 1),
       ('1750839251752243207', '多人', '2', 'cp_contact_way_type', 2);

INSERT INTO sys_dict_type (dict_type_id, dict_name, dict_type, in_use, can_change, create_user, create_time,
                           last_update_user, last_update_time, remark)
VALUES ('1750839251752243305', '企业微信联系我-场景', 'cp_contact_way_scene', '1', '0', null, now(), '', now(), null);
INSERT INTO sys_dict_data (dict_data_id, dict_label, dict_value, dict_type, dict_sort)
VALUES ('1750839251752243306', '在小程序中联系', '1', 'cp_contact_way_scene', 1),
       ('1750839251752243307', '通过二维码联系', '2', 'cp_contact_way_scene', 2);

INSERT INTO sys_dict_type (dict_type_id, dict_name, dict_type, in_use, can_change, create_user, create_time,
                           last_update_user, last_update_time, remark)
VALUES ('1750839251752243405', '企业微信联系我-小程序中联系样式', 'cp_contact_way_style', '1', '0', null, now(), '',
        now(), null);
INSERT INTO sys_dict_data (dict_data_id, parent_value, dict_label, dict_value, dict_type, dict_sort)
VALUES ('1750839251752243406', '1', '单人样式1', '1', 'cp_contact_way_style', 1),
       ('1750839251752243407', '1', '单人样式2', '2', 'cp_contact_way_style', 2),
       ('1750839251752243408', '1', '单人样式3', '3', 'cp_contact_way_style', 3),
       ('1750839251752243416', '2', '单人样式1', '1', 'cp_contact_way_style', 1),
       ('1750839251752243417', '2', '单人样式2', '2', 'cp_contact_way_style', 2);


alter table wechat_cp_contact add delete_flag char default '0' null comment '删除标志（1删除 0在用）' after corp_full_name;