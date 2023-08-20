package com.daofab.transaction.util;

import com.daofab.transaction.repo.dao.Child;
import com.daofab.transaction.repo.dao.Parent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing JSON data and converting it to Java objects.
 */
public class JsonParserUtil {

    private JsonParserUtil(){
    }

    /**
     * Parses and retrieves a list of parent data from a JSON file.
     *
     * @return A list of Parent objects representing parsed parent data.
     */
    public static List<Parent> getParentData() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Parent> result =  new ArrayList<>();
        try {
            // Read JSON file and convert to Java object
            InputStream jsonInputStream = JsonParserUtil.class.getResourceAsStream("/parent.json");
            Parent parent = objectMapper.readValue(jsonInputStream, Parent.class);

            result = parent.getData();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Parses and retrieves a list of child data from a JSON file.
     *
     * @return A list of Child objects representing parsed child data.
     */
    public static List<Child> getChildData() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Child> transactions = new ArrayList<>();
        try {
            // Read JSON file and convert to Java object
            InputStream jsonInputStream = JsonParserUtil.class.getResourceAsStream("/child.json");
            Child child = objectMapper.readValue(jsonInputStream, Child.class);

            transactions = child.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
