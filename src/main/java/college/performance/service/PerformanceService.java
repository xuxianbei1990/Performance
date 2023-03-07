package college.performance.service;

import college.performance.dao.MyPerformanceMapper;
import college.performance.dao.TemplatePerformanceUserMapper;
import college.performance.model.MyPerformance;
import college.performance.model.TaskPipeline;
import college.performance.model.TemplatePerformanceUser;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 13:51
 * Version:V1.0
 */
@Service
public class PerformanceService {

    @Resource
    private MyPerformanceMapper myPerformanceMapper;

    @Autowired
    private TaskPipelineService taskPipelineService;

    public List<MyPerformance> list(Integer userId) {
        return myPerformanceMapper.selectList(Wrappers.lambdaQuery(MyPerformance.class).eq(MyPerformance::getUserId, userId));
    }


    public Integer add(MyPerformance myPerformance) {
        myPerformance.setId(null);
        return myPerformanceMapper.insert(myPerformance);
    }

    public Integer update(MyPerformance myPerformance) {
        return myPerformanceMapper.updateById(myPerformance);
    }

    public Integer commit(Integer id) {
        MyPerformance myPerformance = myPerformanceMapper.selectById(id);
        TaskPipeline taskPipeline = new TaskPipeline();
        taskPipeline.setUserId(myPerformance.getUserId());
        taskPipeline.setTemplatePerformanceId(myPerformance.getTemplatePerformanceId());
        TaskPipeline nextTask = taskPipelineService.nextTask(taskPipeline);
        myPerformance.setStep(nextTask.getStep());
        myPerformanceMapper.updateById(myPerformance);
        return 1;
    }
}
