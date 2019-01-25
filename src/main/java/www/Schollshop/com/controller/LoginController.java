package www.Schollshop.com.controller;

import org.apache.commons.lang.enums.Enum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import www.Schollshop.com.entity.ConstantForSuperAdmin;
import www.Schollshop.com.entity.HeadLine;
import www.Schollshop.com.entity.LocalAuth;
import www.Schollshop.com.service.HeadLineService;
import www.Schollshop.com.service.LocalAuthService;
import www.Schollshop.com.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Controller
@RequestMapping(value= "Login")
public class LoginController {
    @Autowired
    private LocalAuthService localAuthService;
    @RequestMapping("/login")
    public  String  getLogin(){
        return  "login/login";
    }


    @RequestMapping(value = "/logincheck",method =RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>logincheck(HttpServletRequest request){
        Map<String,Object>map=new HashMap<>();
           String userName=HttpServletRequestUtil.getString(request,"userName");
           String passWord=HttpServletRequestUtil.getString(request,"password");
           if(userName!=null&&passWord!=null){
               passWord= www.Schollshop.com.util.MD5.getMd5(passWord);
               LocalAuth localAuth=localAuthService.getLocalAuthByUserNameAndPwd(userName,passWord);
              if(localAuth!=null){
                  map.put("success",true);
                  request.getSession().setAttribute("userName",userName);
                  request.getSession().setAttribute("passWord",passWord);
              }
              else{
                  map.put("success",false);
              }
              return  map;
           }
         return  null;
    }

    @RequestMapping("/gotoMain")
    public  String  gotoMain(){
        return "home/home";
    }

    @RequestMapping("/gotoDefault")
    public  String  gotoDefault(){
        return  "login/default";
    }

    @RequestMapping("/gotoHeadLinemanage")
    public  String  gotoHeadLinemanage(){
        return  "headlinemange/headlinelist";
    }


}
