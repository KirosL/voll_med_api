package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacientesSinConsulta implements ValidadorDeConsultas {

	@Autowired
	private ConsultaRepository repository;

	public void validar(DatosAgendarConsulta datos) {

		var primerHorario = datos.fecha().withHour(7);
		var ultimoHorario = datos.fecha().withHour(18);

		var pacienteConConsulta = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorario);
		//var despuesCierre = datos.fecha().getHour() > 19;
		if (pacienteConConsulta) {
			throw new ValidationException("Solo se puede una consulta por dia");
		}

	}
}
