package college.performance.controller;

import college.performance.model.UserMain;
import college.performance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 11:10
 * Version:V1.0
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public UserMain login(@RequestParam String user, @RequestParam String pwd){
        return userService.login(user, pwd);
    }

    @PostMapping("register")
    public Integer register(@Validated @RequestBody UserMain userMain){
        return userService.register(userMain);
    }

    @PostMapping("update")
    public Integer update(@Validated @RequestBody UserMain userMain){
        return userService.update(userMain);
    }

    @GetMapping("login/list")
    public List<UserMain> loginList(@RequestParam String user){
        return userService.loginList(user);
    }

    @GetMapping("list")
    public List<UserMain> list(){
        return userService.list();
    }
}
