package com.fastdelivery.fastdelivery.api.controller;

import com.fastdelivery.fastdelivery.domain.model.Permission;
import com.fastdelivery.fastdelivery.domain.service.PermissionService;
import com.fastdelivery.fastdelivery.domain.service.exceptions.EntityInUseException;
import com.fastdelivery.fastdelivery.domain.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<Page<Permission>> findAll(Pageable pageable){
        Page<Permission> permissions = permissionService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            Permission permission = permissionService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(permission);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Permission> insert(@RequestBody Permission permission){
        permission = permissionService.insert(permission);
        return ResponseEntity.status(HttpStatus.OK).body(permission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Permission permission){
        try{
            permission = permissionService.update(id, permission);
            return ResponseEntity.status(HttpStatus.OK).body(permission);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            permissionService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }catch (EntityInUseException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
