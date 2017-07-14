package alan.teste.entities;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uri;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parameter;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    
    
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String docNumber;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
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

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    


    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Message(Integer errorCode, String uri, String message) {
        super();
        this.errorCode = errorCode;
        this.uri = uri;
        this.message = message;
    }
    public Message(Integer errorCode, String uri, String message, String parameter) {
        super();
        this.errorCode = errorCode;
        this.uri = uri;
        this.message = message;
        this.parameter = parameter;
    }

    public Message(Integer errorCode, String uri, String message, String parameter, String type) {
        this.errorCode = errorCode;
        this.uri = uri;
        this.message = message;
        this.parameter = parameter;
        this.type = type;
    }
    
    

    public Message() {

    }

}
