package college.performance.service;

import college.performance.dao.*;
import college.performance.model.*;
import college.performance.model.Vo.MyPerformanceVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Resource
    private TemplateStepMapper templateStepMapper;

    public List<MyPerformanceVo> list(Integer userId) {
        List<TemplatePerformanceUser> performanceUsers = templatePerformanceUserMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceUser.class)
                .eq(TemplatePerformanceUser::getUserId, userId));
        List<Integer> integers = performanceUsers.stream().map(TemplatePerformanceUser::getPerformanceId).collect(Collectors.toList());
        List<MyPerformance> myPerformances = new ArrayList<>();
        if (CollectionUtils.isEmpty(integers)) {
            myPerformances.addAll(myPerformanceMapper.selectList(Wrappers.lambdaQuery(MyPerformance.class).in(MyPerformance::getUserId, userId)));
        } else {
            myPerformances.addAll(myPerformanceMapper.selectList(Wrappers.lambdaQuery(MyPerformance.class).in(MyPerformance::getId, integers)));
        }
        List<TemplateStep> templateSteps = templateStepMapper.selectList(Wrappers.lambdaQuery());
        return myPerformances.stream().map(myPerformance -> {
            MyPerformanceVo myPerformanceVo = new MyPerformanceVo();
            BeanUtils.copyProperties(myPerformance, myPerformanceVo);
            myPerformanceVo.setStep(templateSteps.stream().filter(t -> t.getStepValue().equals(myPerformance.getStep())).findFirst().get().getStepName());
            return myPerformanceVo;
        }).collect(Collectors.toList());
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
        if (myPerformance.getStep() == -1) {
            myPerformance.setStatus(1);
            List<MyPerformanceDetail> myPerformanceDetails = myPerformanceDetailMapper.selectList(Wrappers.lambdaQuery(MyPerformanceDetail.class).eq(MyPerformanceDetail::getMyPerformanceId, id));
            BigDecimal sum = BigDecimal.ZERO;
            for (MyPerformanceDetail myPerformanceDetail : myPerformanceDetails) {
                sum = sum.add(myPerformanceDetail.getScore().multiply(new BigDecimal(myPerformanceDetail.getWeight()).divide(BigDecimal.valueOf(100))));
            }
            myPerformance.setScore(sum);
        }
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
