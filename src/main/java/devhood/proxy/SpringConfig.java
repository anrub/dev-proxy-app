package devhood.proxy;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	
	@Bean
	public ServletRegistrationBean proxy(ProxyConfig config) {
		ServletRegistrationBean r = new ServletRegistrationBean(new ProxyServlet(config));
		r.addInitParameter("proxyTo", config.getProxyTo());
		r.addInitParameter("prefix", "/");
		r.addInitParameter("maxThreads", "100");

		r.addUrlMappings("/*");

		return r;
	}
	
}
