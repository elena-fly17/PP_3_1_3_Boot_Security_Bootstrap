package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

@Configuration // гикбрейнс сказал, что эту аннотац. можно убрать, она уже в @EnableWebSecurity
@EnableWebSecurity // аннотация говорит, что этот класс отвечает за security-конфигурацию
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // возможно, в поле и сетере лучше указать не название класса,
    // а название интерфейса, который он имплементирует
    // хотя гикбрейнс указал название класса
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    // НАДО БЫ, ЖЕЛАТЕЛЬНО ИСПОЛЬЗОВАТЬ
    // может, к тому же, через сетер его внедрить?..
    @Autowired
    SuccessUserHandler successUserHandler;

    // настройка доступа на различ. страницы
    // может, в метод доб. строку "antMatchers("/user").authenticated()" -
    // чтобы на стр. профиля могли заходить только аутентифицированные юзеры -
    // так в видео гикбрейнс (11 м 40 с) - не уверена, что это нужно
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // в условии задачи указан урл "/admin/" - возможно, нужно убрать звездочки
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/{id}").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                // в строке ниже внедряется SuccessUserHandler - но есть др. способ внедрения -
                // с исполь-ем аннотации @Bean - пример по ссылке -
                // https://www.javadevjournal.com/spring/spring-security-success-handler/
                // по-моему, способф в коде и по ссылке - идентичны - просто разн. форма записи
                .successHandler(successUserHandler)
                .and()
                .logout() // не уверена, что эта строка нужна, и что она правильная
                .permitAll(); // разлогиниться могут все - не уверена, что строка нужна
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);

        return daoAuthenticationProvider;
    }
}