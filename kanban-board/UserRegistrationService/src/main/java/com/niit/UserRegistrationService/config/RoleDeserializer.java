package com.niit.UserRegistrationService.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.niit.UserRegistrationService.Enum.Role;

import java.io.IOException;

public class RoleDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        if ("Team Lead".equalsIgnoreCase(value)) {
            return Role.TEAM_LEAD;
        } else {
            return Role.valueOf(value); // Use default enum deserialization for other values
        }
    }
}
