package sample.model.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import sample.model.JsonEventClient;


@UtilityClass
public class JacksonHelper {

    @SneakyThrows
    public String eventClientToJson(JsonEventClient jsonEventClient) {
        return new ObjectMapper().writeValueAsString(jsonEventClient);
    }

    @SneakyThrows
    public JsonEventClient jsonToEventClient(String json) {
        return new ObjectMapper().readValue(json, JsonEventClient.class);
    }

}
