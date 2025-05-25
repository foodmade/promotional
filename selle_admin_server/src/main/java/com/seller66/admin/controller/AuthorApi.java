package com.seller66.admin.controller;

import com.seller66.admin.biz.AuthorBiz;
import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.entity.Author;
import com.seller66.admin.utils.rest.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
* @description Author控制层
* @author  jcb
* @since  2025-04-20 22:15:34
*/
@RestController
@RequestMapping("/author")
public class AuthorApi extends BaseController<AuthorBiz, Author> {

    @GetMapping("/getAllAuthor")
    public CommonResultList<Author> getAll(){
        return CommonResultList.success(baseBiz.selectListAll());
    }

    @PostMapping("/createAuthor")
    public CommonResult<String> addAuthor(@RequestBody Author author){
        return baseBiz.addAuthor(author);
    }

    @GetMapping("/deleteAuthor")
    public CommonResult<Boolean> deleteAuthor(@NotNull Long id){
        baseBiz.deleteById(id);
        return CommonResult.success(true);
    }

    @PostMapping("/updateAuthor")
    public CommonResult<String> updateAuthor(@RequestBody Author author){
        return baseBiz.updateAuthor(author);
    }

    @GetMapping("/getAuthorById")
    public CommonResult<Author> getAuthorById(@NotNull Long id){
        Author author = baseBiz.selectById(id);
        if (author == null) {
            return CommonResult.failed("作者不存在");
        }
        return CommonResult.success(author);
    }
}
