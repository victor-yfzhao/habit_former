package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.laorui_out.habit_former.bean.LoginBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.LoginMapper;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, UserBean> implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    public LoginResult login(String username, String password, HttpServletRequest request) {
        if(username.isEmpty() || password.length() < 8) {
            loginLog(LoginResult.INVALID_INPUT, request, username);
            return LoginResult.INVALID_INPUT;
        }
        UserBean user = baseMapper.selectByUsername(username);
        if(user == null) {
            loginLog(LoginResult.USERNAME_ERROR, request, username);
            return LoginResult.USERNAME_ERROR;
        }
        if(user.getPassword().equals(password)) {
            loginLog(LoginResult.SUCCESS, request, username);
            return LoginResult.SUCCESS;
        }
        loginLog(LoginResult.PASSWORD_ERROR, request, username);
        return LoginResult.PASSWORD_ERROR;
    }

    private void loginLog(LoginResult result, HttpServletRequest request, String username){

        LoginBean loginBean = new LoginBean();

        String ip = request.getHeader("x-forwarded-for");
        if(ip==null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip==null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip.equals("0:0:0:0:0:0:0:1")){
            ip="127.0.0.1";
        }
        System.out.println(ip);
        loginBean.setIP_Address(ip);

        String agentStr = request.getHeader("user-agent");
        UserAgent agent = UserAgent.parseUserAgentString(agentStr);
        Browser browser = agent.getBrowser();
        loginBean.setIE(browser.getName());

        OperatingSystem os = agent.getOperatingSystem();
        loginBean.setOS(os.getName());

        loginBean.setCreateDate(new java.sql.Date(System.currentTimeMillis()));
        loginBean.setLog_Content(result.toString());

        if(result == LoginResult.SUCCESS || result == LoginResult.PASSWORD_ERROR) {
            loginBean.setUserID(baseMapper.selectByUsername(username).getUserID());
        }

        loginMapper.insert(loginBean);

    }
}
