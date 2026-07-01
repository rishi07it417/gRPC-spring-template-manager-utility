package com.example.grpc.template.service;


import com.example.grpc.template.Empty;
import com.example.grpc.template.TemplateStore;
import com.example.grpc.template.entitiy.TemplateStoreEntity;
import com.example.grpc.template.repo.TemplateStoreRepo;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import protobuf.com.example.grpc.template.TemplateStoreServiceGrpc;
import org.springframework.grpc.server.service.GrpcService;

import java.time.ZoneId;

@GrpcService
class TemplateStoreGrpcService
        extends TemplateStoreServiceGrpc.TemplateStoreServiceImplBase {

    private final TemplateStoreRepo templateStoreRepo;

    public TemplateStoreGrpcService(TemplateStoreRepo templateStoreRepo) {
        this.templateStoreRepo = templateStoreRepo;
    }

    @Override
    public void getAllTemplate(
            Empty request,
            StreamObserver<TemplateStore> responseObserver) {

        templateStoreRepo.getAllTemplates().map(this::getGrpcTemplateStore).subscribe(
                        responseObserver :: onNext,
                        responseObserver :: onError,
                        responseObserver :: onCompleted
        );


    }

    private TemplateStore getGrpcTemplateStore(TemplateStoreEntity templateStore) {
       return  TemplateStore.newBuilder()
                .setTemplateStoreId(templateStore.getTemplateStoreId())
                .setTemplateName(templateStore.getTemplateName())
                .setCreatedBy(templateStore.getCreatedBy())
                .setCreatedDate(convertToTimestamp(templateStore.getCreatedDate()))
                .setUpdatedBy(templateStore.getUpdatedBy())
                .setUpdatedDate(convertToTimestamp(templateStore.getUpdatedDate()))
                .build();
    }

    private Timestamp convertToTimestamp(java.time.LocalDateTime localDateTime) {
        return Timestamp.newBuilder()
                .setSeconds(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond())
                .setNanos(localDateTime.getNano())
                .build();
    }




}

