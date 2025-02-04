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

package cn.sliew.scaleph.engine.seatunnel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.sliew.scaleph.dao.entity.master.ws.WsDiJobLink;
import cn.sliew.scaleph.dao.mapper.master.ws.WsDiJobLinkMapper;
import cn.sliew.scaleph.engine.seatunnel.service.WsDiJobLinkService;
import cn.sliew.scaleph.engine.seatunnel.service.convert.WsDiJobLinkConvert;
import cn.sliew.scaleph.engine.seatunnel.service.dto.WsDiJobLinkDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author gleiyu
 */
@Service
public class WsDiJobLinkServiceImpl implements WsDiJobLinkService {

    @Autowired
    private WsDiJobLinkMapper diJobLinkMapper;

    @Override
    public List<WsDiJobLinkDTO> listJobLink(Long jobId) {
        List<WsDiJobLink> list = diJobLinkMapper.selectList(
                new LambdaQueryWrapper<WsDiJobLink>()
                        .eq(WsDiJobLink::getJobId, jobId)
        );
        return WsDiJobLinkConvert.INSTANCE.toDto(list);
    }

    @Override
    public int insert(WsDiJobLinkDTO diJobLink) {
        WsDiJobLink link = WsDiJobLinkConvert.INSTANCE.toDo(diJobLink);
        return diJobLinkMapper.insert(link);
    }

    @Override
    public int upsert(WsDiJobLinkDTO diJobLink) {
        WsDiJobLink link = diJobLinkMapper.selectOne(
                new LambdaQueryWrapper<WsDiJobLink>()
                        .eq(WsDiJobLink::getJobId, diJobLink.getJobId())
                        .eq(WsDiJobLink::getLinkCode, diJobLink.getLinkCode())
        );
        WsDiJobLink jobLink = WsDiJobLinkConvert.INSTANCE.toDo(diJobLink);
        if (link == null) {
            return diJobLinkMapper.insert(jobLink);
        } else {
            return diJobLinkMapper.update(jobLink,
                    new LambdaUpdateWrapper<WsDiJobLink>()
                            .eq(WsDiJobLink::getJobId, jobLink.getJobId())
                            .eq(WsDiJobLink::getLinkCode, jobLink.getLinkCode())
            );
        }
    }

    @Override
    public int deleteByProjectId(Collection<? extends Serializable> projectIds) {
        return diJobLinkMapper.deleteByProjectId(projectIds);
    }

    @Override
    public int deleteByJobId(Collection<? extends Serializable> jobIds) {
        return diJobLinkMapper.deleteByJobId(jobIds);
    }

    @Override
    public int deleteSurplusLink(Long jobId, List<String> linkCodeList) {
        return diJobLinkMapper.delete(
                new LambdaQueryWrapper<WsDiJobLink>()
                        .eq(WsDiJobLink::getJobId, jobId)
                        .notIn(CollectionUtil.isNotEmpty(linkCodeList), WsDiJobLink::getLinkCode,
                                linkCodeList)
        );
    }

    @Override
    public int clone(Long sourceJobId, Long targetJobId) {
        return diJobLinkMapper.clone(sourceJobId, targetJobId);
    }
}
