package com.seller66.admin.biz;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.mapper.AuthorMapper;
import com.seller66.admin.entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @description Author业务层
* @author  jcb
* @since  2025-04-20 22:15:34
*/
@Slf4j
@Service
@Transactional
public class AuthorBiz extends BaseBiz<AuthorMapper, Author> {

    public CommonResult<String> addAuthor(Author author) {
        Author author1 = this.mapper.selectByName(author.getName());
        if(author1 != null){
            return CommonResult.failed(author.getName() + "已存在");
        }
        insert(author);
        return CommonResult.success();
    }

    public CommonResult<String> updateAuthor(Author author) {
        if(author == null || author.getId() == null){
            return CommonResult.failed("信息缺失");
        }
        updateSelectiveById(author);
        return CommonResult.success();
    }
}
