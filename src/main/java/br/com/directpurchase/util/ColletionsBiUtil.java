package br.com.directpurchase.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ColletionsBiUtil<T> {

    public Map<String, List<T>> groupMapStringField(final List<Object[]> list, final int groupArrayPosition,
            final ObjectBiGroup<T> objectBiGroup) {

        Map<String, List<T>> map = new LinkedHashMap<>();

        list.stream().forEach(r -> {
            String field = (String) r[groupArrayPosition];

            T object = objectBiGroup.newObject(r);
            if (!map.containsKey(field)) {
                map.put(field, new ArrayList<>());
            }

            List<T> lsField = map.get(field);
            lsField.add(object);
        });
        return map;
    }

    public String getValueField(T t, String fieldName) {
        for (Field f : Arrays.asList(t.getClass().getDeclaredFields())) {
            if (f.getName().equals(fieldName)) {
                try {
                    f.setAccessible(true);
                    return f.get(t).toString();
                } catch (IllegalArgumentException | IllegalAccessException e1) {
                    log.error("Erro ao executar getValueField ", e1);
                }
            }
        }
        return null;
    }

    public void setValueField(T t, String fieldName, Object value) {
        Field field;
        try {
            field = t.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(t, value);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            log.error("Erro ao executar setValueField", e);
        }
    }

    public List<T> groupListSumValue(final List<T> list, final String fieldName) {

        List<T> auxList = new ArrayList<>();
        for (T t : list) {
            if (auxList.contains(t)) {
                int index = auxList.indexOf(t);
                T o = auxList.get(index);

                String newValue = getValueField(t, fieldName);
                BigDecimal newFieldValue = (newValue == null || newValue.isEmpty() || "null".equals(newValue)
                        ? BigDecimal.ZERO
                        : new BigDecimal(newValue));

                String value = getValueField(o, fieldName);
                BigDecimal fieldValue = (value == null || value.isEmpty() || value.equals("null") ? BigDecimal.ZERO
                        : new BigDecimal(value));

                BigDecimal bigDecimal = fieldValue.add(newFieldValue);
                setValueField(o, fieldName, bigDecimal);
                auxList.set(index, o);
            } else {
                auxList.add(t);
            }
        }
        return auxList;
    }

    public Map<String, List<T>> sortMapByStringKey(Map<String, List<T>> map) {

        return map.entrySet().stream().sorted(Map.Entry.<String, List<T>>comparingByKey()).collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public Map<String, Object> sortMapByKeyObject(Map<String, Object> map) {

        return map.entrySet().stream().sorted(Map.Entry.<String, Object>comparingByKey()).collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @FunctionalInterface
    public interface ObjectBiGroup<T> {

        public abstract T newObject(Object... fields);
    }

    @FunctionalInterface
    public interface ObjectDateBiGroup<T> {

        public abstract T newObject(String d, Object... fields);
    }

}
