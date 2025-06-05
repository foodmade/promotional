<template>
    <div class="app-container">
        <el-card>
            <div style="margin-bottom: 16px; display: flex; justify-content: space-between; align-items: center;">
                <el-button type="primary" @click="handleAdd">新增弹窗</el-button>
                <el-input v-model="search.title" placeholder="搜索标题" style="width: 200px;" @keyup.enter="fetchList" />
            </div>
            <el-table :data="list" border stripe style="width: 100%">
                <el-table-column prop="logo" label="Logo" width="100">
                    <template #default="scope">
                        <img :src="scope.row.logo" style="width:60px;height:60px;object-fit:contain;"
                            v-if="scope.row.logo" />
                    </template>
                </el-table-column>
                <el-table-column prop="title" label="标题" />
                <el-table-column prop="popupId" label="绑定页面" />
                <el-table-column prop="buttonText" label="按钮文案" />
                <el-table-column prop="userCount" label="用户数" width="90" />
                <el-table-column prop="discountAmount" label="优惠金额" width="90" />
                <el-table-column prop="originalPrice" label="原价" width="90" />
                <el-table-column prop="specialPrice" label="优惠价" width="90" />
                <el-table-column prop="createTime" label="创建时间" width="160" />
                <el-table-column label="操作" width="180">
                    <template #default="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                        <el-button size="small" @click="handlePreview(scope.row)">预览</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination v-model:current-page="page.pageNum" v-model:page-size="page.pageSize" :total="page.total"
                @current-change="fetchList" @size-change="fetchList" layout="total, prev, pager, next, sizes"
                :page-sizes="[10, 20, 50, 100]" style="margin-top: 16px; text-align: right;" />
        </el-card>
        <edit-dialog v-if="dialogVisible" :row="currentRow" @success="onDialogSuccess" @close="dialogVisible = false" />
        <preview-dialog v-if="previewVisible" :row="previewRow" @close="previewVisible = false" />
    </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import ApiFactory from '@/api'
import EditDialog from './edit.vue'
import PreviewDialog from './preview.vue'

export default {
    name: 'PromotionPopupList',
    components: { EditDialog, PreviewDialog },
    data() {
        return {
            list: [],
            page: { pageNum: 1, pageSize: 10, total: 0 },
            search: { title: '' },
            dialogVisible: false,
            currentRow: null,
            previewVisible: false,
            previewRow: null
        }
    },
    methods: {
        async fetchList() {
            const params = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
            }
            const api = ApiFactory.getPromotionPopupApi();
            const res = await api.getList()
            console.log(res)
            if (res.isSuccess()) {
                this.list = res.data
                this.page.total = res.total
            }
        },
        handleAdd() {
            this.currentRow = null
            this.dialogVisible = true
        },
        handleEdit(row) {
            this.currentRow = { ...row }
            this.dialogVisible = true
        },
        handleDelete(row) {
            ElMessageBox.confirm('确定要删除该弹窗吗？', '提示', { type: 'warning' })
                .then(async () => {
                    const res = await ApiFactory.getPromotionPopupApi().delete(row.id)
                    if (res.isSuccess()) {
                        ElMessage.success('删除成功')
                        this.fetchList()
                    }
                })
        },
        handlePreview(row) {
            this.previewRow = { ...row }
            this.previewVisible = true
        },
        onDialogSuccess() {
            this.dialogVisible = false
            this.fetchList()
        }
    },
    mounted() {
        this.fetchList()
    }
}
</script>