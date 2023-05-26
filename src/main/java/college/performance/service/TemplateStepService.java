package college.performance.service;

import college.performance.dao.TemplateStepMapper;
import college.performance.model.TemplateStep;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/17
 * Time: 11:49
 * Version:V1.0
 */
@Service
public class TemplateStepService {

    @Resource
    private TemplateStepMapper templateStepMapper;

    public List<TemplateStep> list() {
        return templateStepMapper.selectList(Wrappers.lambdaQuery(TemplateStep.class)
                .ne(TemplateStep::getStepValue, -1));
    }


    public Integer add(TemplateStep templateStep) {
        templateStep.setId(null);
        return templateStepMapper.insert(templateStep);
    }

    public Integer update(TemplateStep templateStep) {
        return templateStepMapper.updateById(templateStep);
    }

}
