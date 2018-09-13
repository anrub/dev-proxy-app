package devhood.proxy;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.proxy.ProxyServlet.Transparent;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proxy-Servlet um REST-Requests an die entsprechenden Backends weiterzuleiten.
 * 
 * Requests können künstlich verlangsamt bzw. fehlerhaft beantwortet werden.
 * (Env-Variable wait (msec Warten) bzw. error (Statuscode der Response)
 * 
 * Flag block=true, wenn der Response sehr lange andauern soll. (d.h.
 * readTimeout greift nicht, da erste Bytes kommen, aber nicht komplett)
 * 
 * Das ganze basiert auf dem Jetty-ProxyServlet.
 */
public class ProxyServlet extends Transparent {

	private static final long serialVersionUID = -3143373839320765866L;

	private static final Logger LOG = LoggerFactory.getLogger(ProxyServlet.class);

	private String proxyTo;

	private ProxyConfig config;
	
	
	public ProxyServlet(ProxyConfig config) {
		this.config = config;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		this.proxyTo = config.getInitParameter("proxyTo");
		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		int wait = config.getWait();
		boolean block = config.isBlock();
		if (block) {
			blockAndSendBytes(arg1, wait);
		} else {
			wait(wait);
		}
		if (!block && config.getError() != 0) {
			arg1.sendError(config.getError());
		} else {
			super.service(arg0, arg1);
		}
	}


	private void blockAndSendBytes(HttpServletResponse arg1, int wait) throws IOException {
		for (int i = 0; i < (wait / 1000); i++) {
			arg1.getOutputStream().write(' ');
			arg1.getOutputStream().flush();
			arg1.flushBuffer();
			wait(1000);
		}
	}

	private void wait(int wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return a new HttpClient instance
	 */
	@Override
	protected HttpClient newHttpClient() {
		HttpClient client;
		if (proxyTo != null && proxyTo.startsWith("https")) {
			SslContextFactory sslContextFactory = new SslContextFactory();
			client = new HttpClient(sslContextFactory);
		} else {
			client = super.newHttpClient();
		}

		return client;
	}
}