package ${package};

import com.seller66.admin.utils.rest.BaseController;
import ${package?substring(0,package?last_index_of('.'))}.biz.${tableClass.shortClassName}${bizSuffix};
import ${tableClass.fullClassName};
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<#assign dateTime = .now>
/**
* @description ${tableClass.shortClassName}控制层
* @author  jcb
* @since  ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
*/
@RestController
@RequestMapping("/${tableClass.variableName?uncap_first}")
public class ${tableClass.shortClassName}${controllerSuffix} extends BaseController<${tableClass.shortClassName}${bizSuffix}, ${tableClass.shortClassName}> {

}
