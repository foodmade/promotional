package com.seller66.admin.common.rest;


import com.seller66.admin.model.BaseModel;

/**
 * Created by jcb on 2020-03-17 15:39
 */
public interface IBaseApi<Entity extends BaseModel> extends IAddBasicApi<Entity>,IDeleteBasicApi<Entity>, IUpdateBasicApi<Entity>,ISelectBasicApi<Entity> {

}
