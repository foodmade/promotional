package com.seller66.admin.common.rest;

import com.seller66.admin.common.model.ObjectRestResponse;
import com.seller66.admin.common.model.TableRestResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by jcb on 2020-03-19 10:27
 */
public interface ISelectBasicApi<Entity> {

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    ObjectRestResponse<Entity> get(@PathVariable int id);

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    ObjectRestResponse<List<Entity>> all();

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    TableRestResponse<Entity> list(@RequestParam Map<String, Object> params);

}
