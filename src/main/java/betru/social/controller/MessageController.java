package betru.social.controller;

import betru.social.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int count = 4;

    private List<Map<String,String>> list = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>(){{put("id", "1"); put("text", "first");}});
        add(new HashMap<String, String>(){{put("id", "2"); put("text", "second");}});
        add(new HashMap<String, String>(){{put("id","3"); put("text", "third");}});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return list;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return list.stream()
                .filter(mes -> mes.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String,String> create(@RequestBody Map<String, String> mess){
        mess.put("id", String.valueOf(count++));
        list.add(mess);
        return mess;
    }

    @PutMapping("{id}")
    public Map<String,String> update(@PathVariable String id, @RequestBody Map<String, String> mess){
        Map<String, String> messageFromDB = getMessage(mess.get("id"));
        mess.putAll(messageFromDB);
        messageFromDB.put("id",id);
        return messageFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id, @RequestBody Map<String, String> mess){
        Map<String, String> message = getMessage(id);
        list.remove(message);
    }
}
