package com.dsa360.api.utility;

import java.text.DecimalFormat;

/**
 * @author RAM
 *
 */
public class NumberToWords {

	private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty", " fifty", " sixty",
			" seventy", " eighty", " ninety" };

	private static final String[] numNames = { "", " one", " two", " three", " four", " five", " six", " seven",
			" eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
			" seventeen", " eighteen", " nineteen" };

	private NumberToWords() {
	}

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[number] + " hundred" + soFar;
	}

	public static String capitalizeWord(String str) {
		String words[] = str.split("\\s");
		var capitalizeWord = new StringBuilder() ;
		for (String w : words) {
			var first = w.substring(0, 1);
			var afterfirst = w.substring(1);
			capitalizeWord.append(first.toUpperCase() + afterfirst + " ") ;
		}
		return capitalizeWord+ " Rupees Only";
	}

	public static String convert(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "zero";
		}

		var snumber = Long.toString(number);

		// pad with "0"
		var mask = "000000000000";
		var df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		var billions = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		var millions = Integer.parseInt(snumber.substring(3, 6));
		// nnnnnnXXXnnn
		var	hundredThousands = Integer.parseInt(snumber.substring(6, 9));
		// nnnnnnnnnXXX
		var thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
			break;
		default:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
			break;
		default:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "one thousand ";
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return capitalizeWord(result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ")).trim();
	}

}