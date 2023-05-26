package college.performance.schedule;

import college.performance.dao.*;
import college.performance.model.*;
import college.performance.service.PerformanceService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: xuxianbei
 * Date: 2023/3/7
 * Time: 10:48
 * Version:V1.0
 */
@Service
public class TaskGenerate {

    @Resource
    private TemplatePerformanceMainMapper templatePerformanceMainMapper;

    @Resource
    private TemplatePerformanceDetailMapper templatePerformanceDetailMapper;

    @Resource
    private MyPerformanceDetailMapper myPerformanceDetailMapper;

    @Autowired
    private PerformanceService performanceService;

    @Resource
    private TaskPipelineMapper taskPipelineMapper;

    @Resource
    private TemplatePerformanceUserMapper templatePerformanceUserMapper;

    public TaskGenerate() {
    }

    @Scheduled(cron = "0 0 0 1 * ? *")
    public void execute() {
        /**
         * 1.查出模板数据
         * 2.向所有1一线发送绩效信息
         */
        //获取所有的模板信息
        List<TemplatePerformanceMain> mainList = templatePerformanceMainMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceMain.class));
        //获取所有的任务流信息
        List<TaskPipeline> userList = taskPipelineMapper.selectList(Wrappers.lambdaQuery(TaskPipeline.class).eq(TaskPipeline::getStep, 0));
        //获取所有的绩效明细信息
        List<TemplatePerformanceDetail> details = templatePerformanceDetailMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceDetail.class));
        mainList.forEach(templatePerformanceMain -> {
            //用steam流过滤获取任务流
            Optional<TaskPipeline> optional = userList.stream().filter(t -> t.getTemplatePerformanceId().equals(templatePerformanceMain.getId())).findFirst();
            if (!optional.isPresent()) {
                return;
            }
            TaskPipeline templatePerformanceUser = optional.get();
            //组装信息
            MyPerformance myPerformance = new MyPerformance();
            myPerformance.setActivityName(templatePerformanceMain.getTemplateName());
            myPerformance.setUserId(templatePerformanceUser.getUserId());
            myPerformance.setPeriod(LocalDateTime.now().getMonth().getValue() + "月");
            myPerformance.setYear(LocalDateTime.now().getYear() + "年");
            myPerformance.setScore(BigDecimal.ZERO);
            myPerformance.setStatus(0);
            myPerformance.setStep(0);
            myPerformance.setTemplatePerformanceId(templatePerformanceMain.getId());
            //给用户分配绩效
            performanceService.add(myPerformance);
            TemplatePerformanceUser templatePerformanceUser1 = new TemplatePerformanceUser();
            templatePerformanceUser1.setTemplatePerformanceId(templatePerformanceUser.getId());
            templatePerformanceUser1.setUserId(templatePerformanceUser.getUserId());
            templatePerformanceUser1.setPerformanceId(myPerformance.getId());
            //给用户绩效配置绩效模板
            templatePerformanceUserMapper.insert(templatePerformanceUser1);
            //通过steam流获取绩效明细
            List<TemplatePerformanceDetail> details1 = details.stream().filter(t -> t.getTemplatePerformanceId().equals(templatePerformanceMain.getId())).collect(Collectors.toList());
            details1.forEach(templatePerformanceDetail -> {
                MyPerformanceDetail myPerformanceDetail = new MyPerformanceDetail();
                BeanUtils.copyProperties(templatePerformanceDetail, myPerformanceDetail);
                myPerformanceDetail.setMyPerformanceId(myPerformance.getId());
                //给用户的分配绩效配置绩效明细
                myPerformanceDetailMapper.insert(myPerformanceDetail);
            });
        });
    }
}
