package alan.teste.entities;

import alan.teste.filters.Numeracao;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Message implements Serializable {

    private String errorCode;

    private String uri;

    private String message;

    private String parameter;
    
    private String type;
    
    private String typeName;

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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Message(String errorCode, String uri, String message) {
        super();
        this.errorCode = errorCode;
        this.uri = uri;
        this.message = message;
    }
    public Message(String errorCode, String uri, String message, String parameter) {
        super();
        this.errorCode = errorCode;
        this.uri = uri;
        this.message = message;
        this.parameter = parameter;
    }

    public Message(String errorCode, String uri, String message, String parameter, String type, String typeName) {
        this.errorCode = errorCode;
        this.uri = uri;
        this.message = message;
        this.parameter = parameter;
        this.type = type;
        this.typeName = typeName;
    }
    
    

    public Message() {

    }

}
