package com.farouk.batch.handler.writer;

import com.farouk.batch.model.Task;
import java.util.Map;
import java.util.UUID;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TaskWriter implements ItemWriter<Map<UUID, Task>>{

    @Override
    public void write(Chunk<? extends Map<UUID, Task>> items) throws Exception {
        items.getItems()
                .forEach(map -> map.values()
                        .forEach(task -> System.out.println("--- TaskWriter : writing task " + task.getId())));
        items.getItems()
                .forEach(map -> map.values()
                        .forEach(task -> task.setStatus("FINISHED")));
    }
    
}
