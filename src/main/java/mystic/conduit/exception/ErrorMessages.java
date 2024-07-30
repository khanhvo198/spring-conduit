package mystic.conduit.exception;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@JsonTypeName("errors")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class ErrorMessages {
    private final List<Map<String, String>> body;

    public ErrorMessages() {
        body = new ArrayList<Map<String, String>>();
    }


    public void append(HashMap<String, String> message) {
        body.add(message);
    }


}
