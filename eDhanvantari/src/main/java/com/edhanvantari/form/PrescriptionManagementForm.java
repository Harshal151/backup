package com.edhanvantari.form;

import java.io.InputStream;
import java.util.Date;

public class PrescriptionManagementForm {

	private String searchCategory;
	private String category;
	private int categoryID;
	private String searchTaxName;
	private String taxName;
	private double taxPercent;
	private int taxID;
	private String drugName;
	private String tradeName;
	private String barcode;
	private String description;
	private String unit;
	private String productFormName;
	private String code;
	private double minQuantity;
	private double dose;
	private double costPrice;
	private double sellingPrice;
	private String priceFrom;
	private int taxInclusive;
	private String searchProductName;
	private int id;
	private int activePrice;
	private String CategoryName;
	public Date date;
	private String activityStatus;
	private String TaxInclusiveValue;
	private int productID;
	private String name;
	private String agency;
	private String mobile;
	private String email;
	private String address;
	private String city;
	private String state;
	private String country;
	private String pinCode;
	private String vatNumber;
	private String registrationDate;
	private String SearchSupplierName;
	private int supplierID;
	private String productBarcode;
	private int practiceID;
	private String[] productCompID;
	private String[] productCompBarcode;
	private String[] productQuantity;
	private String[] productRate;
	private String[] productAmount;
	private String[] productExpiryDate;
	private String[] productCostPrice;
	private String[] productSellingPrice;
	private String[] productTaxInclusive;
	private String[] productTaxName;
	private String[] productTaxPercent;
	private String[] productStockID;
	private String receiptNo;
	private double productTotalAmount;
	private double productAdvancePaymentAmt;
	private double productBalancePaymentAmt;
	private double productVAT;
	private int stockReceiptID;
	private int stockID;
	private String searchStock;
	private String receiptDate;
	private String productName;
	private double quantity;
	private double rate;
	private double amount;
	private String expireDate;
	private double netStock;
	private int receiptID;
	private String receiptByName;
	private String stockRemoveReason;
	private String[] customerID;
	private String submitButton;
	private int srNo;
	private int clinicID;
	private String taxInclusiveName;
	private String categoryType;
	private String clinicName;
	private String[] stockClinicID;
	private String orderNo;
	private String[] removeReason;
	private String searchCharges;
	private int roomTypeID;
	private String roomType;
	private String IPDItems;
	private int IPDChargeID;
	private double charges;
	private String doctorName;
	private int consultationChargeID;
	private int chargesID;
	private String chargeType;
	private int testID;
	private String test;
	private InputStream fileInputStream;
	private String fileName;
	private int OTTypeID;
	private String searchOTType;
	private String OTType;
	private String searchServiceType;
	private int serviceTypeID;
	private int roomCapacity;
	private String serviceType;
	private int roomChargesID;
	private String otCategory;

	/**
	 * @return the otCategory
	 */
	public String getOtCategory() {
		return otCategory;
	}

	/**
	 * @param otCategory the otCategory to set
	 */
	public void setOtCategory(String otCategory) {
		this.otCategory = otCategory;
	}

	/**
	 * @return the roomChargesID
	 */
	public int getRoomChargesID() {
		return roomChargesID;
	}

	/**
	 * @param roomChargesID the roomChargesID to set
	 */
	public void setRoomChargesID(int roomChargesID) {
		this.roomChargesID = roomChargesID;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the searchServiceType
	 */
	public String getSearchServiceType() {
		return searchServiceType;
	}

	/**
	 * @return the serviceTypeID
	 */
	public int getServiceTypeID() {
		return serviceTypeID;
	}

	/**
	 * @return the roomCapacity
	 */
	public int getRoomCapacity() {
		return roomCapacity;
	}

	/**
	 * @param searchServiceType the searchServiceType to set
	 */
	public void setSearchServiceType(String searchServiceType) {
		this.searchServiceType = searchServiceType;
	}

	/**
	 * @param serviceTypeID the serviceTypeID to set
	 */
	public void setServiceTypeID(int serviceTypeID) {
		this.serviceTypeID = serviceTypeID;
	}

	/**
	 * @param roomCapacity the roomCapacity to set
	 */
	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the oTTypeID
	 */
	public int getOTTypeID() {
		return OTTypeID;
	}

	/**
	 * @return the searchOTType
	 */
	public String getSearchOTType() {
		return searchOTType;
	}

	/**
	 * @return the oTType
	 */
	public String getOTType() {
		return OTType;
	}

	/**
	 * @param oTTypeID the oTTypeID to set
	 */
	public void setOTTypeID(int oTTypeID) {
		OTTypeID = oTTypeID;
	}

	/**
	 * @param searchOTType the searchOTType to set
	 */
	public void setSearchOTType(String searchOTType) {
		this.searchOTType = searchOTType;
	}

	/**
	 * @param oTType the oTType to set
	 */
	public void setOTType(String oTType) {
		OTType = oTType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	/**
	 * @return the testID
	 */
	public int getTestID() {
		return testID;
	}

	/**
	 * @param testID the testID to set
	 */
	public void setTestID(int testID) {
		this.testID = testID;
	}

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return the chargesID
	 */
	public int getChargesID() {
		return chargesID;
	}

	/**
	 * @param chargesID the chargesID to set
	 */
	public void setChargesID(int chargesID) {
		this.chargesID = chargesID;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @return the consultationChargeID
	 */
	public int getConsultationChargeID() {
		return consultationChargeID;
	}

	/**
	 * @param consultationChargeID the consultationChargeID to set
	 */
	public void setConsultationChargeID(int consultationChargeID) {
		this.consultationChargeID = consultationChargeID;
	}

	/**
	 * @return the charges
	 */
	public double getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(double charges) {
		this.charges = charges;
	}

	/**
	 * @return the iPDItems
	 */
	public String getIPDItems() {
		return IPDItems;
	}

	/**
	 * @param iPDItems the iPDItems to set
	 */
	public void setIPDItems(String iPDItems) {
		IPDItems = iPDItems;
	}

	/**
	 * @return the iPDChargeID
	 */
	public int getIPDChargeID() {
		return IPDChargeID;
	}

	/**
	 * @param iPDChargeID the iPDChargeID to set
	 */
	public void setIPDChargeID(int iPDChargeID) {
		IPDChargeID = iPDChargeID;
	}

	/**
	 * @return the roomTypeID
	 */
	public int getRoomTypeID() {
		return roomTypeID;
	}

	/**
	 * @param roomTypeID the roomTypeID to set
	 */
	public void setRoomTypeID(int roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the searchCharges
	 */
	public String getSearchCharges() {
		return searchCharges;
	}

	/**
	 * @param searchCharges the searchCharges to set
	 */
	public void setSearchCharges(String searchCharges) {
		this.searchCharges = searchCharges;
	}

	/**
	 * @return the removeReason
	 */
	public String[] getRemoveReason() {
		return removeReason;
	}

	/**
	 * @param removeReason the removeReason to set
	 */
	public void setRemoveReason(String[] removeReason) {
		this.removeReason = removeReason;
	}

	/**
	 * @return the productStockID
	 */
	public String[] getProductStockID() {
		return productStockID;
	}

	/**
	 * @param productStockID the productStockID to set
	 */
	public void setProductStockID(String[] productStockID) {
		this.productStockID = productStockID;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the stockClinicID
	 */
	public String[] getStockClinicID() {
		return stockClinicID;
	}

	/**
	 * @param stockClinicID the stockClinicID to set
	 */
	public void setStockClinicID(String[] stockClinicID) {
		this.stockClinicID = stockClinicID;
	}

	/**
	 * @return the clinicName
	 */
	public String getClinicName() {
		return clinicName;
	}

	/**
	 * @param clinicName the clinicName to set
	 */
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	/**
	 * @return the categoryType
	 */
	public String getCategoryType() {
		return categoryType;
	}

	/**
	 * @param categoryType the categoryType to set
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * @return the taxInclusiveName
	 */
	public String getTaxInclusiveName() {
		return taxInclusiveName;
	}

	/**
	 * @param taxInclusiveName the taxInclusiveName to set
	 */
	public void setTaxInclusiveName(String taxInclusiveName) {
		this.taxInclusiveName = taxInclusiveName;
	}

	/**
	 * @return the productCostPrice
	 */
	public String[] getProductCostPrice() {
		return productCostPrice;
	}

	/**
	 * @param productCostPrice the productCostPrice to set
	 */
	public void setProductCostPrice(String[] productCostPrice) {
		this.productCostPrice = productCostPrice;
	}

	/**
	 * @return the productSellingPrice
	 */
	public String[] getProductSellingPrice() {
		return productSellingPrice;
	}

	/**
	 * @param productSellingPrice the productSellingPrice to set
	 */
	public void setProductSellingPrice(String[] productSellingPrice) {
		this.productSellingPrice = productSellingPrice;
	}

	/**
	 * @return the productTaxInclusive
	 */
	public String[] getProductTaxInclusive() {
		return productTaxInclusive;
	}

	/**
	 * @param productTaxInclusive the productTaxInclusive to set
	 */
	public void setProductTaxInclusive(String[] productTaxInclusive) {
		this.productTaxInclusive = productTaxInclusive;
	}

	/**
	 * @return the productTaxName
	 */
	public String[] getProductTaxName() {
		return productTaxName;
	}

	/**
	 * @param productTaxName the productTaxName to set
	 */
	public void setProductTaxName(String[] productTaxName) {
		this.productTaxName = productTaxName;
	}

	/**
	 * @return the productTaxPercent
	 */
	public String[] getProductTaxPercent() {
		return productTaxPercent;
	}

	/**
	 * @param productTaxPercent the productTaxPercent to set
	 */
	public void setProductTaxPercent(String[] productTaxPercent) {
		this.productTaxPercent = productTaxPercent;
	}

	/**
	 * @return the clinicID
	 */
	public int getClinicID() {
		return clinicID;
	}

	/**
	 * @param clinicID the clinicID to set
	 */
	public void setClinicID(int clinicID) {
		this.clinicID = clinicID;
	}

	/**
	 * @return the minQuantity
	 */
	public double getMinQuantity() {
		return minQuantity;
	}

	/**
	 * @param minQuantity the minQuantity to set
	 */
	public void setMinQuantity(double minQuantity) {
		this.minQuantity = minQuantity;
	}

	/**
	 * @return the netStock
	 */
	public double getNetStock() {
		return netStock;
	}

	/**
	 * @param netStock the netStock to set
	 */
	public void setNetStock(double netStock) {
		this.netStock = netStock;
	}

	/**
	 * @return the srNo
	 */
	public int getSrNo() {
		return srNo;
	}

	/**
	 * @param srNo the srNo to set
	 */
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	/**
	 * @return the submitButton
	 */
	public String getSubmitButton() {
		return submitButton;
	}

	/**
	 * @param submitButton the submitButton to set
	 */
	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}

	/**
	 * @return the customerID
	 */
	public String[] getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(String[] customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the stockRemoveReason
	 */
	public String getStockRemoveReason() {
		return stockRemoveReason;
	}

	/**
	 * @param stockRemoveReason the stockRemoveReason to set
	 */
	public void setStockRemoveReason(String stockRemoveReason) {
		this.stockRemoveReason = stockRemoveReason;
	}

	/**
	 * @return the receiptByName
	 */
	public String getReceiptByName() {
		return receiptByName;
	}

	/**
	 * @param receiptByName the receiptByName to set
	 */
	public void setReceiptByName(String receiptByName) {
		this.receiptByName = receiptByName;
	}

	/**
	 * @return the receiptID
	 */
	public int getReceiptID() {
		return receiptID;
	}

	/**
	 * @param receiptID the receiptID to set
	 */
	public void setReceiptID(int receiptID) {
		this.receiptID = receiptID;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the receiptDate
	 */
	public String getReceiptDate() {
		return receiptDate;
	}

	/**
	 * @param receiptDate the receiptDate to set
	 */
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	/**
	 * @return the searchStock
	 */
	public String getSearchStock() {
		return searchStock;
	}

	/**
	 * @param searchStock the searchStock to set
	 */
	public void setSearchStock(String searchStock) {
		this.searchStock = searchStock;
	}

	/**
	 * @return the productExpiryDate
	 */
	public String[] getProductExpiryDate() {
		return productExpiryDate;
	}

	/**
	 * @param productExpiryDate the productExpiryDate to set
	 */
	public void setProductExpiryDate(String[] productExpiryDate) {
		this.productExpiryDate = productExpiryDate;
	}

	/**
	 * @return the stockReceiptID
	 */
	public int getStockReceiptID() {
		return stockReceiptID;
	}

	/**
	 * @param stockReceiptID the stockReceiptID to set
	 */
	public void setStockReceiptID(int stockReceiptID) {
		this.stockReceiptID = stockReceiptID;
	}

	/**
	 * @return the stockID
	 */
	public int getStockID() {
		return stockID;
	}

	/**
	 * @param stockID the stockID to set
	 */
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	/**
	 * @return the productTotalAmount
	 */
	public double getProductTotalAmount() {
		return productTotalAmount;
	}

	/**
	 * @param productTotalAmount the productTotalAmount to set
	 */
	public void setProductTotalAmount(double productTotalAmount) {
		this.productTotalAmount = productTotalAmount;
	}

	/**
	 * @return the productAdvancePaymentAmt
	 */
	public double getProductAdvancePaymentAmt() {
		return productAdvancePaymentAmt;
	}

	/**
	 * @param productAdvancePaymentAmt the productAdvancePaymentAmt to set
	 */
	public void setProductAdvancePaymentAmt(double productAdvancePaymentAmt) {
		this.productAdvancePaymentAmt = productAdvancePaymentAmt;
	}

	/**
	 * @return the productBalancePaymentAmt
	 */
	public double getProductBalancePaymentAmt() {
		return productBalancePaymentAmt;
	}

	/**
	 * @param productBalancePaymentAmt the productBalancePaymentAmt to set
	 */
	public void setProductBalancePaymentAmt(double productBalancePaymentAmt) {
		this.productBalancePaymentAmt = productBalancePaymentAmt;
	}

	/**
	 * @return the productVAT
	 */
	public double getProductVAT() {
		return productVAT;
	}

	/**
	 * @param productVAT the productVAT to set
	 */
	public void setProductVAT(double productVAT) {
		this.productVAT = productVAT;
	}

	/**
	 * @return the receiptNo
	 */
	public String getReceiptNo() {
		return receiptNo;
	}

	/**
	 * @param receiptNo the receiptNo to set
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	/**
	 * @return the productCompID
	 */
	public String[] getProductCompID() {
		return productCompID;
	}

	/**
	 * @param productCompID the productCompID to set
	 */
	public void setProductCompID(String[] productCompID) {
		this.productCompID = productCompID;
	}

	/**
	 * @return the productCompBarcode
	 */
	public String[] getProductCompBarcode() {
		return productCompBarcode;
	}

	/**
	 * @param productCompBarcode the productCompBarcode to set
	 */
	public void setProductCompBarcode(String[] productCompBarcode) {
		this.productCompBarcode = productCompBarcode;
	}

	/**
	 * @return the productQuantity
	 */
	public String[] getProductQuantity() {
		return productQuantity;
	}

	/**
	 * @param productQuantity the productQuantity to set
	 */
	public void setProductQuantity(String[] productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * @return the productRate
	 */
	public String[] getProductRate() {
		return productRate;
	}

	/**
	 * @param productRate the productRate to set
	 */
	public void setProductRate(String[] productRate) {
		this.productRate = productRate;
	}

	/**
	 * @return the productAmount
	 */
	public String[] getProductAmount() {
		return productAmount;
	}

	/**
	 * @param productAmount the productAmount to set
	 */
	public void setProductAmount(String[] productAmount) {
		this.productAmount = productAmount;
	}

	/**
	 * @return the practiceID
	 */
	public int getPracticeID() {
		return practiceID;
	}

	/**
	 * @param practiceID the practiceID to set
	 */
	public void setPracticeID(int practiceID) {
		this.practiceID = practiceID;
	}

	/**
	 * @return the productBarcode
	 */
	public String getProductBarcode() {
		return productBarcode;
	}

	/**
	 * @param productBarcode the productBarcode to set
	 */
	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}

	/**
	 * @return the drugName
	 */
	public String getDrugName() {
		return drugName;
	}

	/**
	 * @param drugName the drugName to set
	 */
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	/**
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * @param tradeName the tradeName to set
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the productFormName
	 */
	public String getProductFormName() {
		return productFormName;
	}

	/**
	 * @param productFormName the productFormName to set
	 */
	public void setProductFormName(String productFormName) {
		this.productFormName = productFormName;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the dose
	 */
	public double getDose() {
		return dose;
	}

	/**
	 * @param dose the dose to set
	 */
	public void setDose(double dose) {
		this.dose = dose;
	}

	/**
	 * @return the costPrice
	 */
	public double getCostPrice() {
		return costPrice;
	}

	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * @return the sellingPrice
	 */
	public double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice the sellingPrice to set
	 */
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the priceFrom
	 */
	public String getPriceFrom() {
		return priceFrom;
	}

	/**
	 * @param priceFrom the priceFrom to set
	 */
	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	/**
	 * @return the taxInclusive
	 */
	public int getTaxInclusive() {
		return taxInclusive;
	}

	/**
	 * @param taxInclusive the taxInclusive to set
	 */
	public void setTaxInclusive(int taxInclusive) {
		this.taxInclusive = taxInclusive;
	}

	/**
	 * @return the searchProductName
	 */
	public String getSearchProductName() {
		return searchProductName;
	}

	/**
	 * @param searchProductName the searchProductName to set
	 */
	public void setSearchProductName(String searchProductName) {
		this.searchProductName = searchProductName;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the activePrice
	 */
	public int getActivePrice() {
		return activePrice;
	}

	/**
	 * @param activePrice the activePrice to set
	 */
	public void setActivePrice(int activePrice) {
		this.activePrice = activePrice;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return CategoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the activityStatus
	 */
	public String getActivityStatus() {
		return activityStatus;
	}

	/**
	 * @param activityStatus the activityStatus to set
	 */
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	/**
	 * @return the taxInclusiveValue
	 */
	public String getTaxInclusiveValue() {
		return TaxInclusiveValue;
	}

	/**
	 * @param taxInclusiveValue the taxInclusiveValue to set
	 */
	public void setTaxInclusiveValue(String taxInclusiveValue) {
		TaxInclusiveValue = taxInclusiveValue;
	}

	/**
	 * @return the productID
	 */
	public int getProductID() {
		return productID;
	}

	/**
	 * @param productID the productID to set
	 */
	public void setProductID(int productID) {
		this.productID = productID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * @param vatNumber the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * @return the registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the searchSupplierName
	 */
	public String getSearchSupplierName() {
		return SearchSupplierName;
	}

	/**
	 * @param searchSupplierName the searchSupplierName to set
	 */
	public void setSearchSupplierName(String searchSupplierName) {
		SearchSupplierName = searchSupplierName;
	}

	/**
	 * @return the supplierID
	 */
	public int getSupplierID() {
		return supplierID;
	}

	/**
	 * @param supplierID the supplierID to set
	 */
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	/**
	 * @return the searchTaxName
	 */
	public String getSearchTaxName() {
		return searchTaxName;
	}

	/**
	 * @param searchTaxName the searchTaxName to set
	 */
	public void setSearchTaxName(String searchTaxName) {
		this.searchTaxName = searchTaxName;
	}

	/**
	 * @return the taxName
	 */
	public String getTaxName() {
		return taxName;
	}

	/**
	 * @param taxName the taxName to set
	 */
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	/**
	 * @return the taxPercent
	 */
	public double getTaxPercent() {
		return taxPercent;
	}

	/**
	 * @param taxPercent the taxPercent to set
	 */
	public void setTaxPercent(double taxPercent) {
		this.taxPercent = taxPercent;
	}

	/**
	 * @return the taxID
	 */
	public int getTaxID() {
		return taxID;
	}

	/**
	 * @param taxID the taxID to set
	 */
	public void setTaxID(int taxID) {
		this.taxID = taxID;
	}

	/**
	 * @return the searchCategory
	 */
	public String getSearchCategory() {
		return searchCategory;
	}

	/**
	 * @param searchCategory the searchCategory to set
	 */
	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

}
