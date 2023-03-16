package college.performance.service;

import college.performance.dao.MyPerformanceDetailMapper;
import college.performance.dao.MyPerformanceMapper;
import college.performance.model.MyPerformance;
import college.performance.model.MyPerformanceDetail;
import college.performance.model.TaskPipeline;
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

    @Resource
    private MyPerformanceDetailMapper myPerformanceDetailMapper;

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
        taskPipeline.setStep(myPerformance.getStep());
        TaskPipeline nextTask = taskPipelineService.nextTask(taskPipeline);
        myPerformance.setStep(nextTask.getStep());
        myPerformanceMapper.updateById(myPerformance);
        return 1;
    }

    public List<MyPerformanceDetail> detail(Integer id) {
        return myPerformanceDetailMapper.selectList(Wrappers.lambdaQuery(MyPerformanceDetail.class).eq(MyPerformanceDetail::getMyPerformanceId, id));
    }
}
