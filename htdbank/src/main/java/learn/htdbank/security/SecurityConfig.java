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
                .antMatchers(HttpMethod.GET, "/customer", "/customer/*").permitAll()
                .antMatchers(HttpMethod.GET, "/employee", "/employee/*").permitAll()
                .antMatchers(HttpMethod.GET, "/transaction", "/transaction/*").permitAll()
                .antMatchers(HttpMethod.GET, "/account", "/account/*").permitAll()
                .antMatchers(HttpMethod.GET, "/bank", "/bank/*").permitAll()
                .antMatchers(HttpMethod.GET, "/card", "/card/*").permitAll()
                .antMatchers(HttpMethod.POST, "/customer").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/employee").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/transaction").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/account").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/bank").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/card").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/customer/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/employee/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/account/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/transaction/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/bank/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/card/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/customer/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/employee/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/transaction/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/account/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/bank/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/card/*").hasRole("ADMIN");

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
