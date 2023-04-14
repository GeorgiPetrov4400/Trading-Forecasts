package forexbet.tradingforecasts.model.dto;

public class ChangeAccountRoleDTO {

    private String newRole;

    public ChangeAccountRoleDTO() {
    }

    public String getNewRole() {
        return newRole;
    }

    public ChangeAccountRoleDTO setNewRole(String newRole) {
        this.newRole = newRole;
        return this;
    }
}
