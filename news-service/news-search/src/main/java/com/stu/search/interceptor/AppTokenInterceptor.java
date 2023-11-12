package com.stu.search.interceptor;

import com.stu.common.constants.SecurityConstants;
import com.stu.model.user.pojos.ApUser;
import com.stu.utils.common.AppThreadLocalUtil;
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
        String userId = request.getHeader(SecurityConstants.DETAILS_USER_ID);
        Optional<String> optional = Optional.ofNullable(userId);
        if (optional.isPresent()) {
            ApUser apUser = new ApUser();
            Integer id = Integer.valueOf(userId);
            apUser.setId(id);
            AppThreadLocalUtil.setUser(apUser);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AppThreadLocalUtil.clear();
    }
}
