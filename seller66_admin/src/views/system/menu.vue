<template>
    <div class="app-container">
        <div class="menu-header">
            <!-- <el-button type="primary" @click="openAddMenu()" icon="el-icon-plus">新增菜单</el-button> -->
        </div>
        <el-tree :data="menuTree" node-key="id" :props="treeProps" default-expand-all class="menu-tree styled-tree">
            <template #default="{ node, data }">
                <div class="menu-row">
                    <span class="menu-name">{{ data.menuUsName }}/{{ data.menuCnName }}</span>
                    <div class="menu-actions">
                        <!-- <el-button size="small" type="success" icon="el-icon-plus" @click.stop="openAddMenu(data)">添加子菜单</el-button> -->
                        <el-button size="small" icon="el-icon-edit" @click.stop="openEditMenu(data)">编辑</el-button>
                        <el-button size="small" :type="data.visible ? 'danger' : 'success'"
                            :icon="data.visible ? 'el-icon-close' : 'el-icon-check'"
                            @click.stop="toggleMenuVisible(data)">
                            {{ data.visible ? '禁用' : '启用' }}
                        </el-button>
                    </div>
                </div>
            </template>
        </el-tree>
        <el-dialog v-model="dialogVisible" :title="dialogTitle" width="400px">
            <el-form :model="form" label-width="150px" size="small" class="menu-form">
                <el-form-item label="菜单名称(中文)"><el-input v-model="form.menuCnName" /></el-form-item>
                <el-form-item label="菜单名称(英文)"><el-input v-model="form.menuUsName" /></el-form-item>
                <el-form-item label="标题(中文)"><el-input v-model="form.menuTitleCnDesc" /></el-form-item>
                <el-form-item label="标题(英文)"><el-input v-model="form.menuTitleUsDesc" /></el-form-item>
                <el-form-item label="描述内容(中文)">
                    <el-input v-model="form.menuSubTitleCnDesc" type="textarea" :rows="4" placeholder="请输入描述内容" />
                </el-form-item>
                <el-form-item label="描述内容(英文)">
                    <el-input v-model="form.menuSubTitleUsDesc" type="textarea" :rows="4" placeholder="请输入描述内容" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="saveMenu">更新</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus';
import ApiFactory from '@/api';

export default {
    name: 'MenuManager',
    data() {
        return {
            menuTree: [],
            dialogVisible: false,
            dialogTitle: '',
            form: {
                id: '', parent_id: null, name: '', path: '', icon: '', sort: 0, external: 0, visible: 1, title: '', 
                subtitle: '', menuCnName: '', menuUsName: '', title: '', subtitle: '',menuTitleCnDesc: '',
                menuTitleUsDesc: '',menuSubTitleCnDesc: '',menuSubTitleUsDesc: ''
            },
            treeProps: { label: 'name', children: 'children' }
        }
    },
    mounted() {
        this.fetchMenuTree();
    },
    methods: {
        async fetchMenuTree() {
            const api = ApiFactory.getMenuApi();
            const res = await api.getMenuTree();
            if (res.isSuccess && res.isSuccess()) {
                this.menuTree = res.data;
            }
        },
        openEditMenu(menu) {
            this.dialogTitle = '编辑菜单';
            this.form = { ...menu };
            this.dialogVisible = true;
        },
        async saveMenu() {
            const api = ApiFactory.getMenuApi();
            console.log(this.form);
            await api.updateMenu(this.form);
            ElMessage.success('菜单更新成功');
            this.dialogVisible = false;
            this.fetchMenuTree();
        },
        async toggleMenuVisible(menu) {
            const api = ApiFactory.getMenuApi();
            const newMenu = await api.disableMenu(menu.id);
            ElMessage.success(newMenu.visible === 1 ? '禁用成功' : '启用成功');
            this.fetchMenuTree();
        }
    }
}
</script>

<style scoped>
.menu-header {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 16px;
}

.menu-tree.styled-tree {
    background: #fff;
    border: 1px solid #ebeef5;
    border-radius: 6px;
    padding: 20px 24px 10px 24px;
    min-height: 300px;
}

.menu-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 4px 0;
}

.menu-name {
    font-weight: bold;
    font-size: 15px;
    color: #333;
    margin-right: 16px;
}

.menu-actions {
    display: flex;
    gap: 8px;
}

.menu-form {
    padding-top: 10px;
}
</style>