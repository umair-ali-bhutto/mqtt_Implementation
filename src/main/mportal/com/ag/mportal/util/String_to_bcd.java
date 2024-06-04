package com.ag.mportal.util;

public class String_to_bcd {

	public static byte[] convert(String str) {
		int length;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		int i5 = 0;
		int i6 = 0;
		int i7 = 0;
		int i8 = 0;
		int i9 = 1;
		int i10 = 1;
		int i11 = 2;
		int chk1 = 0;
		int chk2 = 0;

		length = str.length();
		if (length % 2 > 0) {
			length = (length + 1);
			str = str + "0";
		} else {
			length = (length);
		}

		byte[] bytearray = new byte[length / 2];

		for (int i = 0; i < length / 2; i++) {
			i2 = 0;
			i3 = 0;
			i5 = 0;

			for (int x = 1; x <= 2; x++)

			{
				if (x == 1) {

					switch (str.substring(i8, i9).charAt(0)) {
					case 'A':
						i2 = 10;
						chk1 = 1;
						break;
					case 'B':
						i2 = 11;
						chk1 = 1;
						break;
					case 'C':
						i2 = 12;
						chk1 = 1;
						break;
					case 'D':
						i2 = 13;
						chk1 = 1;
						break;
					case 'E':
						i2 = 14;
						chk1 = 1;
						break;
					case 'F':
						i2 = 15;
						chk1 = 1;
						break;
					default:
						chk1 = 0;
					}

					if (chk1 == 0) {
						i2 = Integer.parseInt(str.substring(i8, i9));
					}
					i2 = i2 << 4;
					i8 = i8 + 2;
					i9 = i9 + 2;
				}

				else {

					switch (str.substring(i10, i11).charAt(0)) {
					case 'A':
						i3 = 10;
						chk2 = 1;
						break;
					case 'B':
						i3 = 11;
						chk2 = 1;
						break;
					case 'C':
						i3 = 12;
						chk2 = 1;
						break;
					case 'D':
						i3 = 13;
						chk2 = 1;
						break;
					case 'E':
						i3 = 14;
						chk2 = 1;
						break;
					case 'F':
						i3 = 15;
						chk2 = 1;
						break;
					default:
						chk2 = 0;
					}

					if (chk2 == 0) {
						i3 = Integer.parseInt(str.substring(i10, i11));
					}
					i10 = i10 + 2;
					i11 = i11 + 2;

				}

				if ((byte) i2 < 0) {
					// i2=remove_sign_bit(i2);
					i2 = i2 & 0xFF;
				}

				if ((byte) i3 < 0) {
					// i3=remove_sign_bit(i3);
					i3 = i3 & 0xFF;
				}

				if (x == 2) {
					i5 = i2 | i3;
					bytearray[i4] = (byte) i5;
					i4++;
				}

			}

		}
		return (bytearray);
	}

	public static int remove_sign_bit(int i1) {
		i1 = i1 & 0xFF;
		return (i1);
	}

	public static String int_to_ascii(char c1) {

		switch (c1) {

		case '0':
			return ("30");
		case '1':
			return ("31");
		case '2':
			return ("32");
		case '3':
			return ("33");
		case '4':
			return ("34");
		case '5':
			return ("35");
		case '6':
			return ("36");
		case '7':
			return ("37");
		case '8':
			return ("38");
		case '9':
			return ("39");
		case 'B':
			return ("42");
		case ' ':
			return ("20");

		}
		return ("");
	}

	public static byte[] char_to_hex(String s) {
		byte[] b = new byte[s.length()];
		for (int i = 0; i < s.length(); i++) {
			Character c = new Character(s.charAt(i));
			int ascii = (int) c.charValue();
			String hex = Integer.toHexString(ascii);
			b[i] = (byte) Integer.parseInt(hex, 16);
		}
		return (b);
	}
}
