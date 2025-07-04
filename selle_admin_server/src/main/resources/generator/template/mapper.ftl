package ${package};

import com.seller66.admin.common.rest.BaseMapper;
import ${tableClass.fullClassName};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

<#assign dateTime = .now>
/**
* @description ${tableClass.shortClassName}数据层
* @author  jcb
* @since  ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
*/
@Mapper
@Repository
public interface ${tableClass.shortClassName}${mapperSuffix} extends BaseMapper<${tableClass.shortClassName}> {

}