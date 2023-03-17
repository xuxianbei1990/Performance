package college.performance.controller;

import college.performance.model.MyPerformance;
import college.performance.model.MyPerformanceDetail;
import college.performance.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 11:45
 * Version:V1.0
 */
@RestController
@RequestMapping("performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @GetMapping("list")
    public List<MyPerformance> list(@RequestParam Integer userId) {
        return performanceService.list(userId);
    }

    @GetMapping("detail")
    public List<MyPerformanceDetail> detail(@RequestParam Integer id){
        return performanceService.detail(id);
    }

    @PostMapping("add")
    public Integer add(@RequestBody MyPerformance myPerformance) {
        return performanceService.add(myPerformance);
    }

    @PostMapping("update/detail")
    public Integer updateDetail(@RequestBody List<MyPerformanceDetail> list){
        return performanceService.updateDetail(list);
    }

    @PostMapping("update")
    public Integer update(@RequestBody MyPerformance myPerformance) {
        return performanceService.update(myPerformance);
    }

    @GetMapping("commit")
    public Integer commit(@RequestParam Integer id){
        return performanceService.commit(id);
    }
}
