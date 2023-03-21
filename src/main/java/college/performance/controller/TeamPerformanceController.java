package college.performance.controller;

import college.performance.model.TeamPerformance;
import college.performance.model.TemplateStep;
import college.performance.model.Vo.TeamPerformanceVo;
import college.performance.service.TeamPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 14:12
 * Version:V1.0
 */
@RequestMapping("team/performance")
@RestController
public class TeamPerformanceController {

    @Autowired
    private TeamPerformanceService teamPerformanceService;

    @GetMapping("list")
    public List<TeamPerformanceVo> list(@RequestParam Integer userid) {
        return teamPerformanceService.list(userid);
    }

    @GetMapping("team")
    public List<TeamPerformance> team() {
        return teamPerformanceService.team();
    }

    @PostMapping("add")
    public Integer add(@RequestBody TeamPerformance teamPerformance) {
        return teamPerformanceService.add(teamPerformance);
    }

    @GetMapping("delete")
    public Integer delete(@RequestParam Integer id) {
        return teamPerformanceService.delete(id);
    }

}
