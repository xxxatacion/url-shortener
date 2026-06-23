package de.app.workspace_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService){
        this.workspaceService = workspaceService;
    }

    @PostMapping
    public ResponseEntity<Workspace> getNewWorkspaceToken(){
        return ResponseEntity.status(HttpStatus.CREATED).body(workspaceService.createToken());
    }

    @GetMapping("/check/{token}")
    public boolean tokenExists(@PathVariable String token){
        return workspaceService.tokenExists(token);
    }
}