
package localhost.soap.envialia.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder = { "vCodTipoEst", "dFecHoraAlta" })
@XmlRootElement(name = "ENV_ESTADOS")
public class REnvioEstadoPojo {
    
    protected String vCodTipoEst;
    protected String dFecHoraAlta;
    
    
    public String getvCodTipoEst() {
        return vCodTipoEst;
    }
    
    @XmlAttribute(name = "V_COD_TIPO_EST")
    public void setvCodTipoEst(String vCodTipoEst) {
        this.vCodTipoEst = vCodTipoEst;
    }
    
    public String getdFecHoraAlta() {
        return dFecHoraAlta;
    }
    
    @XmlAttribute(name = "D_FEC_HORA_ALTA")
    public void setdFecHoraAlta(String dFecHoraAlta) {
        this.dFecHoraAlta = dFecHoraAlta;
    }


}
