package com.ag.fuel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the DETL_FLEET_CARD_CPY database table.
 * 
 */
//@Entity
@Table(name = "FUEL_CARDS")
@NamedQueries({ @NamedQuery(name = "FuelCards.findAll", query = "SELECT d FROM FuelCards d")
		 })
public class FuelCards implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "co_code")
	private int coCode;

	@Column(name = "pan")
	private int pan;

	@Column(name = "card_no")
	private int cardNo;

	@Column(name = "issue_date")
	private Timestamp issueDate;

	@Column(name = "from_date")
	private Timestamp fromDate;

	@Column(name = "exp_date")
	private Timestamp expDate;

	@Column(name = "card_type")
	private String cardType;

	@Column(name = "card1")
	private String card1;

	@Column(name = "card2")
	private String card2;

	@Column(name = "card3")
	private String card3;

	@Column(name = "card4")
	private String card4;

	@Column(name = "card5")
	private String card5;

	@Column(name = "card_status")
	private String cardStatus;

	@Column(name = "print_status")
	private String printStatus;

	@Column(name = "printed_on")
	private Timestamp printedOn;

	@Column(name = "couriered")
	private String couriered;

	@Column(name = "coureir_date")
	private Timestamp coureirDate;

	@Column(name = "devlivered")
	private String devlivered;

	@Column(name = "delivered_date")
	private Timestamp deliveredDate;

	@Column(name = "cr_on")
	private Timestamp crOn;

	@Column(name = "cr_by")
	private String crBy;

	@Column(name = "upd_on")
	private Timestamp updOn;

	@Column(name = "upd_by")
	private String updBy;

	@Column(name = "exported")
	private String exported;

	@Column(name = "exported_by")
	private String exportedBy;

	@Column(name = "exported_on")
	private Timestamp exportedOn;

	@Column(name = "batch_no")
	private String batchNo;

	@Column(name = "suppl_card")
	private String supplCard;

	@Column(name = "primary_pan")
	private int primaryPan;

	@Column(name = "name_on_card")
	private String nameOnCard;

	@Column(name = "c_gender")
	private String cGender;

	@Column(name = "c_date_of_birth")
	private Timestamp cDateOfBirth;

	@Column(name = "f_veh_reg_no")
	private String fVehRegNo;

	@Column(name = "qty_amt_txn")
	private int qtyAmtTxn;

	@Column(name = "qty_amt_day")
	private int qtyAmtDay;

	@Column(name = "qty_amt_week")
	private int qtyAmtWeek;

	@Column(name = "qty_amt_month")
	private int qtyAmtMonth;

	@Column(name = "nonfuel_month")
	private int nonfuelMonth;

	@Column(name = "month_per")
	private int monthPer;

	@Column(name = "max_daily_useage")
	private int maxDailyUseage;

	@Column(name = "max_weekly_useage")
	private int maxWeeklyUseage;

	@Column(name = "days")
	private String days;

	@Column(name = "f_veh_category")
	private String fVehCategory;

	@Column(name = "fule_tank_desc")
	private String fuleTankDesc;

	@Column(name = "veh_desc")
	private String vehDesc;

	@Column(name = "co_accno")
	private int coAccno;

	@Column(name = "outlet")
	private String outlet;

	@Column(name = "reg_no")
	private String regNo;

	@Column(name = "serv_code")
	private String servCode;

	@Column(name = "pvv")
	private String pvv;

	@Column(name = "cvv")
	private String cvv;

	@Column(name = "reser")
	private String reser;

	@Column(name = "add_date")
	private String addDate;

	@Column(name = "l_date")
	private String lDate;

	@Column(name = "l_id")
	private String lId;

	@Column(name = "l_count")
	private String lCount;

	@Column(name = "l_time")
	private String lTime;

	@Column(name = "midd_name")
	private String middName;

	@Column(name = "marital_status")
	private String maritalStatus;

	@Column(name = "res_address")
	private String resAddress;

	@Column(name = "res_tel")
	private String resTel;

	@Column(name = "off_tel")
	private String offTel;

	@Column(name = "ext")
	private String ext;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "email")
	private String email;

	@Column(name = "loy_pan")
	private String loyPan;

	@Column(name = "activation_date")
	private Timestamp activationDate;

	@Column(name = "first_billing")
	private Timestamp firstBilling;

	@Column(name = "bill_id")
	private String billId;

	@Column(name = "exported_tts")
	private String exportedTts;

	@Column(name = "exported_tts_by")
	private String exportedTtsBy;

	@Column(name = "exported_tts_on")
	private Timestamp exportedTtsOn;
	@Column(name = "qty_amt_year")
	private int qtyAmtYear;

	@Column(name = "issue_type")
	private String issueType;

	@Column(name = "old_exp_date")
	private Timestamp oldExpDate;
	@Column(name = "member_id")
	private String memberId;

	@Column(name = "status_code")
	private String statusCode;

	@Column(name = "re_print")
	private String rePrint;

	@Column(name = "pri_card_flag")
	private String priCardFlag;

	@Column(name = "pri_link")
	private String priLink;

	@Column(name = "supp_desc")
	private String suppDesc;

	@Column(name = "c_first_name")
	private String cFirstName;

	@Column(name = "c_last_name")
	private String cLastName;

	@Column(name = "c_nic")
	private String cNic;

	@Column(name = "c_relation_with_pri_cardholder")
	private String cRelationWithPriCardholder;

	@Column(name = "city")
	private String city;

	@Column(name = "supplimentary")
	private String supplimentary;

	@Column(name = "passport_no")
	private String passportNo;

	@Column(name = "suppli_limit_percentage")
	private int suppliLimitPercentage;

	@Column(name = "suppli_pan")
	private int suppliPan;

	@Column(name = "prod_code")
	private String prodCode;

	@Column(name = "prod_code1")
	private String prodCode1;

	@Column(name = "prod_code2")
	private String prodCode2;

	@Column(name = "prod_code3")
	private String prodCode3;

	@Column(name = "prod_code5")
	private String prodCode5;

	@Column(name = "prodfleet")
	private int prodfleet;

	@Column(name = "prod_code6")
	private String prodCode6;

	@Column(name = "commercial_flag")
	private String commercialFlag;

	@Column(name = "amu_flag")
	private String amuFlag;

	@Column(name = "mproduct_flag")
	private String mproductFlag;

	@Column(name = "ft_flag")
	private String ftFlag;

	@Column(name = "fcear_flag")
	private String fcearFlag;

	@Column(name = "fcearm")
	private int fcearm;

	@Column(name = "smart_card_flg")
	private String smartCardFlg;

	@Column(name = "renewal")
	private String renewal;

	@Column(name = "vision_random")
	private String visionRandom;

	@Column(name = "emailid")
	private String emailid;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "push_smsf")
	private String pushSmsf;

	@Column(name = "pull_smsf")
	private String pullSmsf;

	@Column(name = "emailf")
	private String emailf;

	@Column(name = "txnreport_emailf")
	private String txnreportEmailf;

	@Column(name = "push_smsf_upd_by")
	private String pushSmsfUpdBy;

	@Column(name = "push_smsf_upd_on")
	private Timestamp pushSmsfUpdOn;

	@Column(name = "pull_smsf_upd_by")
	private String pullSmsfUpdBy;

	@Column(name = "pull_smsf_upd_on")
	private Timestamp pullSmsfUpdOn;

	@Column(name = "emailf_upd_by")
	private String emailfUpdBy;

	@Column(name = "emailf_upd_on")
	private Timestamp emailfUpdOn;
	@Column(name = "txnreport_emailf_upd_by")
	private String txnreportEmailfUpdBy;

	@Column(name = "txnreport_emailf_upd_on")
	private Timestamp txnreportEmailfUpdOn;

	@Column(name = "mno_code")
	private String mnoCode;

	@Column(name = "frequency")
	private String frequency;

	@Column(name = "prod_code11")
	private String prodCode11;

	@Column(name = "prod_code4")
	private String prodCode4;

	@Column(name = "prod_code7")
	private String prodCode7;

	@Column(name = "prod_code8")
	private String prodCode8;

	@Column(name = "prod_code9")
	private String prodCode9;

	@Column(name = "prod_code10")
	private String prodCode10;

	@Column(name = "prod_code12")
	private String prodCode12;

	@Column(name = "prod_code13")
	private String prodCode13;

}