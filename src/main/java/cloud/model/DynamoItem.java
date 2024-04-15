package cloud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.UUID;

@Getter
@Setter
@DynamoDbBean
@AllArgsConstructor
public class DynamoItem {
    private UUID codigo;
    private String tipo;
    private String data;
}
