package com.edhanvantari.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.edhanvantari.daoImpl.LoginDAOImpl;
import com.edhanvantari.daoImpl.PatientDAOImpl;
import com.edhanvantari.daoImpl.PrescriptionManagementDAOImpl;
import com.edhanvantari.daoInf.LoginDAOInf;
import com.edhanvantari.daoInf.PatientDAOInf;
import com.edhanvantari.daoInf.PrescriptionManagementDAOInf;
import com.edhanvantari.form.LoginForm;
import com.edhanvantari.form.PrescriptionManagementForm;
import com.edhanvantari.service.eDhanvantariServiceImpl;
import com.edhanvantari.service.eDhanvantariServiceInf;
import com.edhanvantari.util.AWSS3Connect;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.ConfigXMLUtil;
import com.edhanvantari.util.ConfigurationUtil;
import com.edhanvantari.util.ConvertToPDFUtil;
import com.edhanvantari.util.EmailUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class PrescriptionManagementAction extends ActionSupport
		implements ModelDriven<PrescriptionManagementForm>, ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String message = "error";

	HttpServletRequest request;
	HttpServletResponse response;

	eDhanvantariServiceInf serviceInf = null;

	LoginDAOInf loginDAOInf = null;

	private Map<String, Object> session = null;

	PrescriptionManagementForm form = new PrescriptionManagementForm();

	PrescriptionManagementDAOInf managementDAOInf = null;

	List<PrescriptionManagementForm> categoryList = null;

	List<PrescriptionManagementForm> editCategoryList = null;

	List<PrescriptionManagementForm> taxList = null;

	List<PrescriptionManagementForm> editTaxList = null;

	List<PrescriptionManagementForm> productList;
	List<PrescriptionManagementForm> productPriceList;

	LoginDAOInf daoInf = null;

	HashMap<Integer, String> clinicList;

	HashMap<Integer, String> productCategoryList = null;

	List<PrescriptionManagementForm> supplierList = null;
	List<PrescriptionManagementForm> editSupplierList = null;

	HashMap<Integer, String> stockSupplierList = null;

	List<PrescriptionManagementForm> stockList = null;

	List<String> productTaxList = null;

	List<PrescriptionManagementForm> roomTypeList = null;

	List<PrescriptionManagementForm> editRoomTypeList = null;

	List<PrescriptionManagementForm> OTTypeList = null;

	List<PrescriptionManagementForm> editOTTypeList = null;

	List<PrescriptionManagementForm> serviceTypeList = null;

	List<PrescriptionManagementForm> editServiceTypeList = null;

	HashMap<Integer, String> roomTypeMap = null;

	List<PrescriptionManagementForm> IPDChargesList = null;

	List<PrescriptionManagementForm> editIPDChargesList = null;

	List<PrescriptionManagementForm> consultantChargesList = null;

	List<PrescriptionManagementForm> editConsultantChargesList = null;

	List<PrescriptionManagementForm> OPDChargesList = null;

	List<PrescriptionManagementForm> editOPDChargesList = null;

	List<PrescriptionManagementForm> roomChargesList = null;

	List<PrescriptionManagementForm> editRoomChargesList = null;

	/**
	 * 
	 * @return
	 * @throws Excepion
	 */
	public String searchCategory() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		categoryList = managementDAOInf.searchCategory(form.getSearchCategory());

		if (categoryList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No category found for : " + form.getSearchCategory());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllCategories() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		categoryList = managementDAOInf.retrieveAllCategories();

		if (categoryList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No category found. Please add new category.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addCategory() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.addCategory(form);

		if (message.equals("success")) {

			addActionMessage("Category added successfully.");

			// retrieving category list from Category table
			categoryList = managementDAOInf.retrieveAllCategories();

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Category", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Category with same name already exists. Please add new category.");

			// retrieving category list from Category table
			categoryList = managementDAOInf.retrieveAllCategories();

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add Category Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add category. Please check server logs for more details.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Category Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditCategory() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		editCategoryList = managementDAOInf.retrieveCategoryByID(form.getCategoryID(), form.getSearchCategory());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchCategoryName = form.getSearchCategory();

		if (searchCategoryName == null || searchCategoryName == "") {

			// retrieving category list from Category table
			categoryList = managementDAOInf.retrieveAllCategories();

		} else {

			categoryList = managementDAOInf.searchCategory(searchCategoryName);

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editCategory() throws Exception {

		serviceInf = new eDhanvantariServiceImpl();

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		// message = managementDAOInf.updateCategory(form.getCategory(),
		// form.getCategoryID(), form.getCategoryType());
		message = serviceInf.editCategory(form);

		if (message.equals("success")) {

			addActionMessage("Category updated successfully.");

			String searchCategoryName = form.getSearchCategory();

			if (searchCategoryName == null || searchCategoryName == "") {

				// retrieving category list from Category table
				categoryList = managementDAOInf.retrieveAllCategories();

			} else {

				categoryList = managementDAOInf.searchCategory(searchCategoryName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Category", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Category with same name already exists. Please add new category.");

			String searchCategoryName = form.getSearchCategory();

			if (searchCategoryName == null || searchCategoryName == "") {

				// retrieving category list from Category table
				categoryList = managementDAOInf.retrieveAllCategories();

			} else {

				categoryList = managementDAOInf.searchCategory(searchCategoryName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Category Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update category. Please check server logs for more details.");

			String searchCategoryName = form.getSearchCategory();

			if (searchCategoryName == null || searchCategoryName == "") {

				// retrieving category list from Category table
				categoryList = managementDAOInf.retrieveAllCategories();

			} else {

				categoryList = managementDAOInf.searchCategory(searchCategoryName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Category Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteCategory() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = managementDAOInf.deleteCategory(form.getCategoryID());

		if (message.equals("success")) {

			addActionMessage("Category deleted successfully.");

			String searchCategoryName = form.getSearchCategory();

			if (searchCategoryName == null || searchCategoryName == "") {

				// retrieving category list from Category table
				categoryList = managementDAOInf.retrieveAllCategories();

			} else {

				categoryList = managementDAOInf.searchCategory(searchCategoryName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Category", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete category. Please check server logs for more details.");

			String searchCategoryName = form.getSearchCategory();

			if (searchCategoryName == null || searchCategoryName == "") {

				// retrieving category list from Category table
				categoryList = managementDAOInf.retrieveAllCategories();

			} else {

				categoryList = managementDAOInf.searchCategory(searchCategoryName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Category Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchTax() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		taxList = managementDAOInf.searchTax(form.getSearchTaxName());

		if (taxList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No tax found for : " + form.getSearchTaxName());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllTaxes() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		taxList = managementDAOInf.retrieveAllTaxes();

		if (taxList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No tax found. Please add new category.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addTax() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.addTax(form);

		if (message.equals("success")) {

			addActionMessage("Tax added successfully.");

			// retrieving tax list from Category table
			taxList = managementDAOInf.retrieveAllTaxes();

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Tax", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Tax with same details already exists. Please add new Tax details.");

			// retrieving tax list from Category table
			taxList = managementDAOInf.retrieveAllTaxes();

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Tax details Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add tax. Please check server logs for more details.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Tax Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditTax() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		editTaxList = managementDAOInf.retrieveTaxByID(form.getTaxID(), form.getSearchTaxName());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchTaxName = form.getSearchTaxName();

		if (searchTaxName == null || searchTaxName == "") {

			// retrieving tax list from Tax table
			taxList = managementDAOInf.retrieveAllTaxes();

		} else {

			taxList = managementDAOInf.searchTax(searchTaxName);

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editTax() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editTax(form);

		if (message.equals("success")) {

			addActionMessage("Tax updated successfully.");

			String searchTaxName = form.getSearchTaxName();

			if (searchTaxName == null || searchTaxName == "") {

				// retrieving tax list from Tax table
				taxList = managementDAOInf.retrieveAllTaxes();

			} else {

				taxList = managementDAOInf.searchTax(searchTaxName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Tax", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Tax with same details already exists. Please add new Tax details.");

			String searchTaxName = form.getSearchTaxName();

			if (searchTaxName == null || searchTaxName == "") {

				// retrieving tax list from Tax table
				taxList = managementDAOInf.retrieveAllTaxes();

			} else {

				taxList = managementDAOInf.searchTax(searchTaxName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Tax details Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update tax. Please check server logs for more details.");

			String searchTaxName = form.getSearchTaxName();

			if (searchTaxName == null || searchTaxName == "") {

				// retrieving tax list from Tax table
				taxList = managementDAOInf.retrieveAllTaxes();

			} else {

				taxList = managementDAOInf.searchTax(searchTaxName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Tax Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteTax() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = managementDAOInf.deleteTax(form.getTaxID());

		if (message.equals("success")) {

			addActionMessage("Tax deleted successfully.");

			String searchTaxName = form.getSearchTaxName();

			if (searchTaxName == null || searchTaxName == "") {

				// retrieving tax list from Tax table
				taxList = managementDAOInf.retrieveAllTaxes();

			} else {

				taxList = managementDAOInf.searchTax(searchTaxName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Tax", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete tax. Please check server logs for more details.");

			String searchTaxName = form.getSearchTaxName();

			if (searchTaxName == null || searchTaxName == "") {

				// retrieving tax list from Tax table
				taxList = managementDAOInf.retrieveAllTaxes();

			} else {

				taxList = managementDAOInf.searchTax(searchTaxName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Tax Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addSupplier() throws Exception {

		LoginDAOInf daoInf = new LoginDAOImpl();

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.addSupplier(form);

		if (message.equals("success")) {

			addActionMessage("Supplier added successfully.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Supplier", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Supplier with same VatNumber name already exists. Please add new VatNumber.");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Add Supplier with same VatNumber Already Exist Exeption Occurred", userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add supplier. Please check server logs for more details.");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Supplier Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllSuppliers() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		supplierList = managementDAOInf.retrieveAllSuppliers();

		if (supplierList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No supplier found. Please add new supplier.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editSupplier() throws Exception {

		LoginDAOInf daoInf = new LoginDAOImpl();

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		// message = managementDAOInf.updateSupplier(form);

		message = serviceInf.editSupplier(form);

		if (message.equals("success")) {

			addActionMessage("Supplier updated successfully.");

			String searchSupplierName = form.getSearchSupplierName();

			// retrieving Supplier list from Supplier table
			supplierList = managementDAOInf.retrieveAllSuppliers(form.getSearchSupplierName());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Supplier", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Supplier with same VatNumber already exists. Please add new VatNumber.");

			String searchSupplierName = form.getSearchSupplierName();

			// retrieving Supplier list from Supplier table
			supplierList = managementDAOInf.retrieveAllSuppliers(form.getSearchSupplierName());

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Edit Supplier with same VatNumber Already Exist Exeption Occurred", userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update Supplier. Please check server logs for more details.");

			supplierList = managementDAOInf.retrieveAllSuppliers(form.getSearchSupplierName());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Supplier Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditSupplier() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		editSupplierList = managementDAOInf.retrieveSupplierByID(form.getSupplierID(), form.getSearchSupplierName());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchSupplierName = form.getSearchSupplierName();

		if (searchSupplierName == null || searchSupplierName == "") {

			// retrieving Supplier list from supplier table
			supplierList = managementDAOInf.retrieveAllSuppliers();

		} else {

			supplierList = managementDAOInf.searchSupplier(searchSupplierName);

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchSupplier() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		supplierList = managementDAOInf.searchSupplier(form.getSearchSupplierName());

		if (supplierList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No supplier List found for : " + form.getSearchSupplierName());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteSupplier() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = managementDAOInf.deleteSupplier(form.getSupplierID());

		if (message.equals("success")) {

			addActionMessage("Supplier deleted successfully.");

			String searchSupplierName = form.getSearchSupplierName();

			if (searchSupplierName == null || searchSupplierName == "") {

				// retrieving Supplier list from Supplier table
				supplierList = managementDAOInf.retrieveAllSuppliers();

			} else {

				supplierList = managementDAOInf.searchSupplier(searchSupplierName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Supplier", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete Supplier. Please check server logs for more details.");

			String searchSupplierName = form.getSearchSupplierName();

			if (searchSupplierName == null || searchSupplierName == "") {

				// retrieving Supplier list from Supplier table
				supplierList = managementDAOInf.retrieveAllSuppliers();

			} else {

				supplierList = managementDAOInf.searchSupplier(searchSupplierName);

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Supplier Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderAddProduct() throws Exception {
		managementDAOInf = new PrescriptionManagementDAOImpl();
		productCategoryList = managementDAOInf.retrieveProductCategoryList();

		// productTaxList = managementDAOInf.retrieveproducttaxList();
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addProduct() throws Exception {

		serviceInf = new eDhanvantariServiceImpl();
		managementDAOInf = new PrescriptionManagementDAOImpl();

		loginDAOInf = new LoginDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		// Setting session's practice ID into practiceID variable of
		// PrescriptionManagmentForm
		form.setPracticeID(userForm.getPracticeID());

		System.out.println("clinicID: " + userForm.getClinicID());

		form.setClinicID(userForm.getClinicID());

		message = serviceInf.addProduct(form);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Product added successfully.");
			// Retrieving product ctegory List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Retrieving product tax list
			// productTaxList = managementDAOInf.retrieveproducttaxList();

			// Inserting into Audit
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Product", userForm.getUserID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Product with same trade name already exists. Please add new product.");

			// Retrieving product ctegory List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Inserting into Audit
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Product Exception Occurred", userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add product.Please check server logs for more details.");

			// Retrieving product ctegory List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Retrieving product tax list
			// productTaxList = managementDAOInf.retrieveproducttaxList();

			// Inserting into Audit
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Product Exception occurred", userForm.getUserID());
			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchProduct() throws Exception {
		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		productList = managementDAOInf.searchProduct(form.getSearchProductName(), userForm.getClinicID());

		if (productList.size() > 0) {

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "Product with name '" + form.getSearchProductName() + "' not found.";

			addActionError(errorMsg);

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editProductList() throws Exception {
		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginForm userForm = (LoginForm) session.get("USER");

		productList = managementDAOInf.retrieveAllProduct(userForm.getClinicID());

		if (productList.size() > 0) {

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "No products found. Please add new product.";

			addActionError(errorMsg);

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditProduct() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		productList = managementDAOInf.retrieveProductByID(form.getProductID());
		// productPriceList =
		// managementDAOInf.retrieveProductPriceList(form.getProductID());

		// Retrieving product ctegory List
		productCategoryList = managementDAOInf.retrieveProductCategoryList();

		// Retrieving product tax list
		// productTaxList = managementDAOInf.retrieveTaxList();

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editProduct() throws Exception {
		serviceInf = new eDhanvantariServiceImpl();

		managementDAOInf = new PrescriptionManagementDAOImpl();
		loginDAOInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editProduct(form);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Product updated successfully.");

			// Retrieving product ctegory List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Retrieving product tax list
			// productTaxList = managementDAOInf.retrieveTaxList();

			productList = managementDAOInf.retrieveProductByID(form.getProductID());

			// Inserting into Audit
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Edit Product", userForm.getUserID());

			// productPriceList =
			// managementDAOInf.retrieveProductPriceList(form.getProductID());

			return SUCCESS;
		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Product with same Trade name already exists. Please add new Trade name.");

			// Retrieving product ctegory List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Retrieving product tax list

			productList = managementDAOInf.retrieveProductByID(form.getProductID());

			loginDAOInf.insertAudit(request.getRemoteAddr(), "Edit Product Already Exists Exception Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update product.Please check server logs for more details.");

			// Retrieving product ctegory List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Retrieving product tax list
			// productTaxList = managementDAOInf.retrieveTaxList();

			productList = managementDAOInf.retrieveProductByID(form.getProductID());

			// Inserting into Audit
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Edit Product Exception Occurred", userForm.getUserID());

			// productPriceList =
			// managementDAOInf.retrieveProductPriceList(form.getProductID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addPrice() throws Exception {
		serviceInf = new eDhanvantariServiceImpl();

		managementDAOInf = new PrescriptionManagementDAOImpl();
		loginDAOInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editProductPrice(form);

		if (message.equalsIgnoreCase("success")) {

			addActionMessage("Product price added successfully.");

			// Retrieving product ctegory List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Retrieving product tax list
			productTaxList = managementDAOInf.retrieveproducttaxList();

			productList = managementDAOInf.retrieveProductByID(form.getProductID());

			productPriceList = managementDAOInf.retrieveProductPriceList(form.getProductID());

			// Inserting into Audit
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Product Price", userForm.getUserID());

			return SUCCESS;
		} else {

			addActionError("Failed to add product price.Please check server logs for more details.");

			// Retrieving product category List
			productCategoryList = managementDAOInf.retrieveProductCategoryList();

			// Retrieving product tax list
			productTaxList = managementDAOInf.retrieveproducttaxList();

			productList = managementDAOInf.retrieveProductByID(form.getProductID());

			productPriceList = managementDAOInf.retrieveProductPriceList(form.getProductID());

			// Inserting into Audit
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Product Price Exception Occurred",
					userForm.getUserID());

			return ERROR;

		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void verifyProductExist() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = managementDAOInf.verifyProductExist(form.getProductBarcode());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while verifying product exist or not");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void addOPDChargesAJAX() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		try {

			message = managementDAOInf.insertOPDChargeDetails(form.getChargeType(), form.getCharges(),
					userForm.getPracticeID());

			values = managementDAOInf.retrieveOPDChargesForPractice(userForm.getPracticeID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while inserting OPD charges");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void addIPDTarrifChargesAJAX() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		try {

			// Retrieving roomtypeID
			PatientDAOInf patientDAOInf = new PatientDAOImpl();

			int roomTypeID = patientDAOInf.retrieveIPDRoomTypeID("Operation Theatre", userForm.getPracticeID());

			form.setRoomTypeID(roomTypeID);
			form.setPracticeID(userForm.getPracticeID());

			message = managementDAOInf.insertIPDCharges(form);

			values = managementDAOInf.retrieveIPDTarrifChargesForPractice(roomTypeID, userForm.getPracticeID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while inserting OPD charges");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void getTaxPercent() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			String taxName = request.getParameter("taxName");

			if (taxName.contains("==")) {
				taxName = taxName.replace("==", "%");
			}

			values = managementDAOInf.retrieveTaxPercentByTaxName(taxName);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving tax percent based on tax name");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderAddStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		stockSupplierList = managementDAOInf.retrieveSupplierList();

		// clinicList = daoInf.retrieveClinicList(userForm.getPracticeID());

		return SUCCESS;

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveClientDetails() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = managementDAOInf.retrieveSupplierDetailsByID(form.getSupplierID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving supplier details based on supplierID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveProductRate() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = managementDAOInf.retrieveProductRateDetailsByProductID(form.getProductID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product rate from product ID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveProductIDByBarcode() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = managementDAOInf.retrieveProductIDByBarcode(form.getBarcode());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product ID from barcode");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveProductName() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			values = managementDAOInf.retrieveProductNameByID(form.getProductID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product name based on ID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addStockReceipt() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		loginDAOInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.addStockReceipt(form, userForm.getUserID());

		if (message.equals("success")) {

			addActionMessage("Stock added successfully.");

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			// clinicList =
			// loginDAOInf.retrieveClinicList(userForm.getPracticeID());

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Stock", userForm.getUserID());

			return SUCCESS;

		} else if (message.equals("noProductSelected")) {

			addActionError("No product selected. Please select at least one product.");

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			// clinicList =
			// loginDAOInf.retrieveClinicList(userForm.getPracticeID());

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Stock Exception Occurred", userForm.getUserID());

			return "noProductSelected";

		} else {

			addActionError("Failed to add stock. Please check server logs for more details.");

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			// clinicList =
			// loginDAOInf.retrieveClinicList(userForm.getPracticeID());

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Add Stock Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		stockList = managementDAOInf.searchStockList(form.getSearchStock(), form.getSearchCategory(),
				userForm.getClinicID());

		if (stockList.size() > 0) {

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "Stock with name '" + form.getSearchStock() + "' not found.";

			addActionError(errorMsg);

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editStockList() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		stockList = managementDAOInf.retrieveAllStockList(userForm.getClinicID());

		if (stockList.size() > 0) {

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "No stock found. Please add new stock.";

			addActionError(errorMsg);

			return ERROR;

		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void deleteProductRow() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		try {

			/*
			 * Retrieving tax amount of product, to be deleted, from StockReceipt table and
			 * subtracting that value from tax amount from StockReceipt table for that
			 * particular stockReceiptID
			 */
			double taxAmountToBeSubtracted = managementDAOInf.retrieveProductsTaxAmount(form.getProductID());

			System.out.println("Tax amount to be deducted..." + taxAmountToBeSubtracted);

			managementDAOInf.updateStockReceiptTaxAmount(form.getReceiptID(), taxAmountToBeSubtracted);

			values = managementDAOInf.deleteProduct(form.getStockID());

			/*
			 * Updating netStock from Product table by fetching quantity of product from
			 * stock table for particular stockReceiptID
			 */
			// Retrieve quantity of added product based on stockID from
			// StockTransaction table
			double quantity = managementDAOInf.retrieveProductQuantity(form.getStockID());

			// Updating retrieved quantity into Stock table based on
			// stockID
			managementDAOInf.updateProductNetStock(form.getStockID(), quantity);

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while deleting product from stock");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		loginDAOInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		// Retrieving product stock list based on stockReceiptID from Stock
		// table
		productList = managementDAOInf.retireveStockProductList(form.getStockReceiptID());

		// Retrieving stock receipt details based on stockReceiptID from
		// StockReceipt table
		stockList = managementDAOInf.retireveStockReceiptList(form.getStockReceiptID(), userForm.getClinicID());

		// Retrieving clinic list based on practiceID
		clinicList = loginDAOInf.retrieveClinicList(userForm.getPracticeID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editStockReceipt() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		loginDAOInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editStockReceipt(form, userForm.getUserID());

		if (message.equals("success")) {

			addActionMessage("Stock updated successfully.");

			// Retrieving product stock list based on stockReceiptID from Stock
			// table
			productList = managementDAOInf.retireveStockProductList(form.getStockReceiptID());

			// Retrieving stock receipt details based on stockReceiptID from
			// StockReceipt table
			stockList = managementDAOInf.retireveStockReceiptList(form.getStockReceiptID(), userForm.getClinicID());

			// Retrieving clinic list based on practiceID
			clinicList = loginDAOInf.retrieveClinicList(userForm.getPracticeID());

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Edit Stock", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to udpate stock. Please check server logs for more details.");

			// Retrieving product stock list based on stockReceiptID from Stock
			// table
			productList = managementDAOInf.retireveStockProductList(form.getStockReceiptID());

			// Retrieving stock receipt details based on stockReceiptID from
			// StockReceipt table
			stockList = managementDAOInf.retireveStockReceiptList(form.getStockReceiptID(), userForm.getClinicID());

			// Retrieving clinic list based on practiceID
			clinicList = loginDAOInf.retrieveClinicList(userForm.getPracticeID());

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Edit Stock Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderRemoveStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		ConfigurationUtil configurationUtil = new ConfigurationUtil();

		// stockSupplierList = managementDAOInf.retrieveSupplierList();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		productCategoryList = configurationUtil
				.sortHashMap(managementDAOInf.retrieveProductList(userForm.getClinicID()));

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String removeStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		loginDAOInf = new LoginDAOImpl();

		ConfigurationUtil configurationUtil = new ConfigurationUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		productCategoryList = configurationUtil
				.sortHashMap(managementDAOInf.retrieveProductList(userForm.getClinicID()));

		message = serviceInf.removeStock(form, userForm.getUserID());

		if (message.equals("success")) {

			addActionMessage("Stock removed successfully.");

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Remove Stock", userForm.getUserID());

			return SUCCESS;

		} else if (message.equals("noProductSelected")) {

			addActionError("No product selected. Please select at least one product.");

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Remove Stock Exception Occurred", userForm.getUserID());

			return "noProductSelected";

		} else {

			addActionError("Failed to remove stock. Please check server logs for more details.");

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Remove Stock Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderOrderStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		stockSupplierList = managementDAOInf.retrieveSupplierList();

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchOrderStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		stockList = managementDAOInf.searchOrderStock(form.getSupplierID(), form.getClinicID());

		String supplierName = managementDAOInf.retrieveSupplierNameByID(form.getSupplierID());

		if (stockList.size() > 0) {

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			request.setAttribute("userListEnable", "userSearchListEnable");

			return SUCCESS;

		} else {

			String errorMsg = "No stock found for supplier '" + supplierName + "'";

			stockSupplierList = managementDAOInf.retrieveSupplierList();

			addActionError(errorMsg);

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderConfirmOrder() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		PrescriptionManagementForm productForm = null;

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		stockList = new ArrayList<PrescriptionManagementForm>();

		String supplierName = "";

		int srNo = 1;

		/*
		 * Iterating over supplierID array in order to get all Quantities, ProductIDs
		 * and supplier IDs
		 */
		for (int i = 0; i < form.getCustomerID().length; i++) {

			productForm = new PrescriptionManagementForm();

			productForm.setProductID(Integer.parseInt(form.getProductCompID()[i]));
			productForm.setSupplierID(Integer.parseInt(form.getCustomerID()[i]));
			productForm.setQuantity(Double.parseDouble(form.getProductQuantity()[i]));
			productForm.setProductName(
					managementDAOInf.retrieveProductNameByProductID(Integer.parseInt(form.getProductCompID()[i])));
			// productForm.setName(managementDAOInf.retrieveSupplierNameByID(Integer.parseInt(form.getCustomerID()[i])));
			productForm.setSrNo(srNo);
			productForm.setClinicID(Integer.parseInt(form.getStockClinicID()[i]));

			supplierName = managementDAOInf.retrieveSupplierNameByID(Integer.parseInt(form.getCustomerID()[i]));

			stockList.add(productForm);

			srNo++;

		}

		request.setAttribute("supplierName", supplierName);

		request.setAttribute("orderNo", managementDAOInf.retrieveOrderNumber(userForm.getClinicSuffix()));

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confirmOrder() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		loginDAOInf = new LoginDAOImpl();

		PrescriptionManagementForm productForm = null;

		ConvertToPDFUtil pdfUtil = new ConvertToPDFUtil();

		EmailUtil emailUtil = new EmailUtil();

		ConfigXMLUtil xmlUtil = new ConfigXMLUtil();

		ConfigurationUtil util = new ConfigurationUtil();

		String accessKey = xmlUtil.getAccessKey();

		String secreteKey = xmlUtil.getSecreteKey();

		AWSS3Connect awss3Connect = new AWSS3Connect();

		// getting input file location from S3 bucket
		String s3reportFilePath = xmlUtil.getS3RDMLReportFilePath();

		// getting s3 bucket name
		String bucketName = util.getS3BucketName();

		// getting s3 bucket region
		String bucketRegion = xmlUtil.getS3BucketRegion();

		// Set the presigned URL to expire after one hour.
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secreteKey);

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(bucketRegion).build();

		int srNo = 1;

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		String supplierName = "";

		int supplierID = 0;

		stockList = new ArrayList<PrescriptionManagementForm>();

		/*
		 * Verify whether order with current order no id already created or not, if not,
		 * insert into Orders table entries with current order no, else, proceed further
		 */
		boolean check = managementDAOInf.verifyOrderCreated(form.getOrderNo());

		if (check) {
			System.out.println("Order with no.: " + form.getOrderNo() + " is already created.");

			message = "success";
		} else {

			/*
			 * Iterating over supplierID array in order to get all Quantities, ProductIDs
			 * and supplier IDs tp insert intp Orders table
			 */
			for (int i = 0; i < form.getCustomerID().length; i++) {

				System.out.println("Quantity ... " + form.getProductQuantity()[i]);

				/*
				 * Inserting order details into Orders table
				 */
				message = managementDAOInf.insertOrder(Integer.parseInt(form.getCustomerID()[i]),
						Integer.parseInt(form.getProductCompID()[i]), Double.parseDouble(form.getProductQuantity()[i]),
						userForm.getUserID(), Integer.parseInt(form.getStockClinicID()[i]), form.getOrderNo());

				productForm = new PrescriptionManagementForm();

				productForm.setProductID(Integer.parseInt(form.getProductCompID()[i]));
				productForm.setSupplierID(Integer.parseInt(form.getCustomerID()[i]));
				productForm.setQuantity(Double.parseDouble(form.getProductQuantity()[i]));
				productForm.setProductName(
						managementDAOInf.retrieveProductNameByProductID(Integer.parseInt(form.getProductCompID()[i])));
				// productForm.setName(managementDAOInf.retrieveSupplierNameByID(Integer.parseInt(form.getCustomerID()[i])));
				productForm.setSrNo(srNo);
				productForm.setClinicID(Integer.parseInt(form.getStockClinicID()[i]));

				supplierName = managementDAOInf.retrieveSupplierNameByID(Integer.parseInt(form.getCustomerID()[i]));

				supplierID = Integer.parseInt(form.getCustomerID()[i]);

				stockList.add(productForm);

				srNo++;

			}

		}

		request.setAttribute("orderNo", form.getOrderNo());

		request.setAttribute("supplierName", supplierName);

		stockSupplierList = managementDAOInf.retrieveSupplierList();

		if (message.equals("success")) {

			/*
			 * Generating order PDF
			 */
			String realPath = request.getServletContext().getRealPath("/");

			// String PDFFileName = realPath + supplierName + "_" + supplierID + "_" +
			// form.getOrderNo() + "_order"+ ".pdf";
			String PDFFileName = supplierName + "_" + supplierID + "_" + form.getOrderNo() + "_order" + ".pdf";
			String fileName = supplierName + "_" + supplierID + "_" + form.getOrderNo() + "_order" + ".pdf";

			/*
			 * Checking whether Email button is clicked or Print button is clicked, and
			 * according to that proceed further
			 */
			if (form.getSubmitButton().equals("Email")) {

				message = pdfUtil.generateOrderPDF(form, supplierID, PDFFileName, realPath);

				if (message.equals("success")) {
					File inputFile = new File(realPath + "/" + PDFFileName);

					// Storing file to S3 RDML INPUT FILE location
					message = awss3Connect.pushFile(inputFile, PDFFileName, bucketName, bucketRegion, s3reportFilePath);

					S3ObjectInputStream s3ObjectInputStream = s3
							.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, PDFFileName))
							.getObjectContent();

					form.setFileInputStream(s3ObjectInputStream);

					form.setFileName(PDFFileName);

					/*
					 * Retrieving supplierEmail and checking whether it exists or not, if no email
					 * found the supplier, giving message that 'No email available for supplier',
					 * else proceeding for sending email to supplier about stock order
					 */
					String supplierEmail = managementDAOInf.retrieveSupplierEmailID(supplierID);

					if (supplierEmail == "" || supplierEmail == null) {

						addActionError("No email available for supplier");

						// Inserting values into Audit table
						loginDAOInf.insertAudit(request.getRemoteAddr(), "Order Confirmed (Email) Email Not Found",
								userForm.getUserID());

						request.setAttribute("supplierName", supplierName);

						return "noEmail";

					} else if (supplierEmail.isEmpty()) {

						addActionError("No email available for supplier");

						// Inserting values into Audit table
						loginDAOInf.insertAudit(request.getRemoteAddr(), "Order Confirmed (Email) Email Not Found",
								userForm.getUserID());

						request.setAttribute("supplierName", supplierName);

						return "noEmail";

					} else {

						// Retrieve email From emailID
						String fromEmailID = managementDAOInf.retrieveEmailFromByPracticeID(userForm.getPracticeID());

						// Retrieve email from password
						String emailFromPass = managementDAOInf
								.retrieveEmailFromPassByPracticeID(userForm.getPracticeID());

						// Retrieve clinic Name by practiceID
						String clinicName = managementDAOInf.retrieveClinicNameFromPracticeID(userForm.getPracticeID());

						/*
						 * Sending mail to supplier about stock order
						 */
						message = emailUtil.sendOrderMail(fromEmailID, supplierEmail, emailFromPass, clinicName,
								supplierName, realPath, fileName, userForm.getFullName(), form.getOrderNo());

						if (message.equals("success")) {

							addActionMessage("Order confirmed successfully.");

							request.setAttribute("supplierName", supplierName);

							// Inserting values into Audit table
							loginDAOInf.insertAudit(request.getRemoteAddr(), "Order Confirmed (Email)",
									userForm.getUserID());

							return SUCCESS;

						} else {

							addActionError(
									"Failed to send email about stock order. Please check server logs for more details.");

							// Inserting values into Audit table
							loginDAOInf.insertAudit(request.getRemoteAddr(),
									"Order Confirmed (Email) Exception Occurred", userForm.getUserID());

							request.setAttribute("supplierName", supplierName);

							return ERROR;

						}

					}

				} else {

					addActionError(
							"Failed to create PDF for email stock order. Please check server logs for more details.");

					// Inserting values into Audit table
					loginDAOInf.insertAudit(request.getRemoteAddr(), "Order Confirmed PDF Creation Exception Occurred",
							userForm.getUserID());

					request.setAttribute("supplierName", supplierName);

					return ERROR;

				}

			} else {

				message = pdfUtil.generateOrderPDF(form, supplierID, PDFFileName, realPath);

				if (message.equals("success")) {

					File inputFile = new File(realPath + "/" + PDFFileName);

					// Storing file to S3 RDML INPUT FILE location
					message = awss3Connect.pushFile(inputFile, PDFFileName, bucketName, bucketRegion, s3reportFilePath);

					S3ObjectInputStream s3ObjectInputStream = s3
							.getObject(new GetObjectRequest(bucketName + "/" + s3reportFilePath, PDFFileName))
							.getObjectContent();

					form.setFileInputStream(s3ObjectInputStream);

					addActionMessage("Order confirmed successfully.");

					// Inserting values into Audit table
					loginDAOInf.insertAudit(request.getRemoteAddr(), "Order Confirmed (Print)", userForm.getUserID());

					request.setAttribute("supplierName", supplierName);

					// Setting pdf file name into request object
					form.setFileName(PDFFileName);

					return SUCCESS;

				} else {

					addActionError(
							"Failed to create PDF for print stock order. Please check server logs for more details.");

					// Inserting values into Audit table
					loginDAOInf.insertAudit(request.getRemoteAddr(), "Order Confirmed (Print) Exception Occurred",
							userForm.getUserID());

					request.setAttribute("supplierName", supplierName);

					return ERROR;

				}

			}

		} else {

			addActionError("Failed to create stock order. Please check server logs for more details.");

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Order Confirmed Exception Occurred",
					userForm.getUserID());

			request.setAttribute("supplierName", supplierName);

			return ERROR;

		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveStockReceiptNoByClinicID() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		daoInf = new LoginDAOImpl();

		try {

			values = managementDAOInf.retrieveStockReceiptNoByClinicID(form.getClinicID(),
					daoInf.retrieveClinicSuffix(form.getClinicID()));

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product name based on ID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void retrieveProductListByClinicID() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		values = new JSONObject();

		JSONObject object = new JSONObject();

		response.setCharacterEncoding("UTF-8");

		daoInf = new LoginDAOImpl();

		try {

			values = managementDAOInf.retrieveProductListByClinicID(form.getClinicID());

			PrintWriter out = response.getWriter();

			out.print(values);

		} catch (Exception exception) {

			exception.printStackTrace();

			object.put("ErrMsg", "Error occured while retrieving product name based on ID");

			array.add(object);

			values.put("Release", array);

			PrintWriter out = response.getWriter();

			out.print(values);
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchRemoveStock() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		ConfigurationUtil configurationUtil = new ConfigurationUtil();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		productCategoryList = configurationUtil
				.sortHashMap(managementDAOInf.retrieveProductList(userForm.getClinicID()));

		productList = managementDAOInf.retrieveProductStockListToRemove(form.getProductID(), userForm.getClinicID());

		if (productList.size() > 0) {

			request.setAttribute("searchCheck", "Found");

			return SUCCESS;

		} else {

			addActionError("No stock available for selected product.");

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteProduct() throws Exception {

		serviceInf = new eDhanvantariServiceImpl();

		loginDAOInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setClinicID(userForm.getClinicID());

		message = serviceInf.disableProduct(form);

		if (message.equals("success")) {

			addActionMessage("Product/drug deleted successfully.");

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Delete Product", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete product/drug. Please check server logs for more details.");

			// Inserting values into Audit table
			loginDAOInf.insertAudit(request.getRemoteAddr(), "Delete Product Exception Occurred", userForm.getUserID());

			return ERROR;
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchRoomType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeList = managementDAOInf.retrieveRoomTypeListByType(form.getSearchCharges(), userForm.getPracticeID());

		if (roomTypeList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No room type found for : " + form.getSearchCharges());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllRoomTypes() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

		if (roomTypeList.size() > 0) {
			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No room type found. Please add new room type.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRoomType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.addRoomType(form);

		if (message.equals("success")) {

			addActionMessage("Room type added successfully.");

			roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Room Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Room with the same type already exists. Please add new room type.");

			roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add Room Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add room type. Please check server logs for more details.");

			roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Room Type Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditRoomType() throws Exception {

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		managementDAOInf = new PrescriptionManagementDAOImpl();

		editRoomTypeList = managementDAOInf.retrieveRoomTypeListByID(form.getRoomTypeID());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchChargesName = form.getSearchCharges();

		if (searchChargesName == null || searchChargesName == "") {

			roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

		} else if (searchChargesName.isEmpty()) {
			roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());
		} else {

			roomTypeList = managementDAOInf.retrieveRoomTypeListByType(form.getSearchCharges(),
					userForm.getPracticeID());

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editRoomType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = serviceInf.editRoomType(form);

		form.setPracticeID(userForm.getPracticeID());

		if (message.equals("success")) {

			addActionMessage("Room type updated successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());
			} else {

				roomTypeList = managementDAOInf.retrieveRoomTypeListByType(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Room Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Room with the same type already exists. Please user another room type.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());
			} else {

				roomTypeList = managementDAOInf.retrieveRoomTypeListByType(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Room Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update room type. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());
			} else {

				roomTypeList = managementDAOInf.retrieveRoomTypeListByType(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Room Type Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRoomType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = managementDAOInf.updateRoomTypeStatus(form.getRoomTypeID(), ActivityStatus.INACTIVE);

		if (message.equals("success")) {

			addActionMessage("Room type deleted successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());
			} else {

				roomTypeList = managementDAOInf.retrieveRoomTypeListByType(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Room Type", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete room type. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomTypeList = managementDAOInf.retrieveAllRoomTypeList(userForm.getPracticeID());
			} else {

				roomTypeList = managementDAOInf.retrieveRoomTypeListByType(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Room Type Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderIPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchIPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		IPDChargesList = managementDAOInf.retrieveIPDChargesListByItem(form.getSearchCharges(),
				userForm.getPracticeID());

		if (IPDChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No IPD charge found for : " + form.getSearchCharges());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllIPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

		if (IPDChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No IPD charge found. Please add new IPD charge.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addIPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.addIPDCharges(form);

		if (message.equals("success")) {

			addActionMessage("IPD charges added successfully.");

			IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add IPD Charges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("IPD charges with same item name and room type already exists.");

			IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add IPD Charges Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add IPD charges. Please check server logs for more details.");

			IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add IPD Charges Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditIPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		editIPDChargesList = managementDAOInf.retrieveIPDChargesListByID(form.getIPDChargeID());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchChargesName = form.getSearchCharges();

		if (searchChargesName == null || searchChargesName == "") {

			IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

		} else if (searchChargesName.isEmpty()) {
			IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());
		} else {

			IPDChargesList = managementDAOInf.retrieveIPDChargesListByItem(form.getSearchCharges(),
					userForm.getPracticeID());

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editIPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.editIPDCharges(form);

		if (message.equals("success")) {

			addActionMessage("IPD charges updated successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());
			} else {

				IPDChargesList = managementDAOInf.retrieveIPDChargesListByItem(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit IPD Charges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("IPD charges with same item name and room type already exists.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());
			} else {

				IPDChargesList = managementDAOInf.retrieveIPDChargesListByItem(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit IPD Tarrif Charges Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update IPD charges. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());
			} else {

				IPDChargesList = managementDAOInf.retrieveIPDChargesListByItem(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit IPD Tarrif Charges Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteIPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		message = managementDAOInf.updateIPDChargesStatus(form.getIPDChargeID(), ActivityStatus.INACTIVE);

		if (message.equals("success")) {

			addActionMessage("IPD Charge deleted successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());
			} else {

				IPDChargesList = managementDAOInf.retrieveIPDChargesListByItem(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete IPD Charge", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete IPD charge. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				IPDChargesList = managementDAOInf.retrieveAllIPDChargesList(userForm.getPracticeID());
			} else {

				IPDChargesList = managementDAOInf.retrieveIPDChargesListByItem(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete IPD Charge Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderIPDConsultantCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchIPDConsultantCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		consultantChargesList = managementDAOInf.retrieveIPDConsultantChargesListByDoctor(form.getSearchCharges(),
				userForm.getPracticeID());

		if (consultantChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No IPD consultant charge found for : " + form.getSearchCharges());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllIPDConsultantCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

		if (consultantChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No IPD consultant charge found. Please add new IPD consultant charge.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addIPDConsultantCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		message = serviceInf.addIPDConsultantCharges(form, userForm.getPracticeID());

		if (message.equals("success")) {

			addActionMessage("IPD consultant charges added successfully.");

			consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add IPD Consultant Charges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("IPD consultant charges with same doctor name and room type already exists.");

			consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add IPD Consultant Charges Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add IPD Consultant charges. Please check server logs for more details.");

			consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add IPD Consultant Charges Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditIPDConsultantCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		editConsultantChargesList = managementDAOInf
				.retrieveIPDConsultantChargesListByID(form.getConsultationChargeID());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchChargesName = form.getSearchCharges();

		if (searchChargesName == null || searchChargesName == "") {

			consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

		} else if (searchChargesName.isEmpty()) {
			consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());
		} else {

			consultantChargesList = managementDAOInf.retrieveIPDConsultantChargesListByDoctor(form.getSearchCharges(),
					userForm.getPracticeID());

		}

		return SUCCESS;

	}

	public String editIPDConsultantCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.editIPDConsultantCharges(form);

		if (message.equals("success")) {

			addActionMessage("IPD consultant charges updated successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());
			} else {

				consultantChargesList = managementDAOInf
						.retrieveIPDConsultantChargesListByDoctor(form.getSearchCharges(), userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit IPD Consultant Charges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("IPD consultant charges with same doctor name and room type already exists.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());
			} else {

				consultantChargesList = managementDAOInf
						.retrieveIPDConsultantChargesListByDoctor(form.getSearchCharges(), userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit IPD Consultant Charges Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update IPD consultant charges. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());
			} else {

				consultantChargesList = managementDAOInf
						.retrieveIPDConsultantChargesListByDoctor(form.getSearchCharges(), userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit IPD Consultant Charges Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteIPDConsultantCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		message = managementDAOInf.updateIPDConsultantChargesStatus(form.getConsultationChargeID(),
				ActivityStatus.INACTIVE);

		if (message.equals("success")) {

			addActionMessage("IPD Consultant Charge deleted successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());
			} else {

				consultantChargesList = managementDAOInf
						.retrieveIPDConsultantChargesListByDoctor(form.getSearchCharges(), userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete IPD Consultant Charge", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete IPD Consultant charge. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				consultantChargesList = managementDAOInf.retrieveAllIPDConsultantChargesList(userForm.getPracticeID());
			} else {

				consultantChargesList = managementDAOInf
						.retrieveIPDConsultantChargesListByDoctor(form.getSearchCharges(), userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete IPD Consultant Charge Exception Occurred",
					userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Excepion
	 */
	public String searchOPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		OPDChargesList = managementDAOInf.searchOPDCharges(form.getSearchCharges(), userForm.getPracticeID());

		if (OPDChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No OPD Charges found for : " + form.getSearchCharges());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllOPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

		if (OPDChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No OPD Charges found. Please add new OPD Charges.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addOPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.addOPDCharges(form);

		if (message.equals("success")) {

			addActionMessage("OPD Charges added successfully.");

			// retrieving OPDCharges list from PVOPDCharges table
			OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add OPD Charges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("OPD Charges details with same chargeType already exists. Please add new chargeType.");

			// retrieving OPDCharges list from PVOPDCharges table
			OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add OPD Charges Details Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add OPD Charges details. Please check server logs for more details.");

			// retrieving OPDCharges list from PVOPDCharges table
			OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add OPD Charges details Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditOPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		editOPDChargesList = managementDAOInf.retrieveOPDChargeDetailsByID(form.getChargesID(),
				form.getSearchCharges());

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchChargesName = form.getSearchCharges();

		if (searchChargesName == null || searchChargesName == "") {

			// retrieving OPDCharges list from PVOPDCharges table
			OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

		} else {

			OPDChargesList = managementDAOInf.searchOPDCharges(searchChargesName, userForm.getPracticeID());

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editOPDCharges() throws Exception {

		serviceInf = new eDhanvantariServiceImpl();

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.editOPDChargesDetails(form);

		if (message.equals("success")) {

			addActionMessage("OPDCharges details updated successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				// retrieving OPDCharges list from PVOPDCharges table
				OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			} else {

				OPDChargesList = managementDAOInf.searchOPDCharges(searchChargesName, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit OPDCharges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("OPDCharges details with same chargeType already exists. Please add new chargeType.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				// retrieving OPDCharges list from PVOPDCharges table
				OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			} else {

				OPDChargesList = managementDAOInf.searchOPDCharges(searchChargesName, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(),
					"Edit OPDCharges details with same chargeType Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update OPDCharges details. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				// retrieving OPDCharges list from PVOPDCharges table
				OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			} else {

				OPDChargesList = managementDAOInf.searchOPDCharges(searchChargesName, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit OPDCharges Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteOPDCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = managementDAOInf.deleteOPDCharges(form.getChargesID());

		if (message.equals("success")) {

			addActionMessage("OPDCharges details deleted successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				// retrieving OPDCharges list from PVOPDCharges table
				OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			} else {

				OPDChargesList = managementDAOInf.searchOPDCharges(searchChargesName, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete OPDCharges details", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete OPDCharges details. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				// retrieving OPDCharges list from PVOPDCharges table
				OPDChargesList = managementDAOInf.retrieveAllOPDCharges(userForm.getPracticeID());

			} else {

				OPDChargesList = managementDAOInf.searchOPDCharges(searchChargesName, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete OPDCharges details Exception Occurred",
					userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchOTType() throws Exception {

		/*
		 * Getting practiceID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		managementDAOInf = new PrescriptionManagementDAOImpl();

		OTTypeList = managementDAOInf.retrieveOTTypeListByType(form.getSearchOTType(), userForm.getPracticeID());

		if (OTTypeList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No OT type found for : " + form.getSearchOTType());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllOTTypes() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting practiceID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

		if (OTTypeList.size() > 0) {
			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No OT type found. Please add new OT type.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addOTType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.addOTType(form);

		if (message.equals("success")) {

			addActionMessage("OT type added successfully.");

			OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add OT Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("OT with the same type already exists. Please add new OT type.");

			OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add OT Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add OT type. Please check server logs for more details.");

			OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add OT Type Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditOTType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		editOTTypeList = managementDAOInf.retrieveOTTypeListByID(form.getOTTypeID());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchOTType = form.getSearchOTType();

		if (searchOTType == null || searchOTType == "") {

			OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

		} else if (searchOTType.isEmpty()) {
			OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());
		} else {

			OTTypeList = managementDAOInf.retrieveOTTypeListByType(searchOTType, userForm.getPracticeID());

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editOTType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.editOTType(form);

		if (message.equals("success")) {

			addActionMessage("OT type updated successfully.");

			String searchOTType = form.getSearchOTType();

			if (searchOTType == null || searchOTType == "") {

				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			} else if (searchOTType.isEmpty()) {
				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());
			} else {

				OTTypeList = managementDAOInf.retrieveOTTypeListByType(searchOTType, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit OT Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("OT with the same type already exists. Please user another OT type.");

			String searchOTType = form.getSearchOTType();

			if (searchOTType == null || searchOTType == "") {

				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			} else if (searchOTType.isEmpty()) {
				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());
			} else {

				OTTypeList = managementDAOInf.retrieveOTTypeListByType(searchOTType, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit OT Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update OT type. Please check server logs for more details.");

			String searchOTType = form.getSearchOTType();

			if (searchOTType == null || searchOTType == "") {

				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			} else if (searchOTType.isEmpty()) {
				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());
			} else {

				OTTypeList = managementDAOInf.retrieveOTTypeListByType(searchOTType, userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit OT Type Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteOTType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = managementDAOInf.updateOTTypeStatus(form.getOTTypeID(), ActivityStatus.INACTIVE);

		if (message.equals("success")) {

			String searchOTType = form.getSearchOTType();

			if (searchOTType == null || searchOTType == "") {

				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			} else if (searchOTType.isEmpty()) {
				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());
			} else {

				OTTypeList = managementDAOInf.retrieveOTTypeListByType(searchOTType, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete OT Type", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete OT type. Please check server logs for more details.");

			String searchOTType = form.getSearchOTType();

			if (searchOTType == null || searchOTType == "") {

				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());

			} else if (searchOTType.isEmpty()) {
				OTTypeList = managementDAOInf.retrieveAllOTTypeList(userForm.getPracticeID());
			} else {

				OTTypeList = managementDAOInf.retrieveOTTypeListByType(searchOTType, userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete OT Type Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchServiceType() throws Exception {

		/*
		 * Getting practiceID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceTypeList = managementDAOInf.retrieveServiceTypeListByType(form.getSearchServiceType(),
				userForm.getPracticeID());

		if (serviceTypeList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Service type found for : " + form.getSearchOTType());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllServiceTypes() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting practiceID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

		if (serviceTypeList.size() > 0) {
			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Service type found. Please add new Service type.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addServiceType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.addServiceType(form);

		if (message.equals("success")) {

			addActionMessage("Service type added successfully.");

			serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Service Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Service with the same type already exists. Please add new Service type.");

			serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add Service Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add Service type. Please check server logs for more details.");

			serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Service Type Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditServiceType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		editServiceTypeList = managementDAOInf.retrieveServiceTypeListByID(form.getServiceTypeID());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchServiceType = form.getSearchServiceType();

		if (searchServiceType == null || searchServiceType == "") {

			serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

		} else if (searchServiceType.isEmpty()) {
			serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());
		} else {

			serviceTypeList = managementDAOInf.retrieveServiceTypeListByType(searchServiceType,
					userForm.getPracticeID());

		}

		return SUCCESS;

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editServiceType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.editServiceType(form);

		if (message.equals("success")) {

			addActionMessage("Service type updated successfully.");

			String searchServiceType = form.getSearchServiceType();

			if (searchServiceType == null || searchServiceType == "") {

				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			} else if (searchServiceType.isEmpty()) {
				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());
			} else {

				serviceTypeList = managementDAOInf.retrieveServiceTypeListByType(searchServiceType,
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Service Type", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Service with the same type already exists. Please user another Service type.");

			String searchServiceType = form.getSearchServiceType();

			if (searchServiceType == null || searchServiceType == "") {

				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			} else if (searchServiceType.isEmpty()) {
				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());
			} else {

				serviceTypeList = managementDAOInf.retrieveServiceTypeListByType(searchServiceType,
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Service Type Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update Service type. Please check server logs for more details.");

			String searchServiceType = form.getSearchServiceType();

			if (searchServiceType == null || searchServiceType == "") {

				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			} else if (searchServiceType.isEmpty()) {
				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());
			} else {

				serviceTypeList = managementDAOInf.retrieveServiceTypeListByType(searchServiceType,
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Service Type Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteServiceType() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		message = managementDAOInf.updateServiceTypeStatus(form.getServiceTypeID(), ActivityStatus.INACTIVE);

		if (message.equals("success")) {

			String searchServiceType = form.getSearchServiceType();

			if (searchServiceType == null || searchServiceType == "") {

				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			} else if (searchServiceType.isEmpty()) {
				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());
			} else {

				serviceTypeList = managementDAOInf.retrieveServiceTypeListByType(searchServiceType,
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Service Type", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete service type. Please check server logs for more details.");

			String searchServiceType = form.getSearchServiceType();

			if (searchServiceType == null || searchServiceType == "") {

				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());

			} else if (searchServiceType.isEmpty()) {
				serviceTypeList = managementDAOInf.retrieveAllServiceTypeList(userForm.getPracticeID());
			} else {

				serviceTypeList = managementDAOInf.retrieveServiceTypeListByType(searchServiceType,
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Service Type Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderRoomCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchRoomCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		roomChargesList = managementDAOInf.retrieveRoomChargesListByRoom(form.getSearchCharges(),
				userForm.getPracticeID());

		if (roomChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No Room charge found for : " + form.getSearchCharges());

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewAllRoomCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

		if (roomChargesList.size() > 0) {

			request.setAttribute("componentMsg", "available");

			return SUCCESS;

		} else {

			addActionError("No room charge found. Please add new room charge.");

			return ERROR;

		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRoomCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		message = serviceInf.addRoomCharges(form, userForm.getPracticeID());

		if (message.equals("success")) {

			addActionMessage("Room charges added successfully.");

			roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Room Charges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Room charges with same room name and room type already exists.");

			roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Add Room Charges Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to add room charges. Please check server logs for more details.");

			roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Add Room Charges Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String renderEditRoomCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		editRoomChargesList = managementDAOInf.retrieveRoomChargesListByID(form.getRoomChargesID());

		request.setAttribute("componentMsg", "available");

		request.setAttribute("componentEdit", "edit");

		String searchChargesName = form.getSearchCharges();

		if (searchChargesName == null || searchChargesName == "") {

			roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

		} else if (searchChargesName.isEmpty()) {
			roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());
		} else {

			roomChargesList = managementDAOInf.retrieveRoomChargesListByRoom(form.getSearchCharges(),
					userForm.getPracticeID());

		}

		return SUCCESS;

	}

	public String editRoomCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		serviceInf = new eDhanvantariServiceImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		form.setPracticeID(userForm.getPracticeID());

		message = serviceInf.editIPDConsultantCharges(form);

		if (message.equals("success")) {

			addActionMessage("Room charges updated successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());
			} else {

				roomChargesList = managementDAOInf.retrieveRoomChargesListByRoom(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Room Charges", userForm.getUserID());

			return SUCCESS;

		} else if (message.equalsIgnoreCase("input")) {

			addActionError("Room charges with same room name and room type already exists.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());
			} else {

				roomChargesList = managementDAOInf.retrieveRoomChargesListByRoom(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting into Audit
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Room Charges Already Exist Exeption Occurred",
					userForm.getUserID());

			return ERROR;

		} else {

			addActionError("Failed to update room charges. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());
			} else {

				roomChargesList = managementDAOInf.retrieveRoomChargesListByRoom(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Edit Room Charges Exeption Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRoomCharges() throws Exception {

		managementDAOInf = new PrescriptionManagementDAOImpl();

		LoginDAOInf daoInf = new LoginDAOImpl();

		/*
		 * Getting userID from Session
		 */
		LoginForm userForm = (LoginForm) session.get("USER");

		roomTypeMap = managementDAOInf.retrieveRoomTypeList(userForm.getPracticeID());

		message = managementDAOInf.updateRoomChargesStatus(form.getRoomChargesID(), ActivityStatus.INACTIVE);

		if (message.equals("success")) {

			addActionMessage("Room Charge deleted successfully.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());
			} else {

				roomChargesList = managementDAOInf.retrieveRoomChargesListByRoom(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Room Charge", userForm.getUserID());

			return SUCCESS;

		} else {

			addActionError("Failed to delete Room charge. Please check server logs for more details.");

			String searchChargesName = form.getSearchCharges();

			if (searchChargesName == null || searchChargesName == "") {

				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());

			} else if (searchChargesName.isEmpty()) {
				roomChargesList = managementDAOInf.retrieveAllRoomChargesList(userForm.getPracticeID());
			} else {

				roomChargesList = managementDAOInf.retrieveRoomChargesListByRoom(form.getSearchCharges(),
						userForm.getPracticeID());

			}

			request.setAttribute("componentMsg", "available");

			// Inserting values into Audit table
			daoInf.insertAudit(request.getRemoteAddr(), "Delete Room Charge Exception Occurred", userForm.getUserID());

			return ERROR;

		}

	}

	/**
	 * @return the roomChargesList
	 */
	public List<PrescriptionManagementForm> getRoomChargesList() {
		return roomChargesList;
	}

	/**
	 * @return the editRoomChargesList
	 */
	public List<PrescriptionManagementForm> getEditRoomChargesList() {
		return editRoomChargesList;
	}

	/**
	 * @param roomChargesList the roomChargesList to set
	 */
	public void setRoomChargesList(List<PrescriptionManagementForm> roomChargesList) {
		this.roomChargesList = roomChargesList;
	}

	/**
	 * @param editRoomChargesList the editRoomChargesList to set
	 */
	public void setEditRoomChargesList(List<PrescriptionManagementForm> editRoomChargesList) {
		this.editRoomChargesList = editRoomChargesList;
	}

	/**
	 * @return the serviceTypeList
	 */
	public List<PrescriptionManagementForm> getServiceTypeList() {
		return serviceTypeList;
	}

	/**
	 * @return the editServiceTypeList
	 */
	public List<PrescriptionManagementForm> getEditServiceTypeList() {
		return editServiceTypeList;
	}

	/**
	 * @param serviceTypeList the serviceTypeList to set
	 */
	public void setServiceTypeList(List<PrescriptionManagementForm> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}

	/**
	 * @param editServiceTypeList the editServiceTypeList to set
	 */
	public void setEditServiceTypeList(List<PrescriptionManagementForm> editServiceTypeList) {
		this.editServiceTypeList = editServiceTypeList;
	}

	/**
	 * @return the oTTypeList
	 */
	public List<PrescriptionManagementForm> getOTTypeList() {
		return OTTypeList;
	}

	/**
	 * @return the editOTTypeList
	 */
	public List<PrescriptionManagementForm> getEditOTTypeList() {
		return editOTTypeList;
	}

	/**
	 * @param oTTypeList the oTTypeList to set
	 */
	public void setOTTypeList(List<PrescriptionManagementForm> oTTypeList) {
		OTTypeList = oTTypeList;
	}

	/**
	 * @param editOTTypeList the editOTTypeList to set
	 */
	public void setEditOTTypeList(List<PrescriptionManagementForm> editOTTypeList) {
		this.editOTTypeList = editOTTypeList;
	}

	/**
	 * @return the editOPDChargesList
	 */
	public List<PrescriptionManagementForm> getEditOPDChargesList() {
		return editOPDChargesList;
	}

	/**
	 * @param editOPDChargesList the editOPDChargesList to set
	 */
	public void setEditOPDChargesList(List<PrescriptionManagementForm> editOPDChargesList) {
		this.editOPDChargesList = editOPDChargesList;
	}

	/**
	 * @return the oPDChargesList
	 */
	public List<PrescriptionManagementForm> getOPDChargesList() {
		return OPDChargesList;
	}

	/**
	 * @param oPDChargesList the oPDChargesList to set
	 */
	public void setOPDChargesList(List<PrescriptionManagementForm> oPDChargesList) {
		OPDChargesList = oPDChargesList;
	}

	/**
	 * @return the consultantChargesList
	 */
	public List<PrescriptionManagementForm> getConsultantChargesList() {
		return consultantChargesList;
	}

	/**
	 * @param consultantChargesList the consultantChargesList to set
	 */
	public void setConsultantChargesList(List<PrescriptionManagementForm> consultantChargesList) {
		this.consultantChargesList = consultantChargesList;
	}

	/**
	 * @return the editConsultantChargesList
	 */
	public List<PrescriptionManagementForm> getEditConsultantChargesList() {
		return editConsultantChargesList;
	}

	/**
	 * @param editConsultantChargesList the editConsultantChargesList to set
	 */
	public void setEditConsultantChargesList(List<PrescriptionManagementForm> editConsultantChargesList) {
		this.editConsultantChargesList = editConsultantChargesList;
	}

	/**
	 * @return the editIPDChargesList
	 */
	public List<PrescriptionManagementForm> getEditIPDChargesList() {
		return editIPDChargesList;
	}

	/**
	 * @param editIPDChargesList the editIPDChargesList to set
	 */
	public void setEditIPDChargesList(List<PrescriptionManagementForm> editIPDChargesList) {
		this.editIPDChargesList = editIPDChargesList;
	}

	/**
	 * @return the iPDChargesList
	 */
	public List<PrescriptionManagementForm> getIPDChargesList() {
		return IPDChargesList;
	}

	/**
	 * @param iPDChargesList the iPDChargesList to set
	 */
	public void setIPDChargesList(List<PrescriptionManagementForm> iPDChargesList) {
		IPDChargesList = iPDChargesList;
	}

	/**
	 * @return the roomTypeMap
	 */
	public HashMap<Integer, String> getRoomTypeMap() {
		return roomTypeMap;
	}

	/**
	 * @param roomTypeMap the roomTypeMap to set
	 */
	public void setRoomTypeMap(HashMap<Integer, String> roomTypeMap) {
		this.roomTypeMap = roomTypeMap;
	}

	/**
	 * @return the clinicList
	 */
	public HashMap<Integer, String> getClinicList() {
		return clinicList;
	}

	/**
	 * @param clinicList the clinicList to set
	 */
	public void setClinicList(HashMap<Integer, String> clinicList) {
		this.clinicList = clinicList;
	}

	/**
	 * @return the stockList
	 */
	public List<PrescriptionManagementForm> getStockList() {
		return stockList;
	}

	/**
	 * @param stockList the stockList to set
	 */
	public void setStockList(List<PrescriptionManagementForm> stockList) {
		this.stockList = stockList;
	}

	/**
	 * @return the stockSupplierList
	 */
	public HashMap<Integer, String> getStockSupplierList() {
		return stockSupplierList;
	}

	/**
	 * @param stockSupplierList the stockSupplierList to set
	 */
	public void setStockSupplierList(HashMap<Integer, String> stockSupplierList) {
		this.stockSupplierList = stockSupplierList;
	}

	/**
	 * @return the productList
	 */
	public List<PrescriptionManagementForm> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<PrescriptionManagementForm> productList) {
		this.productList = productList;
	}

	/**
	 * @return the productPriceList
	 */
	public List<PrescriptionManagementForm> getProductPriceList() {
		return productPriceList;
	}

	/**
	 * @param productPriceList the productPriceList to set
	 */
	public void setProductPriceList(List<PrescriptionManagementForm> productPriceList) {
		this.productPriceList = productPriceList;
	}

	/**
	 * @return the productCategoryList
	 */
	public HashMap<Integer, String> getProductCategoryList() {
		return productCategoryList;
	}

	/**
	 * @param productCategoryList the productCategoryList to set
	 */
	public void setProductCategoryList(HashMap<Integer, String> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	/**
	 * @return the supplierList
	 */
	public List<PrescriptionManagementForm> getSupplierList() {
		return supplierList;
	}

	/**
	 * @param supplierList the supplierList to set
	 */
	public void setSupplierList(List<PrescriptionManagementForm> supplierList) {
		this.supplierList = supplierList;
	}

	/**
	 * @return the editSupplierList
	 */
	public List<PrescriptionManagementForm> getEditSupplierList() {
		return editSupplierList;
	}

	/**
	 * @param editSupplierList the editSupplierList to set
	 */
	public void setEditSupplierList(List<PrescriptionManagementForm> editSupplierList) {
		this.editSupplierList = editSupplierList;
	}

	/**
	 * @return the productTaxList
	 */
	public List<String> getProductTaxList() {
		return productTaxList;
	}

	/**
	 * @param productTaxList the productTaxList to set
	 */
	public void setProductTaxList(List<String> productTaxList) {
		this.productTaxList = productTaxList;
	}

	/**
	 * @return the taxList
	 */
	public List<PrescriptionManagementForm> getTaxList() {
		return taxList;
	}

	/**
	 * @param taxList the taxList to set
	 */
	public void setTaxList(List<PrescriptionManagementForm> taxList) {
		this.taxList = taxList;
	}

	/**
	 * @return the editTaxList
	 */
	public List<PrescriptionManagementForm> getEditTaxList() {
		return editTaxList;
	}

	/**
	 * @param editTaxList the editTaxList to set
	 */
	public void setEditTaxList(List<PrescriptionManagementForm> editTaxList) {
		this.editTaxList = editTaxList;
	}

	/**
	 * @return the editCategoryList
	 */
	public List<PrescriptionManagementForm> getEditCategoryList() {
		return editCategoryList;
	}

	/**
	 * @param editCategoryList the editCategoryList to set
	 */
	public void setEditCategoryList(List<PrescriptionManagementForm> editCategoryList) {
		this.editCategoryList = editCategoryList;
	}

	/**
	 * @return the categoryList
	 */
	public List<PrescriptionManagementForm> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<PrescriptionManagementForm> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return the form
	 */
	public PrescriptionManagementForm getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(PrescriptionManagementForm form) {
		this.form = form;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	/**
	 * @return the editRoomTypeList
	 */
	public List<PrescriptionManagementForm> getEditRoomTypeList() {
		return editRoomTypeList;
	}

	/**
	 * @param editRoomTypeList the editRoomTypeList to set
	 */
	public void setEditRoomTypeList(List<PrescriptionManagementForm> editRoomTypeList) {
		this.editRoomTypeList = editRoomTypeList;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	public PrescriptionManagementForm getModel() {
		// TODO Auto-generated method stub
		return form;
	}

	/**
	 * @return the roomTypeList
	 */
	public List<PrescriptionManagementForm> getRoomTypeList() {
		return roomTypeList;
	}

	/**
	 * @param roomTypeList the roomTypeList to set
	 */
	public void setRoomTypeList(List<PrescriptionManagementForm> roomTypeList) {
		this.roomTypeList = roomTypeList;
	}

}
