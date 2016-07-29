package alan.teste.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Message_1 implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String uri;
	
	private String message;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Message_1(String errorCode, String uri, String message) {
		super();
		this.errorCode = errorCode;
		this.uri = uri;
		this.message = message;
	}
	
	public Message_1() {
		
	}
	

}
