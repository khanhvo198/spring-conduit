package mystic.conduit.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class convertUtils {

    public Map<String, Object> toMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public List<String> toList(String body) {
        List<String> list = new ArrayList<>();
        list.add(body);
        return list;
    }
}
