package numbers;

import java.util.*;
import java.util.stream.IntStream;

public class NumberProperties {

    public final String number;
    public final Properties[] properties;

    public static final List<List<Properties>> exclude = List.of(
            List.of(Properties.EVEN, Properties.ODD),
            List.of(Properties.DUCK, Properties.SPY),
            List.of(Properties.SUNNY, Properties.SQUARE),
            List.of(Properties.SAD, Properties.HAPPY)
    );

    public NumberProperties(String number) {
        this.number = number;
        properties = Properties.values();
        for (Properties p : properties) {
            p.setValue(p.apply(number));
        }
    }

    static void elementsProcess(String[] values) {
        boolean isFirstValid = values[0].matches("^[1-9]\\d*$");
        boolean isSecondValid = values[1].matches("^[1-9]\\d*$");
        if (isFirstValid && isSecondValid) {
            Map<String, List<Properties> > map = new HashMap<>();
            map.put("include", new ArrayList<>());
            map.put("exclude", new ArrayList<>());
            if (values.length >= 3) IntStream.range(2, values.length)
                    .forEach(i -> map.get(values[i].startsWith("-") ? "exclude" : "include")
                            .add(Properties.getProperty(values[i])));
            if (handleErrorNullProperties(values, map.get("include"))
                    || handleErrorNullProperties(values, map.get("exclude")))
                return;
            if (handleErrorProperties(map)) return;
            for (long i = Long.parseLong(values[0]), count = 0; count < Long.parseLong(values[1]); ++i) {
                final NumberProperties np = new NumberProperties(String.valueOf(i));
                if (values.length == 2 || allMatchProperties(map, np)) {
                    np.inLine(); ++count;
                }
            }
        } else {
            if (!isFirstValid) System.out.println("The first parameter should be a natural number or zero.");
            if (!isSecondValid) System.out.println("The second parameter should be a natural number or zero.");
        }
    }

    private static boolean allMatchProperties(Map<String, List<Properties> > map, NumberProperties np) {
        for (Properties prop : map.get("include")) {
            Properties property = np.properties[List.of(np.properties).indexOf(prop)];
            if (!property.value) return false;
        }
        for (Properties prop : map.get("exclude")) {
            Properties property = np.properties[List.of(np.properties).indexOf(prop)];
            if (property.value) return false;
        }
        return true;
    }

    private static boolean handleErrorNullProperties(String[] values, List<Properties> props) {
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < props.size(); i++) {
            if (props.get(i) == null) index.add(i);
        }
        if (index.size() > 0) {
            msgErrorWrongProperties(values, index);
            return true;
        }
        return false;
    }

    private static void msgErrorWrongProperties(String[] values, List<Integer> index) {
        String[] arr = index.stream().map(i -> values[i]).toArray(String[]::new);
        System.out.printf("""
                            The propert%s [%s] %s wrong.
                            Available properties: [%s]
                            """,
                arr.length > 1 ? "ies" : "y",
                arr.length > 1 ? String.join(", ", arr) : arr[0],
                arr.length > 1 ? "are" : "is",
                String.join(", ", Arrays.stream(Properties.values()).map(Enum::name).toArray(String[]::new)));
    }

    private static boolean handleErrorProperties(Map<String, List<Properties> > map) {
        for (Properties e : map.get("include")) {
            if (map.get("exclude").contains(e)) {
                msgErrorExcludeProperties(e, e);
                return true;
            }
        }
        for (String key : map.keySet()) {
            for (List<Properties> element : exclude) {
                if (map.get(key).contains(element.get(0)) && map.get(key).contains(element.get(1))
                        && !element.get(0).equals(element.get(1))) {
                    msgErrorExcludeProperties(element.get(0), element.get(1));
                    return true;
                }
            }
        }
        return false;
    }

    private static void msgErrorExcludeProperties(Properties first, Properties second) {
        System.out.printf("""
                    The request contains mutually exclusive properties: [%s, %s]
                    There are no numbers with these properties.
                    """, first.name(), second.name());
    }

    public void inLine() {
        System.out.println(number + " is " + String.join(", ",
                Arrays.stream(properties).filter(p -> p.value).map(p -> p.name).toArray(String[]::new)));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\nProperties of %s\n".formatted(number));
        for (Properties p : properties) {
            str.append("\t%s: %b\n".formatted(p.name, p.value));
        }
        return str.toString();
    }
}
