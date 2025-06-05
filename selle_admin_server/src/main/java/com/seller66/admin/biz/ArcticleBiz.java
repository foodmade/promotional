package com.seller66.admin.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.mapper.ArcticleMapper;
import com.seller66.admin.entity.Arcticle;
import com.seller66.admin.vo.ArticleQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* @description Arcticle业务层
* @author  jcb
* @since  2025-05-22 13:05:45
*/
@Slf4j
@Service
@Transactional
public class ArcticleBiz extends BaseBiz<ArcticleMapper, Arcticle> {

    public CommonResult<Boolean> createArticle(Arcticle arcticle) {
        // 这里可以添加一些业务逻辑，比如验证参数等
        if (arcticle == null || arcticle.getTitle() == null || arcticle.getContent() == null) {
            return CommonResult.failed("缺失必填信息");
        }
        //验证slug是否存在
        Arcticle arcticle1 = this.mapper.selectBySlug(arcticle.getSlug());
        if(arcticle1 != null && !arcticle1.getId().equals(arcticle.getId())) {
            return CommonResult.failed("slug已存在: " + arcticle1.getSlug());
        }
        if(arcticle.getId() != null) {
            this.mapper.updateByPrimaryKey(arcticle);
        }else{
            arcticle.setCreateTime(new Date());
            // 调用 mapper 方法保存文章
            this.mapper.insert(arcticle);
        }
        return CommonResult.success(true);
    }

    public CommonResultList<Arcticle> getArticleList(ArticleQueryVo queryVo) {
        PageHelper.startPage(queryVo.getPageNum(), queryVo.getPageSize());
        List<Arcticle> articleList = this.mapper.selectAllByFilter(queryVo);
        PageInfo<Arcticle> pageInfo = new PageInfo<>(articleList);
        return CommonResultList.success(articleList, pageInfo.getTotal(), queryVo.getPageNum(), queryVo.getPageSize());
    }
}
