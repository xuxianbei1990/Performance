package college.performance.service;

import college.performance.dao.TemplatePerformanceDetailMapper;
import college.performance.dao.TemplatePerformanceMainMapper;
import college.performance.model.Dto.TemplatePerformanceDto;
import college.performance.model.TemplatePerformanceMain;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

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
        return templatePerformanceMainMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceMain.class));
    }

    public Integer add(TemplatePerformanceDto templatePerformanceMain) {
        templatePerformanceMain.getTemplatePerformanceMain().setId(null);
        templatePerformanceMainMapper.insert(templatePerformanceMain.getTemplatePerformanceMain());
        templatePerformanceMain.getDetailList().forEach(templatePerformanceDetail -> {
            templatePerformanceDetail.setId(null);
            templatePerformanceDetailMapper.insert(templatePerformanceDetail);
        });
        return 1;
    }

    public Integer update(TemplatePerformanceDto templatePerformanceMain) {
        templatePerformanceMainMapper.updateById(templatePerformanceMain.getTemplatePerformanceMain());
        templatePerformanceMain.getDetailList().forEach(templatePerformanceDetail -> {
            templatePerformanceDetailMapper.updateById(templatePerformanceDetail);
        });
        return 1;
    }
}
