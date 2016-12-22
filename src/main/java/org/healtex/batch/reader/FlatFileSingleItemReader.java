package org.healtex.batch.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.batch.item.ParseException;

import org.springframework.batch.item.ItemStreamSupport;

import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.BufferedReaderFactory;
import org.springframework.batch.item.file.DefaultBufferedReaderFactory;

import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ExecutionContext;

public class FlatFileSingleItemReader<T> extends ItemStreamSupport implements
    ResourceAwareItemReaderItemStream<T>, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(FlatFileSingleItemReader.class);

    // default encoding for input files
    public static final String DEFAULT_CHARSET = Charset.defaultCharset().name();

    private Resource resource;

    private BufferedReader reader;

    private boolean noInput = false;

    private String encoding = DEFAULT_CHARSET;

    private LineMapper<T> lineMapper;

    private boolean strict = true;

    private BufferedReaderFactory bufferedReaderFactory = new DefaultBufferedReaderFactory();

    public FlatFileSingleItemReader() {
        setName(ClassUtils.getShortName(FlatFileSingleItemReader.class));
    }

    /**
     * In strict mode the reader will throw an exception if the input resource does not exist.
     * @param strict <code>true</code> by default
     */
    public void setStrict(boolean strict) {
      this.strict = strict;
    }

    /**
       * Setter for line mapper. This property is required to be set.
       * @param lineMapper maps line to item
       */
      public void setLineMapper(LineMapper<T> lineMapper) {
        this.lineMapper = lineMapper;
      }

    /**
     * Setter for the encoding for this input source. Default value is #DEFAULT_CHARSET.
     *
     * @param encoding a properties object which possibly contains the encoding for this input file;
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Factory for the BufferedReader that will be used to extract lines from the file. The default is fine for
     * plain text files, but this is a useful strategy for binary files where the standard BufferedReaader from java.io
     * is limiting.
     *
     * @param bufferedReaderFactory the bufferedReaderFactory to set
     */
    public void setBufferedReaderFactory(BufferedReaderFactory bufferedReaderFactory) {
        this.bufferedReaderFactory = bufferedReaderFactory;
    }

    /**
     * Public setter for the input resource.
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (noInput) {
            return null;
        }

        if (reader == null) {
            throw new ReaderNotOpenException("Reader must be open before it can be read.");
        }

        try {
            StringBuffer sb = new StringBuffer();

            String line = this.reader.readLine();

            if (line == null) {
                return null;
            }

            while (line != null) {
                sb.append(line);
                line = this.reader.readLine();
            }

            try {
                return lineMapper.mapLine(sb.toString(), 0);
            }
            catch (Exception ex) {
                throw new ParseException("Parsing error in resource=[" + resource.getDescription() + "]", ex);
            }
        }
        catch (IOException e) {
            // Prevent IOException from recurring indefinitely
            // if client keeps catching and re-calling
            noInput = true;
            throw new NonTransientResourceException("Unable to read from resource: [" + resource + "]", e);
        }
    }

    @Override
    public void close() throws ItemStreamException {
        super.close();
        try {
            if (reader != null) {
                reader.close();
            }
        }
        catch (Exception e) {
            throw new ItemStreamException("Error while closing item reader", e);
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.open(executionContext);
        try {
              Assert.notNull(resource, "Input resource must be set");

              noInput = true;
              if (!resource.exists()) {
                  if (strict) {
                      throw new IllegalStateException("Input resource must exist (reader is in 'strict' mode): " + resource);
                  }
                  log.warn("Input resource does not exist {}", resource.getDescription());
                  return;
              }

              if (!resource.isReadable()) {
                  if (strict) {
                      throw new IllegalStateException("Input resource must be readable (reader is in 'strict' mode): " + resource);
                  }
                  log.warn("Input resource is not readable {}", resource.getDescription());
                  return;
              }

              reader = bufferedReaderFactory.create(resource, encoding);

              noInput = false;
        }
        catch (Exception e) {
            throw new ItemStreamException("Failed to initialize the reader", e);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        super.update(executionContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(lineMapper, "LineMapper is required");
    }

}
