
package localhost.soap.envialia.pojo;

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
 *         &lt;element name="Result" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="strCodAgeOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strCodCliOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strCod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strNom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strCodCR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strTipo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strError" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strSesion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strURLDetSegEnv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
		"result",
		"strCodAgeOut",
		"strCodCliOut",
		"strCod",
		"strNom",
		"strCodCR",
		"strTipo",
		"strVersion",
		"strError",
		"strSesion",
		"strURLDetSegEnv"
})
@XmlRootElement(name = "LoginWSService___LoginDep2Response")
public class LoginDep2ResponsePojo {

	@XmlElement(name = "Result")
	protected boolean result;
	@XmlElement(required = true)
	protected String strCodAgeOut;
	@XmlElement(required = true)
	protected String strCodCliOut;
	@XmlElement(required = true)
	protected String strCod;
	@XmlElement(required = true)
	protected String strNom;
	@XmlElement(required = true)
	protected String strCodCR;
	@XmlElement(required = true)
	protected String strTipo;
	@XmlElement(required = true)
	protected String strVersion;
	@XmlElement(required = true)
	protected String strError;
	@XmlElement(required = true)
	protected String strSesion;
	@XmlElement(required = true)
	protected String strURLDetSegEnv;

	/**
	 * Gets the value of the result property.
	 * 
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * Sets the value of the result property.
	 * 
	 */
	public void setResult(boolean value) {
		this.result = value;
	}

	/**
	 * Gets the value of the strCodAgeOut property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrCodAgeOut() {
		return strCodAgeOut;
	}

	/**
	 * Sets the value of the strCodAgeOut property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrCodAgeOut(String value) {
		this.strCodAgeOut = value;
	}

	/**
	 * Gets the value of the strCodCliOut property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrCodCliOut() {
		return strCodCliOut;
	}

	/**
	 * Sets the value of the strCodCliOut property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrCodCliOut(String value) {
		this.strCodCliOut = value;
	}

	/**
	 * Gets the value of the strCod property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrCod() {
		return strCod;
	}

	/**
	 * Sets the value of the strCod property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrCod(String value) {
		this.strCod = value;
	}

	/**
	 * Gets the value of the strNom property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrNom() {
		return strNom;
	}

	/**
	 * Sets the value of the strNom property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrNom(String value) {
		this.strNom = value;
	}

	/**
	 * Gets the value of the strCodCR property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrCodCR() {
		return strCodCR;
	}

	/**
	 * Sets the value of the strCodCR property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrCodCR(String value) {
		this.strCodCR = value;
	}

	/**
	 * Gets the value of the strTipo property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrTipo() {
		return strTipo;
	}

	/**
	 * Sets the value of the strTipo property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrTipo(String value) {
		this.strTipo = value;
	}

	/**
	 * Gets the value of the strVersion property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrVersion() {
		return strVersion;
	}

	/**
	 * Sets the value of the strVersion property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrVersion(String value) {
		this.strVersion = value;
	}

	/**
	 * Gets the value of the strError property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrError() {
		return strError;
	}

	/**
	 * Sets the value of the strError property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrError(String value) {
		this.strError = value;
	}

	/**
	 * Gets the value of the strSesion property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrSesion() {
		return strSesion;
	}

	/**
	 * Sets the value of the strSesion property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrSesion(String value) {
		this.strSesion = value;
	}

	/**
	 * Gets the value of the strURLDetSegEnv property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getStrURLDetSegEnv() {
		return strURLDetSegEnv;
	}

	/**
	 * Sets the value of the strURLDetSegEnv property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setStrURLDetSegEnv(String value) {
		this.strURLDetSegEnv = value;
	}

}
