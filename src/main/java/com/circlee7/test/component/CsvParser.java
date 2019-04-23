package com.circlee7.test.component;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvFactory;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvParser {

    private final CsvMapper csvMapper;



    public CsvParser(){
        CsvFactory csvFactory = new CsvFactory();
        csvFactory.enable(com.fasterxml.jackson.dataformat.csv.CsvParser.Feature.TRIM_SPACES);
        // csvFactory.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        csvFactory.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
        csvMapper = new CsvMapper(csvFactory);
    }

    public  <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
        ObjectReader reader = csvMapper.readerFor(clazz).with(schema);


        // todo : encoding detect 처리
        return reader.<T>readValues(new InputStreamReader(stream, "euc-kr")).readAll();
    }


}
