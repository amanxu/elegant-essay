package com.elegant.essay.filter;

import com.alibaba.fastjson.JSON;
import com.elegant.essay.config.JwtConfigProperties;
import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.utils.InitPermessionLimit;
import com.elegant.essay.utils.JwtHelper;
import com.elegant.essay.utils.ResultUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-08 10:10
 */
@Slf4j
public class AuthJwtFilter extends GenericFilterBean {

    private static String JWT_CLAIMS = "CLAIMS";
    private static String HTTP_OPTIONS = "OPTIONS";
    private static String TOKEN_BEARER = "bearer;";
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    /**
     * JWT 参数说明
     * claims（保留），它的含义就像是编程语言的保留字一样，属于JWT标准里面规定的一些claim。JWT标准里面定好的claim有：
     * iss(ISSUER)：代表这个JWT的签发主体；
     * sub(Subject)：代表这个JWT的主体，即它的所有人；
     * aud(Audience)：代表这个JWT的接收对象；
     * exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
     * nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
     * iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
     * jti(JWT ID)：是JWT的唯一标识。
     *
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        //等到请求头信息authorization信息
        final String authHeader = request.getHeader("AuthToken");
        // 校验当前uri是否需要鉴权
        if (ifLimitUri(request)) {
            chain.doFilter(req, res);
            return;
        }
        // 跨域请求
        if (HTTP_OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
            return;
        }
        // 正常鉴权处理
        if (authHeader == null || !authHeader.startsWith(TOKEN_BEARER)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        final String token = authHeader.substring(7);
        try {
            final Claims claims = JwtHelper.parseJwt(token, getJwtStaticConfig(request).getBase64Secret());
            if (claims == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            request.setAttribute(JWT_CLAIMS, claims);
        } catch (final Exception ex) {
            if (ex instanceof ExpiredJwtException) {
                Result filterReturn = ResultUtil.error(ErrorCodeEnum.JWT_EXPIRED_ERR.getCode(), ErrorCodeEnum.JWT_EXPIRED_ERR.getMsg());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(JSON.toJSONString(filterReturn));
            } else {
                log.error("AuthFilter DoFilter:{}", ex);
            }
            return;
        }
        chain.doFilter(req, res);
    }

    /**
     * 是否需要过滤
     *
     * @param request
     * @return
     */
    private boolean ifLimitUri(HttpServletRequest request) {
        String reqUri = request.getRequestURI();
        if (StringUtils.isBlank(getJwtStaticConfig(request).getStaticPath())) {
            throw new BusinessException("JWT static location not config. Please first config 'jwt.staticPath' param");
        }
        List<String> unLimitPath = getJwtStaticConfig(request).unLimitPath();
        for (String uri : unLimitPath) {
            if (pathMatcher.match(uri, reqUri)) {
                return true;
            }
        }
        for (String url : InitPermessionLimit.limitUrl) {
            if (url.equals(reqUri)) {
                return true;
            }
        }
        return false;
    }

    private JwtConfigProperties getJwtStaticConfig(HttpServletRequest request) {
        if (jwtConfigProperties == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            jwtConfigProperties = (JwtConfigProperties) factory.getBean("jwtConfigProperties");
        }
        return jwtConfigProperties;
    }
}