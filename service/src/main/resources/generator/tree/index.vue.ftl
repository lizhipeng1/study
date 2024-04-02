[#assign businessNameFristUpper=table.businessName?cap_first]
<template>
    <div class="app-container">
        <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
    [#list table.columns as field]
    [#if field.isPk=="1"][#assign pkColumns=field][/#if]
    [#if field.isQuery=="1"]
        [#if field.htmlType=="input"]
            <el-form-item label="${field.columnComment!}" prop="${field.javaField!}">
                <el-input
                        v-model="queryParams.${field.javaField!}"
                        placeholder="请输入${field.columnComment!'-'}"
                        clearable
                        size="small"
                        @keyup.enter.native="handleQuery"
                />
            </el-form-item>
        [#elseif field.htmlType=="select" || field.htmlType=="radio"]
            <el-form-item label="${field.columnComment!'-'}" prop="${field.javaField!}">
                <el-select v-model="queryParams.${field.javaField!}" placeholder="请选择${field.columnComment!'-'}" clearable size="small">
                    <el-option
                            v-for="dict in ${field.javaField!}Options"
                            :key="dict.dictValue"
                            :label="dict.dictLabel"
                            :value="dict.dictValue"
                    />
                </el-select>
            </el-form-item>
        [#elseif field.htmlType=="datetime"]
            <el-form-item label="${field.columnComment!'-'}" prop="${field.javaField!}">
                <el-date-picker clearable size="small" style="width: 200px"
                                v-model="queryParams.${field.javaField!}"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="选择${field.columnComment!'-'}">
                </el-date-picker>
            </el-form-item>
        [/#if]
    [/#if]
    [/#list]
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                        type="primary"
                        icon="el-icon-plus"
                        size="mini"
                        @click="handleAdd"
                        v-hasPermi="['${table.moduleName!}:${table.businessName!}:add']"
                >新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="primary"
                        icon="el-icon-edit"
                        size="mini"
                        @click="handleUpdate"
                        v-hasPermi="['${table.moduleName!}:${table.businessName!}:update']"
                >修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="danger"
                        icon="el-icon-delete"
                        size="mini"
                        @click="handleDelete"
                        v-hasPermi="['${table.moduleName!}:${table.businessName!}:delete']"
                >删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="primary"
                        icon="el-icon-download"
                        size="mini"
                        @click="handleExport"
                        v-hasPermi="['${moduleName!}:${businessName!}:export']"
                >导出</el-button>
            </el-col>
        </el-row>

        <el-table
                v-loading="loading"
                :data="${table.businessName!}"
                row-key="${pkColumns.javaField!}"
                default-expand-all
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        >
    [#list table.columns as field]
    [#if field.isList="1"]
        [#if field.htmlType=="datetime"]
            <el-table-column label="${field.columnComment!'-'}" align="center" prop="${field.javaField!}" width="200">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.${field.javaField!}) }}</span>
                </template>
            </el-table-column>
        [#elseif field.dictType?? && field.dictType!=""]
            <el-table-column align="center" prop="${field.javaField!}" label="${field.columnComment!'-'}" :formatter="${field.javaField!}Format" />
        [#else]
            <el-table-column prop="${field.javaField!}" label="${field.columnComment!'-'}"/>
        [/#if]
    [/#if]
    [/#list]
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                            v-hasPermi="['${table.moduleName!}:${table.businessName!}:update']"
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdate(scope.row)"
                    >修改</el-button>
                    <el-button
                            v-hasPermi="['${table.moduleName!}:${table.businessName!}:add']"
                            size="mini"
                            type="text"
                            icon="el-icon-plus"
                            @click="handleAdd(scope.row)"
                    >新增</el-button>
                    <el-button
                            v-hasPermi="['${table.moduleName!}:${table.businessName!}:delete']"
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.row)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加或修改对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="600px">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-row>
        [#list table.columns as field]
            [#if field.isInsert=="1" && field.isPk!="1"]
                [#if field.htmlType=="treeselect" && field.javaField==table.treeParentCode]
                    <el-col :span="24">
                        <el-form-item label="上级${field.columnComment!'-'}:" prop="${table.treeParentCode!}">
                            <treeselect v-model="form.${table.treeParentCode!}" :options="parentOptions" placeholder="选择上级${field.columnComment!'-'}" />
                        </el-form-item>
                    </el-col>
                [#elseif field.htmlType=="input"]
                    <el-col :span="12">
                        <el-form-item label="${field.columnComment!'-'}:" prop="${field.javaField!}">
                            <el-input v-model="form.${field.javaField!}" placeholder="请输入${field.columnComment!'-'}" />
                        </el-form-item>
                    </el-col>
                [#elseif field.htmlType=="number"]
                    <el-col :span="12">
                        <el-form-item label="${field.columnComment!'-'}:" prop="${field.javaField!}">
                            <el-input-number v-model="form.${field.javaField!}" controls-position="right" :min="0" />
                        </el-form-item>
                    </el-col>
                [#elseif field.htmlType=="radio"]
                    <el-col :span="12">
                        <el-form-item label="${field.columnComment!'-'}:">
                            <el-radio-group v-model="form.${field.javaField!}">
                                <el-radio
                                        v-for="dict in ${field.javaField!}Options"
                                        :key="dict.dictValue"
                                        :label="dict.dictValue"
                                >{{ dict.dictLabel }}</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                [#elseif field.htmlType=="select"]
                    <el-col :span="12">
                        <el-form-item label="${field.columnComment!'-'}:">
                            <el-select v-model="form.${field.javaField!}" placeholder="请选择${field.columnComment!'-'}">
                                <el-option
                                        v-for="dict in ${field.javaField!}Options"
                                        :key="dict.dictValue"
                                        :label="dict.dictLabel"
                                        :value="dict.dictValue"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                [#elseif field.htmlType=="datetime"]
                    <el-col :span="12">
                        <el-form-item label="${field.columnComment!'-'}:" prop="${field.javaField!}">
                            <el-date-picker clearable size="small" style="width: 200px"
                                            v-model="form.${field.javaField!}"
                                            type="date"
                                            value-format="yyyy-MM-dd"
                                            placeholder="选择${field.columnComment!'-'}">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                [#elseif field.htmlType=="textarea"]
                    <el-col :span="12">
                        <el-form-item label="${field.columnComment!'-'}:" prop="${field.javaField!}">
                            <el-input v-model="form.${field.javaField!}" type="textarea" placeholder="请输入${field.columnComment!'-'}" />
                        </el-form-item>
                    </el-col>
                [/#if]
            [/#if]
        [/#list]
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { list${businessNameFristUpper!}, get${businessNameFristUpper!}, treeSelect, del${businessNameFristUpper!}, add${businessNameFristUpper!}, update${businessNameFristUpper!} } from '@/api/${table.moduleName!}/${table.businessName!}'
    import Treeselect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'

    export default {
        name: '${businessNameFristUpper!}',
        components: { Treeselect },
        data() {
            return {
                // 遮罩层
                loading: true,
                // 表格树数据
                ${table.businessName!}: [],
                // 树选项
                parentOptions: undefined,
                // 弹出层标题
                title: '',
                // 是否显示弹出层
                open: false,
            [#list table.columns as field]
            [#if field.htmlType=="select" || field.htmlType=="radio"]
                // ${field.columnComment!'-'}数据字典
                ${field.javaField!}Options: [],
            [/#if]
            [/#list]
                // 查询参数
                queryParams: {
                [#list table.columns as field]
                    [#if field.isQuery=="1"]
                    ${field.javaField!}: undefined,
                    [/#if]
                [/#list]
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                    ${table.treeParentCode!}: [
                        { required: true, message: '上级不能为空', trigger: 'blur' }
                    ],
                [#list table.columns as field]
                [#if field.isRequired?? && field.isRequired=="1"]
                    ${field.javaField!}: [
                        { required: true, message: '${field.columnComment!'-'}不能为空', trigger: 'blur' }
                    ],
                [/#if]
                [/#list]
                }
            }
        },
        created() {
            this.getList()
        [#list table.columns as field]
        [#if field.htmlType=="select" || field.htmlType=="radio"]
            this.getDictListByDictType('${field.dictType!}').then(response => {
                this.${field.javaField!}Options = response.data
            })
        [/#if]
        [/#list]
        },
        methods: {
            /** 查询列表 */
            getList() {
                this.loading = true
                list${businessNameFristUpper!}(this.queryParams).then(response => {
                    this.${table.businessName!} = response.data
                    this.loading = false
                })
            },
            /** 查询下拉树结构 */
            getTreeSelect() {
                treeSelect().then(response => {
                    this.parentOptions = []
                    const ${table.businessName!} = { id: -1, label: '根目录', children: [] }
                    ${table.businessName!}.children = response.data
                    this.parentOptions.push(${table.businessName!})
                })
            },
[#list table.columns as field]
    [#if field.isList="1"]
        [#if field.dictType?? && field.dictType!=""]
            // ${field.columnComment!'-'}字典翻译
            ${field.javaField!}Format(row, column) {
                return this.selectDictLabel(this.${field.javaField!}Options, row.${field.javaField!})
            },
        [/#if]
    [/#if]
[/#list]
            // 取消按钮
            cancel() {
                this.open = false
                this.reset()
            },
            // 表单重置
            reset() {
                this.form = {
                [#list table.columns as field]
                [#if field.isInsert=="1" && field.isPk!="1"]
                    [#if field.htmlType == "radio"]
                    ${field.javaField!}: 0,
                    [#else]
                    ${field.javaField!}: undefined,
                    [/#if]
                [/#if]
                [/#list]
                    ${table.treeParentCode!}: -1
                }
                this.resetForm('form')
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.getList()
            },
            /** 重置按钮操作 */
            resetQuery() {
                this.resetForm('queryForm')
                this.handleQuery()
            },
            /** 新增按钮操作 */
            handleAdd(row) {
                this.reset()
                this.getTreeSelect()
                if (row !== undefined) {
                    this.form.${table.treeParentCode!} = row.${pkColumns.javaField!}
                }
                this.open = true
                this.title = '添加${table.tableComment!}'
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.reset()
                this.getTreeSelect()
                get${businessNameFristUpper!}(row.${pkColumns.javaField!}).then(response => {
                    this.form = response.data
                    this.open = true
                    this.title = '修改${table.tableComment!}'
                })
            },
            /** 提交按钮 */
            submitForm: function() {
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        if (this.form.${pkColumns.javaField!} !== undefined) {
                            update${businessNameFristUpper!}(this.form).then(response => {
                                if (response.respCode === '0000') {
                                    this.msgSuccess('修改成功')
                                    this.open = false
                                    this.getList()
                                } else {
                                    this.msgError(response.respMsg)
                                }
                            })
                        } else {
                            add${businessNameFristUpper!}(this.form).then(response => {
                                if (response.respCode === '0000') {
                                    this.msgSuccess('新增成功')
                                    this.open = false
                                    this.getList()
                                } else {
                                    this.msgError(response.respMsg)
                                }
                            })
                        }
                    }
                })
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                this.$confirm('是否确认删除?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function() {
                    return del${businessNameFristUpper!}(row.${pkColumns.javaField!})
                }).then((response) => {
                    if (response.respCode === '0000') {
                        this.getList()
                        this.msgSuccess('删除成功')
                    } else {
                        this.msgError(response.respMsg)
                    }
                }).catch(function() {})
            }
        }
    }
</script>
