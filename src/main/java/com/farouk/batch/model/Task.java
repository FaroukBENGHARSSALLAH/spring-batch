package com.farouk.batch.model;

import java.util.UUID;

public class Task {
    
    private UUID id;
    private String title;
    private String description;
    private String status; 
    
    public Task(UUID id, String title, String description, String status){
       this.id = id;
       this.title = title;
       this.description = description;
       this.status = status;
    }
    
    public UUID getId(){
       return this.id;
    }
    
    public void setStatus(String status){
       this.status = status;
    }
    
    
}
