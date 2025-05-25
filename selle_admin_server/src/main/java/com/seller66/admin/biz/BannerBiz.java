package com.seller66.admin.biz;

import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.mapper.BannerMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Banner;
import com.seller66.admin.vo.BannerQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @description Banner业务层
* @author  jcb
* @since  2025-05-25 11:10:34
*/
@Slf4j
@Service
@Transactional
public class BannerBiz extends BaseBiz<BannerMapper, Banner> {

    public CommonResultList<Banner> getBannerList(BannerQueryVo bannerQueryVo) {
         List<Banner> bannerList = this.mapper.getAllBannerList(bannerQueryVo);
         return CommonResultList.success(bannerList);
    }

    public void createBanner(Banner banner) {
        insertSelective(banner);
    }
}
