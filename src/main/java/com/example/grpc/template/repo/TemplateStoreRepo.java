package com.example.grpc.template.repo;


import com.example.grpc.template.entitiy.TemplateStoreEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TemplateStoreRepo extends ReactiveCrudRepository<TemplateStoreEntity, Long> {

    @Query("SELECT templateStoreId, templateName, createdBy, createdDate, updatedBy, updatedDate FROM templateStore")
    Flux<TemplateStoreEntity> getAllTemplates();

}
