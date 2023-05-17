package learn.htdbank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // This method allows configuring web based security for specific http requests.

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // we're not using HTML forms in our app
        //so disable CSRF (Cross Site Request Forgery)
        http.csrf().disable();

        // this configures Spring Security to allow
        //CORS related requests (such as preflight checks)
        http.cors();

        // the order of the antMatchers() method calls is important
        // as they're evaluated in the order that they're added
        //anyone can do a GET, but must be logged in for other requests
        http.authorizeRequests().antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/api/customer", "/api/customer/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/employee", "/api/employee/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/transaction", "/api/transaction/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/account", "/api/account/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/bank", "/api/bank/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/card", "/api/card/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/customer").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/employee").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/transaction").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/account").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/bank").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/card").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/customer/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/employee/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/account/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/transaction/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/bank/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/card/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/customer/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/employee/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/transaction/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/account/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/bank/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/card/*").hasRole("ADMIN");

        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/create_account").permitAll()
                .antMatchers("/refresh_token").authenticated()
                .antMatchers("/**").denyAll()
                // if we get to this point, let's deny all requests
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // Configure CORS globally versus
        // controller-by-controller.
        // Can be combined with @CrossOrigin.
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }
}
