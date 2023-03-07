package college.performance.service;

import college.performance.dao.TaskPipelineMapper;
import college.performance.model.TaskPipeline;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 17:31
 * Version:V1.0
 */
@Service
public class TaskPipelineService {

    @Resource
    private TaskPipelineMapper taskPipelineMapper;

    public Integer add(TaskPipeline taskPipeline) {
        taskPipeline.setId(null);
        LambdaQueryWrapper<TaskPipeline> lambdaQueryWrapper = new QueryWrapper<TaskPipeline>()
                .select("MAX(step)").lambda();
        TaskPipeline taskPipeline1 = taskPipelineMapper.selectOne(
                lambdaQueryWrapper
                        .eq(TaskPipeline::getTemplatePerformanceId, taskPipeline.getTemplatePerformanceId())
                        .eq(TaskPipeline::getUserId, taskPipeline.getUserId()));
        if (taskPipeline1 != null) {
            taskPipeline.setStep(taskPipeline1.getStep() + 1);
        } else {
            taskPipeline.setStep(0);
        }
        return taskPipelineMapper.insert(taskPipeline);
    }

    public List<TaskPipeline> list() {
        return taskPipelineMapper.selectList(Wrappers.lambdaQuery(TaskPipeline.class));
    }

    public Integer delete(Integer id) {
        return taskPipelineMapper.deleteById(id);
    }

    public TaskPipeline nextTask(TaskPipeline taskPipeline) {
        List<TaskPipeline> list = taskPipelineMapper.selectList(Wrappers.lambdaQuery(TaskPipeline.class)
                .eq(TaskPipeline::getUserId, taskPipeline.getUserId())
                .eq(TaskPipeline::getTemplatePerformanceId, taskPipeline.getTemplatePerformanceId())
                .orderByAsc(TaskPipeline::getStep));
        boolean next = false;
        for (TaskPipeline pipeline : list) {
            if (pipeline.getStep() == taskPipeline.getStep()) {
                next = true;
                continue;
            }
            if (next) {
                return pipeline;
            }
        }
        if (next) {
            taskPipeline.setStep(-1);
            return taskPipeline;
        }
        return null;
    }
}
