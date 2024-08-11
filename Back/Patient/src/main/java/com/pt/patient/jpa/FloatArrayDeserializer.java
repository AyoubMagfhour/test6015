package com.pt.patient.jpa;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class FloatArrayDeserializer extends JsonDeserializer<float[]> {

    @Override
    public float[] deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        float[] result = new float[node.size()];
        int index = 0;
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            result[index++] = (float) field.getValue().asDouble();
        }
        return result;
    }
}
