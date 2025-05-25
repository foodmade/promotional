package ${package};

import ${package?substring(0,package?last_index_of('.'))}.mapper.${tableClass.shortClassName}${mapperSuffix};
import com.seller66.admin.common.model.BaseBiz;
import ${tableClass.fullClassName};
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#assign dateTime = .now>
/**
* @description ${tableClass.shortClassName}业务层
* @author  jcb
* @since  ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
*/
@Slf4j
@Service
@Transactional
public class ${tableClass.shortClassName}${bizSuffix} extends BaseBiz<${tableClass.shortClassName}${mapperSuffix}, ${tableClass.shortClassName}> {

}
