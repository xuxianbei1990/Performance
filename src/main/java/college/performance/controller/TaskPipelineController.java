package college.performance.controller;

import college.performance.model.TaskPipeline;
import college.performance.schedule.TaskGenerate;
import college.performance.service.TaskPipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 15:57
 * Version:V1.0
 */
@RestController
@RequestMapping("task/pipeline")
public class TaskPipelineController {

    @Autowired
    private TaskPipelineService taskPipelineService;

    @Autowired
    private TaskGenerate taskGenerate;

    @PostMapping("add")
    public Integer add(@RequestBody TaskPipeline taskPipeline) {
        return taskPipelineService.add(taskPipeline);
    }

    @GetMapping("list")
    public List<TaskPipeline> list() {
        return taskPipelineService.list();
    }

    @GetMapping("delete")
    public Integer delete(@RequestParam Integer id) {
        return taskPipelineService.delete(id);
    }

    @GetMapping("send")
    public Integer send() {
        taskGenerate.execute();
        return 1;
    }
}
