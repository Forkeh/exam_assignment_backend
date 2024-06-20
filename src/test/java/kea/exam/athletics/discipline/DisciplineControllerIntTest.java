package kea.exam.athletics.discipline;

import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DisciplineControllerIntTest {

    @LocalServerPort
    private int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    @Test
    void getAllDisciplines() {

        ResponseEntity<List<DisciplineResponseSmallDTO>> response = restClient.get()
                .uri("/disciplines")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        assert response.getBody() != null;
        assertEquals(9, response.getBody()
                .size());
    }

    @Test
    void getDisciplineById() {
        Discipline discipline = restClient.get()
                .uri("/disciplines/1")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert discipline != null;
        assertEquals("100m Sprint", discipline.getName());
    }
}