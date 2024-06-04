package com.ag.mportal.services;

import java.util.Date;
import java.util.List;

import com.ag.mportal.entity.TxnLog;

public interface TxnLogsService {

	public long insertLog(TxnLog que);

	public List<TxnLog> fetchTxnLogDetails(List<String> merchant, String terminal, Date from, Date to,
			String[] posUpdate, int numberOfRows, int pageNumber);

	public String fetchTxnLogDetailsCount(List<String> merchant, String terminal, Date from, Date to,
			String[] posUpdate);

	public List<TxnLog> fetchMqttTxnLogDetails(String merchant, String terminal, String serialNumber);

}
