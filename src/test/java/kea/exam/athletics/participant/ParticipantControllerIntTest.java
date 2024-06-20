package kea.exam.athletics.participant;

import kea.exam.athletics.participant.dto.ParticipantRequestDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseDTO;
import kea.exam.athletics.participant.dto.ParticipantResponseFullDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ParticipantControllerIntTest {

    @LocalServerPort
    private int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    @Test
    void getAllParticipants() {

        ResponseEntity<List<ParticipantResponseFullDTO>> response = restClient.get()
                .uri("/participants/all")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        assertEquals(41, response.getBody()
                .size());

    }

    @Test
    void getParticipantById() {
        ParticipantResponseFullDTO participant = restClient.get()
                .uri("/participants/1")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert participant != null;
        assertEquals("John", participant.name());
        assertEquals(25, participant.age());
    }

    @Test
    void createParticipant() {
        ParticipantRequestDTO requestDTO = new ParticipantRequestDTO("Leeroy", "MALE", 30, "Breakfast Club", List.of(1L, 2L));

        ParticipantResponseDTO response = restClient.post()
                .uri("/participants")
                .body(requestDTO)
                .retrieve()
                .toEntity(ParticipantResponseDTO.class)
                .getBody();


        assertNotNull(response);
        assertEquals("Leeroy", response.name());
        assertEquals(30, response.age());
        assertEquals("Breakfast Club", response.club());
    }

    @Test
    void updateParticipant() {
        ParticipantRequestDTO requestDTO = new ParticipantRequestDTO("Leeroy", "MALE", 30, "Breakfast Club", List.of(1L, 2L));

        ParticipantResponseDTO response = restClient.put()
                .uri("/participants/1")
                .body(requestDTO)
                .retrieve()
                .toEntity(ParticipantResponseDTO.class)
                .getBody();

        assertNotNull(response);
        assertEquals("Leeroy", response.name());
        assertEquals(30, response.age());
        assertEquals("Breakfast Club", response.club());
    }

    @Test
    void deleteParticipantById() {

        ParticipantResponseDTO response = restClient.delete()
                .uri("/participants/1")
                .retrieve()
                .toEntity(ParticipantResponseDTO.class)
                .getBody();

        assertNotNull(response);
        assertEquals("John", response.name());
        assertEquals(25, response.age());

        // Attempt to retrieve the participant after deletion
        try {
            restClient.get()
                    .uri("/participants/1")
                    .retrieve()
                    .toEntity(ParticipantResponseDTO.class)
                    .getBody();
            fail("Expected an exception due to participant not found");
        } catch (Exception e) {
            // Expected exception due to participant not found
        }
    }

    @Test
    void removeParticipantFromDiscipline() {


        restClient.delete()
                .uri("/participants/1/disciplines/1")
                .retrieve()
                .toEntity(ParticipantResponseDTO.class)
                .getBody();

        // Check if participant is removed from discipline
        ParticipantResponseFullDTO participant = restClient.get()
                .uri("/participants/1")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert participant != null;
        // Assert that the participant only has 1 discipline left
        assertEquals(1, participant.disciplines()
                .size());


    }

    @Test
    void addParticipantToDiscipline() {

        restClient.post()
                .uri("/participants/1/disciplines/3")
                .retrieve()
                .toEntity(ParticipantResponseDTO.class)
                .getBody();

        // Check if participant is added to discipline
        ParticipantResponseFullDTO participant = restClient.get()
                .uri("/participants/1")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert participant != null;
        // Assert that the participant has 3 disciplines
        assertEquals(3, participant.disciplines()
                .size());
    }
}