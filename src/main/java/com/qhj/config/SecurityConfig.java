package com.qhj.config;

import com.qhj.core.LoginValidateAuthenticationProvider;
import com.qhj.core.handler.LoginFailureHandler;
import com.qhj.core.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.Resource;

/**
 * 安全认证配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //自定义认证
    @Resource
    private LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;

    //登录成功handler
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    //登录失败handler
    @Resource
    private LoginFailureHandler loginFailureHandler;


    /**
     * 权限核心配置
     * @param http
     * @throws Exception
     */
    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //取消csrf防护
        //基础设置
        http.httpBasic()//配置HTTP基本身份验证
                .and()
                .authorizeRequests().antMatchers("/css/**","/js/**","/fonts/**","/img/**","/lib/**").permitAll() //静态资源不被拦截
                .and()
                .authorizeRequests().antMatchers("/admin/login/**").permitAll()  //用户登录注册页面不被拦截
                .and()
                .authorizeRequests().antMatchers("/admin/**").authenticated()
//                .authorizeRequests().anyRequest().authenticated()   //其余页面都需要认证（只有登录后才可以正常访问
                .and()
                .formLogin() //登录表单
                .loginPage("/admin/login")//登录页面url
                .loginProcessingUrl("/admin/login")//登录验证url
                .defaultSuccessUrl("/admin/index")//成功登录跳转
                .successHandler(loginSuccessHandler)//成功登录处理器
                .failureHandler(loginFailureHandler)//失败登录处理器
                .permitAll()//登录成功后有权限访问所有页面
                .and()
                .logout() //开启注销功能
                .logoutUrl("/logout") //注销路径
                .logoutSuccessUrl("/admin/login")
                .deleteCookies("JSESSIONID") //清除cookie
                .invalidateHttpSession(true) //使session失效
                .permitAll();

        //单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线
        //http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);

        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8"); filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
    }


    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置自定义认证
        auth.authenticationProvider(loginValidateAuthenticationProvider);
    }


}
