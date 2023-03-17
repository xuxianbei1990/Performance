package college.performance.service;

import college.performance.dao.MyPerformanceDetailMapper;
import college.performance.dao.MyPerformanceMapper;
import college.performance.dao.TaskPipelineMapper;
import college.performance.dao.TemplatePerformanceUserMapper;
import college.performance.model.MyPerformance;
import college.performance.model.MyPerformanceDetail;
import college.performance.model.TaskPipeline;
import college.performance.model.TemplatePerformanceUser;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private TemplatePerformanceUserMapper templatePerformanceUserMapper;

    @Resource
    private TaskPipelineMapper taskPipelineMapper;

    public List<MyPerformance> list(Integer userId) {
        List<TemplatePerformanceUser> performanceUsers = templatePerformanceUserMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceUser.class)
                .eq(TemplatePerformanceUser::getUserId, userId));
        List<Integer> integers = performanceUsers.stream().map(TemplatePerformanceUser::getPerformanceId).collect(Collectors.toList());
        return myPerformanceMapper.selectList(Wrappers.lambdaQuery(MyPerformance.class).eq(MyPerformance::getId, integers));
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
        TaskPipeline taskPipeline1 = taskPipelineMapper.selectOne(Wrappers.lambdaQuery(TaskPipeline.class)
                .eq(TaskPipeline::getTemplatePerformanceId, myPerformance.getTemplatePerformanceId())
                .eq(TaskPipeline::getUserId, myPerformance.getUserId()));
        Assert.isTrue(taskPipeline1.getStep() == myPerformance.getStep(), "不可操作");
        TaskPipeline taskPipeline = new TaskPipeline();
        taskPipeline.setUserId(myPerformance.getUserId());
        taskPipeline.setTemplatePerformanceId(myPerformance.getTemplatePerformanceId());
        taskPipeline.setStep(myPerformance.getStep());
        TaskPipeline nextTask = taskPipelineService.nextTask(taskPipeline);
        myPerformance.setStep(nextTask.getStep());
        myPerformance.setUserId(nextTask.getUserId());
        myPerformanceMapper.updateById(myPerformance);
        return 1;
    }

    public List<MyPerformanceDetail> detail(Integer id) {
        return myPerformanceDetailMapper.selectList(Wrappers.lambdaQuery(MyPerformanceDetail.class).eq(MyPerformanceDetail::getMyPerformanceId, id));
    }

    public Integer updateDetail(List<MyPerformanceDetail> list) {
        list.forEach(myPerformanceDetail -> {
            myPerformanceDetailMapper.updateById(myPerformanceDetail);
        });
        return 1;
    }
}
