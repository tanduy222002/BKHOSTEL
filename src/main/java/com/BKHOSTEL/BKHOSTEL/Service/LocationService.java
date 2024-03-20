package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.Entity.District;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {
    public List<District> getAllDistricts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("static/districts.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<District>>(){});
    }
    public List<String> getAllWardFromDistrict(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("static/wards.json");
        Map<String,List<String>> districtMap= objectMapper.readValue(resource.getInputStream(),  new TypeReference<Map<String, List<String>>>() {});
        System.out.println("District");
        return districtMap.get(id);
    }
}
