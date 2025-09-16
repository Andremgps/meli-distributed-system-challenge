package com.meli.inventory.v1.reservation;

import com.meli.inventory.exception.InsufficientStockException;
import com.meli.inventory.exception.InventoryNotFoundException;
import com.meli.inventory.exception.ReservationExpiredException;
import com.meli.inventory.exception.ReservationNotFoundException;
import com.meli.inventory.model.Inventory;
import com.meli.inventory.model.Reservation;
import com.meli.inventory.repository.InventoryRepository;
import com.meli.inventory.repository.ReservationRepository;
import com.meli.inventory.v1.event.InventoryEventPublisher;
import com.meli.inventory.v1.event.InventoryUpdatedEvent;
import com.meli.inventory.v1.reservation.dto.ReservationRequestDTO;
import com.meli.inventory.v1.reservation.dto.ReservationResponseDTO;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private InventoryRepository inventoryRepository;
    private InventoryEventPublisher eventPublisher;

    public ReservationService(ReservationRepository reservationRepository, InventoryEventPublisher eventPublisher, InventoryRepository inventoryRepository) {
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    @Retryable(value = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public ReservationResponseDTO reserveStock(ReservationRequestDTO reservationRequestDTO){
        Inventory inventory = inventoryRepository.findByProductIdAndStoreId(reservationRequestDTO.getProductId(), reservationRequestDTO.getStoreId())
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found for product: " + reservationRequestDTO.getProductId() + " in store: " + reservationRequestDTO.getStoreId()));
        if (!inventory.canReserve(reservationRequestDTO.getQuantity())) {
            throw new InsufficientStockException(
                    "Insufficient stock. Available: " + inventory.getAvailableStock() +
                            ", Requested: " + reservationRequestDTO.getQuantity());
        }

        inventory.reserveStock(reservationRequestDTO.getQuantity());
        inventoryRepository.save(inventory);

        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(reservationRequestDTO.getReservationTtl());
        Reservation reservation = new Reservation(
                inventory,
                reservationRequestDTO.getStoreId(),
                reservationRequestDTO.getCustomerId(),
                reservationRequestDTO.getOrderId(),
                reservationRequestDTO.getQuantity(),
                expiresAt
        );
        reservationRepository.save(reservation);

        eventPublisher.publishInventoryUpdatedEvent(new InventoryUpdatedEvent(
                reservationRequestDTO.getProductId(),
                reservationRequestDTO.getStoreId(),
                inventory.getTotalStock(),
                inventory.getAvailableStock(),
                inventory.getReservedStock(),
                "RESERVATION_MADE"
        ));

        return new ReservationResponseDTO(
                reservation.getId(),
                inventory.getProduct().getId(),
                reservation.getQuantity(),
                reservation.getStoreId(),
                reservation.getExpiresAt(),
                reservation.getStatus().name()
        );
    }

    @Transactional
    @Retryable(value = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public ReservationResponseDTO confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + reservationId));

        if (reservation.isExpired()) {
            throw new ReservationExpiredException("Reservation has expired");
        }

        Inventory inventory = reservation.getInventory();
        inventory.confirmReservation(reservation.getQuantity());
        inventoryRepository.save(inventory);
        reservation.confirm();
        reservationRepository.save(reservation);

        eventPublisher.publishInventoryUpdatedEvent(new InventoryUpdatedEvent(
                inventory.getProduct().getId(),
                reservation.getStoreId(),
                inventory.getTotalStock(),
                inventory.getAvailableStock(),
                inventory.getReservedStock(),
                "RESERVATION_CONFIRMED"
        ));

        return new ReservationResponseDTO(
                reservationId,
                reservation.getInventory().getProduct().getId(),
                reservation.getQuantity(),
                reservation.getStoreId(),
                reservation.getExpiresAt(),
                "CONFIRMED"
        );
    }

    @Transactional
    @Retryable(value = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + reservationId));

        Inventory inventory = reservation.getInventory();
        inventory.releaseStock(reservation.getQuantity());
        inventoryRepository.save(inventory);
        reservation.cancel();
        reservationRepository.save(reservation);

        eventPublisher.publishInventoryUpdatedEvent(new InventoryUpdatedEvent(
                inventory.getProduct().getId(),
                reservation.getStoreId(),
                inventory.getTotalStock(),
                inventory.getAvailableStock(),
                inventory.getReservedStock(),
                "ReSERVATION_CANCELED"
        ));

        reservationRepository.save(reservation);
    }

}
