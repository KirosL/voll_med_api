package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas {


	public void validar(DatosAgendarConsulta datos) {

		var ahora = LocalDateTime.now();
		var horaDeConsulta = datos.fecha();

		// diferencia d ehora 30 minutos
		var diferenciaHora = Duration.between(ahora, horaDeConsulta).toMinutes() < 30;

		if (diferenciaHora) {
			throw new ValidationException("Las consultas deben ser programadas 30 minutos de anticipación");
		}

	}
}
