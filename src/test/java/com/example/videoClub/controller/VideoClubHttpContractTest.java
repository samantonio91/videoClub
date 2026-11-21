package com.example.videoClub.controller;

import com.example.videoClub.domain.dto.ClienteResponse;
import com.example.videoClub.domain.dto.JuegoResponse;
import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.Juego;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VideoClubHttpContractTest {
    @Autowired MockMvc mvc;

    private static final String CUSTOMER =
            "{\"documento\":\"ABC-123\",\"nombre\":\"Ana García\",\"telefono\":\"555-0101\",\"email\":\"ana@example.com\"}";
    private static final String GAME =
            "{\"codigo\":101,\"titulo\":\"Adventure\",\"precio\":199.0,\"plataforma\":\"PC\",\"genero\":\"RPG\"}";

    @Test
    void createCustomerReturnsStableDtoNotJpaGraph() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content(CUSTOMER))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Ana García"))
                .andExpect(jsonPath("$.documento").value("ABC-123"))
                .andExpect(jsonPath("$.alquileres").doesNotExist())
                .andExpect(jsonPath("$.clienteId").doesNotExist());
    }

    @Test
    void customerCanBeFilteredAndReadById() throws Exception {
        String content = mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content(CUSTOMER))
                .andReturn().getResponse().getContentAsString();
        long id = ((Number) com.jayway.jsonpath.JsonPath.read(content, "$.id")).longValue();

        mvc.perform(get("/api/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
        mvc.perform(get("/api/clientes").param("nombre", "ana").param("documento", "abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ana García"));
    }

    @Test
    void createGameReturnsStableDto() throws Exception {
        mvc.perform(post("/api/juegos").contentType(MediaType.APPLICATION_JSON).content(GAME))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.codigo").value(101))
                .andExpect(jsonPath("$.titulo").value("Adventure"))
                .andExpect(jsonPath("$.plataforma").value("PC"))
                .andExpect(jsonPath("$.productId").doesNotExist());
    }

    @Test
    void invalidCustomerBodyReturnsStructured400() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"  \",\"email\":\"bad\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.instance").value("/api/clientes"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void invalidGameFieldsReturn400() throws Exception {
        mvc.perform(post("/api/juegos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigo\":-1,\"titulo\":\"  \",\"precio\":-5,\"plataforma\":\"\",\"genero\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void malformedJsonAndBadIdReturnConsistent400() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        mvc.perform(get("/api/clientes/not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void missingCustomerReturnsConsistent404() throws Exception {
        mvc.perform(get("/api/clientes/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Cliente no encontrado"));
    }

    @Test
    void legacyRoutesRemainAvailable() throws Exception {
        mvc.perform(post("/api/save-cliente").contentType(MediaType.APPLICATION_JSON).content(CUSTOMER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana García"));
        mvc.perform(get("/api/get-client"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void responseMappersExposeOnlyScalarFields() {
        Cliente customer = new Cliente();
        customer.setClienteId(42L);
        customer.setNombre("Ana");
        ClienteResponse customerResponse = ClienteResponse.from(customer);
        assertEquals(42L, customerResponse.id());
        assertEquals("Ana", customerResponse.nombre());

        Juego game = new Juego();
        game.setProductId(11L);
        game.setTitulo("Adventure");
        JuegoResponse gameResponse = JuegoResponse.from(game);
        assertEquals(11L, gameResponse.id());
        assertEquals("Adventure", gameResponse.titulo());
    }
}
