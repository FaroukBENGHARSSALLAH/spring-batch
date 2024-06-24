package com.farouk.batch.configuration;

import com.farouk.batch.handler.processor.TaskProcessor;
import com.farouk.batch.handler.reader.TaskReader;
import com.farouk.batch.handler.writer.TaskWriter;
import com.farouk.batch.model.Task;
import java.util.Map;
import java.util.UUID;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {
    
    private final TaskReader taskReader;
    private final TaskProcessor taskProcessor;
    private final TaskWriter taskWriter;
    
    public BatchConfiguration(TaskReader taskReader, TaskProcessor taskProcessor,
        TaskWriter taskWriter){
        this.taskReader = taskReader;
        this.taskProcessor = taskProcessor;
        this.taskWriter = taskWriter;
    }
    
    @Bean
    public Job runJob(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            Step step) {
        return (Job) new JobBuilder("Task-management", jobRepository)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("user-step", jobRepository).<Task, Map<UUID, Task>>
                chunk(10, transactionManager)
                .reader(taskReader)
                .processor((ItemProcessor) taskProcessor)
                .writer(taskWriter)
                .build();
    }

}
