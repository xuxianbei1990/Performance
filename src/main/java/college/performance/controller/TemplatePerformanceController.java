package college.performance.controller;

import college.performance.model.Dto.TemplatePerformanceDto;
import college.performance.model.TemplatePerformanceMain;
import college.performance.service.TemplatePerformanceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 17:57
 * Version:V1.0
 */
@RequestMapping("template/performance")
@RestController
public class TemplatePerformanceController {

    @Resource
    private TemplatePerformanceService templatePerformanceService;

    @GetMapping("list")
    public List<TemplatePerformanceMain> list() {
        return templatePerformanceService.list();
    }

    @PostMapping("add")
    public Integer add(@RequestBody TemplatePerformanceDto templatePerformanceMain) {
        return templatePerformanceService.add(templatePerformanceMain);
    }

    @PostMapping("update")
    public Integer update(@RequestBody TemplatePerformanceDto templatePerformanceMain) {
        return templatePerformanceService.update(templatePerformanceMain);
    }

    @GetMapping("delete")
    public Integer delete(@RequestParam Integer id) {
        return templatePerformanceService.delete(id);
    }
}
