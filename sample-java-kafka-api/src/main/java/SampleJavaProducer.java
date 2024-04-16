import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
    Documentazione ufficiale delle KafkaProducer API

    https://kafka.apache.org/36/javadoc/org/apache/kafka/clients/producer/KafkaProducer.html
*/
public class SampleJavaProducer {

    public static void main (String[] args) throws JsonProcessingException {

        final KafkaProducer<String, String> producer = createKafkaProducer();

        final String topic = "user";
        Map<String, Object> mapRecord = new HashMap<>();

        for (int index = 0; index < 5; index++) {

            final String key = "key-" + index;
            final String value = "{\"name\": \"User "+index+"\", \"creationDate\":"+System.currentTimeMillis()+", \"userID\": "+index+"}";

            final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);

            producer.send(record);

            mapRecord.put(record.key(), record.value());
        }

        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(mapRecord);
        System.out.println("RECORD INVIATI");
        System.out.println(json);
    }

    private static KafkaProducer<String, String> createKafkaProducer() {
        Properties settings = new Properties();
        settings.put("client.id", "java-producer");
        settings.put("bootstrap.servers", "broker1:29092,broker2:39092,broker3:49092");

        /*
            acks corrisponde al numero di acknowledgment che il producer aspetterà
            prima di considerare il messaggio inviato correttamente

            "all" indica che il producer aspetterà di ricevere un acknowledgment
            dal leader e da tutte le In-Sync Replicas

            "1" di default
        */
        settings.put("acks", "all");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(settings);
        return producer;
    }
}