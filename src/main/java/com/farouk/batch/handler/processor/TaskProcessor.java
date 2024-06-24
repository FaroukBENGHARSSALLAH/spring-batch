package com.farouk.batch.handler.processor;

import com.farouk.batch.model.Task;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor  implements ItemProcessor<Task, Map<UUID, Task>> {

    @Override
    public Map<UUID, Task> process(Task task) throws Exception {
        System.out.println("--- TaskProcessor : processing task " + task.getId());
        task.setStatus("PROCESSED");
        return Map.of(task.getId(), task);
    }
    
}
