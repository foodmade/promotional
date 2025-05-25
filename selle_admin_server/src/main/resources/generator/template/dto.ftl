package ${package};

import com.seller66.admin.model.BaseModel;
<#assign baseTypeArr=["Byte","Short","Integer","Long","Float","Double","Char","Boolean","String"] />
<#assign importStrs="" />
<#list tableClass.allFields as field>
    <#if !(baseTypeArr?seq_contains(field.shortTypeName)) >
        <#if importStrs?contains(field.type)>
            <#continue />
        <#else>
            <#assign importStrs=importStrs+","+field.type />
        </#if>
    </#if>
</#list>
<#list importStrs?split(",") as importStr>
    <#if importStr !="">
        import ${importStr};
    </#if>
</#list>
import lombok.Data;

<#assign dateTime = .now>
/**
* @description ${tableClass.shortClassName}视图
* @author  jcb
* @since  ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
*/

@Data
public class ${tableClass.shortClassName}${suffix} extends BaseModel {

<#list  tableClass.allFields as field>
    /**
    * ${field.fieldName}
    * ${field.remarks?if_exists}
    */
    private ${field.shortTypeName} ${field.fieldName};
</#list>


}
