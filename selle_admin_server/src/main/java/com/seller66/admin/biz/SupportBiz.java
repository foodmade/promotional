package com.seller66.admin.biz;

import com.seller66.admin.mapper.SupportMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Support;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @description Support业务层
* @author  jcb
* @since  2025-05-25 13:05:56
*/
@Slf4j
@Service
@Transactional
public class SupportBiz extends BaseBiz<SupportMapper, Support> {

}
