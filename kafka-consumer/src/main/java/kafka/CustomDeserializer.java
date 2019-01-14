package kafka;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import event.EventResource;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<EventResource> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public EventResource deserialize(String topic,  byte[]  data) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        EventResource object = null;
        try {
            object = mapper.readValue(data, EventResource.class);
        } catch (Exception exception) {
            System.out.println("Error deserialize bytes "+ exception);
        }
        return object;
    }

    @Override
    public void close() {
    }

}
