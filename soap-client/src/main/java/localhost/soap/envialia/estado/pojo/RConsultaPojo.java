
package localhost.soap.envialia.estado.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


// @XmlType(propOrder = { "vCodTipoEst", "dFecHoraAlta" })
@XmlRootElement(name = "CONSULTA")
public class RConsultaPojo {

	protected List<REnvioEstadoPojo> Estados;

	public List<REnvioEstadoPojo> getEstados() {
		return Estados;
	}

	@XmlElement(name = "ENV_ESTADOS")
	public void setEstados(List<REnvioEstadoPojo> estados) {
		Estados = estados;
	}


}
