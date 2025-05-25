package com.seller66.admin.utils.rest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.common.model.ObjectRestResponse;
import com.seller66.admin.common.model.PageModel;
import com.seller66.admin.common.model.TableRestResponse;
import com.seller66.admin.common.rest.IBaseApi;
import com.seller66.admin.model.BaseModel;
import com.seller66.admin.vo.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class BaseController<Biz extends BaseBiz, Entity extends BaseModel> implements IBaseApi<Entity> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Biz baseBiz;

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Entity> add(@RequestBody Entity entity) {
        baseBiz.insertSelective(entity);
        return new ObjectRestResponse<>(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Entity> get(@PathVariable int id) {
        ObjectRestResponse<Entity> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.selectById(id);
        entityObjectRestResponse.setData((Entity) o);
        return entityObjectRestResponse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Entity> update(@PathVariable int id,@RequestBody Entity entity) {
        baseBiz.updateSelectiveById(entity);
        return new ObjectRestResponse<>(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Entity> remove(@PathVariable int id) {
        baseBiz.deleteById(id);
        return new ObjectRestResponse<>();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<Entity>> all() {
        return new ObjectRestResponse<>(baseBiz.selectListAll());
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public TableRestResponse<Entity> list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo<Entity> pageInfo = baseBiz.selectByQuery(query);
        return new TableRestResponse<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getList());
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String _PAGE_CONST = "pageNum";
    private static final String _PAGE_SIZE_CONST = "pageSize";
    /**
     * 分页
     */
    protected <T extends PageModel> void startPage(T param){
        if(Objects.isNull(param)){
            throw new IllegalArgumentException("Start page params object must not be null.");
        }
//        Object pageObj = ReflectionUtils.getFieldValue(param,_PAGE_CONST);
        //获取分页参数
//        Object pageSizeObj = ReflectionUtils.getFieldValue(param,_PAGE_SIZE_CONST);

        //分页
        setPage(param.getPageNum(),param.getPageSize());
    }

    protected void setPage(Object pageObj,Object pageSizeObj){
        //分页
        if(!Objects.isNull(pageObj) && !Objects.isNull(pageSizeObj)){
            //todo: 加入sort条件....
            PageHelper.startPage(Integer.parseInt(pageObj + ""),Integer.parseInt(pageSizeObj + ""));
        }
    }
    /**
     *
     * 获取分页数据
     */
    protected <T extends Serializable> TableRestResponse<T> getTableData(List<T> datas){
        return build(datas);
    }

    /**
     *
     * 获取分页数据
     */
    protected <T extends Serializable> TableRestResponse<T> build(List<T> datas){
        if(datas == null){
            return TableRestResponse.build();
        }
        TableRestResponse<T> restResponse = new TableRestResponse<>();
        restResponse.getData().setTotal(new PageInfo<>(datas).getTotal());
        restResponse.getData().setRows(datas);
        return restResponse;
    }
}
