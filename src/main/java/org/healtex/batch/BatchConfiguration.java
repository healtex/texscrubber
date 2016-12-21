package org.healtex.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.healtex.batch.processor.FirstPassItemProcessor;
import org.healtex.batch.listener.JobCompletionNotificationListener;
import org.healtex.model.Document;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // tag::readerwriterprocessor[]
    @Bean
    public MultiResourceItemReader<Document> reader() {
        MultiResourceItemReader<Document> reader = new MultiResourceItemReader<Document>();
        return reader;
    }

    @Bean
    public FirstPassItemProcessor processor() {
        return new FirstPassItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<Document> writer() {
        FlatFileItemWriter<Document> writer = new FlatFileItemWriter<Document>();
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job texscrubberJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("texscrubberJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Document, Document> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
}
