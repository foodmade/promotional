package ${package};

import com.github.jcbjcb.tools.common.rest.IBaseApi;
import com.github.jcbjcb.tools.common.util.BaseModel;
import ${package?substring(0,package?last_index_of('.'))}.hystrix.${tableClass.shortClassName}${hystrixSuffix};
import org.springframework.cloud.openfeign.FeignClient;

<#assign dateTime = .now>
/**
* @description ${tableClass.shortClassName}控制层
* @author  jcb
* @since  ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
*/
@FeignClient(value = "<#noparse>${</#noparse>${serverId}:${serverId}}",path = "/${tableClass.variableName?uncap_first}",fallback = ${tableClass.shortClassName}${hystrixSuffix}.class)
public interface ${tableClass.shortClassName}${suffix}{

}