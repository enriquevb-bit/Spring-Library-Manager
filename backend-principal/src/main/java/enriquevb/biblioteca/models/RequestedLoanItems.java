package enriquevb.biblioteca.models;

public class RequestedLoanItems<UUID, Integer> {
    private UUID bookId;
    private Integer quantity;

    public RequestedLoanItems(UUID bookId, Integer availableCopies) {
        this.bookId = bookId;
        this.quantity = availableCopies;
    }

    public UUID getBookId() {
        return bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
