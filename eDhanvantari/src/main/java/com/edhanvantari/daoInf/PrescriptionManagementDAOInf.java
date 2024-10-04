package com.edhanvantari.daoInf;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.edhanvantari.form.PrescriptionManagementForm;

public interface PrescriptionManagementDAOInf {

	/**
	 * 
	 * @param searchCategoryName
	 * @return
	 */
	public List<PrescriptionManagementForm> searchCategory(String searchCategoryName);

	/**
	 * 
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveAllCategories();

	/**
	 * 
	 * @param categoryName
	 * @param categoryType
	 * @return
	 */
	public String insertCategory(String categoryName, String categoryType);

	/**
	 * 
	 * @param categoryID
	 * @param searchCategoryName
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveCategoryByID(int categoryID, String searchCategoryName);

	/**
	 * 
	 * @param categoryName
	 * @param categoryID
	 * @param categoryType
	 * @return
	 */
	public String updateCategory(String categoryName, int categoryID, String categoryType);

	/**
	 * 
	 * @param categoryID
	 * @return
	 */
	public String deleteCategory(int categoryID);

	/**
	 * 
	 * @param searchTaxName
	 * @return
	 */
	public List<PrescriptionManagementForm> searchTax(String searchTaxName);

	/**
	 * 
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveAllTaxes();

	/**
	 * 
	 * @param taxName
	 * @param taxPercent
	 * @return
	 */
	public String insertTax(String taxName, double taxPercent);

	/**
	 * 
	 * @param taxID
	 * @param searchTaxName
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveTaxByID(int taxID, String searchTaxName);

	/**
	 * 
	 * @param taxName
	 * @param taxPercent
	 * @param taxID
	 * @return
	 */
	public String updateTax(String taxName, double taxPercent, int taxID);

	/**
	 * 
	 * @param taxID
	 * @return
	 */
	public String deleteTax(int taxID);

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> retrieveProductCategoryList();

	/**
	 * 
	 * @return
	 */

	public List<String> retrieveproducttaxList();

	/**
	 * 
	 * @return
	 */
	public String insertProduct(PrescriptionManagementForm form);

	/**
	 * 
	 * @param barcode
	 * @return
	 */
	public int retrieveLastEnteredProductId(String barcode);

	/**
	 * 
	 * @param form
	 * @param productID
	 * @return
	 */
	public String insertProductPrice(PrescriptionManagementForm form, int productID);

	/**
	 * 
	 * @param searchProductName
	 * @return
	 */
	public List<PrescriptionManagementForm> searchProduct(String searchProductName, int clinicID);

	/**
	 * 
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveAllProduct(int clinicID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveProductByID(int productID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveProductPriceList(int productID);

	/**
	 * 
	 * @return
	 */
	public List<String> retrieveTaxList();

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String updateProduct(PrescriptionManagementForm form);

	/**
	 * 
	 * @param productID
	 */
	public void inactiveProductPrive(int productID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public String insertProductPrice(int productID);

	/**
	 * 
	 * @param productID
	 * @param sellingPrice
	 * @return
	 */
	public String updateActiveProductPrice(int productID, double sellingPrice);

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String insertSupplier(PrescriptionManagementForm form);

	/**
	 * 
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveAllSuppliers();

	/**
	 * 
	 * @param form
	 * @return
	 */
	public String updateSupplier(PrescriptionManagementForm form);

	/**
	 * 
	 * @param searchSupplierName
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveAllSuppliers(String searchSupplierName);

	/**
	 * 
	 * @param supplierID
	 * @param searchSupplierName
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveSupplierByID(int supplierID, String searchSupplierName);

	/**
	 * 
	 * @param searchSupplierName
	 * @return
	 */
	public List<PrescriptionManagementForm> searchSupplier(String searchSupplierName);

	/**
	 * 
	 * @param supplierID
	 * @return
	 */
	public String deleteSupplier(int supplierID);

	/**
	 * 
	 * @param productBarcode
	 * @return
	 */
	public JSONObject verifyProductExist(String productBarcode);

	/**
	 * 
	 * @param taxName
	 * @return
	 */
	public JSONObject retrieveTaxPercentByTaxName(String taxName);

	/**
	 * 
	 * @param clinicID
	 * @param suffix
	 * @return
	 */
	public String retrieveReceiptNo(int clinicID, String suffix);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public HashMap<Integer, String> retrieveProductList(int clinicID);

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> retrieveSupplierList();

	/**
	 * 
	 * @param supplierID
	 * @return
	 */
	public JSONObject retrieveSupplierDetailsByID(int supplierID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public JSONObject retrieveProductRateDetailsByProductID(int productID);

	/**
	 * 
	 * @param barcode
	 * @return
	 */
	public JSONObject retrieveProductIDByBarcode(String barcode);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public JSONObject retrieveProductNameByID(int productID);

	/**
	 * 
	 * @param form
	 * @param userID
	 * @return
	 */
	public String insertStockReceipt(PrescriptionManagementForm form, int userID);

	/**
	 * 
	 * @return
	 */
	public int retrieveLastStockReceiptID();

	/**
	 * 
	 * @param quantity
	 * @param productID
	 * @param amount
	 * @param taxPercent
	 * @param stockReceiptID
	 * @param expiryDate
	 * @param stockTransaction
	 * @param costPrice
	 * @param sellingPrice
	 * @param taxInclusive
	 * @param taxName
	 * @param clinicID
	 * @return
	 */
	public String insertStock(double quantity, int productID, double amount, double taxPercent, int stockReceiptID,
			String expiryDate, String stockTransaction, double costPrice, double sellingPrice, int taxInclusive,
			String taxName, int clinicID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public double retrieveTaxPercentByProductID(int productID);

	/**
	 * 
	 * @param stockID
	 * @return
	 */
	public double retrieveProductQuantity(int stockID);

	/**
	 * 
	 * @param stockID
	 * @param quantity
	 * @return
	 */
	public String updateProductNetStock(int stockID, double quantity);

	/**
	 * 
	 * @param searchName
	 * @param searchCriteria
	 * @param clinicID
	 * @return
	 */
	public List<PrescriptionManagementForm> searchStockList(String searchName, String searchCriteria, int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveAllStockList(int clinicID);

	/**
	 * 
	 * @param stockReceiptID
	 * @return
	 */
	public double retrieveTotalTaxAmount(int stockReceiptID);

	/**
	 * 
	 * @param stockReceiptID
	 * @param taxAmount
	 * @return
	 */
	public String updateTaxAmount(int stockReceiptID, double taxAmount);

	/**
	 * 
	 * @param stockID
	 * @return
	 */
	public JSONObject deleteProduct(int stockID);

	/**
	 * 
	 * @param stockReceiptID
	 * @return
	 */
	public List<PrescriptionManagementForm> retireveStockProductList(int stockReceiptID);

	/**
	 * 
	 * @param stockReceiptID
	 * @param clinicID
	 * @return
	 */
	public List<PrescriptionManagementForm> retireveStockReceiptList(int stockReceiptID, int clinicID);

	/**
	 * 
	 * @param managementForm
	 * @param userID
	 * @return
	 */
	public String updateStockReceipt(PrescriptionManagementForm managementForm, int userID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public double retrieveProductsTaxAmount(int productID);

	/**
	 * 
	 * @param stockReceiptID
	 * @param taxAmountToBeSubtracted
	 */
	public void updateStockReceiptTaxAmount(int stockReceiptID, double taxAmountToBeSubtracted);

	/**
	 * 
	 * @param supplierID
	 * @param clinicID
	 * @return
	 */
	public List<PrescriptionManagementForm> searchOrderStock(int supplierID, int clinicID);

	/**
	 * 
	 * @param supplierID
	 * @return
	 */
	public String retrieveSupplierNameByID(int supplierID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public String retrieveProductNameByProductID(int productID);

	/**
	 * 
	 * @param supplierID
	 * @param productID
	 * @param quantity
	 * @param userID
	 * @param clinicID
	 * @param orderNo
	 * @return
	 */
	public String insertOrder(int supplierID, int productID, double quantity, int userID, int clinicID, String orderNo);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrieveClinicNameFromPracticeID(int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrieveEmailFromByPracticeID(int practiceID);

	/**
	 * 
	 * @param practiceID
	 * @return
	 */
	public String retrieveEmailFromPassByPracticeID(int practiceID);

	/**
	 * 
	 * @param supplierID
	 * @return
	 */
	public String retrieveSupplierEmailID(int supplierID);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public String retrieveProductNameRateTaxByProductID(int productID);

	/**
	 * 
	 * @param stockReceiptID
	 * @param productID
	 * @return
	 */
	public int retrieveLastEnteredStockID(int stockReceiptID, int productID);

	/**
	 * 
	 * @param stockID
	 * @param quantity
	 * @param action
	 * @return
	 */
	public String insertStockTransaction(int stockID, double quantity, String action);

	/**
	 * 
	 * @param stockReceiptID
	 * @param clinicID
	 * @return
	 */
	public String updateStockClinic(int stockReceiptID, int clinicID);

	/**
	 * 
	 * @param clinicSuffix
	 * @return
	 */
	public String retrieveOrderNumber(String clinicSuffix);

	/**
	 * 
	 * @param orderNo
	 * @return
	 */
	public boolean verifyOrderCreated(String orderNo);

	/**
	 * 
	 * @param tradeName
	 * @param categoryID
	 * @return
	 */
	public boolean verifyProductExits(String tradeName, int clinicID);

	/**
	 * 
	 * @param clinicID
	 * @param clinicSuffix
	 * @return
	 */
	public JSONObject retrieveStockReceiptNoByClinicID(int clinicID, String clinicSuffix);

	/**
	 * 
	 * @param clinicID
	 * @return
	 */
	public JSONObject retrieveProductListByClinicID(int clinicID);

	/**
	 * 
	 * @param productID
	 * @param clinicID
	 * @return
	 */
	public List<PrescriptionManagementForm> retrieveProductStockListToRemove(int productID, int clinicID);

	/**
	 * 
	 * @param stockID
	 * @return
	 */
	public double retrieveProductNetStockByStockID(int stockID);

	/**
	 * 
	 * @param stockID
	 * @param status
	 * @param quantity
	 * @return
	 */
	public String updateStockByStockID(int stockID, String status, double quantity);

	/**
	 * 
	 * @param productID
	 * @return
	 */
	public String disableProduct(int productID);

	/**
	 * 
	 * @param productID
	 * @param clinicID
	 * @return
	 */
	public HashMap<Integer, Double> retrieveProductStockList(int productID, int clinicID);

	public boolean verifyCategoryNameAlreadyExists(String category);

	public boolean verifyVisitTypeNameAlreadyExists(String visitTypeName, int clinicID, String formName);

	public boolean verifyEditCategoryNameAlreadyExists(String category, int categoryID);

	public boolean verifyVatNumberAlreadyExists(String vatNumber);

	public boolean verifyEditVatNumberAlreadyExists(String vatNumber, int supplierID);

	public boolean verifyTaxDetailsAlreadyExists(String taxName, double taxPercent);

	public boolean verifyEditTaxDetailsAlreadyExists(String taxName, double taxPercent, int taxID);

	public boolean verifyEditTradeNameAlreadyExists(String tradeName, int productID);

	public String retrievePaymentTypeByReceiptID(int visitID);

	public boolean verifyEditVisitTypeNameAlreadyExists(String visitTypeName, int clinicID, String formName,
			int visitTypeID);

	public boolean verifyTestNameExits(String testName);

	public List<PrescriptionManagementForm> retrieveRoomTypeListByType(String searchCharges, int practiceID);

	public List<PrescriptionManagementForm> retrieveAllRoomTypeList(int practiceID);

	public boolean verifyRoomTypeExists(String roomType, int practiceID);

	public String insertRoomType(PrescriptionManagementForm form);

	public List<PrescriptionManagementForm> retrieveRoomTypeListByID(int roomTypeID);

	public boolean verifyRoomTypeExists(String roomType, int roomTypeID, int practiceID);

	public String updateRoomType(PrescriptionManagementForm form);

	public String updateRoomTypeStatus(int roomTypeID, String inactive);

	public HashMap<Integer, String> retrieveRoomTypeList(int practiceID);

	public List<PrescriptionManagementForm> retrieveIPDChargesListByItem(String searchCharges, int practiceID);

	public List<PrescriptionManagementForm> retrieveAllIPDChargesList(int practiceID);

	public boolean verifyIPDChargesExists(String ipdItems, int roomTypeID, int practiceID);

	public String insertIPDCharges(PrescriptionManagementForm form);

	public List<PrescriptionManagementForm> retrieveIPDChargesListByID(int ipdChargeID);

	public boolean verifyIPDChargesExists(String ipdItems, int roomTypeID, int ipdChargeID, int practiceID);

	public String updateIPDCharges(PrescriptionManagementForm form);

	public String updateIPDChargesStatus(int ipdChargeID, String inactive);

	public List<PrescriptionManagementForm> retrieveIPDConsultantChargesListByDoctor(String searchCharges,
			int practiceID);

	public List<PrescriptionManagementForm> retrieveAllIPDConsultantChargesList(int practiceID);

	public boolean verifyIPDConsultantChargesExists(String doctorName, int roomTypeID, int practiceID);

	public String insertIPDConsultantCharges(PrescriptionManagementForm form, int practiceID);

	public List<PrescriptionManagementForm> retrieveIPDConsultantChargesListByID(int consultationChargeID);

	public boolean verifyIPDConsultantChargesExists(String doctorName, int roomTypeID, int consultationChargeID,
			int practiceID);

	public String updateIPDConsultantCharges(PrescriptionManagementForm form);

	public String updateIPDConsultantChargesStatus(int consultationChargeID, String inactive);

	public List<PrescriptionManagementForm> searchOPDCharges(String searchCharges, int practiceID);

	public List<PrescriptionManagementForm> retrieveAllOPDCharges(int practiceID);

	public JSONObject retrieveOPDChargesForPractice(int practiceID);

	public JSONObject retrieveIPDTarrifChargesForPractice(int roomTypeID, int practiceID);

	public boolean verifyChargeTypeNameAlreadyExists(String chargeType, int practiceID);

	public String insertOPDChargeDetails(String chargeType, double charges, int practiceID);

	public List<PrescriptionManagementForm> retrieveOPDChargeDetailsByID(int chargesID, String searchCharges);

	public boolean verifyEditChargeTypeAlreadyExists(String chargeType, int chargesID, int practiceID);

	public String updateOPDChargesDateils(String chargeType, double charges, int chargesID);

	public String deleteOPDCharges(int chargesID);

	public String retrieveOPDChargesDetailsByReceiptID(int visitID);

	public List<PrescriptionManagementForm> retrieveAllLabTests(int practiceID);

	public String retrieveInvestigationDetailsByVisitID(int visitID);

	public List<PrescriptionManagementForm> retrieveOTTypeListByType(String searchOTType, int practiceID);

	public List<PrescriptionManagementForm> retrieveAllOTTypeList(int practiceID);

	public boolean verifyOTTypeExists(String OTType, int practiceID);

	public String insertOTType(PrescriptionManagementForm form);

	public List<PrescriptionManagementForm> retrieveOTTypeListByID(int OTTypeID);

	public boolean verifyOTTypeExists(String OTType, int roomTypeID, int practiceID);

	public String updateOTType(PrescriptionManagementForm form);

	public String updateOTTypeStatus(int OTTypeID, String status);

	public List<PrescriptionManagementForm> retrieveServiceTypeListByType(String searchServiceType, int practiceID);

	public List<PrescriptionManagementForm> retrieveAllServiceTypeList(int practiceID);

	public boolean verifyServiceTypeExists(String serviceType, int practiceID);

	public String insertServiceType(PrescriptionManagementForm form);

	public List<PrescriptionManagementForm> retrieveServiceTypeListByID(int serviceTypeID);

	public boolean verifyServiceTypeExists(String serviceType, int serviceTypeID, int practiceID);

	public String updateServiceType(PrescriptionManagementForm form);

	public String updateServiceTypeStatus(int serviceTypeID, String status);

	public List<PrescriptionManagementForm> retrieveRoomChargesListByRoom(String searchCharges, int practiceID);

	public List<PrescriptionManagementForm> retrieveAllRoomChargesList(int practiceID);

	public boolean verifyRoomChargesExists(String roomName, int roomTypeID, int practiceID);

	public String insertRoomCharges(PrescriptionManagementForm form, int practiceID);

	public List<PrescriptionManagementForm> retrieveRoomChargesListByID(int roomChargeID);

	public boolean verifyRoomChargesExists(String roomName, int roomTypeID, int roomChargeID, int practiceID);

	public String updateRoomCharges(PrescriptionManagementForm form);

	public String updateRoomChargesStatus(int roomChargeID, String inactive);

	/**
	 * 
	 * @param categoryName
	 * @param categoryType
	 * @param otFlag
	 * @return
	 */
	public String insertCategoryWithOT(String categoryName, String categoryType, String otFlag);

	/**
	 * 
	 * @param categoryName
	 * @param categoryID
	 * @param categoryType
	 * @param otCategory
	 * @return
	 */
	public String updateCategoryWithOT(String categoryName, int categoryID, String categoryType, String otCategory);

}
