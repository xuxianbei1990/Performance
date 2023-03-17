package college.performance.controller;

import college.performance.model.TemplateStep;
import college.performance.service.TemplateStepService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/17
 * Time: 11:47
 * Version:V1.0
 */
@RequestMapping("template/step")
@RestController
public class TemplateStepController {

    @Autowired
    private TemplateStepService templateStepService;

    @GetMapping("list")
    public List<TemplateStep> list() {
        return templateStepService.list();
    }

    @PostMapping("add")
    public Integer add(@RequestBody TemplateStep templateStep){
        return templateStepService.add(templateStep);
    }

    @PostMapping("update")
    public Integer update(@RequestBody TemplateStep templateStep) {
        return templateStepService.update(templateStep);
    }
}
