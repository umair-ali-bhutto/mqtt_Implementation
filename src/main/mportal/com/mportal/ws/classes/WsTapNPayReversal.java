package com.mportal.ws.classes;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TapNPayRoutingConfig;
import com.ag.mportal.entity.TapNPaySaf;
import com.ag.mportal.entity.TapNPayTransactions;
import com.ag.mportal.model.ISO8583Model;
import com.ag.mportal.services.TapNPaySafService;
import com.ag.mportal.services.TapNPayTransactionService;
import com.ag.mportal.util.ISO8583Util;
import com.ag.mportal.util.String_to_bcd;
import com.ag.mportal.util.TapNPayUtil;

@Component("com.mportal.ws.classes.WsTapNPayReversal")
public class WsTapNPayReversal {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	TapNPaySafService tapNPaySafService;

	@Autowired
	TapNPayTransactionService tapNPayTransactionService;
	
	@Autowired
	ISO8583Util iSO8583Util;

	public void doProcessSaf() {
		try {
			List<TapNPaySaf> t = tapNPaySafService.fetchAll();
			for (TapNPaySaf r : t) {
				TapNPayTransactions rec = tapNPayTransactionService.fetchTxnByRecIdForReversal(r.getTapnpayTxnId());
				if (rec != null) {
					TapNPayRoutingConfig trConfig = TapNPayUtil.getRouting("00004", "REVERSAL", "-");
					if (trConfig != null) {
						ISO8583Model m = doProcess(trConfig, rec);
						ISO8583Model responseIso = iSO8583Util.doProcess(m);
						if(responseIso!=null) {
							//Update Here Code
							if(responseIso.getCode().equals("00")) {
								r.setStatus("SUCCESS");
								r.setMinHits(1);
								tapNPaySafService.update(r);
							}else{
								AgLogger.logInfo("ISO Error:"+m.getCode());	
							}
						}else {
							AgLogger.logInfo("Something went wrong.");	
						}
					} else {
						AgLogger.logInfo("No TapNPay Config Found.");
					}
				} else {
					AgLogger.logInfo("No Record Found on ID:" + r.getTapnpayTxnId());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public ISO8583Model doProcess(TapNPayRoutingConfig routing, TapNPayTransactions tns) {
		try {
			
			SimpleDateFormat sdfDate = new SimpleDateFormat("MMdd");

			String peckagerPath = routing.getIsoPeckagerPath();
			boolean loggerEnable = Boolean.parseBoolean(routing.getLogger());
			String nacIp = routing.getNacIp();
			int nacPort = Integer.parseInt(routing.getNacPort());
			String isoTpdu = routing.getIsoTpdu();

			String f11 = tns.getStan();// STAN
			String f62 = tns.getInvoiceNumber();// INVOICE

			// Configuration Values
			String f0 = routing.getIsoMessage();
			String f3 = routing.getIsoTxnType(); // To be defined by HOST ITS SALE TXN
			String f4 = "000000000010"; // Amount

			String f14 = sdfDate.format(tns.getCardExpiriy()); // CARD EXPIRY FROM CARD
			String f22 = routing.getIsoPosEntryMode(); // POS ENTRY MODE
			String f23 = StringUtils.leftPad("1", 4, "0"); // PAN SEQUENCE NUMBER
			String f24 = isoTpdu.substring(2, 6); // NII FROM TPDU
			String f25 = routing.getIsoPointServiceCode(); // POINT OF SERVICE CONDITION CODE
			String f35 = tns.getTrack2Data(); // TRACK 2 DATA FROM CARD
			String f41 = tns.getTid(); // TID FROM APP
			String f42 = tns.getMid(); // MID FROM APP
			String f55 = "-----"; // 2D 2D 2D 2D 2D
			String f61 = routing.getIsoPrviateData(); // PRIVATE DATA FROM HOST

			ISOMsg mRequest = new ISOMsg();
			GenericPackager packager = new GenericPackager(peckagerPath);
			mRequest.setPackager(packager);
			mRequest.set(0, f0);
			mRequest.set(3, String_to_bcd.convert(f3));
			mRequest.set(4, String_to_bcd.convert(f4));
			mRequest.set(11, String_to_bcd.convert(f11));
			mRequest.set(14, String_to_bcd.convert(f14));
			mRequest.set(22, String_to_bcd.convert(f22));
			mRequest.set(23, String_to_bcd.convert(f23));
			mRequest.set(24, String_to_bcd.convert(f24));
			mRequest.set(25, String_to_bcd.convert(f25));
			mRequest.set(35, String_to_bcd.convert(f35.length() + f35));
			mRequest.set(41, f41);
			mRequest.set(42, f42);
			mRequest.set(55, f55);
			mRequest.set(61, String_to_bcd.convert("000" + f61.length() + Hex.encodeHexString(f61.getBytes())));
			mRequest.set(62, String_to_bcd.convert("000" + f62.length() + Hex.encodeHexString(f62.getBytes())));

			/* Field 55 Parsing Ended..... */

			byte[] data = mRequest.pack();
			String isoMessageDataWithoutField55 = TapNPayUtil.formatByte(data, false, false);
			String isoMessageDataWithField55 = isoMessageDataWithoutField55.replaceAll("2D2D2D2D2D",
					"0" + tns.getField55Data().length() / 2 + "" + tns.getField55Data());
			mRequest.setHeader(String_to_bcd.convert(isoTpdu + isoMessageDataWithField55));

			ISO8583Model mdl = new ISO8583Model();
			mdl.setHeader(isoTpdu);
			mdl.setIsoMessageRequest(mRequest);
			mdl.setLoggerEnable(loggerEnable);
			mdl.setNacIp(nacIp);
			mdl.setNacPort(nacPort);
			mdl.setPeckager(peckagerPath);
			mdl.setField55Data(tns.getField55Data().toString());
			return mdl;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

}