package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosCancelamientoConsulta(
		@NotNull
		Long id,
		@NotNull
		Consulta consulta,
		LocalDateTime fecha,
		@NotNull
		MotivoCancelacion motivo) {


}
