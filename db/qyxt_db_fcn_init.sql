set global log_bin_trust_function_creators=1;
create
    function fcn_dict_name(dictValue varchar(100), dictType varchar(100)) returns varchar(100)
    comment '字典code转换name'
BEGIN
    DECLARE dictName VARCHAR(100) DEFAULT '';

    SELECT dict_label
    INTO dictName
    FROM
        sys_dict_data
    WHERE
            dict_type = dictType and dict_value=dictValue LIMIT 0,1;
    RETURN dictName;
END;

create
    function fcn_dict_name_list(dictValue varchar(100), dictType varchar(100)) returns varchar(100)
    comment '字典codes转换names'
BEGIN
    DECLARE dictName VARCHAR(100) DEFAULT '';

    select group_concat(dict_label) INTO dictName from sys_dict_data b
    where b.dict_type=dictType and  FIND_IN_SET(b.dict_value,dictValue)>0;
    RETURN dictName;
END;
set global log_bin_trust_function_creators=0;

