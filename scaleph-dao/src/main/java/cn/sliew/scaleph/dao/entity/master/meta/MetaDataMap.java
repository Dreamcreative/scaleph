/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.dao.entity.master.meta;

import cn.sliew.scaleph.dao.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 元数据-参考数据映射
 * </p>
 *
 * @author liyu
 * @since 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("meta_data_map")
@ApiModel(value = "MetaDataMap对象", description = "元数据-参考数据映射")
public class MetaDataMap extends BaseDO {

    private static final long serialVersionUID = 1413344387211239856L;

    @TableField(exist = false)
    @ApiModelProperty(value = "源参考数据类型 id")
    private Long srcDataSetTypeId;

    @TableField(exist = false)
    @ApiModelProperty(value = "源参考数据类型编码")
    private String srcDataSetTypeCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "源参考数据类型名称")
    private String srcDataSetTypeName;

    @ApiModelProperty(value = "源参考数据id")
    private Long srcDataSetId;

    @TableField(exist = false)
    @ApiModelProperty(value = "源参考数据编码")
    private String srcDataSetCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "源参考数据值")
    private String srcDataSetValue;

    @TableField(exist = false)
    @ApiModelProperty(value = "目标参考数据类型 id")
    private String tgtDataSetTypeId;

    @TableField(exist = false)
    @ApiModelProperty(value = "目标参考数据类型编码")
    private String tgtDataSetTypeCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "目标参考数据类型名称")
    private String tgtDataSetTypeName;

    @ApiModelProperty(value = "目标参考数据id")
    private Long tgtDataSetId;

    @TableField(exist = false)
    @ApiModelProperty(value = "目标参考数据编码")
    private String tgtDataSetCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "目标参考数据值")
    private String tgtDataSetValue;

    @ApiModelProperty(value = "备注")
    private String remark;
}
