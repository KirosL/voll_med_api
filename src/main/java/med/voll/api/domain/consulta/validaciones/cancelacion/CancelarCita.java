package med.voll.api.domain.consulta.validaciones.cancelacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class CancelarCita implements ValidadorDeCancelacion {
	@Override
	public void validar(DatosCancelamientoConsulta datos) {
		var ahora = LocalTime.now();
		var despues = datos.fecha();

		var diferencia = Duration.between(ahora, despues).toHours() < 24;

		if (diferencia) {
			throw new ValidationException("No se puede cancelar la cita medica debido a que solo" +
					" se puede cancelar" +
					" con 24 horas de anticipación.");
		}
	}
}
