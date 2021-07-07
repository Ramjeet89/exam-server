package com.exam.config;

import com.exam.constant.ExamPortalConstant;
import com.exam.service.Impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(ExamPortalConstant.AUTHORIZATION);
        System.out.println(requestTokenHeader);
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(ExamPortalConstant.BEARER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = this.jwtUtil.extractUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
                System.out.println(ExamPortalConstant.JWT_TOKEN_EXPIRED + e);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(ExamPortalConstant.ERROR);
            }
        } else {
            System.out.println(ExamPortalConstant.INVALID_TOKEN);
        }
        //Validate tokens
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         final  UserDetails userDetails =  this.userDetailsService.loadUserByUsername(username);
         if(this.jwtUtil.validateToken(jwtToken,userDetails)){
             //token is valid
             UsernamePasswordAuthenticationToken usernamePasswordAuthentication=
                     new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
             usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
         }
        }else {
            System.out.println(ExamPortalConstant.TOKEN_NOT_VALID);
        }
        filterChain.doFilter(request,response);
    }
}
