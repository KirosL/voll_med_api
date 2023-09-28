package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

import java.time.DayOfWeek;

public class HorarioDeFuncionamiento implements ValidadorDeConsultas {

	public void validar(DatosAgendarConsulta datos) {

		var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

		var antesAperturar = datos.fecha().getHour()<7;
		var despuesCierre = datos.fecha().getHour()>19;
		if (domingo || antesAperturar || despuesCierre) {
			throw new ValidationException("El horario de atención de la clinica es de lunes a sabado" +
					"de 7:00 a 19:00");
		}

	}
}
