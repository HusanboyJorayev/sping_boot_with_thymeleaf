package com.example.sping_boot_with_thymeleaf.test;

import com.example.sping_boot_with_thymeleaf.home.Book;
import com.example.sping_boot_with_thymeleaf.todoes.Posts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ObjectMapperTest {

    @Test
    void sampleCode() throws Exception {
        String bookJson = """
                {
                "id":1,
                "name":"Husanboy",
                "author":"Jorayev"
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(bookJson, Book.class);
        System.out.println(book);
        String string = mapper.writeValueAsString(book);
        System.out.println(string);
    }

    @Test
    void objectFromFile() throws Exception {
        File file = new File("jsonBook");
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(file, Book.class);
        System.out.println(book);
        System.out.println(book.getId());
    }

    @Test
    void readObjectFromJsonReader() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String bookJson = """
                {
                "id":1,
                "name":"Husanboy",
                "author":"Jorayev"
                }
                """;
        Reader reader = new StringReader(bookJson);
        Book book = mapper.readValue(reader, Book.class);
        System.out.println(book);
    }

    @Test
    void readJsonFromUrl() throws Exception {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts");
        ObjectMapper mapper = new ObjectMapper();
        // todo array orqali
        /*Posts[] posts = mapper.readValue(url, Posts[].class);
        for (Posts post : posts) {
            System.out.println(post);
        }*/

        // todo List orqali
        List<Posts> posts = mapper.readValue(url, new TypeReference<List<Posts>>() {
        });
        for (Posts post : posts) {
            System.out.println(post);
        }
    }

    @Test
    void readJsonFromJsonInputStream() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("jsonTodo");
        Posts posts = mapper.readValue(inputStream, Posts.class);
        System.out.println(posts);
    }

    @Test
    void readObjectFromByteArray() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String postJson = """
                {
                    "userId": 1,
                    "id": 1,
                    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
                  }
                """;
        byte[] bytes = postJson.getBytes();
        Posts posts = mapper.readValue(bytes, Posts.class);
        System.out.println(posts);
    }

    @Test
    void readMapFromJsonString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String postJson = """
                {
                    "userId": 1,
                    "id": 1,
                    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
                  }
                """;
        Map<String, String> stringStringMap = mapper.readValue(postJson, new TypeReference<Map<String, String>>() {
        });
       /* List<String> list = stringStringMap.keySet().stream().toList();
        for (String string : list) {
            System.out.println(string);
        }*/
        Iterator<String> iterator = stringStringMap.keySet().stream().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = stringStringMap.get(key);
            System.out.println(key + ":" + value);
        }
    }

    @Test
    void failOnNullJsonValuesFromPrimitiveTypes() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        String postJson = """
                {
                    "userId": 1,
                    "id":null,
                    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
                  }
                """;
        Posts posts = mapper.readValue(postJson, Posts.class);
        System.out.println(posts);
    }

    @Test
    void writeJsonFromObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new FileOutputStream("jsonTodo"), new Posts(2, 3, "name", "author"));
    }

    @Test
    void jacksonDateFormats() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        ObjectMapper mapper1 = mapper.setDateFormat(dateFormat);
        System.out.println(mapper1.toString());
        String string = mapper.writeValueAsString(dateFormat);
        System.out.println(string);

    }

    @Test
    void jacksonTreeModel() {
        ObjectMapper mapper = new ObjectMapper();
        String postJson = """
                {
                    "userId": 1,
                    "id":3,
                    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
                  }
                """;
        try {
            JsonNode jsonNode = mapper.readValue(postJson, JsonNode.class);
            JsonNode userId = jsonNode.get("userId");
            System.out.println(userId.asInt());

            // todo tree model
            JsonNode jsonNode1 = mapper.readTree(postJson);
            System.out.println(jsonNode1.get("title"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void jacksonJsonNodeClass() throws Exception {
        String carJson =
                "{ \"brand\" : \"Mercedes\", \"doors\" : 5," +
                        "  \"owners\" : [\"John\", \"Jack\", \"Jill\"]," +
                        "  \"nestedObject\" : { \"field\" : \"value\" } }";

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            JsonNode jsonNode = objectMapper.readValue(carJson, JsonNode.class);

            /*JsonNode brandNode = jsonNode.get("brand");
            String brand = brandNode.asText();
            System.out.println("brand = " + brand);

            JsonNode doorsNode = jsonNode.get("doors");
            int doors = doorsNode.asInt();
            System.out.println("doors = " + doors);*/

            JsonNode array = jsonNode.get("owners");
            JsonNode jsonNode1 = array.get(0);
            for (JsonNode node : array) {
                System.out.println(node);
            }
            String john = jsonNode1.asText();
            System.out.println("john  = " + john);

            /*JsonNode child = jsonNode.get("nestedObject");
            JsonNode childField = child.get("field");
            String field = childField.asText();
            System.out.println("field = " + field);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void convertObjectToJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        Posts posts = new Posts(1, 2, "title", "body");
        JsonNode jsonNode = mapper.valueToTree(posts);
        System.out.println(jsonNode);
    }

    @Test
    void convertJsonNodeToObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String postJson = """
                {
                    "userId": 1,
                    "id":3,
                    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
                  }
                """;
        JsonNode jsonNode = mapper.readTree(postJson);
        Posts posts = mapper.treeToValue(jsonNode, Posts.class);
        System.out.println(posts);
    }

    @Test
    void readingAndWritingYML() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Posts posts = new Posts(2, 5, "asd", "stfs efdygeb udgeen");
        String yamlString = null;
        try {
            yamlString = mapper.writeValueAsString(posts);
            System.out.println(yamlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
