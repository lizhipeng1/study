package com.study.core.page;

import com.study.core.serializer.json.LongJsonSerializer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Collections;
import java.util.List;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-15 21:52
 */
public class RespPage<T> extends Page<T> {

    private List<T> rows = Collections.emptyList();

    @JsonSerialize(using = LongJsonSerializer.class)
    private long total;


    public RespPage(long current, long size) {
        super(current, size);
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        this.rows=records;
        return this;
    }

    @Override
    public Page<T> setTotal(long total) {
        this.total=total;
        return this;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public long getTotal() {
        return total;
    }
}
