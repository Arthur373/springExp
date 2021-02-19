package am.gitc.spring_exp.config;

import am.gitc.spring_exp.security.JwtTokenConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenConfigurer jwtTokenConfigurer;

    public SecurityConfig(JwtTokenConfigurer jwtTokenConfigurer) {
        this.jwtTokenConfigurer = jwtTokenConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/v1/auth/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                /** my apply start */
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .and()
                /** end*/
                .apply(jwtTokenConfigurer);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
