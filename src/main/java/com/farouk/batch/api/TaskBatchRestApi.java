package com.farouk.batch.api;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TaskBatchRestApi {
    
    private JobLauncher jobLauncher;
    private Job taskJob;
    private JobExplorer jobExplorer;
    
    public TaskBatchRestApi(JobLauncher jobLauncher, Job taskJob, JobExplorer jobExplorer){
        this.jobLauncher = jobLauncher;
        this.taskJob = taskJob;
        this.jobExplorer = jobExplorer;
    }
    
    @GetMapping("/launch/{size}")
    public Mono<ResponseEntity> submitFlow(@PathVariable("size") long size) throws Exception{
        return Mono.just(size)
                   .map(chunkSize -> new JobParametersBuilder()
                                    .addLong("size", size)
                                    .toJobParameters())
                   .map(jobParameters -> this.runJob(jobLauncher, taskJob, jobParameters))
                   .map(jobExecution -> jobExecution.getJobId())
                   .map(jobId -> ResponseEntity.ok(jobId));
    }
    
    @GetMapping("/status/{jobId}")
    public Mono<ResponseEntity> batchStatus(@PathVariable("jobId") long jobId) throws Exception{
        return Mono.just(jobId)
                   .map(batchId -> jobExplorer.getJobExecution(batchId).getStatus())
                   .map(status -> ResponseEntity.ok(status));
    }
    
    private JobExecution runJob(JobLauncher jobLauncher, Job taskJob, JobParameters jobParameters){
        try {
            return jobLauncher.run(taskJob, jobParameters);
        } catch (Exception exception) {
        }
        return null;
    }
}
