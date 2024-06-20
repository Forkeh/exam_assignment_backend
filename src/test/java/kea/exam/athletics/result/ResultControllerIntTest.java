package kea.exam.athletics.result;

import kea.exam.athletics.discipline.dto.DisciplineResponseSmallDTO;
import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import kea.exam.athletics.result.dto.ResultRequestDTO;
import kea.exam.athletics.result.dto.ResultResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ResultControllerIntTest {

    @LocalServerPort
    private int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    //TODO: How to test Pageables?
//    @Test
//    void getAllResults() {
//
//        ResponseEntity<Page<ResultResponseDTO>> response = restClient.get()
//                .uri("/results?pageIndex=0&pageSize=10")
//                .retrieve()
//                .toEntity(new ParameterizedTypeReference<Page<ResultResponseDTO>>() {
//                });
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }

    @Test
    void createResult() {
        ResultRequestDTO resultRequestDTO = new ResultRequestDTO(1L, 1L, "100");

        ResultResponseDTO response = restClient.post()
                .uri("/results")
                .body(resultRequestDTO)
                .retrieve()
                .toEntity(ResultResponseDTO.class)
                .getBody();

        assert response != null;
        assertEquals(22, response.id());
        assertEquals(1, response.participant()
                .id());
        assertEquals("100", response.result());

    }

    @Test
    void updateResult() {
        ResultRequestDTO resultRequestDTO = new ResultRequestDTO(1L, 1L, "999");

        ResultResponseDTO response = restClient.put()
                .uri("/results/1")
                .body(resultRequestDTO)
                .retrieve()
                .toEntity(ResultResponseDTO.class)
                .getBody();

        assert response != null;
        assertEquals(1, response.id());
        assertEquals(1, response.participant()
                .id());
        assertEquals("999", response.result());
    }

    @Test
    void deleteResultById() {
        ResultResponseDTO response = restClient.delete()
                .uri("/results/1")
                .retrieve()
                .toEntity(ResultResponseDTO.class)
                .getBody();

        assert response != null;
        assertEquals(1, response.id());
        assertEquals(1, response.participant()
                .id());
        assertEquals("10.500", response.result());

        // Attempt to retrieve the result after deletion
        try {
            restClient.get()
                    .uri("/results/1")
                    .retrieve()
                    .toEntity(ResultResponseDTO.class)
                    .getBody();
            fail("Expected an exception due to participant not found");
        } catch (Exception e) {
            // Expected exception due to result not found
        }
    }
}