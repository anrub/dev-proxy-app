package devhood.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProxyConfig {

	@Value("${error:0}")
	private Integer error;

	@Value("${block:false}")
	private boolean block;

	@Value("${wait:0}")
	private Integer wait;

	@Value("${proxyTo}")
	private String proxyTo;

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public Integer getWait() {
		return wait;
	}

	public void setWait(Integer wait) {
		this.wait = wait;
	}

	public String getProxyTo() {
		return proxyTo;
	}

	public void setProxyTo(String proxyTo) {
		this.proxyTo = proxyTo;
	}

}
