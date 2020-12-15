package edu.bth.ma.passthebomb.server.config

import edu.bth.ma.passthebomb.server.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.logout.ForwardLogoutSuccessHandler


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig @Autowired constructor(private val customUserDetailsService: CustomUserDetailsService) :
    WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf()
            .disable() // required for post method testing, remove this in production
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/api/**")
            .permitAll()
            .anyRequest()
            .authenticated()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService<UserDetailsService>(customUserDetailsService)
            .passwordEncoder(encoder())
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun webAuthenticationDetailsSource(): WebAuthenticationDetailsSource {
        return WebAuthenticationDetailsSource()
    }

    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun forwardAuthenticationSuccessHandler(): ForwardAuthenticationSuccessHandler {
        return ForwardAuthenticationSuccessHandler("/api/login/success")
    }

    @Bean
    fun forwardLogoutSuccessHandler(): ForwardLogoutSuccessHandler {
        return ForwardLogoutSuccessHandler("/api/login/logout")
    }

    @Bean
    fun simpleUrlAuthenticationFailureHandler(): SimpleUrlAuthenticationFailureHandler {
        return SimpleUrlAuthenticationFailureHandler("/api/login/failure")
    }

}