package med.voll.api.domain.consulta.validaciones.cancelacion;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;

public interface ValidadorDeCancelacion {
	public void validar(DatosCancelamientoConsulta datos);
}
