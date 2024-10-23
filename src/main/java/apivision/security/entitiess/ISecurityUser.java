package apivision.security.entitiess;

public interface ISecurityUser {
    boolean verifyPassword(String pw);
    void addRole(Role role);
}
