package com.seller66.admin.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.mapper.SellerToolsMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.SellerTools;
import com.seller66.admin.vo.SellerToolsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @description SellerTools业务层
* @author  jcb
* @since  2025-05-23 14:16:36
*/
@Slf4j
@Service
@Transactional
public class SellerToolsBiz extends BaseBiz<SellerToolsMapper, SellerTools> {

    public CommonResultList<SellerTools> getAll(SellerToolsQuery sellerToolsQuery) {
        PageHelper.startPage(sellerToolsQuery.getPageNum(), sellerToolsQuery.getPageSize());
        List<SellerTools> list = this.mapper.selectAll();
        PageInfo<SellerTools> pageInfo = new PageInfo<>(list);
        return CommonResultList.success(list, pageInfo.getTotal(), sellerToolsQuery.getPageNum(), sellerToolsQuery.getPageSize());
    }

    public CommonResult<SellerTools> create(SellerTools sellerTools) {
        if (sellerTools == null || sellerTools.getName() == null || sellerTools.getType() == null) {
            return CommonResult.failed("缺失必填信息");
        }
        insertSelective(sellerTools);
        return CommonResult.success(sellerTools);
    }
}
