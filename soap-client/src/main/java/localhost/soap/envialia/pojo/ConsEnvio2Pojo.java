
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
 *         &lt;element name="strCodAgeCargo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="strAlbaran" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "strCodAgeCargo",
    "strAlbaran"
})
@XmlRootElement(name = "WebServService___ConsEnvio2")
public class ConsEnvio2Pojo {

    @XmlElement(required = true)
    protected String strCodAgeCargo;
    @XmlElement(required = true)
    protected String strAlbaran;

    /**
     * Gets the value of the strCodAgeCargo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCodAgeCargo() {
        return strCodAgeCargo;
    }

    /**
     * Sets the value of the strCodAgeCargo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCodAgeCargo(String value) {
        this.strCodAgeCargo = value;
    }

    /**
     * Gets the value of the strAlbaran property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAlbaran() {
        return strAlbaran;
    }

    /**
     * Sets the value of the strAlbaran property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAlbaran(String value) {
        this.strAlbaran = value;
    }

}
