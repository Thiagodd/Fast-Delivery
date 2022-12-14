package com.fastdelivery.fastdelivery.domain.service;

import com.fastdelivery.fastdelivery.domain.model.Permission;
import com.fastdelivery.fastdelivery.domain.repository.PermissionRepository;
import com.fastdelivery.fastdelivery.domain.service.exceptions.EntityInUseException;
import com.fastdelivery.fastdelivery.domain.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class PermissionService {

    String MSG_ENTITY_NOT_FOUND = "[custom] Não foi possivel encontrar uma permissão com o ID fornecido : %s";
    String MSG_ENTITY_IN_USE = "[custom] Não foi possivel deletar a permissão com o ID fornecido, pois ele está em uso : %s";

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Transactional(readOnly = true)
    public Page<Permission> findAll(Pageable pageable){
        return permissionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Permission findById(Long id){
        return permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
    }

    @Transactional
    public Permission insert(Permission permission){
        return permissionRepository.save(permission);
    }

    @Transactional
    public Permission update(Long id, Permission permission){
        try{
            Permission currentPermission = this.findById(id);
            BeanUtils.copyProperties(permission, currentPermission, "id");
            return this.insert(currentPermission);
        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND));
        }
    }

    public void deleteById(Long id){
        try{
            permissionRepository.deleteById(id);
        }catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }catch (DataIntegrityViolationException exception){
            throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
