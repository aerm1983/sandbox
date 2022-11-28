
package localhost.SoapMinAuxiliar;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "ENV_ESTADOS")
public class PojoEnvioEstado {

    protected String iId;
    protected String vCodTipoEst;
    protected String dFecHoraAlta;
	
    @XmlAttribute(name = "I_ID")
    public String getiId() {
		return iId;
	}
	public void setiId(String iId) {
		this.iId = iId;
	}
	
	@XmlAttribute(name = "V_COD_TIPO_EST")
	public String getvCodTipoEst() {
		return vCodTipoEst;
	}
	public void setvCodTipoEst(String vCodTipoEst) {
		this.vCodTipoEst = vCodTipoEst;
	}
	
	@XmlAttribute(name = "D_FEC_HORA_ALTA")
	public String getdFecHoraAlta() {
		return dFecHoraAlta;
	}
	public void setdFecHoraAlta(String dFecHoraAlta) {
		this.dFecHoraAlta = dFecHoraAlta;
	}

}
