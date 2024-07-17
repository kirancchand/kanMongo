package com.kan.kanMongo.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class KafkaConfiguration {
	 @NotNull
	    @Value("${spring.kafka.consumer.bootstrap-servers}")
	    private String bootstrapServers;
	    
	    @NotNull
	    @Value("${spring.kafka.consumer.group-id}")
	    private String groupId;
	    
	    @NotNull
	    @Value("${kafka.partitions}")
	    private String partitions;
	    @NotNull
	    @Value("${kafka.replication}")
	    private String replication;
	    
	    public void kafkaCreateTopic(List<String> topics, String retention) {
	        try {
	            Map adminClientConfig = new HashMap<>();
	            adminClientConfig.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	            AdminClient adminClient = KafkaAdminClient.create(adminClientConfig);
	            
	            ListTopicsResult listTopicsResult = adminClient.listTopics();
	            Map availableTopics = listTopicsResult.namesToListings().get();
	            Map configs = new HashMap<>();
	            //configs.put(TopicConfig .MAX_MESSAGE_BYTES_CONFIG, "10485760");// 1MB max message size
	            if(Integer.parseInt(partitions) > 2)
	                configs.put(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2");// QUORUM writes
	            if(retention.equalsIgnoreCase("master"))
	                configs.put(TopicConfig.RETENTION_MS_CONFIG, ""+Long.MAX_VALUE); // forever log retention for the messages
	            else {
	                configs.put(TopicConfig.RETENTION_MS_CONFIG, "604800000"); // 7 day log retention for the messages
	                configs.put(TopicConfig.SEGMENT_MS_CONFIG, "604800000"); // roll over after 7 days even if not full
	            }
	            
	            for (String masterOther : topics) {
	                if(!availableTopics.containsKey(masterOther)) {
	                    NewTopic topic = new NewTopic(masterOther, Integer.parseInt(partitions), (short) Integer.parseInt(replication));
	                    topic.configs(configs);
	                    CreateTopicsResult result = adminClient.createTopics(Arrays.asList(topic));
	                    result.all().get(); // @todo: see if we didn't get an exception
	                }
	            }

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } 
	    }
}
