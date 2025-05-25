package com.seller66.admin.controller;

import com.seller66.admin.biz.ArcticleBiz;
import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.entity.Arcticle;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.vo.ArticleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @description Arcticle控制层
* @author  jcb
* @since  2025-05-22 13:05:45
*/
@Api(tags = "文章管理")
@RestController
@RequestMapping("/arcticle")
public class ArcticleApi extends BaseController<ArcticleBiz, Arcticle> {

    @ApiOperation("创建文章")
    @PostMapping(value = "/createArticle")
    public CommonResult<Boolean> createArticle(@ApiParam("文章信息") @RequestBody Arcticle arcticle) {
        // 这里可以添加一些业务逻辑，比如验证参数等
        if (arcticle == null || arcticle.getTitle() == null || arcticle.getContent() == null) {
            return CommonResult.failed("文章标题和内容不能为空");
        }
        // 调用业务层方法保存文章
        return baseBiz.createArticle(arcticle);
    }

    @ApiOperation("获取文章列表")
    @GetMapping("/getArticleList")
    public CommonResultList<Arcticle> getArticleList(@ApiParam("查询参数") ArticleQueryVo queryVo) {
        return baseBiz.getArticleList(queryVo);
    }

    @ApiOperation("删除文章")
    @GetMapping("/deleteArticle")
    public CommonResult<Boolean> deleteArticle(@ApiParam("文章ID") Long id) {
        baseBiz.deleteById(id);
        return CommonResult.success();
    }

    @ApiOperation("根据ID获取文章")
    @GetMapping("/getArticleById")
    public CommonResult<Arcticle> getArticleById(@ApiParam("文章ID") Long id) {
        Arcticle arcticle = baseBiz.selectById(id);
        if (arcticle == null) {
            return CommonResult.failed("文章不存在");
        }
        return CommonResult.success(arcticle);
    }

    @ApiOperation("更新文章")
    @PostMapping("/updateArticle")
    public CommonResult<Boolean> updateArticle(@ApiParam("文章信息") @RequestBody Arcticle arcticle) {
        // 这里可以添加一些业务逻辑，比如验证参数等
        if (arcticle == null || arcticle.getId() == null) {
            return CommonResult.failed("文章ID不能为空");
        }
        // 调用业务层方法更新文章
        baseBiz.updateSelectiveById(arcticle);
        return CommonResult.success(true);
    }
}
