package de.app.workspace_service;
import jakarta.persistence.*;


@Entity
public class Workspace {

    @Id
    @Column(unique = true)
    private String token;

    public Workspace() {}
    public Workspace(String token) { this.token = token; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
