package com.seller66.admin.controller;

import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.LangBiz;
import com.seller66.admin.entity.Lang;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @description Lang控制层
* @author  jcb
* @since  2025-06-05 19:48:24
*/
@RestController
@RequestMapping("/lang")
public class LangApi extends BaseController<LangBiz, Lang> {

}
