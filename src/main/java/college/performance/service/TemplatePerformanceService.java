package college.performance.service;

import college.performance.dao.TemplatePerformanceDetailMapper;
import college.performance.dao.TemplatePerformanceMainMapper;
import college.performance.model.Dto.TemplatePerformanceDto;
import college.performance.model.TemplatePerformanceDetail;
import college.performance.model.TemplatePerformanceMain;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 18:04
 * Version:V1.0
 */
@Service
public class TemplatePerformanceService {

    @Resource
    private TemplatePerformanceMainMapper templatePerformanceMainMapper;

    @Resource
    private TemplatePerformanceDetailMapper templatePerformanceDetailMapper;

    public List<TemplatePerformanceMain> list() {
        List<TemplatePerformanceMain> result = templatePerformanceMainMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceMain.class));
        result.forEach(templatePerformanceMain -> {
            templatePerformanceMain.setDetailList(templatePerformanceDetailMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceDetail.class)
                    .eq(TemplatePerformanceDetail::getTemplatePerformanceId, templatePerformanceMain.getId())));
        });
        return result;
    }

    public Integer add(TemplatePerformanceDto templatePerformanceMain) {
        templatePerformanceMain.getTemplatePerformanceMain().setId(null);
        templatePerformanceMainMapper.insert(templatePerformanceMain.getTemplatePerformanceMain());
        templatePerformanceMain.getDetailList().forEach(templatePerformanceDetail -> {
            templatePerformanceDetail.setId(null);
            templatePerformanceDetail.setTemplatePerformanceId(templatePerformanceMain.getTemplatePerformanceMain().getId());
            templatePerformanceDetailMapper.insert(templatePerformanceDetail);
        });
        return 1;
    }

    public Integer update(TemplatePerformanceDto templatePerformanceMain) {
        templatePerformanceMainMapper.updateById(templatePerformanceMain.getTemplatePerformanceMain());
        templatePerformanceMain.getDetailList().forEach(templatePerformanceDetail -> {
            if (templatePerformanceDetail.getId() != null) {
                templatePerformanceDetailMapper.updateById(templatePerformanceDetail);
            } else {
                templatePerformanceDetail.setTemplatePerformanceId(templatePerformanceMain.getTemplatePerformanceMain().getId());
                templatePerformanceDetailMapper.insert(templatePerformanceDetail);
            }
        });
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Integer id) {
        templatePerformanceMainMapper.deleteById(id);
        templatePerformanceDetailMapper.delete(Wrappers.lambdaQuery(TemplatePerformanceDetail.class).eq(TemplatePerformanceDetail::getTemplatePerformanceId, id));
        return 1;
    }
}
