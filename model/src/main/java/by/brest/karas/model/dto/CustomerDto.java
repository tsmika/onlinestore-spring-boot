package by.brest.karas.model.dto;

import by.brest.karas.model.Role;

/**
 * POJO Customer for model.
 */
public class CustomerDto {
    /**
     * CustomerId.
     */
    private Integer customerId;

    /**
     * Login.
     */
    private String login;

    /**
     * Password.
     */
    private String password;

    /**
     * Role.
     */
    private Role role;

    /**
     * Is customer actual.
     */
    private Boolean isActual;

    /**
     * Constructor without arguments.
     */
    public CustomerDto() {
    }

    /**
     * Constructor with arguments.
     * @param login customer login
     * @param password customer password
     * @param role customer role
     * @param isActual customer status
     */
    public CustomerDto(String login, String password, Role role, Boolean isActual) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.isActual = isActual;
    }
}
