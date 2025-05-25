package ${package};

import com.github.jcbjcb.tools.common.hystrix.BaseHystrix;
import ${package?substring(0,package?last_index_of('.'))}.dto.${tableClass.shortClassName}${dtoSuffix};
import ${package?substring(0,package?last_index_of('.'))}.feign.${tableClass.shortClassName}${feignSuffix};
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

<#assign dateTime = .now>
/**
* @description ${tableClass.shortClassName} 熔断器
* @author  jcb
* @since  ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
*/
@Slf4j
@Component
public class ${tableClass.shortClassName}${suffix} extends BaseHystrix<${tableClass.shortClassName}${dtoSuffix}> implements ${tableClass.shortClassName}${feignSuffix} {

}
