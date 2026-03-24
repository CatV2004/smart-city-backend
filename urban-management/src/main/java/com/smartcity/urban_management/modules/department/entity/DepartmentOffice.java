package com.smartcity.urban_management.modules.department.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Table(name = "department_offices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentOffice {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "location", nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Column(name = "is_active")
    private Boolean isActive = true;
}