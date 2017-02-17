package org.healtex.batch;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;

import org.healtex.batch.processor.FirstPassItemProcessor;
import org.healtex.batch.processor.SecondPassItemProcessor;
import org.healtex.batch.listener.FirstPassStepExecutionListener;
import org.healtex.batch.listener.JobCompletionNotificationListener;
import org.healtex.batch.listener.SecondPassStepExecutionListener;
import org.healtex.batch.reader.FlatFileSingleItemReader;
import org.healtex.batch.writer.DeidentifiedDocumentWriter;
import org.healtex.batch.writer.NamedEntitiesWriter;
import org.healtex.model.Document;
import org.healtex.model.GATEDocument;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // tag::readerwriterprocessor[]
    @Bean
    public MultiResourceItemReader<Document> reader() {
        MultiResourceItemReader<Document> reader = new MultiResourceItemReader<Document>();

        File folder = new File("workspace/sample-input/");
        List<Resource> resList = new ArrayList<Resource>();

        for (File file: folder.listFiles()) {
            if (file.isFile()) {
                resList.add(new FileSystemResource(file));
            }
        }

        Resource[] resources = resList.toArray(new Resource[resList.size()]);
        reader.setResources(resources);

        reader.setDelegate(new FlatFileSingleItemReader());
        return reader;
    }

    @Bean
    public FirstPassItemProcessor processor1() {
        return new FirstPassItemProcessor();
    }

    @Bean
    public SecondPassItemProcessor processor2() {
        return new SecondPassItemProcessor();
    }

    @Bean
    public ItemWriter<GATEDocument> firstPassWriter() {
        NamedEntitiesWriter writer = new NamedEntitiesWriter("workspace/dev-test-output");
        return writer;
    }

    @Bean
    public ItemWriter<GATEDocument> secondPassWriter() {
        DeidentifiedDocumentWriter writer = new DeidentifiedDocumentWriter("workspace/dev-test-output-2");
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job texscrubberJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("texscrubberJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Document, GATEDocument> chunk(10)
                .reader(reader())
                .processor(processor1())
                .writer(firstPassWriter())
                .listener(new FirstPassStepExecutionListener())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<Document, GATEDocument> chunk(10)
                .reader(reader())
                .processor(processor2())
                .writer(secondPassWriter())
                .listener(new SecondPassStepExecutionListener())
                .build();
    }
    // end::jobstep[]
}
