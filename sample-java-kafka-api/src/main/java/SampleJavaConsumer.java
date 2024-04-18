import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
    Documentazione ufficiale delle KafkaConsumer API

    https://kafka.apache.org/36/javadoc/org/apache/kafka/clients/consumer/KafkaConsumer.html
 */

public class SampleJavaConsumer {

    public static void main(String[] args) throws JsonProcessingException {

        final KafkaConsumer<String, String> consumer = createKafkaConsumer();

        /*consumer.subscribe(Arrays.asList("user"));*/

        TopicPartition partitionZero = new TopicPartition("user", 0);
        TopicPartition partitionOne = new TopicPartition("user", 1);
        List<TopicPartition> partitions = new ArrayList<TopicPartition>();
        partitions.add(partitionZero);
        partitions.add(partitionOne);

        consumer.assign(partitions);
        consumer.seekToBeginning(partitions);

        final int giveUp = 100;
        int noRecordsCount = 0;

        Map<String, Object> mapRecord = new HashMap<>();

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if (records.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp)
                    break;
                else
                    continue;
            }

            records.forEach(record -> {
                mapRecord.put("Partition " + record.partition() + " - Offset: " + record.offset() + " - Key: " + record.key(), record.value());
            });
            consumer.commitAsync();
        }
        consumer.close();

        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(mapRecord);
        System.out.println("RECORD CONSUMATI");
        System.out.println(json);
    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {
        Properties settings = new Properties();
        settings.put("group.id", "java-consumergroup");
        settings.put("client.id", "java-consumer");
        settings.put("bootstrap.servers", "broker1:29092,broker2:39092,broker3:49092");
        /*
            Usually for test purposes we can enable auto-commit,
            together with the auto.commit.interval.ms property
        */
        settings.setProperty("enable.auto.commit", "false");

        /*
            This configuration set the offset to the earliest in the topic.
            Doing so we can read all the messages in the topic.
            This is the equivalent of --from-beginning we used in the CLI example
        */
        settings.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        settings.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        settings.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        final KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(settings);
        return consumer;
    }
}