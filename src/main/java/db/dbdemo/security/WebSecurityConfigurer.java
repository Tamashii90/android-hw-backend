package db.dbdemo.security;

import db.dbdemo.repository.VehiclesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    JwtFilter filter;
    @Autowired
    VehiclesRepo vehiclesRepo;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT driver, pluged_number, TRUE from vehicles WHERE driver = ?")
                .authoritiesByUsernameQuery("SELECT driver, 'USER' from vehicles WHERE driver = ?")
                .passwordEncoder(getNoEncoder())
                .and()
                .jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, TRUE from admins WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, 'ADMIN' from admins WHERE username = ?")
                .passwordEncoder(getEncoder());
//        auth.userDetailsService(new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                MyUser dbUser = userRepository.findByEmail(username).orElse(null);
//                if (dbUser != null) {
//                    return new User(dbUser.getEmail(), dbUser.getPassword(), null);
//                }
//                return null;
//            }
//        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/api/register", "/api/login", "/api/hello").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/violations-log/*").authenticated()
                .mvcMatchers(HttpMethod.GET, "/api/violations-log/user/*").authenticated()
                .mvcMatchers("/api/violations-log/**", "/api/vehicles/*").hasAuthority("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/api/violations").permitAll()
                .mvcMatchers("/api/**").authenticated()
                .mvcMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    PasswordEncoder getNoEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
