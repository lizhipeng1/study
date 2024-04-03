package com.study.core.select;

import lombok.Data;

import java.util.List;

/**
 * @projectName: qyxt
 * @package: com.study.core.domain.select
 * @className: GroupSelect
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/30 20:19
 */
@Data
public class GroupSelect {

    private String groupName;

    private List<GroupItem> itemList;

    @Data
    public static class GroupItem {
        private String itemId;
        private String itemName;
    }

}
