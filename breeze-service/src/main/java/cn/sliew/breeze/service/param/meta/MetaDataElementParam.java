package cn.sliew.breeze.service.param.meta;

import cn.sliew.breeze.service.param.PaginationParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gleiyu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MetaDataElementParam extends PaginationParam {
    @ApiModelProperty(value = "数据元标识")
    private String elementCode;

    @ApiModelProperty(value = "数据元名称")
    private String elementName;
}
