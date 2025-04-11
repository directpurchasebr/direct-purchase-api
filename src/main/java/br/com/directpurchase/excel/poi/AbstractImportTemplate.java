package br.com.directpurchase.excel.poi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractImportTemplate<T> implements ImportTamplate<T> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected String getString(Object o) {
        return (o instanceof String) ? ((String) o).trim() : null;
    }

    protected Number getNumber(Object o) {
        return (o instanceof Number) ? (Number) o : null;
    }

    @Override
    public List<T> convertTemplate(Map<Integer, List<Object>> excel) {
        List<T> result = new ArrayList<>();

        for (Map.Entry<Integer, List<Object>> entry : excel.entrySet()) {
            try {
                T item = mapRow(entry.getValue());
                if (item != null) {
                    result.add(item);
                }
            } catch (Exception e) {
                log.warn("Erro ao processar linha {}: {}", entry.getKey(), e.getMessage());
            }
        }

        return result;
    }

    protected abstract T mapRow(List<Object> row) throws Exception;
}
