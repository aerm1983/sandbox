package localhost.SoapAuxiliar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strCodAge" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strCodCli" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strDepartamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strPass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "strCodAge",
    "strCodCli",
    "strDepartamento",
    "strPass"
})
@XmlRootElement(name = "LoginWSService___LoginDep2")
public class PojoLoginDep2 {

    @XmlElement(required = true)
    protected String strCodAge;
    @XmlElement(required = true)
    protected String strCodCli;
    @XmlElement(required = true)
    protected String strDepartamento;
    @XmlElement(required = true)
    protected String strPass;

    /**
     * Gets the value of the strCodAge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCodAge() {
        return strCodAge;
    }

    /**
     * Sets the value of the strCodAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCodAge(String value) {
        this.strCodAge = value;
    }

    /**
     * Gets the value of the strCodCli property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCodCli() {
        return strCodCli;
    }

    /**
     * Sets the value of the strCodCli property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCodCli(String value) {
        this.strCodCli = value;
    }

    /**
     * Gets the value of the strDepartamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrDepartamento() {
        return strDepartamento;
    }

    /**
     * Sets the value of the strDepartamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDepartamento(String value) {
        this.strDepartamento = value;
    }

    /**
     * Gets the value of the strPass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrPass() {
        return strPass;
    }

    /**
     * Sets the value of the strPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrPass(String value) {
        this.strPass = value;
    }

}
