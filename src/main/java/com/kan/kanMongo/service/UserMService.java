package com.kan.kanMongo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kan.kanMongo.repository.UserMRepository;
import com.kan.kanMongo.vo.UserM;

@Service
public class UserMService {
	@Autowired
    private UserMRepository userMRepository;

	 private final ObjectMapper objectMapper = new ObjectMapper();

//
//    public Optional<YourModel> getById(String id) {
//        return repository.findById(id);
//    }
//
    public UserM save(UserM userM) {
        return userMRepository.save(userM);
    }
    
	public List<UserM> getAll() {
	  return userMRepository.findAll();
	}
	
	  public String ReqFromKafka(@RequestBody String receivedMessage) throws JsonMappingException, JsonProcessingException {
	    	
	        Map<String, Object> messageMap = objectMapper.readValue(receivedMessage, Map.class);
	    	System.out.println("received from kafka {}"+messageMap.get("payload"));
	        List<Map<String, Object>> payloadData = (List<Map<String, Object>>) messageMap.get("payload");
	    	Map<String, Object> userMMap = payloadData.get(0);
	    	UserM userM = objectMapper.convertValue(userMMap, UserM.class);
	    	this.save(userM);
	    	return "hyy";
	    }
//
//    public void deleteById(String id) {
//        repository.deleteById(id);
//    }
}
