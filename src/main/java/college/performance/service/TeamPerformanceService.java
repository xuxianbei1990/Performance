package college.performance.service;

import cn.hutool.core.collection.CollectionUtil;
import college.performance.dao.*;
import college.performance.model.*;
import college.performance.model.Vo.TeamPerformanceVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 14:11
 * Version:V1.0
 */
@Service
public class TeamPerformanceService {

    @Resource
    private TeamPerformanceMapper teamPerformanceMapper;

    @Resource
    private MyPerformanceMapper myPerformanceMapper;

    @Resource
    private TemplatePerformanceUserMapper templatePerformanceUserMapper;

    @Resource
    private UserMainMapper userMainMapper;
    @Resource
    private TemplateStepMapper templateStepMapper;

    public List<TeamPerformanceVo> list(Integer userid) {
        List<TeamPerformance> list = teamPerformanceMapper.selectList(Wrappers.lambdaQuery(TeamPerformance.class)
                .eq(TeamPerformance::getOwnerId, userid));

        List<Integer> integers = list.stream().map(TeamPerformance::getUserId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(integers)) {
            return new ArrayList<>();
        }
        List<TemplatePerformanceUser> performanceUsers = templatePerformanceUserMapper.selectList(Wrappers.lambdaQuery(TemplatePerformanceUser.class)
                .in(TemplatePerformanceUser::getUserId, integers));
        List<Integer> performanceIds = performanceUsers.stream().map(TemplatePerformanceUser::getPerformanceId).collect(Collectors.toList());
        List<MyPerformance> myPerformances = myPerformanceMapper.selectList(Wrappers.lambdaQuery(MyPerformance.class).in(MyPerformance::getId, performanceIds));
        List<UserMain> userMains = userMainMapper.selectList(Wrappers.lambdaQuery(UserMain.class).in(UserMain::getId, integers));
        List<TemplateStep> templateSteps = templateStepMapper.selectList(Wrappers.lambdaQuery());
        return performanceUsers.stream().map(templatePerformanceUser -> {
            TeamPerformanceVo vo = new TeamPerformanceVo();
            Optional<MyPerformance> optionalMyPerformance = myPerformances.stream().filter(t -> t.getId().equals(templatePerformanceUser.getPerformanceId())).findFirst();
            if (!optionalMyPerformance.isPresent()) {
                return null;
            }
            MyPerformance myPerformance = optionalMyPerformance.get();
            BeanUtils.copyProperties(myPerformance, vo);
            UserMain userMain = userMains.stream().filter(t -> t.getId().equals(templatePerformanceUser.getUserId())).findFirst().get();
            vo.setDepartment(userMain.getDepartment());
            vo.setTeamMemberName(userMain.getUserName());
            vo.setStep(templateSteps.stream().filter(t -> t.getStepValue().equals(myPerformance.getStep())).findFirst().get().getStepName());
            return vo;
        }).filter(t -> t != null).collect(Collectors.toList());
    }

    public Integer add(TeamPerformance teamPerformance) {
        return teamPerformanceMapper.insert(teamPerformance);
    }

    public Integer delete(Integer id) {
        return teamPerformanceMapper.deleteById(id);
    }

    public List<TeamPerformance> team() {
        List<TeamPerformance> result = teamPerformanceMapper.selectList(Wrappers.lambdaQuery(TeamPerformance.class).orderByDesc(TeamPerformance::getOwnerId));
        return result;
    }
}
