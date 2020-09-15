package kr.co.fastcam.eatgo;

import kr.co.fastcam.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcam.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}") //application.yml에 있는거 불러옴
    private String secret;

    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil());

        http
                .cors().disable()
                //CORS는 2012년 이후에 출시된 거의 모든 브라우저 버전에 탑재된 보안 기능이다.
                .csrf().disable()
                //CSRF는 악의적인 웹사이트, 전자 메일, 블로그, 인스턴트 메시지 또는 프로그램으로 인해
                // 사용자의 웹 브라우저가 사용자가 인증 된 다른 신뢰할 수 있는 사이트에서
                // 원치 않는 작업을 수행 할 때 발생하는 공격 유형이다.
                .formLogin().disable()
                //build.gradle에서 security를 implement해주면 localhost:8080검색시 로그인 화면이 뜨게되는데
                //formLogin().disable(); 써주면 그 화면이 뜨지 않도록 해줌
                .headers().frameOptions().disable()
                //위에꺼만 쓰면 h2console 화면 안떠서 이거 추가해주는거
                .and()//초기화작업
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secret);
    }
}
