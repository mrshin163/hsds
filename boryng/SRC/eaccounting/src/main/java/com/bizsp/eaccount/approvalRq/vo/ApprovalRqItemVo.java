package com.bizsp.eaccount.approvalRq.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ApprovalRqItemVo {
	private String companyCode;
	private String approvalId;
	private String oldApprovalId;
	private Integer seq;
	private String sApprovalId;
	private Integer sApprovalSeq;
	private String customerCode;
	private String customerName;
	private String saupNo;
	private String userId;  //담당자명
	private String userName;
	private String account2Code;
	private String account2Name;
	private String productCode;
	private String productName;
	private String ftrCode;
	private String details;
	private String opinion;
	private String meetReport;
	private String seminarReport;
	private String cardOwnerId;
	private String cardOwnerName;
	private String cardOwnerDeptCode;
	private String cardOwnerDeptName;
	private String cardOwnerTitleCode;
	private String cardOwnerTitleName;
	private String cardOwnerDutyCode;
	private String cardOwnerDutyName;
	private String apGeoraeCand;
	private String cardNum;
	private String authDate;
	private String authNum;
	private String georaeStat;
	private String requestAmount;
	private String georaeColl;
	private String mercName;
	private String settDate;
	private String ownerRegNo;
	private String cardCode;
	private String cardName;
	private String useUserName; //소지자명
	private String authTime;
	private String aquiDate;
	private String georaeCand;
	private String amtAmount;
	private String vatAmount;
	private String serAmount;
	private String freAmount;
	private String amtMdAmount;
	private String vatMdAmount;
	private String georaeGukga;
	private String forAmount;
	private String usdAmount;
	private String aquiRate;
	private String mercSaupNo;
	private String mercAddr;
	private String mercRepr;
	private String mercTel;
	private String mercZip;
	private String mccName;
	private String mccCode;
	private String mccStat;
	private String vatStat;
	private String canDate;
	private String askSite;
	private String siteDate;
	private String askDate;
	private String askTime;
	private String gongjeNoChk;
	private String createDate;
	private String createTime;
	private String sendYn;
	private String aquiTime;
	private String conversionFee;
	private String orgColl;
	private String cardKind;
	private String settDay;
	private String settBankCode;
	private String settAcco;
	private String juminNo;
	private Date financeDate;
	private String finaceSeq;
	private Date junpyoInsDate;
	private String junpyoStCode;
	private String regDatetime; 
	private String regUserId;
	private String updtDatetme;
	private String updtUserId;
	private String financeNo;
	
	private String accountCode;
	private String accountName;
	private String budgetDeptCode;
	private String budgetDeptName;
	
	private List<ApprovalRqLineVo> approvalRqLines;
	
	private List<ApprovalRqAttachVo> approvalRqAttachs;
	
	public List<ApprovalRqLineVo> getApprovalRqLines() {
		return approvalRqLines;
	}
	public void setApprovalRqLines(List<ApprovalRqLineVo> approvalRqLines) {
		this.approvalRqLines = approvalRqLines;
	}
	
	public List<ApprovalRqAttachVo> getApprovalRqAttachs() {
		return approvalRqAttachs;
	}
	
	public String getOldApprovalId() {
		return oldApprovalId;
	}
	public void setOldApprovalId(String oldApprovalId) {
		this.oldApprovalId = oldApprovalId;
	}
	public void setApprovalRqAttachs(List<ApprovalRqAttachVo> approvalRqAttachs) {
		this.approvalRqAttachs = approvalRqAttachs;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBudgetDeptName() {
		return budgetDeptName;
	}
	public void setBudgetDeptName(String budgetDeptName) {
		this.budgetDeptName = budgetDeptName;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getBudgetDeptCode() {
		return budgetDeptCode;
	}
	public void setBudgetDeptCode(String budgetDeptCode) {
		this.budgetDeptCode = budgetDeptCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getsApprovalId() {
		return sApprovalId;
	}
	public void setsApprovalId(String sApprovalId) {
		this.sApprovalId = sApprovalId;
	}
	public Integer getsApprovalSeq() {
		return sApprovalSeq;
	}
	public void setsApprovalSeq(Integer sApprovalSeq) {
		this.sApprovalSeq = sApprovalSeq;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSaupNo() {
		return saupNo;
	}
	public void setSaupNo(String saupNo) {
		this.saupNo = saupNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccount2Code() {
		return account2Code;
	}
	public void setAccount2Code(String account2Code) {
		this.account2Code = account2Code;
	}
	public String getAccount2Name() {
		return account2Name;
	}
	public void setAccount2Name(String account2Name) {
		this.account2Name = account2Name;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getFtrCode() {
		return ftrCode;
	}
	public void setFtrCode(String ftrCode) {
		this.ftrCode = ftrCode;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getMeetReport() {
		return meetReport;
	}
	public void setMeetReport(String meetReport) {
		this.meetReport = meetReport;
	}
	public String getSeminarReport() {
		return seminarReport;
	}
	public void setSeminarReport(String seminarReport) {
		this.seminarReport = seminarReport;
	}
	public String getCardOwnerId() {
		return cardOwnerId;
	}
	public void setCardOwnerId(String cardOwnerId) {
		this.cardOwnerId = cardOwnerId;
	}
	public String getCardOwnerName() {
		return cardOwnerName;
	}
	public void setCardOwnerName(String cardOwnerName) {
		this.cardOwnerName = cardOwnerName;
	}
	public String getCardOwnerDeptCode() {
		return cardOwnerDeptCode;
	}
	public void setCardOwnerDeptCode(String cardOwnerDeptCode) {
		this.cardOwnerDeptCode = cardOwnerDeptCode;
	}
	public String getCardOwnerDeptName() {
		return cardOwnerDeptName;
	}
	public void setCardOwnerDeptName(String cardOwnerDeptName) {
		this.cardOwnerDeptName = cardOwnerDeptName;
	}
	public String getCardOwnerTitleCode() {
		return cardOwnerTitleCode;
	}
	public void setCardOwnerTitleCode(String cardOwnerTitleCode) {
		this.cardOwnerTitleCode = cardOwnerTitleCode;
	}
	public String getCardOwnerTitleName() {
		return cardOwnerTitleName;
	}
	public void setCardOwnerTitleName(String cardOwnerTitleName) {
		this.cardOwnerTitleName = cardOwnerTitleName;
	}
	public String getCardOwnerDutyCode() {
		return cardOwnerDutyCode;
	}
	public void setCardOwnerDutyCode(String cardOwnerDutyCode) {
		this.cardOwnerDutyCode = cardOwnerDutyCode;
	}
	public String getCardOwnerDutyName() {
		return cardOwnerDutyName;
	}
	public void setCardOwnerDutyName(String cardOwnerDutyName) {
		this.cardOwnerDutyName = cardOwnerDutyName;
	}
	public String getApGeoraeCand() {
		return apGeoraeCand;
	}
	public void setApGeoraeCand(String apGeoraeCand) {
		this.apGeoraeCand = apGeoraeCand;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getAuthNum() {
		return authNum;
	}
	public void setAuthNum(String authNum) {
		this.authNum = authNum;
	}
	public String getGeoraeStat() {
		return georaeStat;
	}
	public void setGeoraeStat(String georaeStat) {
		this.georaeStat = georaeStat;
	}
	public String getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}
	public String getGeoraeColl() {
		return georaeColl;
	}
	public void setGeoraeColl(String georaeColl) {
		this.georaeColl = georaeColl;
	}
	public String getMercName() {
		return mercName;
	}
	public void setMercName(String mercName) {
		this.mercName = mercName;
	}
	public String getSettDate() {
		return settDate;
	}
	public void setSettDate(String settDate) {
		this.settDate = settDate;
	}
	public String getOwnerRegNo() {
		return ownerRegNo;
	}
	public void setOwnerRegNo(String ownerRegNo) {
		this.ownerRegNo = ownerRegNo;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getUseUserName() {
		return useUserName;
	}
	public void setUseUserName(String useUserName) {
		this.useUserName = useUserName;
	}
	public String getAuthTime() {
		return authTime;
	}
	public void setAuthTime(String authTime) {
		this.authTime = authTime;
	}
	public String getAquiDate() {
		return aquiDate;
	}
	public void setAquiDate(String aquiDate) {
		this.aquiDate = aquiDate;
	}
	public String getGeoraeCand() {
		return georaeCand;
	}
	public void setGeoraeCand(String georaeCand) {
		this.georaeCand = georaeCand;
	}
	public String getAmtAmount() {
		return amtAmount;
	}
	public void setAmtAmount(String amtAmount) {
		this.amtAmount = amtAmount;
	}
	public String getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(String vatAmount) {
		this.vatAmount = vatAmount;
	}
	public String getSerAmount() {
		return serAmount;
	}
	public void setSerAmount(String serAmount) {
		this.serAmount = serAmount;
	}
	public String getFreAmount() {
		return freAmount;
	}
	public void setFreAmount(String freAmount) {
		this.freAmount = freAmount;
	}
	public String getAmtMdAmount() {
		return amtMdAmount;
	}
	public void setAmtMdAmount(String amtMdAmount) {
		this.amtMdAmount = amtMdAmount;
	}
	public String getVatMdAmount() {
		return vatMdAmount;
	}
	public void setVatMdAmount(String vatMdAmount) {
		this.vatMdAmount = vatMdAmount;
	}
	public String getGeoraeGukga() {
		return georaeGukga;
	}
	public void setGeoraeGukga(String georaeGukga) {
		this.georaeGukga = georaeGukga;
	}
	public String getForAmount() {
		return forAmount;
	}
	public void setForAmount(String forAmount) {
		this.forAmount = forAmount;
	}
	public String getUsdAmount() {
		return usdAmount;
	}
	public void setUsdAmount(String usdAmount) {
		this.usdAmount = usdAmount;
	}
	public String getAquiRate() {
		return aquiRate;
	}
	public void setAquiRate(String aquiRate) {
		this.aquiRate = aquiRate;
	}
	public String getMercSaupNo() {
		return mercSaupNo;
	}
	public void setMercSaupNo(String mercSaupNo) {
		this.mercSaupNo = mercSaupNo;
	}
	public String getMercAddr() {
		return mercAddr;
	}
	public void setMercAddr(String mercAddr) {
		this.mercAddr = mercAddr;
	}
	public String getMercRepr() {
		return mercRepr;
	}
	public void setMercRepr(String mercRepr) {
		this.mercRepr = mercRepr;
	}
	public String getMercTel() {
		return mercTel;
	}
	public void setMercTel(String mercTel) {
		this.mercTel = mercTel;
	}
	public String getMercZip() {
		return mercZip;
	}
	public void setMercZip(String mercZip) {
		this.mercZip = mercZip;
	}
	public String getMccName() {
		return mccName;
	}
	public void setMccName(String mccName) {
		this.mccName = mccName;
	}
	public String getMccCode() {
		return mccCode;
	}
	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}
	public String getMccStat() {
		return mccStat;
	}
	public void setMccStat(String mccStat) {
		this.mccStat = mccStat;
	}
	public String getVatStat() {
		return vatStat;
	}
	public void setVatStat(String vatStat) {
		this.vatStat = vatStat;
	}
	public String getCanDate() {
		return canDate;
	}
	public void setCanDate(String canDate) {
		this.canDate = canDate;
	}
	public String getAskSite() {
		return askSite;
	}
	public void setAskSite(String askSite) {
		this.askSite = askSite;
	}
	public String getSiteDate() {
		return siteDate;
	}
	public void setSiteDate(String siteDate) {
		this.siteDate = siteDate;
	}
	public String getAskDate() {
		return askDate;
	}
	public void setAskDate(String askDate) {
		this.askDate = askDate;
	}
	public String getAskTime() {
		return askTime;
	}
	public void setAskTime(String askTime) {
		this.askTime = askTime;
	}
	public String getGongjeNoChk() {
		return gongjeNoChk;
	}
	public void setGongjeNoChk(String gongjeNoChk) {
		this.gongjeNoChk = gongjeNoChk;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSendYn() {
		return sendYn;
	}
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	public String getAquiTime() {
		return aquiTime;
	}
	public void setAquiTime(String aquiTime) {
		this.aquiTime = aquiTime;
	}
	public String getConversionFee() {
		return conversionFee;
	}
	public void setConversionFee(String conversionFee) {
		this.conversionFee = conversionFee;
	}
	public String getOrgColl() {
		return orgColl;
	}
	public void setOrgColl(String orgColl) {
		this.orgColl = orgColl;
	}
	public String getCardKind() {
		return cardKind;
	}
	public void setCardKind(String cardKind) {
		this.cardKind = cardKind;
	}
	public String getSettDay() {
		return settDay;
	}
	public void setSettDay(String settDay) {
		this.settDay = settDay;
	}
	public String getSettBankCode() {
		return settBankCode;
	}
	public void setSettBankCode(String settBankCode) {
		this.settBankCode = settBankCode;
	}
	public String getSettAcco() {
		return settAcco;
	}
	public void setSettAcco(String settAcco) {
		this.settAcco = settAcco;
	}
	public String getJuminNo() {
		return juminNo;
	}
	public void setJuminNo(String juminNo) {
		this.juminNo = juminNo;
	}
	public Date getFinanceDate() {
		return financeDate;
	}
	public void setFinanceDate(Date financeDate) {
		this.financeDate = financeDate;
	}
	public String getFinaceSeq() {
		return finaceSeq;
	}
	public void setFinaceSeq(String finaceSeq) {
		this.finaceSeq = finaceSeq;
	}
	public Date getJunpyoInsDate() {
		return junpyoInsDate;
	}
	public void setJunpyoInsDate(Date junpyoInsDate) {
		this.junpyoInsDate = junpyoInsDate;
	}
	public String getJunpyoStCode() {
		return junpyoStCode;
	}
	public void setJunpyoStCode(String junpyoStCode) {
		this.junpyoStCode = junpyoStCode;
	}
	public String getRegDatetime() {
		return regDatetime;
	}
	public void setRegDatetime(String regDatetime) {
		this.regDatetime = regDatetime;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getUpdtDatetme() {
		return updtDatetme;
	}
	public void setUpdtDatetme(String updtDatetme) {
		this.updtDatetme = updtDatetme;
	}
	public String getUpdtUserId() {
		return updtUserId;
	}
	public void setUpdtUserId(String updtUserId) {
		this.updtUserId = updtUserId;
	}
	public String getFinanceNo() {
		return financeNo;
	}
	public void setFinanceNo(String financeNo) {
		this.financeNo = financeNo;
	}
	
}
