package org.laorui_out.habit_former.admin.controller;

import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.user.service.LoginResult;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Value("${admin.password}")
    private String password;

    @GetMapping("/admin/login")
    public ResponseMessage<LoginResult> login(String password) {
        if (password.equals(this.password)) {
            return new ResponseMessage<>(200, "admin login success", LoginResult.SUCCESS);
        }
        return new ResponseMessage<>(400, "admin login failed", LoginResult.PASSWORD_ERROR);
    }

}
