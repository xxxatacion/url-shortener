package de.app.workspace_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class WorkspaceService {

    private static final Logger log = LoggerFactory.getLogger(WorkspaceService.class);

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository){
        this.workspaceRepository = workspaceRepository;
    }
    public Workspace createToken(){
        String token = UUID.randomUUID().toString();

        Workspace neuerWorkspace = new Workspace(token);
        //TODO: Try Catch...
        workspaceRepository.save(neuerWorkspace);

        log.info("Neuer Workspace-Token generiert: {}", token);

        return neuerWorkspace;
    }

    public boolean tokenExists(String token){
        return workspaceRepository.existsByToken(token);
    }
}