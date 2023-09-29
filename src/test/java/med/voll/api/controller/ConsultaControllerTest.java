package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

	@Autowired
	private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;

	@MockBean
	private AgendaDeConsultaService agendaDeConsultaService;

	ConsultaControllerTest() {
	}

	@Test
	@DisplayName("deberia retornar estado http 200 cuando los datos ingresados sean validos")
	@WithMockUser
	void agendarEscenario1() throws Exception {
		// given
		var consulta = MockMvcRequestBuilders.post("/consultas");
		var fecha = LocalDateTime.now().plusHours(1);
		var especialidad = Especialidad.CARDIOLOGIA;
		var datos = new DatosDetalleConsulta(null, 1L, 2L, fecha);
		//when
		Mockito.when(agendaDeConsultaService.agendar(Mockito.any())).thenReturn(datos);
		var response = mvc.perform(consulta
						.contentType(MediaType.APPLICATION_JSON)
						.content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(1L,2L,fecha,especialidad)).getJson()))
				.andReturn().getResponse();
		//then
		AssertionsForClassTypes.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		var jsonEsperado = detalleConsultaJacksonTester.write(datos).getJson();
		AssertionsForClassTypes.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

	@Test
	@DisplayName("deberia retornar estado http 400 cuando los datos ingresados sean invalidos")
	@WithMockUser
	void agendarEscenario2() throws Exception {
		// given
		var consulta = MockMvcRequestBuilders.post("/consultas");
		//when
		var response = mvc.perform(consulta).andReturn().getResponse();
		//then
		AssertionsForClassTypes.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
}