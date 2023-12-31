package com.stu.behavior.interceptor;

import com.stu.common.constants.SecurityConstants;
import com.stu.model.wemedia.pojos.WmUser;
import com.stu.utils.common.WmThreadLocalUtil;
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
            WmUser wmUser = new WmUser();
            Integer id = Integer.valueOf(userId);
            wmUser.setId(id);
            WmThreadLocalUtil.setUser(wmUser);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        WmThreadLocalUtil.clear();
    }
}
