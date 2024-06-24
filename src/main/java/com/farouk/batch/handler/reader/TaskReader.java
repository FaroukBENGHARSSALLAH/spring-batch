package com.farouk.batch.handler.reader;

import com.farouk.batch.model.Task;
import java.util.Map;
import java.util.UUID;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class TaskReader implements ItemReader<Task> {
 
    @Value("#{jobParameters}")
    Map<String, Object> jobParameters;
    
    private long counter = 0l;
    
    @Override
    public Task read() {
        long itemSize = (long) jobParameters.get("size");
        if(counter++ < itemSize) {
            System.out.println("--- TaskReader : generating task " + counter);
            Task task = new Task(UUID.randomUUID(), "title "+counter, "description "+counter,
                    "SCHEDULED");
            System.out.println("--- TaskReader : task " + task.getId() + " generated");
            return task;
        }
        return null;
    }
}
