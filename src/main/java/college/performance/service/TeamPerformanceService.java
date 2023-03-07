package college.performance.service;

import college.performance.dao.MyPerformanceMapper;
import college.performance.dao.TeamPerformanceMapper;
import college.performance.dao.UserMainMapper;
import college.performance.model.MyPerformance;
import college.performance.model.TeamPerformance;
import college.performance.model.UserMain;
import college.performance.model.Vo.TeamPerformanceVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
    private UserMainMapper userMainMapper;

    public List<TeamPerformanceVo> list(Integer userid) {
        List<TeamPerformance> list = teamPerformanceMapper.selectList(Wrappers.lambdaQuery(TeamPerformance.class)
                .eq(TeamPerformance::getOwnerId, userid));

        List<Integer> integers = list.stream().map(TeamPerformance::getUserId).collect(Collectors.toList());

        List<MyPerformance> myPerformances = myPerformanceMapper.selectList(Wrappers.lambdaQuery(MyPerformance.class).in(MyPerformance::getUserId, integers));
        List<UserMain> userMains = userMainMapper.selectList(Wrappers.lambdaQuery(UserMain.class).in(UserMain::getId, integers));

        return list.stream().map(teamPerformance -> {
            TeamPerformanceVo vo = new TeamPerformanceVo();
            MyPerformance myPerformance = myPerformances.stream().filter(t -> t.getUserId().equals(teamPerformance.getUserId())).findFirst().get();
            BeanUtils.copyProperties(myPerformance, vo);
            UserMain userMain = userMains.stream().filter(t -> t.getId().equals(teamPerformance.getUserId())).findFirst().get();
            vo.setDepartment(userMain.getDepartment());
            vo.setTeamMemberName(userMain.getUserName());
            vo.setId(teamPerformance.getId());
            return vo;
        }).collect(Collectors.toList());
    }

    public Integer add(TeamPerformance teamPerformance) {
        return teamPerformanceMapper.insert(teamPerformance);
    }

    public Integer delete(Integer id) {
        return teamPerformanceMapper.deleteById(id);
    }
}
