package example.awsdemo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class SpringRunner implements CommandLineRunner {

    @Autowired
    public AmazonS3 s3Client;

    @Override
    public void run(String... args) {
        SelectObjectContentRequest request = new SelectObjectContentRequest();
        request.setBucketName("inspection-55");
        request.setKey("s3select/test001.csv");
        request.setExpression("select * from S3Object s");
        request.setExpressionType(ExpressionType.SQL);

        InputSerialization inputSerialization = new InputSerialization();
        inputSerialization.setCsv(new CSVInput());
        inputSerialization.setCompressionType(CompressionType.NONE);
        request.setInputSerialization(inputSerialization);

        OutputSerialization outputSerialization = new OutputSerialization();
        outputSerialization.setCsv(new CSVOutput());
        request.setOutputSerialization(outputSerialization);

        SelectObjectContentResult result = s3Client.selectObjectContent(request);

        System.out.println("------------- Line Start -------------");

        InputStream resultInputStream = result.getPayload().getRecordsInputStream(new SelectObjectContentEventVisitor() {
        });
        try (InputStreamReader reader = new InputStreamReader(resultInputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("exit")) {
                    System.exit(0);
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("------------- Line End -------------");

    }
}