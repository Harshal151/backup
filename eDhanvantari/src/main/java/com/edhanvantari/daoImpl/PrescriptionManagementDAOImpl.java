package com.edhanvantari.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.PrescriptionManagementDAOInf;
import com.edhanvantari.form.PrescriptionManagementForm;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.JDBCHelper;
import com.edhanvantari.util.QueryMaker;
import com.edhanvantari.util.ActivityStatus;

public class PrescriptionManagementDAOImpl extends DAOConnection implements PrescriptionManagementDAOInf {

	Connection connection = null;

	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	String status = "error";

	Connection connection1 = null;

	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#searchCategory(java.
	 * lang.String)
	 */
	public List<PrescriptionManagementForm> searchCategory(String searchCategoryName) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String searchCategoryQuery = QueryMaker.SEARCH_CATEGORY;

			preparedStatement = connection.prepareStatement(searchCategoryQuery);

			if (searchCategoryName.contains(" ")) {
				searchCategoryName = searchCategoryName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchCategoryName + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setCategoryID(resultSet.getInt("id"));
				form.setCategory((resultSet.getString("name")));
				form.setCategoryType(resultSet.getString("type"));
				form.setSearchCategory(searchCategoryName);
				form.setOtCategory(resultSet.getString("otcategory"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching category list due to:::" + exception.getMessage());
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#searchCategory(java.
	 * lang.String)
	 */
	public List<PrescriptionManagementForm> searchCategory_bk(String searchCategoryName) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String searchCategoryQuery = QueryMaker.SEARCH_CATEGORY;

			preparedStatement = connection.prepareStatement(searchCategoryQuery);

			if (searchCategoryName.contains(" ")) {
				searchCategoryName = searchCategoryName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchCategoryName + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setCategoryID(resultSet.getInt("id"));
				form.setCategory((resultSet.getString("name")));
				form.setCategoryType(resultSet.getString("type"));
				form.setSearchCategory(searchCategoryName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching category list due to:::" + exception.getMessage());
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PrescriptionManagementDAOInf#
	 * retrieveAllCategories()
	 */
	public List<PrescriptionManagementForm> retrieveAllCategories() {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String retrieveAllCategoriesQuery = QueryMaker.RETRIEVE_ALL_CATEGORY;

			preparedStatement = connection.prepareStatement(retrieveAllCategoriesQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setCategoryID(resultSet.getInt("id"));
				form.setCategory((resultSet.getString("name")));
				form.setCategoryType(resultSet.getString("type"));
				form.setOtCategory(resultSet.getString("otcategory"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving all category list due to:::" + exception.getMessage());
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#insertCategory(java.
	 * lang.String, java.lang.String)
	 */
	public String insertCategory(String categoryName, String categoryType) {

		try {

			connection = getConnection();

			String insertCategoryQuery = QueryMaker.INSERT_CATEGORY;

			preparedStatement = connection.prepareStatement(insertCategoryQuery);

			preparedStatement.setString(1, categoryName);
			preparedStatement.setString(2, categoryType);

			preparedStatement.execute();

			status = "success";

			System.out.println("Category added successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting category details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#retrieveCategoryByID
	 * (int, java.lang.String)
	 */
	public List<PrescriptionManagementForm> retrieveCategoryByID(int categoryID, String searchCategoryName) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String retrieveCategoryByIDQuery = QueryMaker.RETRIEVE_CATEGORY_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveCategoryByIDQuery);

			preparedStatement.setInt(1, categoryID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setCategoryID(resultSet.getInt("id"));
				form.setCategory((resultSet.getString("name")));
				form.setCategoryType(resultSet.getString("type"));
				form.setOtCategory(resultSet.getString("otcategory"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving category by ID due to:::" + exception.getMessage());
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#updateCategory(java.
	 * lang.String, int, java.lang.String)
	 */
	public String updateCategory(String categoryName, int categoryID, String categoryType) {

		try {

			connection = getConnection();

			String updateCategoryQuery = QueryMaker.UPDATE_CATEGORY;

			preparedStatement = connection.prepareStatement(updateCategoryQuery);

			preparedStatement.setString(1, categoryName);
			preparedStatement.setString(2, categoryType);
			preparedStatement.setInt(3, categoryID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Category updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating category due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PrescriptionManagementDAOInf#deleteCategory(int)
	 */
	public String deleteCategory(int categoryID) {

		try {

			connection = getConnection();

			String deleteCategoryQuery = QueryMaker.DELETE_CATEGORY;

			preparedStatement = connection.prepareStatement(deleteCategoryQuery);

			preparedStatement.setInt(1, categoryID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Category delete successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting category due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#searchTax(java.lang.
	 * String)
	 */
	public List<PrescriptionManagementForm> searchTax(String searchTaxName) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String searchTaxQuery = QueryMaker.SEARCH_TAX;

			preparedStatement = connection.prepareStatement(searchTaxQuery);

			if (searchTaxName.contains(" ")) {
				searchTaxName = searchTaxName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchTaxName + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setTaxID(resultSet.getInt("id"));
				form.setTaxName((resultSet.getString("taxName")));
				form.setTaxPercent(resultSet.getDouble("taxPercent"));
				form.setSearchTaxName(searchTaxName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching tax list due to:::" + exception.getMessage());
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PrescriptionManagementDAOInf#retrieveAllTaxes()
	 */
	public List<PrescriptionManagementForm> retrieveAllTaxes() {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String retrieveAllTaxesQuery = QueryMaker.RETRIEVE_ALL_TAXES;

			preparedStatement = connection.prepareStatement(retrieveAllTaxesQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setTaxID(resultSet.getInt("id"));
				form.setTaxName((resultSet.getString("taxName")));
				form.setTaxPercent(resultSet.getDouble("taxPercent"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving all tax list due to:::" + exception.getMessage());
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#insertTax(java.lang.
	 * String, double)
	 */
	public String insertTax(String taxName, double taxPercent) {

		try {

			connection = getConnection();

			String insertTaxQuery = QueryMaker.INSERT_TAX;

			preparedStatement = connection.prepareStatement(insertTaxQuery);

			preparedStatement.setString(1, taxName);
			preparedStatement.setDouble(2, taxPercent);

			preparedStatement.execute();

			status = "success";

			System.out.println("Tax added successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting tax details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#retrieveTaxByID(int,
	 * java.lang.String)
	 */
	public List<PrescriptionManagementForm> retrieveTaxByID(int taxID, String searchTaxName) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String retrieveTaxByIDQuery = QueryMaker.RETRIEVE_TAX_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveTaxByIDQuery);

			preparedStatement.setInt(1, taxID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setTaxID(resultSet.getInt("id"));
				form.setTaxName((resultSet.getString("taxName")));
				form.setTaxPercent(resultSet.getDouble("taxPercent"));
				form.setSearchTaxName(searchTaxName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving all tax list due to:::" + exception.getMessage());
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#updateTax(java.lang.
	 * String, double, int)
	 */
	public String updateTax(String taxName, double taxPercent, int taxID) {

		try {

			connection = getConnection();

			String updateTaxQuery = QueryMaker.UPDATE_TAX;

			preparedStatement = connection.prepareStatement(updateTaxQuery);

			preparedStatement.setString(1, taxName);
			preparedStatement.setDouble(2, taxPercent);
			preparedStatement.setInt(3, taxID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Tax updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while udpating tax details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edhanvantari.daoInf.PrescriptionManagementDAOInf#deleteTax(int)
	 */
	public String deleteTax(int taxID) {

		try {

			connection = getConnection();

			String deleteTaxQuery = QueryMaker.DELETE_TAX;

			preparedStatement = connection.prepareStatement(deleteTaxQuery);

			preparedStatement.setInt(1, taxID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Tax delete successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting tax due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public HashMap<Integer, String> retrieveProductCategoryList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveProductCategoryListQuery = QueryMaker.RETRIEVE_PRODUCT_CATEGORIES;

			preparedStatement = connection.prepareStatement(retrieveProductCategoryListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return map;
	}

	public List<String> retrieveproducttaxList() {
		List<String> taxList = new ArrayList<String>();

		try {

			connection = getConnection();
			String retrieveproducttaxListQuery = QueryMaker.RETRIEVE_PRODUCT_TAX;
			preparedStatement = connection.prepareStatement(retrieveproducttaxListQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				taxList.add(resultSet.getString("taxName"));

			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception exception) {
			exception.getSuppressed();
		}
		return taxList;
	}

	public String insertProduct(PrescriptionManagementForm form) {

		try {
			connection = getConnection();
			String insertProductQuery = QueryMaker.INSERT_PRODUCT;

			preparedStatement = connection.prepareStatement(insertProductQuery);

			preparedStatement.setString(1, form.getDrugName());
			preparedStatement.setString(2, form.getTradeName());
			preparedStatement.setString(3, form.getBarcode());
			preparedStatement.setString(4, form.getDescription());
			preparedStatement.setString(5, form.getUnit());
			preparedStatement.setInt(6, form.getCategoryID());
			preparedStatement.setDouble(7, form.getMinQuantity());
			preparedStatement.setDouble(8, form.getDose());
			preparedStatement.setString(9, form.getProductFormName());
			preparedStatement.setString(10, form.getCode());
			// preparedStatement.setDouble(11, form.getSellingPrice());
			preparedStatement.setString(11, ActivityStatus.ACTIVE);
			preparedStatement.setInt(12, form.getClinicID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Product inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	public int retrieveLastEnteredProductId(String barcode) {
		int productID = 0;

		try {

			connection = getConnection();

			String retriveLastEnteredProductIdQuery = QueryMaker.RETIREVE_LAST_ENTERED_PRODUCT_ID;

			preparedStatement = connection.prepareStatement(retriveLastEnteredProductIdQuery);

			preparedStatement.setString(1, barcode);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				productID = resultSet.getInt("id");

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return productID;

	}

	public String insertProductPrice(PrescriptionManagementForm form, int productID) {

		/*
		 * Converting outstanding paid date into YYYY-MM-DD format in order insert the
		 * record into database
		 */
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String insertProductPriceQuery = QueryMaker.INSERT_PRODUCT_PRICE;

			preparedStatement = connection.prepareStatement(insertProductPriceQuery);

			preparedStatement.setDouble(1, form.getCostPrice());
			preparedStatement.setDouble(2, form.getSellingPrice());
			preparedStatement.setString(3, dbDateFormat.format(dateFormat.parse(form.getPriceFrom())));
			preparedStatement.setInt(4, form.getTaxInclusive());
			preparedStatement.setString(5, form.getTaxName());
			preparedStatement.setDouble(6, form.getTaxPercent());
			preparedStatement.setInt(7, productID);
			preparedStatement.setString(8, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Price inserted successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	public String retrieveProductCategoryName(int categoryID) {

		String categoryName = "";

		try {

			connection1 = getConnection();

			String retrieveProductCategoryNameQuery = QueryMaker.RETRIEVE_PRODUCT_CATEGORY_NAME;

			preparedStatement1 = connection.prepareStatement(retrieveProductCategoryNameQuery);

			preparedStatement1.setInt(1, categoryID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				categoryName = resultSet1.getString("name");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return categoryName;
	}

	public List<PrescriptionManagementForm> retrieveAllProduct(int clinicID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm productForm = null;

		try {

			connection = getConnection();

			String retrieveAllProductQuery = QueryMaker.RETIREVE_ALL_PRODUCT;

			preparedStatement = connection.prepareStatement(retrieveAllProductQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				productForm = new PrescriptionManagementForm();

				productForm.setProductID(resultSet.getInt("id"));
				productForm.setDrugName(resultSet.getString("tradeName"));
				productForm.setDescription(resultSet.getString("description"));
				productForm.setActivityStatus(resultSet.getString("activityStatus"));
				productForm.setCategoryName(retrieveProductCategoryName(resultSet.getInt("categoryID")));

				list.add(productForm);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public List<PrescriptionManagementForm> retrieveProductByID(int productID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm productForm = new PrescriptionManagementForm();

		try {

			connection = getConnection();

			String retrieveProductByIDQuery = QueryMaker.RETRIEVE_PRODUCT_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveProductByIDQuery);

			preparedStatement.setInt(1, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				productForm.setProductID(resultSet.getInt("id"));
				productForm.setDrugName(resultSet.getString("drugName"));
				productForm.setDescription(resultSet.getString("description"));
				// productForm.setActivePrice(resultSet.getInt("activePrice"));
				productForm.setCategoryID(resultSet.getInt("categoryID"));
				productForm.setBarcode(resultSet.getString("barcode"));
				productForm.setUnit(resultSet.getString("unit"));
				productForm.setMinQuantity(resultSet.getDouble("minQuantity"));
				productForm.setTradeName(resultSet.getString("tradeName"));
				productForm.setCode(resultSet.getString("code"));
				productForm.setDose(resultSet.getDouble("strength"));
				productForm.setProductFormName(resultSet.getString("form"));

			}

			list.add(productForm);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		}

		catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;

	}

	public List<PrescriptionManagementForm> retrieveProductPriceList(int productID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm productForm = null;

		/*
		 * Converting outstanding paid date into DD-MM-YYYY format in order display it
		 */
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String retrieveProductPriceByIDQuery = QueryMaker.RETRIEVE_PRODUCT_PRICE_BY_ID;

			preparedStatement1 = connection.prepareStatement(retrieveProductPriceByIDQuery);

			preparedStatement1.setInt(1, productID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				productForm = new PrescriptionManagementForm();

				productForm.setId(resultSet1.getInt("id"));
				productForm.setTaxName(resultSet1.getString("taxName"));
				productForm.setCostPrice(resultSet1.getDouble("costPrice"));
				productForm.setSellingPrice(resultSet1.getDouble("sellingPrice"));

				if (resultSet1.getInt("taxInclusive") == 0) {
					productForm.setTaxInclusiveValue("Yes");
				} else {
					productForm.setTaxInclusiveValue("No");
				}

				productForm.setTaxPercent(resultSet1.getDouble("taxPercent"));
				productForm.setActivityStatus(resultSet1.getString("activityStatus"));
				productForm.setProductID(resultSet1.getInt("productID"));

				date = dbDateFormat.parse(resultSet1.getString("priceFrom"));

				productForm.setPriceFrom(dateFormat.format(date));

				list.add(productForm);

			}

			resultSet1.close();
			preparedStatement1.close();

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;

	}

	public List<String> retrieveTaxList() {
		List<String> productTaxList = new ArrayList<String>();
		try {

			connection = getConnection();

			String retrieveTaxListQuery = QueryMaker.RETRIEVE_PRODUCT_TAX;

			preparedStatement = connection.prepareStatement(retrieveTaxListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				productTaxList.add(resultSet.getString("taxName"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.getSuppressed();
		}
		return productTaxList;
	}

	public List<PrescriptionManagementForm> searchProduct(String searchProductName, int clinicID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm productForm = null;

		try {

			connection = getConnection();

			String searchProductQuery = QueryMaker.SEARCH_PRODUCT;

			preparedStatement = connection.prepareStatement(searchProductQuery);

			if (searchProductName.contains(" ")) {
				searchProductName = searchProductName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchProductName + "%");
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				productForm = new PrescriptionManagementForm();

				productForm.setProductID(resultSet.getInt("id"));
				productForm.setDrugName(resultSet.getString("tradeName"));
				productForm.setDescription(resultSet.getString("description"));
				// productForm.setActivePrice(resultSet.getInt("activePrice"));
				productForm.setActivityStatus(resultSet.getString("activityStatus"));
				productForm.setCategoryName(retrieveProductCategoryName(resultSet.getInt("categoryID")));

				list.add(productForm);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public String updateProduct(PrescriptionManagementForm form) {
		try {

			connection = getConnection();

			String updateProductQuery = QueryMaker.UPDATE_PRODUCT;

			preparedStatement = connection.prepareStatement(updateProductQuery);

			preparedStatement.setString(1, form.getDrugName());
			preparedStatement.setString(2, form.getTradeName());
			preparedStatement.setString(3, form.getBarcode());
			preparedStatement.setString(4, form.getDescription());
			preparedStatement.setString(5, form.getUnit());
			preparedStatement.setInt(6, form.getCategoryID());
			preparedStatement.setDouble(7, form.getMinQuantity());
			preparedStatement.setDouble(8, form.getDose());
			preparedStatement.setString(9, form.getProductFormName());
			preparedStatement.setString(10, form.getCode());
			preparedStatement.setInt(11, form.getProductID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Product updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	public void inactiveProductPrive(int productID) {

		try {

			connection = getConnection();

			String inactiveProductPriveQuery = QueryMaker.INACTIVE_PRODUCT_PRICE;

			preparedStatement = connection.prepareStatement(inactiveProductPriveQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, productID);

			preparedStatement.execute();

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public String insertProductPrice(int productID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateActiveProductPrice(int productID, double activePrice) {

		try {

			connection = getConnection();

			String updateActiveProductPriceQuery = QueryMaker.UPDATE_ACTIVE_PRODUCT_PRICE;

			preparedStatement = connection.prepareStatement(updateActiveProductPriceQuery);

			preparedStatement.setDouble(1, activePrice);
			preparedStatement.setInt(2, productID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String insertSupplier(PrescriptionManagementForm form) {

		/*
		 * Converting outstanding paid date into YYYY-MM-DD format in order insert the
		 * record into database
		 */
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String insertSupplierQuery = QueryMaker.INSERT_SUPPLIER;

			preparedStatement = connection.prepareStatement(insertSupplierQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setString(2, form.getAgency());
			preparedStatement.setString(3, form.getVatNumber());
			preparedStatement.setString(4, form.getMobile());
			preparedStatement.setString(5, form.getEmail());
			preparedStatement.setString(6, form.getAddress());
			preparedStatement.setString(7, form.getCity());
			preparedStatement.setString(8, form.getState());
			preparedStatement.setString(9, form.getCountry());
			preparedStatement.setString(10, form.getPinCode());
			preparedStatement.setString(11, dbDateFormat.format(dateFormat.parse(form.getRegistrationDate())));
			preparedStatement.setString(12, ActivityStatus.ENABLE);
			preparedStatement.execute();

			status = "success";

			System.out.println("Supplier added successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting supplier details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveAllSuppliers() {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String retrieveAllSuppliersQuery = QueryMaker.RETRIEVE_ALL_SUPPLIER;

			preparedStatement = connection.prepareStatement(retrieveAllSuppliersQuery);

			preparedStatement.setString(1, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setSupplierID(resultSet.getInt("id"));
				form.setName((resultSet.getString("name")));
				form.setAgency(resultSet.getString("agency"));
				form.setVatNumber(resultSet.getString("vatNumber"));
				form.setMobile(resultSet.getString("mobile"));
				form.setEmail(resultSet.getString("email"));
				form.setAddress(resultSet.getString("address"));
				form.setCity(resultSet.getString("city"));
				form.setState(resultSet.getString("state"));
				form.setCountry(resultSet.getString("country"));
				form.setPinCode(resultSet.getString("pinCode"));
				form.setRegistrationDate(resultSet.getString("registrationDate"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving all supplier list due to:::" + exception.getMessage());
		}
		return list;

	}

	public String updateSupplier(PrescriptionManagementForm form) {
		/*
		 * Converting outstanding paid date into YYYY-MM-DD format in order insert the
		 * record into database
		 */
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String updateSupplierQuery = QueryMaker.UPDATE_SUPPLIER;

			preparedStatement = connection.prepareStatement(updateSupplierQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setString(2, form.getAgency());
			preparedStatement.setString(3, form.getVatNumber());
			preparedStatement.setString(4, form.getMobile());
			preparedStatement.setString(5, form.getEmail());
			preparedStatement.setString(6, form.getAddress());
			preparedStatement.setString(7, form.getCity());
			preparedStatement.setString(8, form.getState());
			preparedStatement.setString(9, form.getCountry());
			preparedStatement.setString(10, form.getPinCode());
			preparedStatement.setString(11, dbDateFormat.format(dateFormat.parse(form.getRegistrationDate())));
			preparedStatement.setString(12, ActivityStatus.ENABLE);
			preparedStatement.setInt(13, form.getSupplierID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Supplier updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while udpating supplier details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveAllSuppliers(int supplierID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String retrieveAllSuppliersQuery = QueryMaker.RETRIEVE_ALL_SUPPLIERS;

			preparedStatement = connection.prepareStatement(retrieveAllSuppliersQuery);

			preparedStatement.setInt(1, supplierID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setSupplierID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setAgency(resultSet.getString("agency"));
				form.setVatNumber(resultSet.getString("vatNumber"));
				form.setMobile(resultSet.getString("mobile"));
				form.setEmail(resultSet.getString("email"));
				form.setAddress(resultSet.getString("address"));
				form.setCity(resultSet.getString("city"));
				form.setState(resultSet.getString("state"));
				form.setCountry(resultSet.getString("country"));
				form.setPinCode(resultSet.getString("pinCode"));
				form.setRegistrationDate(resultSet.getString("registrationDate"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving all supplier list due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<PrescriptionManagementForm> retrieveSupplierByID(int supplierID, String searchSupplierName) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {
			connection = getConnection();

			String retrieveSupplierByIDQuery = QueryMaker.RETRIEVE_SUPPLIER_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveSupplierByIDQuery);

			preparedStatement.setInt(1, supplierID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setSupplierID(resultSet.getInt("id"));
				form.setName((resultSet.getString("name")));
				form.setAgency(resultSet.getString("agency"));
				form.setVatNumber(resultSet.getString("vatNumber"));
				form.setEmail(resultSet.getString("email"));
				form.setAddress(resultSet.getString("address"));
				form.setMobile(resultSet.getString("mobile"));
				form.setCity(resultSet.getString("city"));
				form.setState(resultSet.getString("state"));
				form.setCountry(resultSet.getString("country"));
				form.setPinCode(resultSet.getString("pinCode"));
				form.setRegistrationDate(resultSet.getString("registrationDate"));
				form.setSearchSupplierName(searchSupplierName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while retrieving all supplier list due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<PrescriptionManagementForm> searchSupplier(String searchSupplierName) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String searchSupplierQuery = QueryMaker.SEARCH_SUPPLIER;

			preparedStatement = connection.prepareStatement(searchSupplierQuery);

			if (searchSupplierName.contains(" ")) {
				searchSupplierName = searchSupplierName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchSupplierName + "%");
			preparedStatement.setString(2, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setSupplierID(resultSet.getInt("id"));
				form.setName(resultSet.getString("name"));
				form.setAgency(resultSet.getString("agency"));
				form.setVatNumber(resultSet.getString("vatNumber"));
				form.setMobile(resultSet.getString("mobile"));
				form.setEmail(resultSet.getString("email"));
				form.setAddress(resultSet.getString("address"));
				form.setCity(resultSet.getString("city"));
				form.setState(resultSet.getString("state"));
				form.setCountry(resultSet.getString("country"));
				form.setPinCode(resultSet.getString("pinCode"));
				form.setRegistrationDate(resultSet.getString("registrationDate"));
				form.setSearchSupplierName(searchSupplierName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching supplier list due to:::" + exception.getMessage());
		}
		return list;

	}

	public String deleteSupplier(int supplierID) {

		try {

			connection = getConnection();

			String deleteSupplierQuery = QueryMaker.DELETE_SUPPLIER;

			preparedStatement = connection.prepareStatement(deleteSupplierQuery);

			preparedStatement.setString(1, ActivityStatus.DISABLE);
			preparedStatement.setInt(2, supplierID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Supplier delete successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Supplier due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveAllSuppliers(String searchSupplierName) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject verifyProductExist(String productBarcode) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		boolean result = false;

		try {
			connection = getConnection();

			String verifyProductQuery = QueryMaker.VERIFY_PRODUCT_EXIST;

			preparedStatement = connection.prepareStatement(verifyProductQuery);

			preparedStatement.setString(1, productBarcode);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				result = true;

			}

			if (result) {

				object.put("ProductCheck", "check");

				array.add(object);

				values.put("Release", array);

				preparedStatement.close();

				return values;

			} else {

				object.put("ProductCheck", "uncheck");

				array.add(object);

				values.put("Release", array);

				preparedStatement.close();

				return values;

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying product exist or not");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrieveTaxPercentByTaxName(String taxName) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String retrieveTaxPercentByTaxNameQuery = QueryMaker.RETRIEVE_TAX_PERCENT_BY_TAX_NAME;

			preparedStatement = connection.prepareStatement(retrieveTaxPercentByTaxNameQuery);

			preparedStatement.setString(1, taxName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object.put("taxPercent", resultSet.getDouble("taxPercent"));

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving tax percent based on tax name");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public String retrieveReceiptNo(int clinicID, String suffix) {

		String receiptNo = "";

		int check = 0;

		try {

			connection = getConnection();

			String retrieveReceiptNoQuery = QueryMaker.RETRIEVE_RECEIPT_NO;

			preparedStatement = connection.prepareStatement(retrieveReceiptNoQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				String[] receiptNoArray = resultSet.getString("receiptNo").split("-");

				receiptNo = suffix + "-ST-" + (Integer.parseInt(receiptNoArray[2]) + 1);

			}

			if (check == 0) {

				receiptNo = suffix + "-ST-1";

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return receiptNo;
	}

	public String retrievePracticeSuffix(int practiceID) {

		String suffix = "";

		try {

			connection1 = getConnection();

			String retrievePracticeSuffixQuery = QueryMaker.RETRIEVE_PRACTICE_SUFFIX;

			preparedStatement1 = connection1.prepareStatement(retrievePracticeSuffixQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				suffix = resultSet1.getString("suffix");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return suffix;

	}

	public HashMap<Integer, String> retrieveProductList(int clinicID) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveProductListQuery = QueryMaker.RETRIEVE_PRODUCT_LIST;

			preparedStatement = connection.prepareStatement(retrieveProductListQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("tradeName"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public HashMap<Integer, String> retrieveSupplierList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveSupplierListQuery = QueryMaker.RETRIEVE_SUPPLIER_LIST;

			preparedStatement = connection.prepareStatement(retrieveSupplierListQuery);

			preparedStatement.setString(1, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getInt("id"), resultSet.getString("name"));

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public JSONObject retrieveSupplierDetailsByID(int supplierID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String retrieveSupplierDetailsByIDQuery = QueryMaker.RETRIEVE_SUPPLIER_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveSupplierDetailsByIDQuery);

			preparedStatement.setInt(1, supplierID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object.put("clientID", resultSet.getInt("id"));
				object.put("clientMobile", resultSet.getString("mobile"));
				object.put("clientAgency", resultSet.getString("agency"));
				object.put("clientAddress", resultSet.getString("address"));
				object.put("clientVAT", resultSet.getString("vatNumber"));

				check = 1;

				object.put("check", check);

				array.add(object);

			}
			values.put("Release", array);

			resultSet.close();

			preparedStatement.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving supplier details based on supplierID");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrieveProductRateDetailsByProductID(int productID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String retrieveProductRateDetailsByProductIDQuery = QueryMaker.RETRIEVE_PRODUCT_RATE_DETAILS_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveProductRateDetailsByProductIDQuery);

			preparedStatement.setInt(1, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object.put("productBarcode", resultSet.getString("barcode"));

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product rate from product ID");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrieveProductIDByBarcode(String barcode) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		try {
			connection = getConnection();

			String retrieveProductIDByBarcodeQuery = QueryMaker.RETRIEVE_PRODUCT_ID_BY_BARCODE;

			preparedStatement = connection.prepareStatement(retrieveProductIDByBarcodeQuery);

			preparedStatement.setString(1, barcode);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				object.put("productID", resultSet.getInt("id"));
				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {

				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product ID from barcode");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrieveProductNameByID(int productID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String retrieveProductNameByIDQuery = QueryMaker.RETRIEVE_PRODUCT_NAME_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveProductNameByIDQuery);

			preparedStatement.setInt(1, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object.put("productName", resultSet.getString("tradeName"));

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product name based on ID");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public String insertStockReceipt(PrescriptionManagementForm form, int userID) {

		try {

			connection = getConnection();

			String insertStockReceiptQuery = QueryMaker.INSERT_STOCK_RECEIPT;

			preparedStatement = connection.prepareStatement(insertStockReceiptQuery);

			preparedStatement.setString(1, form.getReceiptNo());
			preparedStatement.setDouble(2, form.getProductTotalAmount());
			preparedStatement.setDouble(3, form.getProductBalancePaymentAmt());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, userID);
			preparedStatement.setInt(6, form.getSupplierID());
			preparedStatement.setInt(7, form.getClinicID());

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public int retrieveLastStockReceiptID() {

		int stockReceiptID = 0;

		try {

			connection = getConnection();

			String retrieveLastStockReceiptIDQuery = QueryMaker.RETRIEVE_LAST_INSERTED_STOCK_RECEIPT_ID;

			preparedStatement = connection.prepareStatement(retrieveLastStockReceiptIDQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				stockReceiptID = resultSet.getInt("id");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return stockReceiptID;
	}

	public String insertStock(double quantity, int productID, double amount, double taxPercent, int stockReceiptID,
			String expiryDate, String stockTransaction, double costPrice, double sellingPrice, int taxInclusive,
			String taxName, int clinicID) {

		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateToBeFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {

			connection = getConnection();

			String insertStockQuery = QueryMaker.INSERT_STOCK;

			preparedStatement = connection.prepareStatement(insertStockQuery);

			preparedStatement.setString(1, dateToBeFormat.format(date));
			// preparedStatement.setDouble(2, quantity);
			preparedStatement.setInt(2, productID);
			preparedStatement.setDouble(3, amount);

			/*
			 * calculating tax Amount by multiplying amount and taxPercent and diving the
			 * result by 100
			 */
			double finalTaxAmount = (amount * taxPercent) / 100;

			preparedStatement.setDouble(5, finalTaxAmount);

			if (expiryDate == null || expiryDate == "") {
				preparedStatement.setString(4, null);
			} else {
				if (expiryDate.isEmpty()) {
					preparedStatement.setString(4, null);
				} else {
					preparedStatement.setString(4, dateToBeFormat.format(dateFormat.parse(expiryDate)));
				}
			}
			preparedStatement.setString(6, stockTransaction);
			preparedStatement.setInt(7, stockReceiptID);
			preparedStatement.setDouble(8, costPrice);
			preparedStatement.setDouble(9, sellingPrice);
			preparedStatement.setInt(10, taxInclusive);
			preparedStatement.setString(11, taxName);
			preparedStatement.setDouble(12, taxPercent);
			preparedStatement.setDouble(13, quantity);
			preparedStatement.setInt(14, clinicID);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public double retrieveTaxPercentByProductID(int productID) {

		double taxPercent = 0D;

		try {

			connection = getConnection();

			String retrieveTaxPercentByProductIDQuery = QueryMaker.RETRIEVE_TAX_PERCENT_BY_PRODUCT_ID;

			preparedStatement = connection.prepareStatement(retrieveTaxPercentByProductIDQuery);

			preparedStatement.setInt(1, productID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				taxPercent = resultSet.getDouble("taxPercent");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return taxPercent;
	}

	public double retrieveProductQuantity(int stockID) {

		double quantity = 0;

		try {

			connection = getConnection();

			String retrieveProductQuantityQuery = QueryMaker.RETRIEVE_PRODUCT_QUANTITY;

			preparedStatement = connection.prepareStatement(retrieveProductQuantityQuery);

			preparedStatement.setInt(1, stockID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				quantity = resultSet.getDouble("quantity");

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return quantity;
	}

	public String updateProductNetStock(int stockID, double quantity) {

		try {

			connection = getConnection();

			String updateProductNetStockQuery = QueryMaker.UPDATE_PRODUCT_QUANTITY;

			preparedStatement = connection.prepareStatement(updateProductNetStockQuery);

			preparedStatement.setDouble(1, quantity);
			preparedStatement.setInt(2, stockID);

			preparedStatement.executeUpdate();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			status = "error";

		}

		return status;
	}

	public List<PrescriptionManagementForm> searchStockList(String searchName, String searchCriteria, int clinicID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection = getConnection();

			/*
			 * check what is search criteria and depending upon that search stock receipt
			 * details
			 */
			if (searchCriteria.equals("StockRecptNo")) {

				String searchStockListQuery = QueryMaker.SEARCH_STOCK_RECEIPT;

				preparedStatement = connection.prepareStatement(searchStockListQuery);

				preparedStatement.setString(1, ActivityStatus.ENABLE);
				preparedStatement.setString(2, "%" + searchName + "%");
				preparedStatement.setString(3, "%" + searchName + "%");
				preparedStatement.setInt(4, clinicID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					form = new PrescriptionManagementForm();

					form.setStockReceiptID(resultSet.getInt("id"));
					form.setReceiptNo(resultSet.getString("receiptNo"));
					form.setReceiptDate(dateFormat.format(resultSet.getTimestamp("receiptDate")));
					form.setProductTotalAmount(resultSet.getDouble("totalAmount"));
					form.setName(resultSet.getString("supplier"));
					form.setClinicName(resultSet.getString("clinic"));

					list.add(form);

				}

				resultSet.close();
				preparedStatement.close();
				connection.close();

			} else {

				String searchStockListQuery = QueryMaker.SEARCH_STOCK_RECEIPT_BY_PRODUCT_NAME;

				preparedStatement = connection.prepareStatement(searchStockListQuery);

				preparedStatement.setString(1, ActivityStatus.ENABLE);
				preparedStatement.setString(2, "%" + searchName + "%");
				preparedStatement.setString(3, ActivityStatus.ACTIVE);
				preparedStatement.setInt(4, clinicID);

				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {

					form = new PrescriptionManagementForm();

					form.setStockReceiptID(resultSet.getInt("id"));
					form.setReceiptNo(resultSet.getString("receiptNo"));
					form.setReceiptDate(dateFormat.format(resultSet.getTimestamp("receiptDate")));
					form.setProductTotalAmount(resultSet.getDouble("totalAmount"));
					form.setName(resultSet.getString("supplier"));
					form.setClinicName(resultSet.getString("clinic"));

					list.add(form);

				}

				resultSet.close();
				preparedStatement.close();
				connection.close();

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public List<PrescriptionManagementForm> retrieveAllStockList(int clinicID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection = getConnection();

			String retrieveAllStockListQuery = QueryMaker.RETRIEVE_ALL_STOCK_LIST;

			preparedStatement = connection.prepareStatement(retrieveAllStockListQuery);

			preparedStatement.setString(1, ActivityStatus.ENABLE);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setStockReceiptID(resultSet.getInt("id"));
				form.setReceiptNo(resultSet.getString("receiptNo"));
				form.setReceiptDate(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				form.setProductTotalAmount(resultSet.getDouble("totalAmount"));
				form.setName(resultSet.getString("supplier"));
				form.setClinicName(resultSet.getString("clinic"));

				list.add(form);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public double retrieveTotalTaxAmount(int stockReceiptID) {

		double taxAmount = 0D;

		try {

			connection = getConnection();

			String retrieveTotalTaxAmountQuery = QueryMaker.RETRIEVE_TAX_AMOUNT;

			preparedStatement = connection.prepareStatement(retrieveTotalTaxAmountQuery);

			preparedStatement.setInt(1, stockReceiptID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				taxAmount = resultSet.getDouble("tax");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return taxAmount;
	}

	public String updateTaxAmount(int stockReceiptID, double taxAmount) {

		try {

			connection = getConnection();

			String updateTaxAmountQuery = QueryMaker.UPDATE_TAX_AMOUNT;

			preparedStatement = connection.prepareStatement(updateTaxAmountQuery);

			preparedStatement.setDouble(1, taxAmount);
			preparedStatement.setInt(2, stockReceiptID);

			preparedStatement.executeUpdate();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public JSONObject deleteProduct(int stockID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		try {
			connection = getConnection();

			String deleteProductQuery = QueryMaker.DELETE_PRODUCT_FROM_STOCK;

			preparedStatement = connection.prepareStatement(deleteProductQuery);

			preparedStatement.setInt(1, stockID);

			preparedStatement.executeUpdate();

			object.put("check", "1");

			array.add(object);

			values.put("Release", array);

			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while deleting product from stock");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public List<PrescriptionManagementForm> retireveStockProductList(int stockReceiptID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {

			connection = getConnection();

			String retireveStockProductListQuery = QueryMaker.RETRIEVE_STOCK_PRODUCT_LIST;

			preparedStatement = connection.prepareStatement(retireveStockProductListQuery);

			preparedStatement.setInt(1, stockReceiptID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setStockID(resultSet.getInt("id"));

				int productID = resultSet.getInt("productID");

				form.setReceiptID(resultSet.getInt("stockReceiptID"));

				// Retrieving productName and rate and Tax
				String productName = retrieveProductNameRateTaxByProductID(productID);

				// Splitting productNameRateTax by = in order to get productname
				// and rate and tax seperately
				// String[] array = productNameRateTax.split("=");

				// String productName = array[0];

				// double productRate = Double.parseDouble(array[1]);

				// double productTax = Double.parseDouble(array[2]);

				// Retrieving product barcode from Product table based on
				// productID
				String productBarcode = retrieveProductBarcodeByProductID(productID);

				String taxInclusiveArr[] = { "Yes", "No" };

				form.setProductBarcode(productBarcode);
				form.setProductID(productID);
				form.setProductName(productName);
				// form.setRate(productRate);
				form.setAmount(resultSet.getDouble("amount"));
				form.setProductVAT(resultSet.getDouble("taxAmount"));
				form.setQuantity(retrieveProductQuantityFromStockTransaction(resultSet.getInt("id")));

				if (resultSet.getString("expiryDate") == null || resultSet.getString("expiryDate") == "") {
					form.setExpireDate("");
				} else {
					if (resultSet.getString("expiryDate").isEmpty()) {
						form.setExpireDate("");
					} else {
						form.setExpireDate(dateFormat.format(resultSet.getDate("expiryDate")));
					}
				}

				form.setNetStock(resultSet.getDouble("netStock"));
				form.setTaxInclusiveName(taxInclusiveArr[resultSet.getInt("taxInclusive")]);
				form.setTaxName(resultSet.getString("taxName"));
				form.setTaxPercent(resultSet.getDouble("taxPercent"));
				form.setCostPrice(resultSet.getDouble("costPrice"));
				form.setSellingPrice(resultSet.getDouble("sellingPrice"));
				form.setClinicID(resultSet.getInt("clinicID"));
				// form.setClinicName(resultSet.getString("clinic"));

				list.add(form);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrieveProductNameRateTaxByProductID(int productID) {

		String nameAndrate = "";

		try {

			connection1 = getConnection();

			String retrieveProductNameRateTaxByProductIDQuery = QueryMaker.RETRIEVE_PRODUCT_NAME_AND_RATE_BY_PRODUCT_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveProductNameRateTaxByProductIDQuery);

			preparedStatement1.setInt(1, productID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				nameAndrate = resultSet1.getString("tradeName");

				System.out.println("String is ::: " + nameAndrate);

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return nameAndrate;
	}

	public double retrieveProductQuantityFromStockTransaction(int stockID) {

		double quantity = 0D;

		try {

			connection1 = getConnection();

			String retrieveProductQuantityFromStockTransactionQuery = QueryMaker.RETRIEVE_PRODUCT_QUANTITY_FROM_STOCK_TRANSACTION;

			preparedStatement1 = connection1.prepareStatement(retrieveProductQuantityFromStockTransactionQuery);

			preparedStatement1.setInt(1, stockID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				quantity = resultSet1.getDouble("quantity");

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return quantity;
	}

	public String retrieveProductBarcodeByProductID(int productID) {

		String productBarcode = "";

		try {

			connection1 = getConnection();

			String retrieveProductBarcodeByProductIDQuery = QueryMaker.RETRIEVE_PRODUCT_BARCODE_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveProductBarcodeByProductIDQuery);

			preparedStatement1.setInt(1, productID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				productBarcode = resultSet1.getString("barcode");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return productBarcode;
	}

	public double retrieveProductNetStock(int productID) {

		double netStock = 0;

		try {

			connection1 = getConnection();

			String retrieveProductNetStockQuery = QueryMaker.RETRIEVE_PRODUCT_NET_STOCK;

			preparedStatement1 = connection1.prepareStatement(retrieveProductNetStockQuery);

			preparedStatement1.setInt(1, productID);
			preparedStatement1.setString(2, ActivityStatus.ACTIVE);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				netStock = resultSet1.getDouble("netStock");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return netStock;
	}

	public List<PrescriptionManagementForm> retireveStockReceiptList(int stockReceiptID, int clinicID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = new PrescriptionManagementForm();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {

			connection = getConnection();

			String retireveStockReceiptListQuery = QueryMaker.RETRIEVE_STOCK_RECEIPT_LIST;

			preparedStatement = connection.prepareStatement(retireveStockReceiptListQuery);

			preparedStatement.setInt(1, stockReceiptID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setStockReceiptID(resultSet.getInt("id"));
				form.setReceiptNo(resultSet.getString("receiptNo"));
				form.setReceiptDate(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				form.setProductTotalAmount(resultSet.getDouble("totalAmount"));
				form.setProductBalancePaymentAmt(resultSet.getDouble("balPayment"));
				form.setProductVAT(resultSet.getDouble("tax"));

				// calculating advance payment amount by subtracting balance
				// amount from total amount
				form.setProductAdvancePaymentAmt(form.getProductTotalAmount() - form.getProductBalancePaymentAmt());

				form.setSupplierID(resultSet.getInt("supplierID"));
				form.setReceiptByName(retrieveUsernameByID(resultSet.getInt("userID")));

				if (resultSet.getInt("clinicID") == 0 || resultSet.getInt("clinicID") == -1) {
					form.setClinicID(clinicID);
				} else {
					form.setClinicID(resultSet.getInt("clinicID"));
				}

			}

			resultSet.close();
			preparedStatement.close();

			/*
			 * Retrieving Supplier details based on supplierID
			 */
			String retrieveSupplierDetailsQuery = QueryMaker.RETRIEVE_SUPPLIER_BY_ID;

			preparedStatement1 = connection.prepareStatement(retrieveSupplierDetailsQuery);

			preparedStatement1.setInt(1, form.getSupplierID());

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				form.setName(resultSet1.getString("name"));
				form.setAgency(resultSet1.getString("agency"));
				form.setMobile(resultSet1.getString("mobile"));
				form.setAddress(resultSet1.getString("address"));
				form.setVatNumber(resultSet1.getString("vatNumber"));

			}

			list.add(form);

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	/**
	 * 
	 * @param userID
	 * @return
	 */
	public String retrieveUsernameByID(int userID) {

		String userName = "";

		try {

			connection1 = getConnection();

			String retrieveUsernameByIDQuery = QueryMaker.RETREIVE_USER_BY_USER_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveUsernameByIDQuery);

			preparedStatement1.setInt(1, userID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {

				if (resultSet1.getString("middleName") == null || resultSet1.getString("middleName") == "") {

					userName = resultSet1.getString("firstName") + " " + resultSet1.getString("lastName");

				} else {

					userName = resultSet1.getString("firstName") + " " + resultSet1.getString("middleName") + " "
							+ resultSet1.getString("lastName");

				}

			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return userName;
	}

	public String updateStockReceipt(PrescriptionManagementForm form, int userID) {

		try {

			connection = getConnection();

			String updateStockReceiptQuery = QueryMaker.UPDATE_STOCK_RECEIPT;

			preparedStatement = connection.prepareStatement(updateStockReceiptQuery);

			preparedStatement.setString(1, form.getReceiptNo());
			preparedStatement.setDouble(2, form.getProductTotalAmount());
			preparedStatement.setDouble(3, form.getProductBalancePaymentAmt());
			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(5, userID);
			preparedStatement.setInt(6, form.getSupplierID());
			preparedStatement.setInt(7, form.getClinicID());
			preparedStatement.setInt(8, form.getStockReceiptID());

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public double retrieveProductsTaxAmount(int productID) {

		double taxAmount = 0D;

		try {

			connection = getConnection();

			String retrieveProductsTaxAmountQuery = QueryMaker.RETRIEVE_PRODUCT_TAX_AMOUNT;

			preparedStatement = connection.prepareStatement(retrieveProductsTaxAmountQuery);

			preparedStatement.setInt(1, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				taxAmount = resultSet.getDouble("taxAmount");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return taxAmount;
	}

	public void updateStockReceiptTaxAmount(int stockReceiptID, double taxAmountToBeSubtracted) {

		try {

			connection = getConnection();

			String updateStockReceiptTaxAmountQuery = QueryMaker.UPDATE_STOCK_RECEIPT_TAX_AMOUNT;

			preparedStatement = connection.prepareStatement(updateStockReceiptTaxAmountQuery);

			preparedStatement.setDouble(1, taxAmountToBeSubtracted);
			preparedStatement.setInt(2, stockReceiptID);

			preparedStatement.executeUpdate();

			System.out.println("Tax amount from StockReceipt updated successfully for deleted product from Stock.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			System.out.println("Failed to update tax amount in StockReceipt table for deleted product from Stock.");
		}

	}

	public List<PrescriptionManagementForm> searchOrderStock(int supplierID, int clinicID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		int srNo = 1;

		try {

			connection = getConnection();

			String searchOrderStockQuery = QueryMaker.SEARCH_STOCK_ORDER;

			preparedStatement = connection.prepareStatement(searchOrderStockQuery);

			preparedStatement.setInt(1, supplierID);
			preparedStatement.setInt(2, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setProductID(resultSet.getInt("productID"));
				form.setProductName(resultSet.getString("tradeName"));
				form.setProductBarcode(resultSet.getString("barcode"));
				form.setNetStock(resultSet.getDouble("netStock"));
				form.setMinQuantity(resultSet.getDouble("minQuantity"));
				form.setName(retrieveSupplierNameByID(supplierID));
				form.setSupplierID(supplierID);
				form.setClinicID(resultSet.getInt("clinicID"));
				form.setClinicName(resultSet.getString("clinic"));
				form.setSrNo(srNo);

				list.add(form);

				srNo++;

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}

	public String retrieveSupplierNameByID(int supplierID) {

		String supplierName = "";

		try {

			connection1 = getConnection();

			String retrieveSupplierNameByIDQuery = QueryMaker.RETRIEVE_SUPPLIE_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveSupplierNameByIDQuery);

			preparedStatement1.setInt(1, supplierID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				supplierName = resultSet1.getString("name");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return supplierName;
	}

	public String retrieveProductNameByProductID(int productID) {

		String productName = "";

		try {

			connection1 = getConnection();

			String retrieveProductNameByProductIDQuery = QueryMaker.RETRIEVE_PRODUCT_NAME_BY_ID;

			preparedStatement1 = connection1.prepareStatement(retrieveProductNameByProductIDQuery);

			preparedStatement1.setInt(1, productID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				productName = resultSet1.getString("tradeName");
			}

			resultSet1.close();
			preparedStatement1.close();
			connection1.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return productName;
	}

	public String insertOrder(int supplierID, int productID, double quantity, int userID, int clinicID,
			String orderNo) {

		try {

			connection = getConnection();

			String insertOrderQuery = QueryMaker.INSERT_ORDER;

			preparedStatement = connection.prepareStatement(insertOrderQuery);

			preparedStatement.setInt(1, supplierID);
			preparedStatement.setInt(2, productID);
			preparedStatement.setDouble(3, quantity);
			preparedStatement.setInt(4, userID);
			preparedStatement.setInt(5, clinicID);
			preparedStatement.setString(6, orderNo);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String retrieveClinicNameFromPracticeID(int practiceID) {

		String clinicName = "";

		try {

			connection = getConnection();

			String retrieveClinicNameFromPracticeIDQuery = QueryMaker.RETRIEVE_CLINIC_NAME_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveClinicNameFromPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				clinicName = resultSet.getString("name");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return clinicName;
	}

	public String retrieveEmailFromByPracticeID(int practiceID) {

		String emaiLFrom = "";

		try {

			connection = getConnection();

			String retrieveEmailFromByPracticeIDQuery = QueryMaker.RETRIEVE_EMAIL_FROM_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveEmailFromByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emaiLFrom = resultSet.getString("emailFrom");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return emaiLFrom;
	}

	public String retrieveEmailFromPassByPracticeID(int practiceID) {

		String emaiLFromPass = "";

		try {

			connection = getConnection();

			String retrieveEmailFromPassByPracticeIDQuery = QueryMaker.RETRIEVE_EMAIL_FROM_PASS_BY_PRACTICE_ID;

			preparedStatement = connection.prepareStatement(retrieveEmailFromPassByPracticeIDQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emaiLFromPass = resultSet.getString("emailFromPass");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return emaiLFromPass;
	}

	public String retrieveSupplierEmailID(int supplierID) {

		String supplierName = "";

		try {

			connection = getConnection();

			String retrieveSupplierEmailIDQuery = QueryMaker.RETRIEVE_SUPPLIER_NAME_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveSupplierEmailIDQuery);

			preparedStatement.setInt(1, supplierID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				supplierName = resultSet.getString("email");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return supplierName;
	}

	public int retrieveLastEnteredStockID(int stockReceiptID, int productID) {

		int stockID = 0;

		try {

			connection = getConnection();

			String retrieveLastEnteredStockIDQuery = QueryMaker.RETRIEVE_STOCK_ID;

			preparedStatement = connection.prepareStatement(retrieveLastEnteredStockIDQuery);

			preparedStatement.setInt(1, stockReceiptID);
			preparedStatement.setInt(2, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				stockID = resultSet.getInt("id");
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return stockID;
	}

	public String insertStockTransaction(int stockID, double quantity, String action) {

		try {

			connection = getConnection();

			String insertStockTransactionQuery = QueryMaker.INSERT_STOCK_TRANSACTION;

			preparedStatement = connection.prepareStatement(insertStockTransactionQuery);

			preparedStatement.setInt(1, stockID);
			preparedStatement.setDouble(2, quantity);
			preparedStatement.setString(3, action);

			preparedStatement.execute();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String updateStockClinic(int stockReceiptID, int clinicID) {

		try {

			connection = getConnection();

			String updateStockClinicQuery = QueryMaker.UPDATE_STOCK_CLINIC;

			preparedStatement = connection.prepareStatement(updateStockClinicQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setInt(2, stockReceiptID);

			preparedStatement.executeUpdate();

			status = "success";

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}

		return status;
	}

	public String retrieveOrderNumber(String clinicSuffix) {

		String orderNo = "";

		int check = 0;

		try {

			connection = getConnection();

			String retrieveOrderNumberQuery = QueryMaker.RETRIEVE_ORDER_NO;

			preparedStatement = connection.prepareStatement(retrieveOrderNumberQuery);

			preparedStatement.setString(1, "%" + clinicSuffix + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				String array[] = resultSet.getString("orderNumber").split("-");

				int no = Integer.parseInt(array[1]) + 1;

				orderNo = clinicSuffix + "-" + no;

			}

			if (check == 0) {
				orderNo = clinicSuffix + "-1";
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return orderNo;
	}

	public boolean verifyOrderCreated(String orderNo) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyOrderCreatedQuery = QueryMaker.VERIFY_ORDER_CREATED;

			preparedStatement = connection.prepareStatement(verifyOrderCreatedQuery);

			preparedStatement.setString(1, orderNo);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyProductExits(String tradeName, int clinicID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyProductExitsQuery = QueryMaker.VERIFY_TRADE_NAME_EXISTS;

			preparedStatement = connection.prepareStatement(verifyProductExitsQuery);

			preparedStatement.setString(1, tradeName);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public JSONObject retrieveStockReceiptNoByClinicID(int clinicID, String clinicSuffix) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		int check = 0;

		String receiptNo = "";

		try {
			connection = getConnection();

			String retrieveStockReceiptNoByClinicIDQuery = QueryMaker.RETRIEVE_RECEIPT_NO;

			preparedStatement = connection.prepareStatement(retrieveStockReceiptNoByClinicIDQuery);

			preparedStatement.setInt(1, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check = 1;

				String[] receiptNoArray = resultSet.getString("receiptNo").split("-");

				receiptNo = clinicSuffix + "-ST-" + (Integer.parseInt(receiptNoArray[2]) + 1);

			}

			if (check == 0) {

				receiptNo = clinicSuffix + "-ST-1";

			}

			object.put("receiptNo", receiptNo);

			array.add(object);

			values.put("Release", array);

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving receipt no based in clinicID");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	public JSONObject retrieveProductListByClinicID(int clinicID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		int check = 0;

		try {
			connection = getConnection();

			String retrieveProductListByClinicIDQuery = QueryMaker.RETRIEVE_PRODUCT_LIST;

			preparedStatement = connection.prepareStatement(retrieveProductListByClinicIDQuery);

			preparedStatement.setInt(1, clinicID);

			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("check", check);
				object.put("productID", resultSet.getInt("id"));
				object.put("tradeName", resultSet.getString("tradeName"));

				array.add(object);

				values.put("Release", array);

			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);

				array.add(object);

				values.put("Release", array);

				preparedStatement.close();

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying product list based on clinicID");

			array.add(object);

			values.put("Release", array);

		}

		return values;
	}

	public List<PrescriptionManagementForm> retrieveProductStockListToRemove(int productID, int clinicID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		int srNo = 1;

		try {

			connection = getConnection();

			String retrieveProductStockListToRemoveQuery = QueryMaker.RETRIEVE_PRODUCT_STOCK_LIST_TO_REMOVE;

			preparedStatement = connection.prepareStatement(retrieveProductStockListToRemoveQuery);

			preparedStatement.setInt(1, clinicID);
			preparedStatement.setInt(2, productID);
			preparedStatement.setString(3, ActivityStatus.NOT_EMPTY);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setStockID(resultSet.getInt("id"));
				form.setStockReceiptID(resultSet.getInt("stockReceiptID"));
				form.setProductID(resultSet.getInt("productID"));
				form.setActivityStatus(resultSet.getString("status"));
				form.setProductName(resultSet.getString("product"));
				form.setNetStock(resultSet.getDouble("netStock"));
				form.setCostPrice(resultSet.getDouble("costPrice"));
				form.setSellingPrice(resultSet.getDouble("sellingPrice"));
				form.setReceiptNo(resultSet.getString("receiptNo"));
				form.setReceiptDate(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				form.setSrNo(srNo);

				srNo++;

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public double retrieveProductNetStockByStockID(int stockID) {

		double quantity = 0;

		try {

			connection = getConnection();

			String retrieveProductNetStockByStockIDQuery = QueryMaker.RETRIEVE_PRODUCT_QUANTITY_BY_STOCK_ID;

			preparedStatement = connection.prepareStatement(retrieveProductNetStockByStockIDQuery);

			preparedStatement.setInt(1, stockID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				quantity = resultSet.getDouble("netStock");

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return quantity;
	}

	public String updateStockByStockID(int stockID, String status, double quantity) {

		try {

			connection = getConnection();

			String udpateStockByStockIDQuery = QueryMaker.UPDATE_PRODUCT_STOCK;

			preparedStatement = connection.prepareStatement(udpateStockByStockIDQuery);

			preparedStatement.setDouble(1, quantity);
			preparedStatement.setString(2, status);
			preparedStatement.setInt(3, stockID);

			preparedStatement.executeUpdate();

			status = "success";

		} catch (Exception exception) {

			exception.printStackTrace();

			status = "error";

		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String disableProduct(int productID) {

		try {

			connection = getConnection();

			String disableProductQuery = QueryMaker.DISABLE_PRODUCT;

			preparedStatement = connection.prepareStatement(disableProductQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, productID);

			preparedStatement.executeUpdate();

			status = "success";

		} catch (Exception exception) {
			exception.printStackTrace();
			status = "error";
		} finally {
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public HashMap<Integer, Double> retrieveProductStockList(int productID, int clinicID) {

		HashMap<Integer, Double> map = new HashMap<Integer, Double>();

		try {

			connection = getConnection();

			String retrieveProductStockListQuery = QueryMaker.RETRIEVE_PRODUCT_STOCK_LIST;

			preparedStatement = connection.prepareStatement(retrieveProductStockListQuery);

			preparedStatement.setInt(1, productID);
			preparedStatement.setInt(2, clinicID);
			preparedStatement.setString(3, ActivityStatus.NOT_EMPTY);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getDouble("netStock"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return map;
	}

	public boolean verifyCategoryNameAlreadyExists(String category) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyCategoryNameAlreadyExistsQuery = QueryMaker.VERIFY_Category_Name_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyCategoryNameAlreadyExistsQuery);

			preparedStatement.setString(1, category);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyEditCategoryNameAlreadyExists(String category, int categoryID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditCategoryNameAlreadyExistsQuery = QueryMaker.VERIFY_EDit_Category_Name_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditCategoryNameAlreadyExistsQuery);

			preparedStatement.setString(1, category);
			preparedStatement.setInt(2, categoryID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyVatNumberAlreadyExists(String vatNumber) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyVatNumberAlreadyExistsQuery = QueryMaker.VERIFY_VatNumber_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyVatNumberAlreadyExistsQuery);

			preparedStatement.setString(1, vatNumber);
			preparedStatement.setString(2, ActivityStatus.ENABLE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyEditVatNumberAlreadyExists(String vatNumber, int supplierID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditVatNumberAlreadyExistsQuery = QueryMaker.VERIFY_EDit_VatNumber_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditVatNumberAlreadyExistsQuery);

			preparedStatement.setString(1, vatNumber);
			preparedStatement.setString(2, ActivityStatus.ENABLE);
			preparedStatement.setInt(3, supplierID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyTaxDetailsAlreadyExists(String taxName, double taxPercent) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyTaxDetailsAlreadyExistsQuery = QueryMaker.VERIFY_Tax_Details_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyTaxDetailsAlreadyExistsQuery);

			preparedStatement.setString(1, taxName);

			preparedStatement.setDouble(2, taxPercent);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyEditTaxDetailsAlreadyExists(String taxName, double taxPercent, int taxID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditTaxDetailsAlreadyExistsQuery = QueryMaker.VERIFY_EDit_TaxDeatils_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditTaxDetailsAlreadyExistsQuery);

			preparedStatement.setString(1, taxName);
			preparedStatement.setDouble(2, taxPercent);
			preparedStatement.setInt(3, taxID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyEditTradeNameAlreadyExists(String tradeName, int productID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditTradeNameAlreadyExistsQuery = QueryMaker.VERIFY_EDit_TradeName_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditTradeNameAlreadyExistsQuery);

			preparedStatement.setString(1, tradeName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, productID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String retrievePaymentTypeByReceiptID(int visitID) {

		String paymentType = "";

		try {

			connection = getConnection();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrievePaymentTypeByReceiptIDQuery = QueryMaker.RETRIEVE_PaymentType_By_ReceiptID;

			preparedStatement = connection.prepareStatement(retrievePaymentTypeByReceiptIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				paymentType = resultSet.getString("paymentType");
			}

			System.out.println("payment type retrieved succesfully..");
		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return paymentType;
	}

	public boolean verifyVisitTypeNameAlreadyExists(String visitTypeName, int clinicID, String formName) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyVisitTypeNameAlreadyExistsQuery = QueryMaker.VERIFY_VisitType_Name_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyVisitTypeNameAlreadyExistsQuery);

			preparedStatement.setString(1, visitTypeName);
			preparedStatement.setString(2, formName);
			preparedStatement.setInt(3, clinicID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyEditVisitTypeNameAlreadyExists(String visitTypeName, int clinicID, String formName,
			int visitTypeID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditCategoryNameAlreadyExistsQuery = QueryMaker.VERIFY_EDit_VisitTypey_Name_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditCategoryNameAlreadyExistsQuery);

			preparedStatement.setString(1, visitTypeName);
			preparedStatement.setString(2, formName);
			preparedStatement.setInt(3, clinicID);
			preparedStatement.setInt(4, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public boolean verifyTestNameExits(String testName) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyTestNameExitsQuery = QueryMaker.VERIFY_Test_NAME_EXISTS;

			preparedStatement = connection.prepareStatement(verifyTestNameExitsQuery);

			preparedStatement.setString(1, testName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public List<PrescriptionManagementForm> retrieveRoomTypeListByType(String roomType, int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveRoomTypeListByTypeQuery = QueryMaker.RETRIEVE_ROOM_TYPE_BY_NAME;

			if (roomType.contains(" ")) {
				roomType = roomType.replace(" ", "%");
			}

			preparedStatement = connection.prepareStatement(retrieveRoomTypeListByTypeQuery);

			preparedStatement.setString(1, "%" + roomType + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setRoomType(resultSet.getString("roomType"));
				form.setRoomTypeID(resultSet.getInt("id"));
				form.setRoomCapacity(resultSet.getInt("roomCapacity"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PrescriptionManagementForm> retrieveAllRoomTypeList(int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveAllRoomTypeListQuery = QueryMaker.RETRIEVE_ROOM_TYPE_LIST;

			preparedStatement = connection.prepareStatement(retrieveAllRoomTypeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setRoomType(resultSet.getString("roomType"));
				form.setRoomTypeID(resultSet.getInt("id"));
				form.setRoomCapacity(resultSet.getInt("roomCapacity"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifyRoomTypeExists(String roomType, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyRoomTypeExistsQuery = QueryMaker.VERIFY_ROOM_TYPE_EXISTS;

			preparedStatement = connection.prepareStatement(verifyRoomTypeExistsQuery);

			preparedStatement.setString(1, roomType);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String insertRoomType(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String insertRoomTypeQuery = QueryMaker.INSERT_ROOM_TYPE;

			preparedStatement = connection.prepareStatement(insertRoomTypeQuery);

			preparedStatement.setString(1, form.getRoomType());
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, form.getPracticeID());
			preparedStatement.setInt(4, form.getRoomCapacity());

			preparedStatement.execute();

			status = "success";
			System.out.println("Room type inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveRoomTypeListByID(int roomTypeID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = new PrescriptionManagementForm();

		try {

			connection = getConnection();

			String retrieveRoomTypeListByIDQuery = QueryMaker.RETRIEVE_ROOM_TYPE_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveRoomTypeListByIDQuery);

			preparedStatement.setInt(1, roomTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setRoomType(resultSet.getString("roomType"));
				form.setRoomTypeID(resultSet.getInt("id"));
				form.setRoomCapacity(resultSet.getInt("roomCapacity"));

			}

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifyRoomTypeExists(String roomType, int roomTypeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyRoomTypeExistsQuery = QueryMaker.VERIFY_ROOM_TYPE_EXISTS1;

			preparedStatement = connection.prepareStatement(verifyRoomTypeExistsQuery);

			preparedStatement.setString(1, roomType);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, roomTypeID);
			preparedStatement.setInt(4, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String updateRoomType(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String updateRoomTypeQuery = QueryMaker.UPDATE_ROOM_TYPE;

			preparedStatement = connection.prepareStatement(updateRoomTypeQuery);

			preparedStatement.setString(1, form.getRoomType());
			preparedStatement.setInt(2, form.getRoomCapacity());
			preparedStatement.setInt(3, form.getRoomTypeID());

			preparedStatement.execute();

			status = "success";
			System.out.println("Room type updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateRoomTypeStatus(int roomTypeID, String status) {

		try {

			connection = getConnection();

			String updateRoomTypeStatusQuery = QueryMaker.UPDATE_ROOM_TYPE_STATUS;

			preparedStatement = connection.prepareStatement(updateRoomTypeStatusQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, roomTypeID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Room type status updated");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public HashMap<Integer, String> retrieveRoomTypeList(int practiceID) {

		HashMap<Integer, String> roomTypeMap = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveRoomTypeListQuery = QueryMaker.RETRIEVE_ROOM_TYPE_LIST;

			preparedStatement = connection.prepareStatement(retrieveRoomTypeListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				roomTypeMap.put(resultSet.getInt("id"), resultSet.getString("roomType"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return roomTypeMap;
	}

	public List<PrescriptionManagementForm> retrieveIPDChargesListByItem(String item, int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveIPDChargesListByItemQuery = QueryMaker.RETRIEVE_IPD_CHARGE_LIST_BY_ITEM;

			if (item.contains(" ")) {
				item = item.replace(" ", "%");
			}

			preparedStatement = connection.prepareStatement(retrieveIPDChargesListByItemQuery);

			preparedStatement.setString(1, "%" + item + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setIPDItems(resultSet.getString("itemName"));
				form.setIPDChargeID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PrescriptionManagementForm> retrieveAllIPDChargesList(int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveAllIPDChargesListQuery = QueryMaker.RETRIEVE_ALL_IPD_CHARGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveAllIPDChargesListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setIPDItems(resultSet.getString("itemName"));
				form.setIPDChargeID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifyIPDChargesExists(String item, int roomTypeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyIPDChargesExistssQuery = QueryMaker.VERIFY_IPD_CHARGE_EXISTS;

			preparedStatement = connection.prepareStatement(verifyIPDChargesExistssQuery);

			preparedStatement.setString(1, item);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, roomTypeID);
			preparedStatement.setInt(4, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String insertIPDCharges(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String insertIPDChargesQuery = QueryMaker.INSERT_IPD_CHARGES;

			preparedStatement = connection.prepareStatement(insertIPDChargesQuery);

			preparedStatement.setString(1, form.getIPDItems());
			preparedStatement.setDouble(2, form.getCharges());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, form.getRoomTypeID());
			preparedStatement.setInt(5, form.getPracticeID());

			preparedStatement.execute();

			status = "success";
			System.out.println("IPD tarrif charges inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveIPDChargesListByID(int ipdChargeID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = new PrescriptionManagementForm();

		try {

			connection = getConnection();

			String retrieveIPDChargesListByIDQuery = QueryMaker.RETRIEVE_IPD_CHARGE_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveIPDChargesListByIDQuery);

			preparedStatement.setInt(1, ipdChargeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setIPDItems(resultSet.getString("itemName"));
				form.setIPDChargeID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));
				form.setRoomTypeID(resultSet.getInt("roomTypeID"));

			}

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifyIPDChargesExists(String item, int roomTypeID, int ipdChargeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyIPDChargesExistssQuery = QueryMaker.VERIFY_IPD_CHARGE_EXISTS1;

			preparedStatement = connection.prepareStatement(verifyIPDChargesExistssQuery);

			preparedStatement.setString(1, item);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, roomTypeID);
			preparedStatement.setInt(4, ipdChargeID);
			preparedStatement.setInt(5, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String updateIPDCharges(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String updateIPDChargesQuery = QueryMaker.UPDATE_IPD_CHARGES;

			preparedStatement = connection.prepareStatement(updateIPDChargesQuery);

			preparedStatement.setString(1, form.getIPDItems());
			preparedStatement.setDouble(2, form.getCharges());
			preparedStatement.setInt(4, form.getIPDChargeID());
			preparedStatement.setInt(3, form.getRoomTypeID());

			preparedStatement.execute();

			status = "success";
			System.out.println("IPD tarrif charges updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateIPDChargesStatus(int chargeID, String status) {

		try {

			connection = getConnection();

			String updateIPDChargesStatusQuery = QueryMaker.UPDATE_IPD_CHARGE_STATUS;

			preparedStatement = connection.prepareStatement(updateIPDChargesStatusQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, chargeID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("IPD tarrif status updated");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveIPDConsultantChargesListByDoctor(String doctorName,
			int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveIPDConsultantChargesListByDoctorQuery = QueryMaker.RETRIEVE_IPD_CONSULTANT_CHARGE_LIST_BY_DOCTOR_NAME;

			if (doctorName.contains(" ")) {
				doctorName = doctorName.replace(" ", "%");
			}

			preparedStatement = connection.prepareStatement(retrieveIPDConsultantChargesListByDoctorQuery);

			preparedStatement.setString(1, "%" + doctorName + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setDoctorName(resultSet.getString("doctorName"));
				form.setConsultationChargeID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public List<PrescriptionManagementForm> retrieveAllIPDConsultantChargesList(int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveAllIPDConsultantChargesListQuery = QueryMaker.RETRIEVE_ALL_IPD_CONSULTANT_CHARGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveAllIPDConsultantChargesListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setDoctorName(resultSet.getString("doctorName"));
				form.setConsultationChargeID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifyIPDConsultantChargesExists(String doctorName, int roomTypeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyIPDConsultantChargesExistsQuery = QueryMaker.VERIFY_IPD_CONSULTANT_CHARGE_EXISTS;

			preparedStatement = connection.prepareStatement(verifyIPDConsultantChargesExistsQuery);

			preparedStatement.setString(1, doctorName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, roomTypeID);
			preparedStatement.setInt(4, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String insertIPDConsultantCharges(PrescriptionManagementForm form, int practiceID) {

		try {

			connection = getConnection();

			String insertIPDConsultantChargesQuery = QueryMaker.INSERT_IPD_CONSULTANT_CHARGES;

			preparedStatement = connection.prepareStatement(insertIPDConsultantChargesQuery);

			preparedStatement.setString(1, form.getDoctorName());
			preparedStatement.setDouble(2, form.getCharges());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, form.getRoomTypeID());
			preparedStatement.setInt(5, practiceID);

			preparedStatement.execute();

			status = "success";
			System.out.println("IPD consultant charges inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveIPDConsultantChargesListByID(int ipdConsultantChargeID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveIPDConsultantChargesListByIDQuery = QueryMaker.RETRIEVE_IPD_CONSULTANT_CHARGE_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveIPDConsultantChargesListByIDQuery);

			preparedStatement.setInt(1, ipdConsultantChargeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setDoctorName(resultSet.getString("doctorName"));
				form.setConsultationChargeID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));
				form.setRoomTypeID(resultSet.getInt("roomTypeID"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	public boolean verifyIPDConsultantChargesExists(String doctorName, int roomTypeID, int ipdConsultantChargeID,
			int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyIPDConsultantChargesExistsQuery = QueryMaker.VERIFY_IPD_CONSULTANT_CHARGE_EXISTS1;

			preparedStatement = connection.prepareStatement(verifyIPDConsultantChargesExistsQuery);

			preparedStatement.setString(1, doctorName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, roomTypeID);
			preparedStatement.setInt(4, ipdConsultantChargeID);
			preparedStatement.setInt(5, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	public String updateIPDConsultantCharges(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String updateIPDConsultantChargesQuery = QueryMaker.UPDATE_IPD_CONSULTANT_CHARGES;

			preparedStatement = connection.prepareStatement(updateIPDConsultantChargesQuery);

			preparedStatement.setString(1, form.getDoctorName());
			preparedStatement.setDouble(2, form.getCharges());
			preparedStatement.setInt(3, form.getRoomTypeID());
			preparedStatement.setInt(4, form.getConsultationChargeID());

			preparedStatement.execute();

			status = "success";
			System.out.println("IPD consultant charges updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public String updateIPDConsultantChargesStatus(int chargID, String status) {

		try {

			connection = getConnection();

			String updateIPDConsultantChargesStatusQuery = QueryMaker.UPDATE_IPD_CONSULTANT_CHARGE_STATUS;

			preparedStatement = connection.prepareStatement(updateIPDConsultantChargesStatusQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, chargID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("IPD consultant status updated");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	public List<PrescriptionManagementForm> searchOPDCharges(String searchChargesName, int practiceID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String searchOPDChargesQuery = QueryMaker.SEARCH_OPDCharges;

			preparedStatement = connection.prepareStatement(searchOPDChargesQuery);

			if (searchChargesName.contains(" ")) {
				searchChargesName = searchChargesName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchChargesName + "%");
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setChargesID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setChargeType(resultSet.getString("chargeType"));
				form.setSearchCharges(searchChargesName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching OPD charges list due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<PrescriptionManagementForm> retrieveAllOPDCharges(int practiceID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {
			connection = getConnection();

			String retrieveAllOPDChargesQuery = QueryMaker.RETRIEVE_ALL_OPDCharges;

			preparedStatement = connection.prepareStatement(retrieveAllOPDChargesQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setChargesID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setChargeType(resultSet.getString("chargeType"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving all OPD charges details list due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	public boolean verifyChargeTypeNameAlreadyExists(String chargeType, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyChargeTypeNameAlreadyExistsQuery = QueryMaker.VERIFY_CHARGETYPE_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyChargeTypeNameAlreadyExistsQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String insertOPDChargeDetails(String chargeType, double charges, int practiceID) {

		try {

			connection = getConnection();

			String insertCategoryQuery = QueryMaker.INSERT_OPDChargeDetails;

			preparedStatement = connection.prepareStatement(insertCategoryQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setDouble(2, charges);
			preparedStatement.setInt(3, practiceID);

			preparedStatement.execute();

			status = "success";

			System.out.println("OPDCharge Details added successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while inserting OPDCharge details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public List<PrescriptionManagementForm> retrieveOPDChargeDetailsByID(int chargesID, String searchCharges) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;
		try {
			connection = getConnection();

			String retrieveOPDChargeDetailsByIDQuery = QueryMaker.RETRIEVE_OPDCharge_Details_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOPDChargeDetailsByIDQuery);

			preparedStatement.setInt(1, chargesID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new PrescriptionManagementForm();

				form.setChargesID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setChargeType(resultSet.getString("chargeType"));

				form.setSearchCharges(searchCharges);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving category by ID due to:::" + exception.getMessage());
		}
		return list;

	}

	public boolean verifyEditChargeTypeAlreadyExists(String chargeType, int chargesID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditChargeTypeAlreadyExistsQuery = QueryMaker.VERIFY_EDit_ChargeType_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditChargeTypeAlreadyExistsQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setInt(2, chargesID);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public String updateOPDChargesDateils(String chargeType, double charges, int chargesID) {

		try {

			connection = getConnection();

			String updateOPDChargesDateilsQuery = QueryMaker.UPDATE_OPDCharges_Details;

			preparedStatement = connection.prepareStatement(updateOPDChargesDateilsQuery);

			preparedStatement.setString(1, chargeType);
			preparedStatement.setDouble(2, charges);
			preparedStatement.setInt(3, chargesID);

			preparedStatement.execute();

			status = "success";

			System.out.println("OPDCharges Dateils updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while updating OPDCharges Dateils due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String deleteOPDCharges(int chargesID) {

		try {

			connection = getConnection();

			String deleteOPDChargesQuery = QueryMaker.DELETE_OPDCharges;

			preparedStatement = connection.prepareStatement(deleteOPDChargesQuery);

			preparedStatement.setInt(1, chargesID);

			preparedStatement.execute();

			status = "success";

			System.out.println("OPDCharges details delete successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while deleting OPDCharges details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public String retrieveOPDChargesDetailsByReceiptID(int visitID) {

		String ChargesDetails = "";

		try {

			connection = getConnection();

			System.out.println("visitID: " + visitID);

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveOPDChargesDetailsByReceiptIDQuery = QueryMaker.RETRIEVE_OPDCharges_Details_By_ReceiptID;

			preparedStatement = connection.prepareStatement(retrieveOPDChargesDetailsByReceiptIDQuery);

			preparedStatement.setInt(1, visitID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ChargesDetails = resultSet.getString("Details");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return ChargesDetails;
	}

	public List<PrescriptionManagementForm> retrieveAllLabTests(int practiceID) {

		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {
			connection = getConnection();

			String retrieveAllLabTestsQuery = QueryMaker.RETRIEVE_Test_LIST;

			preparedStatement = connection.prepareStatement(retrieveAllLabTestsQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setTestID(resultSet.getInt("id"));
				form.setTest(resultSet.getString("test"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving all Lab tests details list due to:::" + exception.getMessage());
		}
		return list;

	}

	public String retrieveInvestigationDetailsByVisitID(int visitID) {

		String InvestigationDetails = "";

		try {

			connection = getConnection();

			/*
			 * Retrieving family history details by patientID
			 */
			String retrieveInvestigationDetailsByVisitIDQuery = QueryMaker.RETRIEVE_PrescribedInvestigations_LIST_BY_VISIT_ID;

			preparedStatement = connection.prepareStatement(retrieveInvestigationDetailsByVisitIDQuery);

			preparedStatement.setInt(1, visitID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				InvestigationDetails = resultSet.getString("investigation");
			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return InvestigationDetails;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveOTTypeListByType(String searchOTType, int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveOTTypeListByTypeQuery = QueryMaker.RETRIEVE_OT_TYPE_BY_NAME;

			if (searchOTType.contains(" ")) {
				searchOTType = searchOTType.replace(" ", "%");
			}

			preparedStatement = connection.prepareStatement(retrieveOTTypeListByTypeQuery);

			preparedStatement.setString(1, "%" + searchOTType + "%");
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setOTType(resultSet.getString("name"));
				form.setOTTypeID(resultSet.getInt("id"));
				form.setSearchOTType(searchOTType);

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveAllOTTypeList(int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveAllOTTypeListQuery = QueryMaker.RETRIEVE_ALL_OT_TYPE;

			preparedStatement = connection.prepareStatement(retrieveAllOTTypeListQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setOTType(resultSet.getString("name"));
				form.setOTTypeID(resultSet.getInt("id"));
				form.setSearchOTType("");

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public boolean verifyOTTypeExists(String OTType, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyOTTypeExistsQuery = QueryMaker.VERIFY_OT_TYPE_EXISTS;

			preparedStatement = connection.prepareStatement(verifyOTTypeExistsQuery);

			preparedStatement.setString(1, OTType);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public String insertOTType(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String insertOTTypeQuery = QueryMaker.INSERT_OT_TYPE;

			preparedStatement = connection.prepareStatement(insertOTTypeQuery);

			preparedStatement.setString(1, form.getOTType());
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";
			System.out.println("OT type inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveOTTypeListByID(int OTTypeID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = new PrescriptionManagementForm();

		try {

			connection = getConnection();

			String retrieveOTTypeListByIDQuery = QueryMaker.RETRIEVE_OT_TYPE_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveOTTypeListByIDQuery);

			preparedStatement.setInt(1, OTTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setOTType(resultSet.getString("name"));
				form.setOTTypeID(resultSet.getInt("id"));

			}

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public boolean verifyOTTypeExists(String OTType, int OTTypeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyRoomTypeExistsQuery = QueryMaker.VERIFY_OT_TYPE_EXISTS1;

			preparedStatement = connection.prepareStatement(verifyRoomTypeExistsQuery);

			preparedStatement.setString(1, OTType);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, OTTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public String updateOTType(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String updateOTTypeQuery = QueryMaker.UPDATE_OT_TYPE;

			preparedStatement = connection.prepareStatement(updateOTTypeQuery);

			preparedStatement.setString(1, form.getOTType());
			preparedStatement.setInt(2, form.getOTTypeID());

			preparedStatement.execute();

			status = "success";
			System.out.println("OT type updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateOTTypeStatus(int OTTypeID, String inactive) {

		try {

			connection = getConnection();

			String updateOTTypeStatusQuery = QueryMaker.UPDATE_OT_TYPE_STATUS;

			preparedStatement = connection.prepareStatement(updateOTTypeStatusQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, OTTypeID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("OT type status updated");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveServiceTypeListByType(String searchServiceType, int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveServiceTypeListByTypeQuery = QueryMaker.RETRIEVE_SERVICE_TYPE_BY_NAME;

			if (searchServiceType.contains(" ")) {
				searchServiceType = searchServiceType.replace(" ", "%");
			}

			preparedStatement = connection.prepareStatement(retrieveServiceTypeListByTypeQuery);

			preparedStatement.setString(1, "%" + searchServiceType + "%");
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setServiceType(resultSet.getString("name"));
				form.setServiceTypeID(resultSet.getInt("id"));
				form.setRoomCapacity(resultSet.getInt("roomCapacity"));
				form.setSearchOTType(searchServiceType);

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveAllServiceTypeList(int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveAllServiceTypeListQuery = QueryMaker.RETRIEVE_ALL_SERVICE_TYPE;

			preparedStatement = connection.prepareStatement(retrieveAllServiceTypeListQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setServiceType(resultSet.getString("name"));
				form.setServiceTypeID(resultSet.getInt("id"));
				form.setRoomCapacity(resultSet.getInt("roomCapacity"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public boolean verifyServiceTypeExists(String serviceType, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyServiceTypeExistsQuery = QueryMaker.VERIFY_SERVICE_TYPE_EXISTS;

			preparedStatement = connection.prepareStatement(verifyServiceTypeExistsQuery);

			preparedStatement.setString(1, serviceType);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public String insertServiceType(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String insertServiceTypeQuery = QueryMaker.INSERT_SERVICE_TYPE;

			preparedStatement = connection.prepareStatement(insertServiceTypeQuery);

			preparedStatement.setString(1, form.getOTType());
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, form.getRoomCapacity());

			preparedStatement.execute();

			status = "success";
			System.out.println("Service type inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveServiceTypeListByID(int serviceTypeID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = new PrescriptionManagementForm();

		try {

			connection = getConnection();

			String retrieveServiceTypeListByIDQuery = QueryMaker.RETRIEVE_OT_TYPE_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveServiceTypeListByIDQuery);

			preparedStatement.setInt(1, serviceTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form.setServiceType(resultSet.getString("name"));
				form.setRoomCapacity(resultSet.getInt("roomCapacity"));
				form.setServiceTypeID(resultSet.getInt("id"));

			}

			list.add(form);

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public boolean verifyServiceTypeExists(String serviceType, int serviceTypeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyServiceTypeExistsQuery = QueryMaker.VERIFY_SERVICE_TYPE_EXISTS1;

			preparedStatement = connection.prepareStatement(verifyServiceTypeExistsQuery);

			preparedStatement.setString(1, serviceType);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, serviceTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public String updateServiceType(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String updateServiceTypeQuery = QueryMaker.UPDATE_SERVICE_TYPE;

			preparedStatement = connection.prepareStatement(updateServiceTypeQuery);

			preparedStatement.setString(1, form.getOTType());
			preparedStatement.setInt(2, form.getRoomCapacity());
			preparedStatement.setInt(3, form.getServiceTypeID());

			preparedStatement.execute();

			status = "success";
			System.out.println("Update type updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateServiceTypeStatus(int serviceTypeID, String status) {

		try {

			connection = getConnection();

			String updateServiceTypeStatusQuery = QueryMaker.UPDATE_SERVICE_TYPE_STATUS;

			preparedStatement = connection.prepareStatement(updateServiceTypeStatusQuery);

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, serviceTypeID);

			preparedStatement.executeUpdate();

			status = "success";
			System.out.println("Service type status updated");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveRoomChargesListByRoom(String searchCharges, int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveRoomChargesListByRoomQuery = QueryMaker.RETRIEVE_ROOM_CHARGE_LIST_BY_DOCTOR_NAME;

			if (searchCharges.contains(" ")) {
				searchCharges = searchCharges.replace(" ", "%");
			}

			preparedStatement = connection.prepareStatement(retrieveRoomChargesListByRoomQuery);

			preparedStatement.setString(1, "%" + searchCharges + "%");
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setName(resultSet.getString("roomName"));
				form.setRoomChargesID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));
				form.setSearchCharges(searchCharges);

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveAllRoomChargesList(int practiceID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveRoomChargesListByRoomQuery = QueryMaker.RETRIEVE_ALL_ROOM_CHARGE_LIST;

			preparedStatement = connection.prepareStatement(retrieveRoomChargesListByRoomQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setName(resultSet.getString("roomName"));
				form.setRoomChargesID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public boolean verifyRoomChargesExists(String roomName, int roomTypeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyRoomChargesExistsQuery = QueryMaker.VERIFY_ROOM_CHARGE_EXISTS;

			preparedStatement = connection.prepareStatement(verifyRoomChargesExistsQuery);

			preparedStatement.setString(1, roomName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, roomTypeID);
			preparedStatement.setInt(4, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public String insertRoomCharges(PrescriptionManagementForm form, int practiceID) {

		try {

			connection = getConnection();

			String insertRoomChargesQuery = QueryMaker.INSERT_ROOM_CHARGES;

			preparedStatement = connection.prepareStatement(insertRoomChargesQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setDouble(2, form.getCharges());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setInt(4, form.getRoomTypeID());
			preparedStatement.setInt(5, practiceID);

			preparedStatement.execute();

			status = "success";
			System.out.println("Room charges inserted successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public List<PrescriptionManagementForm> retrieveRoomChargesListByID(int roomChargeID) {
		List<PrescriptionManagementForm> list = new ArrayList<PrescriptionManagementForm>();

		PrescriptionManagementForm form = null;

		try {

			connection = getConnection();

			String retrieveRoomChargesListByIDQuery = QueryMaker.RETRIEVE_ROOM_CHARGES_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveRoomChargesListByIDQuery);

			preparedStatement.setInt(1, roomChargeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				form = new PrescriptionManagementForm();

				form.setName(resultSet.getString("roomName"));
				form.setRoomChargesID(resultSet.getInt("id"));
				form.setCharges(resultSet.getDouble("charges"));
				form.setRoomType(resultSet.getString("roomType"));
				form.setRoomTypeID(resultSet.getInt("roomTypeID"));

				list.add(form);

			}

		} catch (Exception exception) {
			exception.printStackTrace();

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return list;
	}

	@Override
	public boolean verifyRoomChargesExists(String roomName, int roomTypeID, int roomChargeID, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyRoomChargesExistsQuery = QueryMaker.VERIFY_ROOM_CHARGE_EXISTS1;

			preparedStatement = connection.prepareStatement(verifyRoomChargesExistsQuery);

			preparedStatement.setString(1, roomName);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, roomTypeID);
			preparedStatement.setInt(4, roomChargeID);
			preparedStatement.setInt(5, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				check = true;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;

		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return check;
	}

	@Override
	public String updateRoomCharges(PrescriptionManagementForm form) {

		try {

			connection = getConnection();

			String updateRoomChargesQuery = QueryMaker.UPDATE_ROOM_CHARGES;

			preparedStatement = connection.prepareStatement(updateRoomChargesQuery);

			preparedStatement.setString(1, form.getName());
			preparedStatement.setDouble(2, form.getCharges());
			preparedStatement.setInt(3, form.getRoomTypeID());
			preparedStatement.setInt(4, form.getRoomChargesID());

			preparedStatement.execute();

			status = "success";
			System.out.println("Room charges updated successfully.");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public String updateRoomChargesStatus(int roomChargeID, String inactive) {

		try {

			connection = getConnection();

			String updateIPDChargesStatusQuery = QueryMaker.UPDATE_ROOM_CHARGE_STATUS;

			preparedStatement = connection.prepareStatement(updateIPDChargesStatusQuery);

			preparedStatement.setString(1, inactive);
			preparedStatement.setInt(2, roomChargeID);

			preparedStatement.executeUpdate();

			status = "success";

			System.out.println("Room charge status updated");

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {

			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	@Override
	public JSONObject retrieveOPDChargesForPractice(int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveOPDChargesForPracticeQuery = QueryMaker.RETRIEVE_ALL_OPDCharges;

			preparedStatement = connection.prepareStatement(retrieveOPDChargesForPracticeQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("chargeType", resultSet.getString("chargeType"));
				object.put("charges", resultSet.getDouble("charges"));
				object.put("chargeID", resultSet.getInt("id"));

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving OPD charges list");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	@Override
	public JSONObject retrieveIPDTarrifChargesForPractice(int roomTypeID, int practiceID) {

		JSONObject values = null;

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = null;

		try {
			connection = getConnection();

			String retrieveIPDTarrifChargesForPracticeQuery = QueryMaker.RETRIEVE_ALL_IPDCharges_FOR_PRACTICE;

			preparedStatement = connection.prepareStatement(retrieveIPDTarrifChargesForPracticeQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, practiceID);
			preparedStatement.setInt(2, roomTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				object.put("itemName", resultSet.getString("itemName"));
				object.put("charges", resultSet.getDouble("charges"));
				object.put("itemID", resultSet.getInt("id"));

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

			return values;

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving IPD Tarrif charges list");

			array.add(object);

			values.put("Release", array);

			return values;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#insertCategoryWithOT(
	 * java. lang.String, java.lang.String, java.lang.String)
	 */
	public String insertCategoryWithOT(String categoryName, String categoryType, String otFlag) {

		try {

			connection = getConnection();

			String insertCategoryQuery = QueryMaker.INSERT_CATEGORY_WITH_OT;

			preparedStatement = connection.prepareStatement(insertCategoryQuery);

			preparedStatement.setString(1, categoryName);
			preparedStatement.setString(2, categoryType);
			preparedStatement.setString(3, otFlag);

			preparedStatement.execute();

			status = "success";

			System.out.println("Category added successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting category details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edhanvantari.daoInf.PrescriptionManagementDAOInf#updateCategory(java.
	 * lang.String, int, java.lang.String, java.lang.String)
	 */
	public String updateCategoryWithOT(String categoryName, int categoryID, String categoryType, String otCategory) {

		try {

			connection = getConnection();

			String updateCategoryQuery = QueryMaker.UPDATE_CATEGORY_WITH_OT;

			preparedStatement = connection.prepareStatement(updateCategoryQuery);

			preparedStatement.setString(1, categoryName);
			preparedStatement.setString(2, categoryType);
			preparedStatement.setString(3, otCategory);
			preparedStatement.setInt(4, categoryID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Category updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating category due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

}
