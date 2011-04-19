package it.eng.spagobi.studio.core.bo;

/** A server definition
 * 
 * @author gavardi
 *
 */
public class Server {

	String name;
	String url;
	String user;
	String password;
	boolean active;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Server(String name, String url, String user, String password, boolean active) {
		super();
		this.name = name;
		this.url = url;
		this.user = user;
		this.password = password;
		this.active = active;
	}
	
	
	
	
}

