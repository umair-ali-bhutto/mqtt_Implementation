package com.ag.mportal.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import org.jpos.iso.ISOMsg;
import org.springframework.stereotype.Component;

import com.ag.mportal.entity.TapNPayRoutingConfig;

@Component("TapNPayUtil")
public class TapNPayUtil {

	public static HashMap<String, String> tapNPayResponseCodeMap = null;
	public static HashMap<String, TapNPayRoutingConfig> tapNPayRoutingMap = null;

	public static String convertIsoToString(ISOMsg m) {
		ByteArrayOutputStream str = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(str);
		m.dump(ps, "");
		String request = "\n" + str.toString();
		return request;
	}

	public static String mask(String sk) {
		try {
			return sk.substring(0, 5) + "*******" + sk.substring(12, 16);
		} catch (Exception e) {
			return sk;
		}

	}

	public static String formatByte(byte[] pByte, boolean pSpace, boolean pTruncate) {
		String result;
		if (pByte == null) {
			result = "";
		} else {
			int i = 0;
			if (pTruncate) {
				while (i < pByte.length && pByte[i] == 0) {
					++i;
				}
			}

			if (i < pByte.length) {
				int sizeMultiplier = pSpace ? 3 : 2;
				char[] c = new char[(pByte.length - i) * sizeMultiplier];

				for (int j = 0; i < pByte.length; ++j) {
					byte b = (byte) ((pByte[i] & 240) >> 4);
					c[j] = (char) (b > 9 ? b + 55 : b + 48);
					b = (byte) (pByte[i] & 15);
					++j;
					c[j] = (char) (b > 9 ? b + 55 : b + 48);
					if (pSpace) {
						++j;
						c[j] = ' ';
					}

					++i;
				}

				result = pSpace ? new String(c, 0, c.length - 1) : new String(c);
			} else {
				result = "";
			}
		}

		return result;
	}

	public static String getTagLength(String value) {
		String lenth = "00";
		int tempLenth = value.length() / 2;
		if (tempLenth < 10) {
			lenth = "0" + tempLenth;
		} else {
			lenth = tempLenth + "";
		}
		return lenth;
	}

	public static String getTagLengthWithoutDivision(String value) {
		String lenth = "00";
		int tempLenth = value.length();
		if (tempLenth < 10) {
			lenth = "0" + tempLenth;
		} else {
			lenth = tempLenth + "";
		}
		return lenth;
	}

	public static byte[] fromString(String pData) {
		if (pData == null) {
			throw new IllegalArgumentException("Argument can't be null");
		} else {
			StringBuilder sb = new StringBuilder(pData);
			int j = 0;

			for (int i = 0; i < sb.length(); ++i) {
				if (!Character.isWhitespace(sb.charAt(i))) {
					sb.setCharAt(j++, sb.charAt(i));
				}
			}

			sb.delete(j, sb.length());
			if (sb.length() % 2 != 0) {
				throw new IllegalArgumentException("Hex binary needs to be even-length :" + pData);
			} else {
				byte[] result = new byte[sb.length() / 2];
				j = 0;

				for (int i = 0; i < sb.length(); i += 2) {
					result[j++] = (byte) ((Character.digit(sb.charAt(i), 16) << 4)
							+ Character.digit(sb.charAt(i + 1), 16));
				}

				return result;
			}
		}
	}

	public static EmvCardScheme findCardScheme(final String pAid, final String pCardNumber) {
		EmvCardScheme type = EmvCardScheme.getCardTypeByAid(pAid);
		if (type == EmvCardScheme.CB) {
			type = EmvCardScheme.getCardTypeByCardNumber(pCardNumber);
		}
		return type;
	}

	public static String getIsoErrorCode(String key) {
		if (getTapNPayResponseCodeMap().containsKey(key)) {
			return getTapNPayResponseCodeMap().get(key);
		} else {
			return "N/A";
		}

	}

	public static TapNPayRoutingConfig getRouting(String corpId, String txnType, String cardScheme) {
		String key = corpId + "|" + txnType + "|" + cardScheme;
		if (getTapNPayRoutingMap().containsKey(key)) {
			return getTapNPayRoutingMap().get(key);
		} else {
			return null;
		}

	}

	public static HashMap<String, String> getTapNPayResponseCodeMap() {
		return tapNPayResponseCodeMap;
	}

	public static void setTapNPayResponseCodeMap(HashMap<String, String> tapNPayResponseCodeMap) {
		TapNPayUtil.tapNPayResponseCodeMap = tapNPayResponseCodeMap;
	}

	public static HashMap<String, TapNPayRoutingConfig> getTapNPayRoutingMap() {
		return tapNPayRoutingMap;
	}

	public static void setTapNPayRoutingMap(HashMap<String, TapNPayRoutingConfig> tapNPayRoutingMap) {
		TapNPayUtil.tapNPayRoutingMap = tapNPayRoutingMap;
	}

}
