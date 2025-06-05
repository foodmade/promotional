<template>
  <div class="app-container">
    <div class="menu-header">
      <el-button type="primary" @click="openAddMenu()" icon="el-icon-plus">新增菜单</el-button>
    </div>
    <el-tree
      :data="menuTree"
      node-key="id"
      :props="treeProps"
      default-expand-all
      class="menu-tree styled-tree"
    >
      <template #default="{ node, data }">
        <div class="menu-row">
          <span class="menu-name">{{ data.name }}</span>
          <div class="menu-actions">
            <el-button size="small" type="success" icon="el-icon-plus" @click.stop="openAddMenu(data)">添加子菜单</el-button>
            <el-button size="small" icon="el-icon-edit" @click.stop="openEditMenu(data)">编辑</el-button>
            <el-button size="small" type="danger" icon="el-icon-delete" @click.stop="deleteMenu(data)">删除</el-button>
          </div>
        </div>
      </template>
    </el-tree>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="400px">
      <el-form :model="form" label-width="90px" size="small" class="menu-form">
        <el-form-item label="菜单名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="路由路径"><el-input v-model="form.path" /></el-form-item>
        <el-form-item label="图标"><el-input v-model="form.icon" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" /></el-form-item>
        <el-form-item label="外部链接"><el-switch v-model="form.external" /></el-form-item>
        <el-form-item label="显示"><el-switch v-model="form.visible" /></el-form-item>
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.subtitle" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="saveMenu">保存</el-button>
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
        id: '', parent_id: null, name: '', path: '', icon: '', sort: 0, external: 0, visible: 1, title: '', subtitle: ''
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
    openAddMenu(parent) {
      this.dialogTitle = parent ? '添加子菜单' : '新增菜单';
      this.form = { id: '', parent_id: parent ? parent.id : null, name: '', path: '', icon: '', sort: 0, external: 0, visible: 1, title: '', subtitle: '' };
      this.dialogVisible = true;
    },
    openEditMenu(menu) {
      this.dialogTitle = '编辑菜单';
      this.form = { ...menu };
      this.dialogVisible = true;
    },
    async saveMenu() {
      const api = ApiFactory.getMenuApi();
      if (this.form.id) {
        await api.updateMenu(this.form.id, this.form);
        ElMessage.success('菜单更新成功');
      } else {
        await api.createMenu(this.form);
        ElMessage.success('菜单创建成功');
      }
      this.dialogVisible = false;
      this.fetchMenuTree();
    },
    deleteMenu(menu) {
      ElMessageBox.confirm('确定删除该菜单？','提示',{type:'warning'}).then(async()=>{
        const api = ApiFactory.getMenuApi();
        await api.deleteMenu(menu.id);
        ElMessage.success('删除成功');
        this.fetchMenuTree();
      });
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