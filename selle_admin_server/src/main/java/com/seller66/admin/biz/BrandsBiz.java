package com.seller66.admin.biz;

import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.mapper.BrandsMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Brands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @description Brands业务层
* @author  jcb
* @since  2025-05-25 12:16:52
*/
@Slf4j
@Service
@Transactional
public class BrandsBiz extends BaseBiz<BrandsMapper, Brands> {

    public CommonResultList<Brands> getAllBrands() {
        List<Brands> list = this.mapper.getAllBransList();
        return CommonResultList.success(list);
    }
}
