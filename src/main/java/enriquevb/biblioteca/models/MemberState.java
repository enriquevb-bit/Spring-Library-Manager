package enriquevb.biblioteca.models;

public enum MemberState {
    /**
     * The user has registered but hasn't verified their email
     * or completed the initial registration process.
     */
    PENDING,

    /**
     * The ideal state: the member has full access to
     * borrow books and use library facilities.
     */
    ACTIVE,

    /**
     * Temporary restriction: the member has overdue books
     * or unpaid fines. Usually automated by the system.
     */
    SUSPENDED,

    /**
     * Manual restriction: imposed by staff due to
     * policy violations or severe damage to material.
     */
    BLOCKED,

    /**
     * The account is no longer in use (membership expired
     * or cancelled), but we keep the record for historical data.
     */
    INACTIVE
}
