package de.app.client.DTOs;

public class WorkspaceDTO {
    private String token;

    public WorkspaceDTO(String token){
        this.token = token;
    }

    public String getToken(){return token;}
}
