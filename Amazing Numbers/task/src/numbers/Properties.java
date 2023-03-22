package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public enum Properties {
    EVEN("even") {
        @Override
        public boolean apply(String input) {
            return input.matches(".*[02468]$");
        }
    },
    ODD("odd") {
        @Override
        public boolean apply(String input) {
            return !input.matches(".*[02468]$");
        }
    },
    BUZZ("buzz") {
        @Override
        public boolean apply(String input) {
            return Long.parseLong(input) % 7 == 0 || input.charAt(input.length() - 1) == '7';
        }
    },
    DUCK("duck") {
        @Override
        public boolean apply(String input) {
            return input.matches("^[1-9]+0.*?$");
        }
    },
    PALINDROMIC("palindromic") {
        @Override
        public boolean apply(String input) {
            return input.equals(new StringBuilder(input).reverse().toString());
        }
    },
    GAPFUL("gapful") {
        @Override
        public boolean apply(String input) {
            return input.matches("^\\d{3,}$")
                    && Long.parseLong(input) % Long.parseLong(input.charAt(0)+""+input.charAt(input.length()-1))==0;
        }
    },
    SPY("spy") {
        @Override
        public boolean apply(String input) {
            long sum = 0;
            long times = 1;
            for (char c : input.toCharArray()) {
                sum += c - '0';
                times *= (c - '0');
            }
            return sum == times;
        }
    },
    SQUARE("square") {
        @Override
        public boolean apply(String input) {
            double d = Math.sqrt(Long.parseLong(input));
            return d == (long)d;
        }
    },
    SUNNY("sunny") {
        @Override
        public boolean apply(String input) {
            return Properties.SQUARE.apply((Long.parseLong(input)+1)+"");
        }
    },
    JUMPING("jumping") {
        @Override
        public boolean apply(String input) {
            if (input.matches("^\\d$")) return true;
            char[] chars = input.toCharArray();
            return IntStream.range(0, chars.length-1).allMatch(i ->  Math.abs(chars[i] - chars[i + 1]) == 1);
        }
    },
    HAPPY("happy") {
        @Override
        public boolean apply(String input) {
            List<String> l = new ArrayList<>();
            while (!input.equals("1") && !l.contains(input)) {
                l.add(input);
                int sum = 0;
                for (char c : input.toCharArray()) {
                    sum += Math.pow(c - '0', 2);
                }
                input = sum+"";
            }
            return input.equals("1");
        }
    },
    SAD("sad") {
        @Override
        public boolean apply(String input) {
            return !Properties.HAPPY.apply(input);
        }
    };

    public boolean value;
    public final String name;

    Properties(String name) {
        this.name = name;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public abstract boolean apply(String input);

    public static Properties getProperty(String propName) {
        try {
            return valueOf((propName.startsWith("-") ? propName.substring(1) : propName).toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
