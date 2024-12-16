package com.sena.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Collection;
import lombok.Data;

@Entity
@Data
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CRUDAction action;
    @JsonProperty("entity_type")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
