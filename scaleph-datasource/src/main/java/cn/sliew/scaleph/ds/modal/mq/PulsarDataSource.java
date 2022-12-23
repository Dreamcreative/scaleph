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

package cn.sliew.scaleph.ds.modal.mq;

import cn.sliew.scaleph.common.codec.CodecUtil;
import cn.sliew.scaleph.common.dict.job.DataSourceType;
import cn.sliew.scaleph.common.util.BeanUtil;
import cn.sliew.scaleph.ds.modal.AbstractDataSource;
import cn.sliew.scaleph.ds.service.dto.DsInfoDTO;
import cn.sliew.scaleph.ds.service.dto.DsTypeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class PulsarDataSource extends AbstractDataSource {

    @NotBlank
    @ApiModelProperty("admin web service url")
    private String webServiceUrl;

    @NotBlank
    @ApiModelProperty("client service url")
    private String clientServiceUrl;

    @ApiModelProperty("authentication plugin")
    private String authPlugin;

    @ApiModelProperty("authentication plugin parameters")
    private String authParams;

    @Override
    public DataSourceType getType() {
        return DataSourceType.PULSAR;
    }

    @Override
    public DsInfoDTO toDsInfo() {
        DsInfoDTO dto = BeanUtil.copy(this, new DsInfoDTO());
        DsTypeDTO dsType = new DsTypeDTO();
        dsType.setId(getDsTypeId());
        dsType.setType(getType());
        dto.setDsType(dsType);
        Map<String, Object> props = new HashMap<>();
        props.put("webServiceUrl", webServiceUrl);
        props.put("clientServiceUrl", clientServiceUrl);
        if (StringUtils.hasText(authPlugin)) {
            props.put("authPlugin", authPlugin);
        }
        if (StringUtils.hasText(authParams)) {
            props.put("authParams", CodecUtil.encrypt(authParams));
        }
        dto.setProps(props);
        return dto;
    }
}