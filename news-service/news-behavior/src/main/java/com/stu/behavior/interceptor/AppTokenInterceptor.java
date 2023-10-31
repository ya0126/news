package com.stu.behavior.interceptor;

import com.stu.model.wemedia.pojos.WmUser;
import com.stu.utils.common.JwtUtil;
import com.stu.utils.common.WmThreadLocalUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class AppTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //得到header中的信息
        String token = request.getHeader("token");
        Optional<String> optional = Optional.ofNullable(token);
        if (optional.isPresent()) {
            Claims claimsBody = JwtUtil.getClaimsBody(token);
            int result = JwtUtil.verifyToken(claimsBody);
            if (result != 1 && result != 2) {
                Integer userId = claimsBody.get("id", Integer.class);
                //把用户id存入ThreadLocal中
                WmUser wmUser = new WmUser();
                wmUser.setId(userId);
                WmThreadLocalUtil.setUser(wmUser);
                log.info("appTokenFilter设置用户信息到ThreadLocal中...");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        WmThreadLocalUtil.clear();
    }
}
