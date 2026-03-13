package com.smartcity.urban_management.modules.report.specification;

import com.smartcity.urban_management.modules.report.dto.ReportFilterRequest;
import com.smartcity.urban_management.modules.report.entity.Report;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReportSpecification {

//    public static Specification<Report> filter(ReportFilterRequest filter) {
//
//        return (root, query, cb) -> {
//
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (filter.getStatus() != null) {
//                predicates.add(
//                        cb.equal(root.get("status"), filter.getStatus())
//                );
//            }
//
//            if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
//                predicates.add(
//                        cb.equal(root.get("category"), filter.getCategory())
//                );
//            }
//
//            if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
//
//                String like = "%" + filter.getKeyword().toLowerCase() + "%";
//
//                predicates.add(
//                        cb.or(
//                                cb.like(cb.lower(root.get("title")), like),
//                                cb.like(cb.lower(root.get("description")), like)
//                        )
//                );
//            }
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }
}