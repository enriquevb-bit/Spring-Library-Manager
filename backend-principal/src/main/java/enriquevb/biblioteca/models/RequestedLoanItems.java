package enriquevb.biblioteca.models;

import java.util.UUID;

public class RequestedLoanItems<K, V> {
    private UUID bookId;
    private Integer quantity;

    public RequestedLoanItems(UUID bookId, Integer avalailableCopies) {
        this.bookId = bookId;
        this.quantity = avalailableCopies;
    }

    public UUID getBookId() {
        return bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
