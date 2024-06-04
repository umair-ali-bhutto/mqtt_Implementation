package com.mportal.ws.classes;

import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Hex;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.entity.TapNPayTransactions;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.model.TapNPayModel;
import com.ag.mportal.services.TapNPayTransactionService;
import com.ag.mportal.services.WisherTap;
import com.ag.mportal.util.String_to_bcd;
import com.ag.mportal.util.TapNPayEncDecUtil;
import com.ag.mportal.util.TapNPayUtil;

@Component("com.mportal.ws.classes.WsTapNPayUpiVoid")
public class WsTapNPayUpiVoid implements WisherTap {

	@Autowired
	TapNPayTransactionService tapNPayTransactionServiceImpl;

	@Override
	public ISO8583Model doProcess(RequestModel rm, TapNPayModel tap, TapNPayRoutingConfig routing, String stan,
			String invoiceNumber) {
		try {
			String recId = tap.getCardNumber();
			TapNPayTransactions saleTxn = tapNPayTransactionServiceImpl.fetchTxnByRecId(Long.parseLong(recId));
			if (saleTxn != null) {

				SimpleDateFormat sdfTimeFormat = new SimpleDateFormat("HHmmss");
				SimpleDateFormat sdfDateFormat = new SimpleDateFormat("MMdd");

				String peckagerPath = routing.getIsoPeckagerPath();
				boolean loggerEnable = Boolean.parseBoolean(routing.getLogger());
				String nacIp = routing.getNacIp();
				int nacPort = Integer.parseInt(routing.getNacPort());
				String isoTpdu = routing.getIsoTpdu();

				// Configuration Values
				String f0 = routing.getIsoMessage();
				String f2 = extractCardNumber(TapNPayEncDecUtil.decrypt(saleTxn.getTrack2Data()));
				String f3 = routing.getIsoTxnType(); // To be defined by HOST ITS SALE TXN
				String f4 = amountParserSender(saleTxn.getTxnAmount() + ""); // Amount
				String f11 = saleTxn.getStan(); // STAN
				String f12 = sdfTimeFormat.format(saleTxn.getTxnDate()); // TIME (TO BE GENERATED BY MW)
				String f13 = sdfDateFormat.format(saleTxn.getTxnDate()); // DATE (TO BE GENERATED BY MW)
				String f22 = routing.getIsoPosEntryMode(); // POS ENTRY MODE
				String f24 = isoTpdu.substring(2, 6); // NII FROM TPDU
				String f25 = routing.getIsoPointServiceCode(); // POINT OF SERVICE CONDITION CODE
				String f35 = saleTxn.getTrack2Data(); // TRACK 2 DATA FROM CARD
				String f37 = saleTxn.getRrn(); // TRACK 2 DATA FROM CARD
				String f38 = saleTxn.getAuthId(); // TRACK 2 DATA FROM CARD
				String f41 = saleTxn.getTid(); // TID FROM APP
				String f42 = saleTxn.getMid(); // MID FROM APP
				String f61 = routing.getIsoPrviateData(); // PRIVATE DATA FROM HOST
				String f62 = saleTxn.getInvoiceNumber();// INVOICE

				ISOMsg mRequest = new ISOMsg();
				GenericPackager packager = new GenericPackager(peckagerPath);
				mRequest.setPackager(packager);
				mRequest.set(0, f0);
				mRequest.set(2, String_to_bcd.convert(f2));
				mRequest.set(3, String_to_bcd.convert(f3));
				mRequest.set(4, String_to_bcd.convert(f4));
				mRequest.set(11, String_to_bcd.convert(f11));
				mRequest.set(12, String_to_bcd.convert(f12));
				mRequest.set(13, String_to_bcd.convert(f13));
				mRequest.set(22, String_to_bcd.convert(f22));
				mRequest.set(24, String_to_bcd.convert(f24));
				mRequest.set(25, String_to_bcd.convert(f25));
				mRequest.set(35, String_to_bcd.convert(f35.length() + f35));
				mRequest.set(37, String_to_bcd.convert(f37.length() + f37));
				mRequest.set(38, String_to_bcd.convert(f38.length() + f38));
				mRequest.set(41, f41);
				mRequest.set(42, f42);
				mRequest.set(61, String_to_bcd.convert("000" + f61.length() + Hex.encodeHexString(f61.getBytes())));
				mRequest.set(62, String_to_bcd.convert("000" + f62.length() + Hex.encodeHexString(f62.getBytes())));

				byte[] data = mRequest.pack();
				String isoMessageDataWithoutField55 = TapNPayUtil.formatByte(data, false, false);
				mRequest.setHeader(String_to_bcd.convert(isoTpdu + isoMessageDataWithoutField55));

				ISO8583Model mdl = new ISO8583Model();
				mdl.setHeader(isoTpdu);
				mdl.setIsoMessageRequest(mRequest);
				mdl.setLoggerEnable(loggerEnable);
				mdl.setNacIp(nacIp);
				mdl.setNacPort(nacPort);
				mdl.setPeckager(peckagerPath);
				return mdl;
			} else {
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	private String extractCardNumber(String clearPan) {
		String finalCardNumber = clearPan.trim().substring(0, clearPan.trim().indexOf("D"));
		return finalCardNumber;
	}

	private static String amountParserSender(String amount) {
		try {

			int passLeng = amount.substring(amount.lastIndexOf(".") + 1, amount.length()).length();
			if (passLeng == 1) {
				amount = amount + "0";
			} else if (passLeng > 2) {
				amount = amount.substring(0, amount.length() - 1);
			}
			String k = amount.replace(".", "");
			String zs = "";
			for (int z = k.length(); z < 12; z++) {
				zs += "0";
			}
			return zs + k;
		} catch (Exception e) {
			e.printStackTrace();
			return amount;
		}

	}

}