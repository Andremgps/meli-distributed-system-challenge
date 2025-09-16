package com.meli.inventory.v1.reservation;

import com.meli.inventory.v1.reservation.dto.ReservationRequestDTO;
import com.meli.inventory.v1.reservation.dto.ReservationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reservations")
@Tag(name = "/v1/reservations", description = "Endpoints for managing stock reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(summary = "Reserve stock for a product in a specific store")
    public ResponseEntity<ReservationResponseDTO> reserveStock(
            @RequestBody @Valid ReservationRequestDTO reservationRequestDTO
            ) {
        ReservationResponseDTO reservation = reservationService.reserveStock(reservationRequestDTO);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/{reservationId}/confirm")
    @Operation(summary = "Confirm a stock reservation, finalizing the reservation and updating inventory")
    public ResponseEntity<ReservationResponseDTO> confirmReservation(
            @PathVariable("reservationId") Long reservationId
    ) {
        ReservationResponseDTO reservation = reservationService.confirmReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{reservationId}")
    @Operation(summary = "Cancel a stock reservation, releasing the reserved stock back to available inventory")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable("reservationId") Long reservationId
    ) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

}
