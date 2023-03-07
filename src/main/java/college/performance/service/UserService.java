package college.performance.service;

import college.performance.dao.UserMainMapper;
import college.performance.model.UserMain;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 11:12
 * Version:V1.0
 */
@Service
public class UserService {

    @Resource
    private UserMainMapper userMainMapper;

    public Integer login(String user, String pwd) {
        UserMain userMain = userMainMapper.selectOne(Wrappers.lambdaQuery(UserMain.class)
                .eq(UserMain::getUserName, user)
                .eq(UserMain::getUserPwd, pwd));
        if (userMain == null) {
            return 0;
        }
        return 1;
    }

    public Integer register(UserMain userMain) {
        UserMain userMain2 = userMainMapper.selectOne(Wrappers.lambdaQuery(UserMain.class)
                .eq(UserMain::getUserName, userMain.getUserName()));
        if (userMain2 == null) {
            return 0;
        }
        return userMainMapper.insert(userMain);
    }

    public List<UserMain> loginList(String user) {
        return userMainMapper.selectList(Wrappers.lambdaQuery(UserMain.class).eq(UserMain::getUserName, user));
    }

    public Integer update(UserMain userMain) {
        return userMainMapper.updateById(userMain);
    }
}
