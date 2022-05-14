package cn.sliew.scalegh.service.di;

import cn.sliew.scalegh.service.dto.di.DiClusterConfigDTO;
import cn.sliew.scalegh.service.param.di.DiClusterConfigParam;
import cn.sliew.scalegh.service.vo.DictVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DiClusterConfigService {

    int insert(DiClusterConfigDTO dto);

    int update(DiClusterConfigDTO dto);

    int deleteById(Long id);

    int deleteBatch(Map<Integer, ? extends Serializable> map);

    Page<DiClusterConfigDTO> listByPage(DiClusterConfigParam param);

    List<DictVO> listAll();

    DiClusterConfigDTO selectOne(Long id);
}