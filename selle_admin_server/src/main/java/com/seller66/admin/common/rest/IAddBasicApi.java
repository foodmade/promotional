package com.seller66.admin.common.rest;

import com.seller66.admin.common.model.ObjectRestResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jcb on 2020-03-19 10:30
 */
public interface IAddBasicApi<Entity> {

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    ObjectRestResponse<Entity> add(@RequestBody Entity entity);
}
