package cloud.service;
import cloud.model.DynamoItem;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class DynamoDBService {
    @Value("${aws.region.static}")
    private String region;
    public void putItem(String tipo, String data) {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.of(region))
                .build();

        PutItemRequest request = PutItemRequest.builder()
                .tableName("projet30DynamoDB")
                .item(Map.of(
                        "codigo", AttributeValue.builder().s(String.valueOf(UUID.randomUUID())).build(),
                        "tipo", AttributeValue.builder().s(tipo).build(),
                        "data", AttributeValue.builder().s(data).build()
                ))
                .build();

        try {
            PutItemResponse response = ddb.putItem(request);
            log.info("projeto30DynamoDB was successfully updated. The request id is {}", response.responseMetadata().requestId());

        } catch (ResourceNotFoundException e) {
            log.error("Error: The Amazon DynamoDB table \"%s\" can't be found.\n {}", "projeto30DynamoDB");
            log.error("Be sure that it exists and that you've typed its name correctly!");
        } catch (DynamoDbException e) {
            log.error(e.getMessage());
        }
    }
}