package com.seller66.admin.common.rest;

import com.seller66.admin.common.model.ObjectRestResponse;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jcb on 2020-03-19 10:30
 */
public interface IUpdateBasicApi<Entity> {

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    ObjectRestResponse<Entity> update(@PathVariable int id, @RequestBody Entity entity);
}
