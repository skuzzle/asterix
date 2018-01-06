# asterix
Roman numeral parsing and formatting for Java

## Usage

*Parse a roman numeral which comes in arbitrary format*
```java
int value = RomanNumeralType.LENIENT.parse("MMXVIII");
// value is 2018
value = RomanNumeralType.LENIENT.parse("MMXIIX");
// value is 2018
```

*Format an integer number to specific roman numeral*
```java
String formatted = RomanNumeralType.SINGLE_SUBTRACT.format(2018);
// formatted is "MMXVIII"
formatted = RomanNumeralType.DOUBLE_SUBTRACT.format(2018);
// formatted is "MMXIIX"
```

*Use standard Java `NumberFormat` for formatting and parsing*
```
NumberFormat nf = RomanNumeralType.SINGLE_SUBTRACT.getNumberFormat();
String formatted = nf.format(2018);
```

*Convert from one roman numeral notation to another*
```
String reformatted = RomanNumeralType.SINGLE_SUBTRACT.convertTo(RomanNumeralType.DOUBLE_SUBTRACT, "XXX");
// reformatted is "XXL"
```