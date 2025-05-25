package com.seller66.admin.common.model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seller66.admin.vo.Query;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class BaseBiz<M extends Mapper<T>,T> {
    @Autowired
    public M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }


    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }


    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }


    public List<T> selectListAll() {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.orderBy("id").desc();
        return mapper.selectByExample(example);
    }


    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }


    public void insert(T entity) {
        mapper.insert(entity);
    }


    public void insertSelective(T entity) {
        mapper.insertSelective(entity);
    }


    public void delete(T entity) {
        mapper.delete(entity);
    }


    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }


    public void updateById(T entity) {
        mapper.updateByPrimaryKey(entity);
    }


    public void updateSelectiveById(T entity) {
        mapper.updateByPrimaryKeySelective(entity);

    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public PageInfo<T> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.orderBy("id").desc();
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(entry.getValue() instanceof String) {
                    criteria.andLike(entry.getKey(), "%" + entry.getValue() + "%");
                }else{
                    criteria.andEqualTo(entry.getKey(), entry.getValue());
                }
            }
        }
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> list = mapper.selectByExample(example);
        return new PageInfo<T>(list);
    }

    public void startPage(Integer page,Integer pageSize){
        PageHelper.startPage(page,pageSize);
    }

    public boolean feignResponseIsSuccess(ObjectRestResponse restResponse){
        return restResponse.getStatus() == 200;
    }
}
