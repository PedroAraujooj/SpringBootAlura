package med.vol.api.controller;

import med.vol.api.domain.consulta.ConsultaService;
import med.vol.api.domain.consulta.DadosAgendamentoConsulta;
import med.vol.api.domain.consulta.DadosDetalhamentoConsulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsulta;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsulta;
    @MockBean
    private ConsultaService consultaService;


    @Test
    @DisplayName("Deve devolver 400 quando as informações não estão corretas")
    @WithMockUser
    void agendarCenario1() throws Exception {

        MockHttpServletResponse response = mvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver 200 quando as informações estão corretas")
    @WithMockUser
    void agendarCenario2() throws Exception {
        LocalDateTime data = LocalDateTime.now().plusHours(1);
        //Especialidade especialidade = Especialidade.GINECOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 44l, 21l, data);
        when(consultaService.agendar(any())).thenReturn(dadosDetalhamento);



        MockHttpServletResponse response = mvc.perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsulta.write(
                                        new DadosAgendamentoConsulta(44L, 21l, data, null)
                                ).getJson())
                )
                .andReturn().getResponse();
        var jsonEsperado = dadosDetalhamentoConsulta.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}