package com.edhanvantari.daoImpl;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.edhanvantari.daoInf.ConfigurationDAOInf;
import com.edhanvantari.form.ConfigurationForm;
import com.edhanvantari.form.PrescriptionManagementForm;
import com.edhanvantari.util.ActivityStatus;
import com.edhanvantari.util.DAOConnection;
import com.edhanvantari.util.QueryMaker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConfigurationDAOImpl extends DAOConnection implements ConfigurationDAOInf {

	Connection connection = null;

	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;

	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;

	String status = "error";

	public List<ConfigurationForm> retrieveExistingDiagnosisList() {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveExistingDiagnosisListQuery = QueryMaker.RETRIEVE_DIAGNOSIS_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingDiagnosisListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setDiagnosisID(resultSet.getInt("id"));
				configurationForm.setDiagnosis(resultSet.getString("diagnosis"));
				configurationForm.setDiagnosisICD10Code(resultSet.getString("icd10Code"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing diagnosis list from table due to:::"
					+ exception.getMessage());
		}
		return list;
	}
	
    public String insertTemplatesTestTypeData(String data, int labID, String testType) {

        // Initialize Gson and parse the incoming data string into a map
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> map = gson.fromJson(data, type);

        System.out.println("Template for 2: " + map.get("2"));
        System.out.println("Template for 4: " + map.get("4"));
        System.out.println("Lab ID: " + labID);

        try {
            connection = getConnection();  // Get the connection (make sure this method works properly)
            String insertTestTypeTemplatesQuery = QueryMaker.INSERT_TEST_TYPE_TEMPLATES_QUERY;  // Ensure this query is correct

            preparedStatement = connection.prepareStatement(insertTestTypeTemplatesQuery);

            // Loop through the map and add each entry to the batch
            for (HashMap.Entry<String, String> entry : map.entrySet()) {
                preparedStatement.setString(1, testType);   
                int icd10Code = Integer.parseInt(entry.getKey());  // Parse key to int
                preparedStatement.setInt(2, icd10Code);    
                preparedStatement.setInt(3, labID);
                preparedStatement.addBatch();  // Add to batch
            }

            // Execute the batch of inserts
            int[] affectedRows = preparedStatement.executeBatch();
            System.out.println("Rows inserted: " + affectedRows.length);

            status = "success";
            System.out.println("Successfully inserted diagnosis into table");

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception occurred while inserting diagnosis into table: " + exception.getMessage());
            status = "error";

        } finally {
            // Properly close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status;
    }
    
    public String removeTemplatesTestTypeData(String data, int labID) {
    	
        data = data.trim();
        if (data.endsWith(",")) {
            data = data.substring(0, data.length() - 1);
        }
    	
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> map = gson.fromJson(data, type);
        
        System.out.println("Template for 2: " + map.get("2"));
        System.out.println("Template for 4: " + map.get("4"));
        System.out.println("Lab ID: " + labID);
        
    	try {
            connection = getConnection();

            String removeSelectedTemplatesQuery = QueryMaker.REMOVE_SELECTED_TEMPLATES;
            
            preparedStatement = connection.prepareStatement(removeSelectedTemplatesQuery);
            
            // Loop through the map and add each entry to the batch
            for (HashMap.Entry<String, String> entry : map.entrySet()) {  
                int icd10Code = Integer.parseInt(entry.getKey());  // Parse key to int
                preparedStatement.setInt(1, labID);    
                preparedStatement.setInt(2, icd10Code);
                preparedStatement.addBatch();  // Add to batch
            }
  
            // Execute the batch of inserts
            int[] affectedRows = preparedStatement.executeBatch();
            System.out.println("Rows removed: " + affectedRows.length);

            status = "success";
            System.out.println("Successfully removed data from table");
            
    	} catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception occurred while inserting diagnosis into table: " + exception.getMessage());
            status = "error";
    	} finally {
            // Properly close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

		return status;
    }
    
    public HashMap<String, String> retrieveSelectedTemplates(int labTestID) {
        HashMap<String, String> selectedTemplates = new HashMap<>();

        try {
            // Get the connection (ensure getConnection() works properly)
            connection = getConnection();

            // Query to retrieve selected templates
            String retrieveSelectedTemplates = QueryMaker.RETRIEVE_SELECTED_TEMPLATES;

            // Prepare the statement with the query
            preparedStatement = connection.prepareStatement(retrieveSelectedTemplates);
            preparedStatement.setInt(1, labTestID);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Iterate through the ResultSet and populate the HashMap
            while (resultSet.next()) {
                String templateID = String.valueOf(resultSet.getInt("templateID"));
                String TempName = resultSet.getString("TempName");
                selectedTemplates.put(templateID, TempName);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception occurred while retrieving templates: " + exception.getMessage());
        } finally {
            // Properly close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return selectedTemplates;
    }

	public String insertDiagnosis(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String insertDiagnosisQuery = QueryMaker.INSERT_DIAGNOSIS;

			preparedStatement = connection.prepareStatement(insertDiagnosisQuery);

			preparedStatement.setString(1, configurationForm.getDiagnosis());
			preparedStatement.setString(2, configurationForm.getDiagnosisICD10Code());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted diagnosis into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting diagnosis into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> retrieveReferringDoctorList(int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveReferringDoctorListQuery = QueryMaker.RETRIEVE_REFERRING_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(retrieveReferringDoctorListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setReferringDoctID(resultSet.getInt("id"));
				configurationForm.setReferringDoctName(resultSet.getString("doctorName"));
				configurationForm.setReferringDoctSpecialisation(resultSet.getString("specialization"));
				configurationForm.setReferringDoctClinicName(resultSet.getString("clinicName"));
				configurationForm.setReferringDoctClinicAddres(resultSet.getString("clinicAddress"));
				configurationForm.setReferringDoctPhone(resultSet.getString("phone"));
				configurationForm.setReferringDoctEmail(resultSet.getString("email"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing referring doctor list from table due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	public String insertReferringDoctor(ConfigurationForm configurationForm, int practiceID) {

		try {
			connection = getConnection();

			String insertReferringDoctorQuery = QueryMaker.INSERT_REFERRING_DOCTOR;

			preparedStatement = connection.prepareStatement(insertReferringDoctorQuery);

			preparedStatement.setString(1, configurationForm.getReferringDoctName());
			preparedStatement.setString(2, configurationForm.getReferringDoctSpecialisation());
			preparedStatement.setString(3, configurationForm.getReferringDoctClinicName());
			preparedStatement.setString(4, configurationForm.getReferringDoctClinicAddres());
			preparedStatement.setString(5, configurationForm.getReferringDoctPhone());
			preparedStatement.setString(6, configurationForm.getReferringDoctEmail());
			preparedStatement.setInt(7, practiceID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted referring doctor into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting referring doctor into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;

	}

	public List<ConfigurationForm> retrieveExistingFrequencyList(int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveExistingFrequencyListQuery = QueryMaker.RETRIEVE_FREQUENCY_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingFrequencyListQuery);

			preparedStatement.setInt(1, practiceID);
			preparedStatement.setString(2, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setFrequencyID(resultSet.getInt("id"));
				configurationForm.setFrequency(resultSet.getString("frequencyValues"));
				configurationForm.setFrequencyCount(resultSet.getDouble("count"));
				configurationForm.setSortOrder(resultSet.getInt("sortOrder"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing frequency list from table due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	public String insertFrequency(ConfigurationForm configurationForm, int practiceID) {
		try {
			connection = getConnection();

			String insertFrequencyQuery = QueryMaker.INSERT_FREQUENCY;

			preparedStatement = connection.prepareStatement(insertFrequencyQuery);

			preparedStatement.setString(1, configurationForm.getFrequency());
			preparedStatement.setDouble(2, configurationForm.getFrequencyCount());
			preparedStatement.setInt(3, configurationForm.getSortOrder());
			preparedStatement.setInt(4, practiceID);
			preparedStatement.setString(5, ActivityStatus.ACTIVE);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted frequency into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting frequency into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> retrieveDiagnosisListByID(int diagnosisID, String searchDiagnosis) {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm configurationForm = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveDiagnosisListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_DIAGNOSIS_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveDiagnosisListByIDQuery);

			preparedStatement.setInt(1, diagnosisID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setDiagnosisID(resultSet.getInt("id"));
				configurationForm.setDiagnosis(resultSet.getString("diagnosis"));
				configurationForm.setDiagnosisICD10Code(resultSet.getString("icd10Code"));
				configurationForm.setSearchDiagnosis(searchDiagnosis);

			}
			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving diagnosis list from diagnosis ID due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	public String deleteDiagnosis(int diagnosisID) {
		try {

			connection = getConnection();

			String deleteDiagnosisQuery = QueryMaker.DELETE_CONFIGURATION_DIAGNOSIS;

			preparedStatement = connection.prepareStatement(deleteDiagnosisQuery);

			preparedStatement.setInt(1, diagnosisID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Diagnosis deleted successfully..");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting diagnosis from table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateDiagnosis(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String updateDiagnosisQuery = QueryMaker.UPDATE_CONFIGURATION_DIAGNOSIS;

			preparedStatement = connection.prepareStatement(updateDiagnosisQuery);

			preparedStatement.setString(1, configurationForm.getDiagnosis());
			preparedStatement.setString(2, configurationForm.getDiagnosisICD10Code());
			preparedStatement.setInt(3, configurationForm.getDiagnosisID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated diagnosis into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating diagnosis into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> retrieveFrequencyListByID(int frequencyID, String searchFrequency) {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm configurationForm = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveFrequencyListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_FREQUENCY_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveFrequencyListByIDQuery);

			preparedStatement.setInt(1, frequencyID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setFrequencyID(resultSet.getInt("id"));
				configurationForm.setFrequency(resultSet.getString("frequencyValues"));
				configurationForm.setFrequencyCount(resultSet.getDouble("count"));
				configurationForm.setSortOrder(resultSet.getInt("sortOrder"));
				configurationForm.setSearchFrequency(searchFrequency);

			}
			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving frequency list from frequency ID due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	public String deleteFrequency(int frequencyID) {
		try {

			connection = getConnection();

			String deleteFrequencyQuery = QueryMaker.DELETE_CONFIGURATION_FREQUENCY;

			preparedStatement = connection.prepareStatement(deleteFrequencyQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, frequencyID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Frequency deleted successfully..");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating frequency satatus to InActive from table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateFrequency(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String updateFrequencyQuery = QueryMaker.UPDATE_CONFIGURATION_FREQUENCY;

			preparedStatement = connection.prepareStatement(updateFrequencyQuery);

			preparedStatement.setString(1, configurationForm.getFrequency());
			preparedStatement.setDouble(2, configurationForm.getFrequencyCount());
			preparedStatement.setInt(3, configurationForm.getSortOrder());
			preparedStatement.setInt(4, configurationForm.getFrequencyID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated frequency into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating frequency into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> retrieveRefDocListByID(int refDocID, String searchRefDoc) {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm configurationForm = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrievePresriptionListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_REF_DOC_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrievePresriptionListByIDQuery);

			preparedStatement.setInt(1, refDocID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setReferringDoctID(resultSet.getInt("id"));
				configurationForm.setReferringDoctName(resultSet.getString("doctorName"));
				configurationForm.setReferringDoctSpecialisation(resultSet.getString("specialization"));
				configurationForm.setReferringDoctClinicName(resultSet.getString("clinicName"));
				configurationForm.setReferringDoctClinicAddres(resultSet.getString("clinicAddress"));
				configurationForm.setReferringDoctPhone(resultSet.getString("phone"));
				configurationForm.setReferringDoctEmail(resultSet.getString("email"));
				configurationForm.setSearchRefDoctor(searchRefDoc);

			}
			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving Referring doctor list from prescription ID due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	public String deleteRefDoc(int refDocID) {
		try {

			connection = getConnection();

			String deleteRefDocQuery = QueryMaker.DELETE_CONFIGURATION_REF_DOC;

			preparedStatement = connection.prepareStatement(deleteRefDocQuery);

			preparedStatement.setInt(1, refDocID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Referall doctor deleted successfully..");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting Referall doctor from table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateRefDoc(ConfigurationForm configurationForm) {

		try {
			connection = getConnection();

			String updateRefDocQuery = QueryMaker.UPDATE_CONFIGURATION_REF_DOC;

			preparedStatement = connection.prepareStatement(updateRefDocQuery);

			preparedStatement.setString(1, configurationForm.getReferringDoctName());
			preparedStatement.setString(2, configurationForm.getReferringDoctSpecialisation());
			preparedStatement.setString(3, configurationForm.getReferringDoctClinicName());
			preparedStatement.setString(4, configurationForm.getReferringDoctClinicAddres());
			preparedStatement.setString(5, configurationForm.getReferringDoctPhone());
			preparedStatement.setString(6, configurationForm.getReferringDoctEmail());
			preparedStatement.setInt(7, configurationForm.getReferringDoctID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated referring doctor into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating referring doctor into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;

	}

	public List<ConfigurationForm> searchDiagnosesList(String searchDiagnoses) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String searchDiagnosesListQuery = QueryMaker.SEARCH_DIAGNOSIS_LIST;

			preparedStatement = connection.prepareStatement(searchDiagnosesListQuery);

			if (searchDiagnoses.contains(" ")) {
				searchDiagnoses = searchDiagnoses.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchDiagnoses + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setDiagnosisID(resultSet.getInt("id"));
				configurationForm.setDiagnosisICD10Code((resultSet.getString("icd10Code")));
				configurationForm.setDiagnosis(resultSet.getString("diagnosis"));
				configurationForm.setSearchDiagnosis(searchDiagnoses);

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching diagnosis list from table due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> searchFrequencyList(String searchFrequency, int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String searchFrequencyListQuery = QueryMaker.SEARCH_FREQUENCY_LIST;

			preparedStatement = connection.prepareStatement(searchFrequencyListQuery);

			if (searchFrequency.contains(" ")) {
				searchFrequency = searchFrequency.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchFrequency + "%");

			preparedStatement.setInt(2, practiceID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setFrequencyID(resultSet.getInt("id"));
				configurationForm.setFrequency((resultSet.getString("frequencyValues")));
				configurationForm.setFrequencyCount(resultSet.getDouble("count"));
				configurationForm.setSortOrder(resultSet.getInt("sortOrder"));
				configurationForm.setSearchFrequency(searchFrequency);

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching frequency list from table due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> searchreferringDoctorList(String searchRefDcotor) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String searchreferringDoctorListQuery = QueryMaker.SEARCH_REF_DOCTOR_LIST;

			preparedStatement = connection.prepareStatement(searchreferringDoctorListQuery);

			if (searchRefDcotor.contains(" ")) {
				searchRefDcotor = searchRefDcotor.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchRefDcotor + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setReferringDoctID(resultSet.getInt("id"));
				configurationForm.setReferringDoctName((resultSet.getString("doctorName")));
				configurationForm.setReferringDoctSpecialisation(resultSet.getString("specialization"));
				configurationForm.setReferringDoctClinicName(resultSet.getString("clinicName"));
				configurationForm.setReferringDoctClinicAddres(resultSet.getString("clinicAddress"));
				configurationForm.setReferringDoctPhone(resultSet.getString("phone"));
				configurationForm.setReferringDoctEmail(resultSet.getString("email"));
				configurationForm.setSearchRefDoctor(searchRefDcotor);

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching referring doctor list from table due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> searchVisitTypeList(String searchVisitType, int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String searchVisitTypeListQuery = QueryMaker.SEARCH_VISIT_TYPE_LIST;

			preparedStatement = connection.prepareStatement(searchVisitTypeListQuery);

			if (searchVisitType.contains(" ")) {
				searchVisitType = searchVisitType.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchVisitType + "%");
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setVisitTypeID(resultSet.getInt("id"));
				configurationForm.setVisitTypeName((resultSet.getString("name")));
				configurationForm.setVisitTypeDuration(resultSet.getString("visitDuration"));
				configurationForm.setSearchVisitTypeName(searchVisitType);

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching visit type list from table due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> retrieveExistingVisitTypeList(int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveExistingVisitTypeListQuery = QueryMaker.RETRIEVE_ALL_VISIT_TYPE_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingVisitTypeListQuery);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setVisitTypeID(resultSet.getInt("id"));
				configurationForm.setVisitTypeName((resultSet.getString("name")));
				configurationForm.setVisitTypeDuration(resultSet.getString("visitDuration"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching visit type list from table due to:::" + exception.getMessage());
		}
		return list;

	}

	public String insertVisitType(ConfigurationForm configurationForm, int practiceID) {
		try {
			connection = getConnection();

			String insertVisitTypeQuery = QueryMaker.INSERT_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(insertVisitTypeQuery);

			preparedStatement.setString(1, configurationForm.getVisitTypeName());
			preparedStatement.setString(2, configurationForm.getVisitTypeDuration());
			preparedStatement.setInt(3, practiceID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted visit type into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting visit type into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> retrieveVisitTypeListByID(int visitTypeID, String searchVisitTypeName) {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm configurationForm = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveVisitTypeListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_VISIT_TYPE_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveVisitTypeListByIDQuery);

			preparedStatement.setInt(1, visitTypeID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setVisitTypeID(resultSet.getInt("id"));
				configurationForm.setVisitTypeName((resultSet.getString("name")));
				configurationForm.setVisitTypeDuration(resultSet.getString("visitDuration"));
				configurationForm.setSearchVisitTypeName(searchVisitTypeName);

			}
			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving visit type list from visit type ID due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	public String updateVisitType(ConfigurationForm configurationForm, int practiceID) {
		try {
			connection = getConnection();

			String updateVisitTypeQuery = QueryMaker.UPDATE_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(updateVisitTypeQuery);

			preparedStatement.setString(1, configurationForm.getVisitTypeName());
			preparedStatement.setString(2, configurationForm.getVisitTypeDuration());
			preparedStatement.setInt(3, practiceID);
			preparedStatement.setInt(4, configurationForm.getVisitTypeID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully udpated visit type into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while udpating visit type into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String deletevisitType(int visitTypeID) {
		try {

			connection = getConnection();

			String deletevisitTypeQuery = QueryMaker.DELETE_CONFIGURATION_VISIT_TYPE;

			preparedStatement = connection.prepareStatement(deletevisitTypeQuery);

			preparedStatement.setInt(1, visitTypeID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Visit type deleted successfully..");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting visit type from table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> searchInstructionList(String searchInstruction) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String searchInstructionListQuery = QueryMaker.SEARCH_INSTRUCTION_LIST;

			preparedStatement = connection.prepareStatement(searchInstructionListQuery);

			if (searchInstruction.contains(" ")) {
				searchInstruction = searchInstruction.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchInstruction + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setInstructionID(resultSet.getInt("id"));
				configurationForm.setInstruction(resultSet.getString("instructions"));
				configurationForm.setSearchInstructionName(searchInstruction);

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching instruction list from table due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> retrieveExistingInstructionsList() {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveExistingInstructionsListQuery = QueryMaker.RETRIEVE_ALL_INSTRUCTION_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingInstructionsListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setInstructionID(resultSet.getInt("id"));
				configurationForm.setInstruction(resultSet.getString("instructions"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving all instruction list from table due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	public String insertInstruction(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String insertInstructionQuery = QueryMaker.INSERT_INSTRUCTION;

			preparedStatement = connection.prepareStatement(insertInstructionQuery);

			preparedStatement.setString(1, configurationForm.getInstruction());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted instruction into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting instruction into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public String updateInstruction(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String updateInstructionQuery = QueryMaker.UPDATE_INSTRUCTION;

			preparedStatement = connection.prepareStatement(updateInstructionQuery);

			preparedStatement.setString(1, configurationForm.getInstruction());
			preparedStatement.setInt(2, configurationForm.getInstructionID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated instruction into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating instruction into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> retrieveInstructionListByID(int instructionID, String searchInstruction) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = new ConfigurationForm();
		try {
			connection = getConnection();

			String retrieveInstructionListByIDQuery = QueryMaker.RETRIEVE_INSTRUCTION_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveInstructionListByIDQuery);

			preparedStatement.setInt(1, instructionID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setInstructionID(resultSet.getInt("id"));
				configurationForm.setInstruction(resultSet.getString("instructions"));
				configurationForm.setSearchInstructionName(searchInstruction);
			}

			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving instruction list by instruction ID from table due to:::"
							+ exception.getMessage());
		}
		return list;

	}

	public String deleteInstruction(int instructionID) {
		try {

			connection = getConnection();

			String deleteInstructionQuery = QueryMaker.DELETE_CONFIGURATION_INSTRUCTION;

			preparedStatement = connection.prepareStatement(deleteInstructionQuery);

			preparedStatement.setInt(1, instructionID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Instruction deleted successfully..");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while deleting instruction from table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public HashMap<Integer, String> retrieveCategoryList() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveCategoryListQuery = QueryMaker.RETRIEVE_CATEGORY_LIST;

			preparedStatement = connection.prepareStatement(retrieveCategoryListQuery);

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

	public boolean verifyInstructionAlreadyExists(String instruction) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyInstructionAlreadyExistsQuery = QueryMaker.VERIFY_Instructions_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyInstructionAlreadyExistsQuery);

			preparedStatement.setString(1, instruction);

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

	public boolean verifyEditInstructionsAlreadyExists(String instruction, int instructionID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditInstructionsAlreadyExistsQuery = QueryMaker.VERIFY_EDit_Instructions_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditInstructionsAlreadyExistsQuery);

			preparedStatement.setString(1, instruction);
			preparedStatement.setInt(2, instructionID);

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

	public boolean verifyFrequencyDetailsAlreadyExists(String frequency, int sortOrder, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyFrequencyDetailsAlreadyExistsQuery = QueryMaker.VERIFY_Frequency_Details_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyFrequencyDetailsAlreadyExistsQuery);

			preparedStatement.setString(1, frequency);
			preparedStatement.setInt(2, sortOrder);
			preparedStatement.setInt(3, practiceID);
			preparedStatement.setString(4, ActivityStatus.ACTIVE);

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

	public boolean verifyEditFrequencyAlreadyExists(String frequency, int frequencyID, int sortOrder, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditFrequencyAlreadyExistsQuery = QueryMaker.VERIFY_EDit_Frequency_Details_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditFrequencyAlreadyExistsQuery);

			preparedStatement.setString(1, frequency);
			preparedStatement.setInt(2, sortOrder);
			preparedStatement.setInt(3, practiceID);
			preparedStatement.setInt(4, frequencyID);

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

	public HashMap<String, String> retrieveSMSURLDetailsByPracticeID(int practiceID) {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveSMSURLDetailsByPracticeIDQuery = QueryMaker.RETRIEVE_SMS_URL_Details_BY_ID;

			preparedStatement1 = connection.prepareStatement(retrieveSMSURLDetailsByPracticeIDQuery);

			preparedStatement1.setInt(1, practiceID);

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				map.put("smsUsername", resultSet1.getString("smsUsername"));
				map.put("smsPassword", resultSet1.getString("smsPassword"));
				map.put("smsURL", resultSet1.getString("smsURL"));
				map.put("smsSenderID", resultSet1.getString("smsSenderID"));
				map.put("smsApiKey", resultSet1.getString("smsApiKey"));

			}

			resultSet1.close();
			preparedStatement1.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

		}

		return map;
	}

	public List<ConfigurationForm> searchTestList(String searchTest) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String searchTestListQuery = QueryMaker.SEARCH_Test_LIST;

			preparedStatement = connection.prepareStatement(searchTestListQuery);

			if (searchTest.contains(" ")) {
				searchTest = searchTest.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchTest + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setTestID(resultSet.getInt("id"));
				configurationForm.setTest(resultSet.getString("test"));
				configurationForm.setRate(resultSet.getDouble("rate"));
				configurationForm.setPanel(resultSet.getString("panel"));
				configurationForm.setNormalValues(resultSet.getString("normalValues"));
				configurationForm.setGroupName(resultSet.getString("groupName"));
				configurationForm.setSearchTest(searchTest);

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching test details list from table due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> retrieveExistingTestsList() {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveExistingTestsListQuery = QueryMaker.RETRIEVE_Test_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingTestsListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setTestID(resultSet.getInt("id"));
				configurationForm.setTest(resultSet.getString("test"));
				configurationForm.setRate(resultSet.getDouble("rate"));
				configurationForm.setPanel(resultSet.getString("panel"));
				configurationForm.setNormalValues(resultSet.getString("normalValues"));
				configurationForm.setGroupName(resultSet.getString("groupName"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing test details list from table due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	public List<ConfigurationForm> retrieveTestDetailsListByID(int testID, String searchTest) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();
		ConfigurationForm configurationForm = new ConfigurationForm();

		try {

			connection = getConnection();

			String retrieveTestDetailsListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Test_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveTestDetailsListByIDQuery);

			preparedStatement.setInt(1, testID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setTestID(resultSet.getInt("id"));
				configurationForm.setTest(resultSet.getString("test"));
				configurationForm.setRate(resultSet.getDouble("rate"));
				configurationForm.setPanel(resultSet.getString("panel"));
				configurationForm.setNormalValues(resultSet.getString("normalValues"));
				configurationForm.setGroupName(resultSet.getString("groupName"));
				configurationForm.setSearchTest(searchTest);

			}
			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving test details list from test ID due to:::"
					+ exception.getMessage());
		}
		return list;
	}

	public boolean verifyTestAlreadyExists(String test) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyTestAlreadyExistsQuery = QueryMaker.VERIFY_Test_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyTestAlreadyExistsQuery);

			preparedStatement.setString(1, test);

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

	public String insertTestDetails(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String insertTestDetailsQuery = QueryMaker.INSERT_TEST_DETAILS;

			preparedStatement = connection.prepareStatement(insertTestDetailsQuery);

			preparedStatement.setString(1, configurationForm.getTest());
			preparedStatement.setDouble(2, configurationForm.getRate());
			preparedStatement.setString(3, configurationForm.getPanel());
			preparedStatement.setString(4, configurationForm.getNormalValues());
			preparedStatement.setString(5, configurationForm.getGroupName());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted test details into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while inserting testz into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public boolean verifyEditTestAlreadyExists(String test, int testID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditTestAlreadyExistsQuery = QueryMaker.VERIFY_Edit_Test_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditTestAlreadyExistsQuery);

			preparedStatement.setString(1, test);
			preparedStatement.setInt(2, testID);

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

	public String updateTestsDetails(ConfigurationForm configurationForm) {

		try {
			connection = getConnection();

			String updateTestsDetailsQuery = QueryMaker.UPDATE_CONFIGURATION_Tests;

			preparedStatement = connection.prepareStatement(updateTestsDetailsQuery);

			preparedStatement.setString(1, configurationForm.getTest());
			preparedStatement.setDouble(2, configurationForm.getRate());
			preparedStatement.setString(3, configurationForm.getPanel());
			preparedStatement.setString(4, configurationForm.getNormalValues());
			preparedStatement.setString(5, configurationForm.getGroupName());
			preparedStatement.setInt(6, configurationForm.getTestID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated test details into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while updating test details into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public boolean verifySMSTemplateNameAlreadyExists(String smsTitle) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifySMSTemplateNameAlreadyExistsQuery = QueryMaker.VERIFY_SMSTemplate_Name_Already_Exists;

			preparedStatement = connection.prepareStatement(verifySMSTemplateNameAlreadyExistsQuery);

			preparedStatement.setString(1, smsTitle);

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

	public String insertSMSTemplate(String smsTemplateTitle, String smsTemplateText) {

		try {

			connection = getConnection();

			String insertSMSTemplateQuery = QueryMaker.INSERT_SMS_TEMPLATE;

			preparedStatement = connection.prepareStatement(insertSMSTemplateQuery);

			preparedStatement.setString(1, smsTemplateTitle);
			preparedStatement.setString(2, smsTemplateText);

			preparedStatement.execute();

			status = "success";

			System.out.println("SMS Template added successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting SMS Template details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public List<ConfigurationForm> retrieveAllSMSTemplates() {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm form = null;
		try {
			connection = getConnection();

			String retrieveAllSMSTemplatesQuery = QueryMaker.RETRIEVE_ALL_SMS_TEMPLATES;

			preparedStatement = connection.prepareStatement(retrieveAllSMSTemplatesQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new ConfigurationForm();

				form.setSMSTemplateID(resultSet.getInt("id"));
				form.setSMSTemplateTitle((resultSet.getString("title")));
				form.setSMSTemplateText(resultSet.getString("text"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving all sms templates list due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> searchSMSTemplate(String searchCategoryName) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm form = null;
		try {
			connection = getConnection();

			String searchSMSTemplateQuery = QueryMaker.SEARCH_SMS_TEMPLATE;

			preparedStatement = connection.prepareStatement(searchSMSTemplateQuery);

			if (searchCategoryName.contains(" ")) {
				searchCategoryName = searchCategoryName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchCategoryName + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new ConfigurationForm();

				form.setSMSTemplateID(resultSet.getInt("id"));
				form.setSMSTemplateTitle(resultSet.getString("title"));
				form.setSMSTemplateText(resultSet.getString("text"));
				form.setSearchSMSTemplate(searchCategoryName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while searching sms template list due to:::" + exception.getMessage());
		}
		return list;

	}

	@Override
	public boolean verifyEditSMSTemplateNameAlreadyExists(String smsTemplateTitle, int smsTemplateID) {
		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditSMSTemplateNameAlreadyExistsQuery = QueryMaker.VERIFY_EDit_SMS_TEMPLATE_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditSMSTemplateNameAlreadyExistsQuery);

			preparedStatement.setString(1, smsTemplateTitle);
			preparedStatement.setInt(2, smsTemplateID);

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

	@Override
	public String updateSMSTemplate(String smsTemplateTitle, int smsTemplateID, String smsTemplateText) {
		try {

			connection = getConnection();

			String updateSMSTemplateQuery = QueryMaker.UPDATE_SMS_TEMPLATE;

			preparedStatement = connection.prepareStatement(updateSMSTemplateQuery);

			preparedStatement.setString(1, smsTemplateTitle);
			preparedStatement.setString(2, smsTemplateText);
			preparedStatement.setInt(3, smsTemplateID);

			preparedStatement.execute();

			status = "success";

			System.out.println("SMS Template updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating SMS Template due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public List<ConfigurationForm> retrieveSMSTemplateByID(int smsTemplateID, String searchSMSTemplate) {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm form = null;
		try {
			connection = getConnection();

			String retrieveSMSTemplateByIDQuery = QueryMaker.RETRIEVE_SMS_TEMPLATE_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveSMSTemplateByIDQuery);

			preparedStatement.setInt(1, smsTemplateID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new ConfigurationForm();

				form.setSMSTemplateID(resultSet.getInt("id"));
				form.setSMSTemplateTitle((resultSet.getString("title")));
				form.setSMSTemplateText(resultSet.getString("text"));

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving sms template by ID due to:::" + exception.getMessage());
		}
		return list;
	}

	public String deleteSMSTemplate(int SMSTemplateID) {

		try {

			connection = getConnection();

			String deleteSMSTemplateQuery = QueryMaker.DELETE_SMS_TEMPLATE;

			preparedStatement = connection.prepareStatement(deleteSMSTemplateQuery);

			preparedStatement.setInt(1, SMSTemplateID);

			preparedStatement.execute();

			status = "success";

			System.out.println("SMS Template delete successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting SMS Template due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public HashMap<String, String> retrieveSMSTemplateTitleList() {

		HashMap<String, String> map = new HashMap<String, String>();

		try {

			connection = getConnection();

			String retrieveSMSTemplateTitleListQuery = QueryMaker.RETRIEVE_ALL_SMS_TEMPLATES;

			preparedStatement = connection.prepareStatement(retrieveSMSTemplateTitleListQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				map.put(resultSet.getString("text"), resultSet.getString("title"));
			}
			connection.close();
		} catch (Exception exception) {

			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving SMS Template details due to:::" + exception.getMessage());

			status = "error";
		}

		return map;
	}

	@Override
	public List<ConfigurationForm> searchLabTestList(String searchLabTest, int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String searchLabTestListQuery = QueryMaker.SEARCH_LAB_TEST_LIST;

			preparedStatement = connection.prepareStatement(searchLabTestListQuery);

			if (searchLabTest.contains(" ")) {
				searchLabTest = searchLabTest.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchLabTest + "%");
			// preparedStatement.setString(2, "%" + searchLabTest + "%");
			/* preparedStatement.setString(3, "%" + searchLabTest + "%"); */
			preparedStatement.setInt(2, practiceID);

			preparedStatement.setString(3, ActivityStatus.ACTIVE);

			resultSet = preparedStatement.executeQuery();

			int srNo = 1;

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setLabTestID(resultSet.getInt("id"));
				/* configurationForm.setPanel(resultSet.getString("panel")); */
				configurationForm.setTest(resultSet.getString("test"));
				configurationForm.setNormalValues(resultSet.getString("normalValues"));
				configurationForm.setSearchLabTestsName(searchLabTest);
				configurationForm.setSrNo(srNo);

				srNo++;

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return list;

	}

	@Override
	public List<ConfigurationForm> retrieveExistingLabTestsList(int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveExistingLabTestsListQuery = QueryMaker.RETRIEVE_ALL_LAB_TEST_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingLabTestsListQuery);

			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			int srNo = 1;

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setLabTestID(resultSet.getInt("id"));
				/* configurationForm.setPanel(resultSet.getString("panel")); */
				configurationForm.setTest(resultSet.getString("test"));
				configurationForm.setNormalValues(resultSet.getString("normalValues"));
				configurationForm.setSrNo(srNo);

				srNo++;

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return list;

	}

	@Override
	public List<ConfigurationForm> retrieveLabTestListByID(int labTestID, String searchLabTestName) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveLabTestListByIDQuery = QueryMaker.RETRIEVE_CONFIGURATION_Test_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveLabTestListByIDQuery);

			preparedStatement.setInt(1, labTestID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setLabTestID(resultSet.getInt("id"));
				/* configurationForm.setPanel(resultSet.getString("panel")); */
				configurationForm.setTest(resultSet.getString("test"));
				configurationForm.setNormalValues(resultSet.getString("normalValues"));
				configurationForm.setWomenNormalRange(resultSet.getString("normalValuesWomen"));
				configurationForm.setChildNormalRange(resultSet.getString("normalValuesChild"));
				configurationForm.setSubgroup(resultSet.getString("subgroup"));
				configurationForm.setRemarks(resultSet.getString("remarks"));
				configurationForm.setIsExcludeNormalValues(resultSet.getInt("isExcludeNormalValues"));
				configurationForm.setSearchLabTestsName(searchLabTestName);
				configurationForm.setShowNormalRangeDesc(resultSet.getInt("showNormalRangeDesc"));
				configurationForm.setNormalRangeDesc(resultSet.getString("normalRangeDesc"));
				configurationForm.setIsOutsourced(resultSet.getInt("isOutsourced"));
				configurationForm.setTestType(resultSet.getString("type"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return list;

	}

	@Override
	public List<ConfigurationForm> retrieveDefaultValueList(int labTestID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			connection = getConnection();

			String retrieveExistingDefaultValueListQuery = QueryMaker.RETRIEVE_ALL_DefaultValue_LIST;

			preparedStatement = connection.prepareStatement(retrieveExistingDefaultValueListQuery);

			preparedStatement.setInt(1, labTestID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setDefaultValueID(resultSet.getInt("id"));
				configurationForm.setDefaultValue(resultSet.getString("defaultValue"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return list;

	}

	@Override
	public String deleteLabTest(int labTestID) {
		try {
			connection = getConnection();

			String deleteLabTestQuery = QueryMaker.DELETE_CONFIGURATION_LAB_TEST;

			preparedStatement = connection.prepareStatement(deleteLabTestQuery);

			preparedStatement.setString(1, ActivityStatus.INACTIVE);
			preparedStatement.setInt(2, labTestID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated lab test status to inactive");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	@Override
	public boolean verifyLabTestAlreadyExists(String test, String subGroup, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyLabTestAlreadyExistsQuery = QueryMaker.VERIFY_Lab_Test_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyLabTestAlreadyExistsQuery);

			// preparedStatement.setString(1, panel);
			preparedStatement.setString(1, test);
			preparedStatement.setString(2, subGroup);
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

	@Override
	public String insertLabTest(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String insertLabTestQuery = QueryMaker.INSERT_LAB_TEST;

			preparedStatement = connection.prepareStatement(insertLabTestQuery);

			/* preparedStatement.setString(1, configurationForm.getPanel()); */
			preparedStatement.setString(1, configurationForm.getTest());
			preparedStatement.setString(2, configurationForm.getNormalValues());
			preparedStatement.setString(3, ActivityStatus.ACTIVE);
			preparedStatement.setString(4, configurationForm.getWomenNormalRange());
			preparedStatement.setString(5, configurationForm.getChildNormalRange());
			preparedStatement.setString(6, configurationForm.getSubgroup());
			preparedStatement.setString(7, configurationForm.getRemarks());
			preparedStatement.setInt(8, configurationForm.getIsExcludeNormalValues());
			preparedStatement.setInt(9, configurationForm.getShowNormalRangeDesc());
			preparedStatement.setString(10, configurationForm.getNormalRangeDesc());
			preparedStatement.setInt(11, configurationForm.getPracticeID());
			preparedStatement.setInt(12, configurationForm.getIsOutsourced());
			preparedStatement.setString(13, configurationForm.getTestType());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted lab test into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	@Override
	public int retrieveLabTestID(String test) {
		int LabTestID = 0;
		try {
			connection = getConnection();

			String retrieveLabTestIDQuery = QueryMaker.RETRIEVE_LABTEST_ID;

			preparedStatement = connection.prepareStatement(retrieveLabTestIDQuery);

			preparedStatement.setString(1, test);
			/* preparedStatement.setString(2, panel); */

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				LabTestID = resultSet.getInt("id");
			}

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving LabTest id from PVLabTests table due to:::"
					+ exception.getMessage());
			status = "error";
		}
		return LabTestID;
	}

	@Override
	public String insertLabTestDefaultValues(String value, int labTestID) {
		try {
			connection = getConnection();

			String insertLabTestDefaultValuesQuery = QueryMaker.INSERT_Default_Value;

			preparedStatement = connection.prepareStatement(insertLabTestDefaultValuesQuery);

			preparedStatement.setString(1, value);
			preparedStatement.setInt(2, labTestID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted default value for lab test into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	@Override
	public String updateLabTest(ConfigurationForm configurationForm) {
		try {
			connection = getConnection();

			String updateLabTestQuery = QueryMaker.UPDATE_LAB_TEST;

			preparedStatement = connection.prepareStatement(updateLabTestQuery);

			/* preparedStatement.setString(1, configurationForm.getPanel()); */
			preparedStatement.setString(1, configurationForm.getTest());
			preparedStatement.setString(2, configurationForm.getNormalValues());
			preparedStatement.setString(3, configurationForm.getWomenNormalRange());
			preparedStatement.setString(4, configurationForm.getChildNormalRange());
			preparedStatement.setString(5, configurationForm.getSubgroup());
			preparedStatement.setString(6, configurationForm.getRemarks());
			preparedStatement.setInt(7, configurationForm.getIsExcludeNormalValues());
			preparedStatement.setInt(8, configurationForm.getShowNormalRangeDesc());
			preparedStatement.setString(9, configurationForm.getNormalRangeDesc());
			preparedStatement.setInt(10, configurationForm.getIsOutsourced());
			preparedStatement.setString(11, configurationForm.getTestType());
			preparedStatement.setInt(12, configurationForm.getLabTestID());

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated lab test into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	@Override
	public boolean verifyEditLabTestAlreadyExists(String test, String groupName, String subGroup, int practiceID,
			int labTestID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditLabTestAlreadyExistsQuery = QueryMaker.VERIFY_EDit_Lab_Test_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditLabTestAlreadyExistsQuery);

			/* preparedStatement.setString(1, panel); */
			preparedStatement.setString(1, test);
			preparedStatement.setString(2, subGroup);
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

	@Override
	public String updateLabTestDefaultValues(String editValue, int editValueID) {
		try {
			connection = getConnection();

			String updateLabTestDefaultValuesQuery = QueryMaker.UPDATE_Default_Value;

			preparedStatement = connection.prepareStatement(updateLabTestDefaultValuesQuery);

			preparedStatement.setString(1, editValue);
			preparedStatement.setInt(2, editValueID);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully updated default value for lab test into table");

			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		}
		return status;
	}

	public boolean verifyReferringDocExist(String doctorName, int practiceID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyReferringDocExistQuery = QueryMaker.VERIFY_REF_DOCTOR_EXISTS;

			preparedStatement = connection.prepareStatement(verifyReferringDocExistQuery);

			preparedStatement.setString(1, doctorName);
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

	public boolean verifyEditReferringDocExist(String doctorName, int doctID) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyReferringDocExistQuery = QueryMaker.VERIFY_REF_DOCTOR_EXISTS1;

			preparedStatement = connection.prepareStatement(verifyReferringDocExistQuery);

			preparedStatement.setString(1, doctorName);
			preparedStatement.setInt(2, doctID);

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

	public boolean verifyTemplateNameAlreadyExists(String tempReport) {

		boolean check = false;

		try {

			connection = getConnection();

			String verifyTemplateNameAlreadyExistsQuery = QueryMaker.VERIFY_TempName_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyTemplateNameAlreadyExistsQuery);

			preparedStatement.setString(1, tempReport);

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

	public String insertTemplate(String tempTempReportType, String TempName, String TemplateText) {

		try {

			connection = getConnection();

			String insertTemplateQuery = QueryMaker.INSERT_TEMPLATE;

			preparedStatement = connection.prepareStatement(insertTemplateQuery);

			preparedStatement.setString(1, tempTempReportType);
			preparedStatement.setString(2, TempName);
			preparedStatement.setString(3, TemplateText);

			preparedStatement.execute();

			status = "success";

			System.out.println(" Template added successfully." + TemplateText);

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out
					.println("Exception occured while inserting  Template details due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	public List<ConfigurationForm> retrieveAllTemplates() {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm form = null;
		try {
			connection = getConnection();

			String retrieveAllTemplatesQuery = QueryMaker.RETRIEVE_ALL_TEMPLATES;

			preparedStatement = connection.prepareStatement(retrieveAllTemplatesQuery);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new ConfigurationForm();

				form.setTemplateID(resultSet.getInt("id"));
				form.setTempReportType(resultSet.getString("TempReportType"));
				form.setTempName(resultSet.getString("TempName"));
				form.setTemplate(resultSet.getString("TemplateText"));

				list.add(form);

				System.out.println("*************" + resultSet.getInt("id"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving all templates list due to:::" + exception.getMessage());
		}
		return list;

	}

	public List<ConfigurationForm> searchTemplate(String searchCategoryName) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm form = null;
		try {
			connection = getConnection();

			String searchTemplateQuery = QueryMaker.SEARCH_TEMPLATE;

			preparedStatement = connection.prepareStatement(searchTemplateQuery);

			if (searchCategoryName.contains(" ")) {
				searchCategoryName = searchCategoryName.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchCategoryName + "%");
			preparedStatement.setString(2, "%" + searchCategoryName + "%");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new ConfigurationForm();

				form.setTemplateID(resultSet.getInt("id"));
				form.setTempReportType(resultSet.getString("TempReportType"));
				form.setTempName(resultSet.getString("TempName"));
				form.setTemplate(resultSet.getString("TemplateText"));
				form.setSearchTemplate(searchCategoryName);

				list.add(form);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while searching template list due to:::" + exception.getMessage());
		}
		return list;

	}

	@Override
	public boolean verifyEditTemplateNameAlreadyExists(String tempTempReportType, int templateID) {
		boolean check = false;

		try {

			connection = getConnection();

			String verifyEditTemplateNameAlreadyExistsQuery = QueryMaker.VERIFY_EDit_TEMPLATE_Already_Exists;

			preparedStatement = connection.prepareStatement(verifyEditTemplateNameAlreadyExistsQuery);

			preparedStatement.setString(1, tempTempReportType);
			preparedStatement.setInt(2, templateID);

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

	@Override
	public String updateTemplate(String tempTempReportType, int templateID, String tempName, String template) {
		try {

			connection = getConnection();

			String updateTemplateQuery = QueryMaker.UPDATE_TEMPLATE;

			preparedStatement = connection.prepareStatement(updateTemplateQuery);

			preparedStatement.setString(1, tempTempReportType);
			preparedStatement.setString(2, tempName);
			preparedStatement.setString(3, template);

			preparedStatement.setInt(4, templateID);

			preparedStatement.execute();

			status = "success";

			System.out.println(" Template updated successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while updating SMS Template due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public List<ConfigurationForm> retrieveTemplateByID(int templateID, String searchTemplate) {
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm form = null;
		try {
			connection = getConnection();
			// templateID=7;
			System.out.println("Final-----------------------" + templateID);
			String retrieveTemplateByIDQuery = QueryMaker.RETRIEVE_TEMPLATE_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveTemplateByIDQuery);

			preparedStatement.setInt(1, templateID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				form = new ConfigurationForm();

				form.setTemplateID(resultSet.getInt("id"));
				form.setTempReportType(resultSet.getString("TempReportType"));
				form.setTempName(resultSet.getString("TempName"));
				form.setTemplate(resultSet.getString("TemplateText"));

				list.add(form);

				System.out.println("Final-------------------sdf----:" + resultSet.getString("TemplateText"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving template by ID due to:::" + exception.getMessage());
		}
		return list;
	}

	public String deleteTemplate(int TemplateID) {

		try {

			connection = getConnection();

			String deleteTemplateQuery = QueryMaker.DELETE_TEMPLATE;

			preparedStatement = connection.prepareStatement(deleteTemplateQuery);

			preparedStatement.setInt(1, TemplateID);

			preparedStatement.execute();

			status = "success";

			System.out.println(" Template delete successfully.");

			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while deleting Template due to:::" + exception.getMessage());

			status = "error";
		}

		return status;
	}

	@Override
	public JSONObject RetrieveTemplate(int templateID) {
		// TODO Auto-generated method stub
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;
		int check1 = 0;

		try {
			connection = getConnection();

			String RetrieveApptTypeList = QueryMaker.RetrieveTemplate;

			preparedStatement = connection.prepareStatement(RetrieveApptTypeList);

			preparedStatement.setInt(1, templateID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("template", resultSet.getString("TemplateText"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

				resultSet.close();
				preparedStatement.close();

				connection.close();

				return values;
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

			return values;
		}

		return null;
	}

	@Override
	public JSONObject RetrieveAddedReferringDoctor(ConfigurationForm configurationForm) {
		// TODO Auto-generated method stub
		JSONObject values = null;

		JSONArray array = new JSONArray();
		values = new JSONObject();

		JSONObject object = null;
		int check1 = 0;

		try {
			connection = getConnection();

			String RetrieveApptTypeList = QueryMaker.RetrieveAddedReferringDoctor;

			preparedStatement = connection.prepareStatement(RetrieveApptTypeList);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				check1 = 1;

				object = new JSONObject();

				object.put("MSG", "New referring doctor added successfully.");
				object.put("id", resultSet.getInt("id"));
				object.put("doctorName", resultSet.getString("doctorName"));
				object.put("specialization", resultSet.getString("specialization"));
				object.put("clinicName", resultSet.getString("clinicName"));
				object.put("clinicAddress", resultSet.getString("clinicAddress"));
				object.put("phone", resultSet.getString("phone"));
				object.put("email", resultSet.getString("email"));

				object.put("check", check1);

				array.add(object);

				values.put("Release", array);

			}

			resultSet.close();
			preparedStatement.close();

			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			object.put("check", check1);

			array.add(object);

			values.put("Release", array);

		}

		return values;

	}

	@Override
	public HashMap<Integer, String> retrieveAllTests(int practiceID) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveLabListQuery = QueryMaker.RETRIEVE_ALL_LAB_TEST_LIST;

			preparedStatement = connection.prepareStatement(retrieveLabListQuery);
			preparedStatement.setString(1, ActivityStatus.ACTIVE);
			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("test"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	@Override
	public String insertGroupTests(String groupName, double groupRate, String labTests, String groupRemark ,int practiceID) {
		// TODO Auto-generated method stub
		try {
			ConfigurationForm configurationForm = new ConfigurationForm();

			connection = getConnection();

			String insertDiagnosisQuery = QueryMaker.INSERT_GROUP_TESTS;

			preparedStatement = connection.prepareStatement(insertDiagnosisQuery);

			preparedStatement.setString(1, groupName);
			preparedStatement.setDouble(2, groupRate);
			preparedStatement.setInt(3, practiceID);
			preparedStatement.setString(4, labTests);
			preparedStatement.setString(5, groupRemark);

			preparedStatement.execute();

			status = "success";

			System.out.println("Successfully inserted diagnosis into table");

			preparedStatement.close();

			String retrieveGroupID = QueryMaker.RETRIEVE_GROUP_ID_LIST;

			preparedStatement = connection.prepareStatement(retrieveGroupID);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int groupID = resultSet.getInt("id");
				String testID[] = labTests.split(",");

				for (int i = 0; i < testID.length; i++) {
					int testID1 = Integer.parseInt(testID[i].replaceAll("\\s+", ""));

					String insertLabGroupIDQuery = QueryMaker.INSERT_TEST_GROUP_ID_TESTS;

					preparedStatement = connection.prepareStatement(insertLabGroupIDQuery);

					preparedStatement.setInt(1, groupID);
					preparedStatement.setInt(2, testID1);

					preparedStatement.execute();
				}

			}
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while inserting diagnosis into table due to:::" + exception.getMessage());
			status = "error";
		}
		return status;
	}

	public List<ConfigurationForm> retrieveAllGroupTests(int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {

			
			connection = getConnection();

			String retrieveAllGroupTests = QueryMaker.RETRIEVE_GROUP_TEST_LIST;
			String retrieveLabTests = QueryMaker.RETRIEVE_CONFIGURATION_Test_LIST_BY_ID;
			String RetriveTestID = QueryMaker.RETRIEVE_TEST_ID;
			preparedStatement = connection.prepareStatement(retrieveAllGroupTests);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				configurationForm = new ConfigurationForm();

				configurationForm.setGroupID(resultSet.getInt("id"));
				configurationForm.setGroupName(resultSet.getString("groupName"));
				configurationForm.setGroupRate(resultSet.getDouble("groupRate"));
				configurationForm.setGroupRemark(resultSet.getString("groupRemark"));
				preparedStatement2 = connection.prepareStatement(RetriveTestID);

				preparedStatement2.setInt(1, resultSet.getInt("id"));

				resultSet2 = preparedStatement2.executeQuery();

				List<String> testArray = new ArrayList<String>();
				
				while (resultSet2.next()) {
					
					String labTestName = "";
					
					String temp = "";

					preparedStatement1 = connection.prepareStatement(retrieveLabTests);

					preparedStatement1.setInt(1, resultSet2.getInt("testID"));

					resultSet1 = preparedStatement1.executeQuery();

					while (resultSet1.next()) {
						System.out.println("test name is :: " + resultSet1.getString("test"));
						temp = resultSet1.getString("test") + ", " + temp;
					}
					labTestName = temp.substring(0, temp.length() - 2);

					testArray.add(labTestName);

				}

				configurationForm.setLabTest(String.join(",", testArray));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing frequency list from table due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	@Override
	public String insertLabTestRate(int labTestID, double testRate, int practiceID) {
		// TODO Auto-generated method stub
		try {
			connection = getConnection();

			String insertLabTestRateQuery = QueryMaker.INSERT_LAB_TEST_RATE;

			preparedStatement = connection.prepareStatement(insertLabTestRateQuery);

			preparedStatement.setInt(1, labTestID);
			preparedStatement.setDouble(2, testRate);
			preparedStatement.setInt(3, practiceID);

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

	@Override
	public List<ConfigurationForm> searchGroupTest(String searchTest, int practiceID) {
		// TODO Auto-generated method stub

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			String labTestName = "";
			String temp = "";
			connection = getConnection();

			String retrieveAllGroupLabTests = QueryMaker.RETRIEVE_CONFIGURATION_Test_LIST_BY_ID;
			String searchGroupListQuery = QueryMaker.SEARCH_GROUP_LIST;

			preparedStatement = connection.prepareStatement(searchGroupListQuery);

			if (searchTest.contains(" ")) {
				searchTest = searchTest.replace(" ", "%");
			}

			preparedStatement.setString(1, "%" + searchTest + "%");

			preparedStatement.setInt(2, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				configurationForm = new ConfigurationForm();

				configurationForm.setGroupID(resultSet.getInt("id"));
				configurationForm.setGroupName(resultSet.getString("groupName"));
				configurationForm.setGroupRate(resultSet.getDouble("groupRate"));
				configurationForm.setLabTestIDs(resultSet.getString("labTestID"));

				if (resultSet.getString("labTestID") != null) {

					String testID[] = resultSet.getString("labTestID").split(",");

					for (int i = 0; i < testID.length; i++) {
						int testID1 = Integer.parseInt(testID[i].replaceAll("\\s+", ""));

						preparedStatement1 = connection.prepareStatement(retrieveAllGroupLabTests);

						preparedStatement1.setInt(1, testID1);

						resultSet1 = preparedStatement1.executeQuery();
						System.out.println("presp :: " + preparedStatement1);
						while (resultSet1.next()) {
							temp = resultSet1.getString("test") + ", " + temp;
						}
					}

				}

				if (temp != "") {
					labTestName = temp.substring(0, temp.length() - 2);
				}
				configurationForm.setLabTest(labTestName);
				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching frequency list from table due to:::" + exception.getMessage());
		}
		return list;
	}

	@Override
	public List<ConfigurationForm> SearchLabTestRate(String searchTest, int practiceID) {
		// TODO Auto-generated method stub

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {

			connection = getConnection();

			String searchGroupListQuery = QueryMaker.SEARCH_LAB_RATE_LIST;

			preparedStatement = connection.prepareStatement(searchGroupListQuery);

			preparedStatement.setInt(1, practiceID);

			if (searchTest.contains(" ")) {
				searchTest = searchTest.replace(" ", "%");
			}

			preparedStatement.setString(2, "%" + searchTest + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm = new ConfigurationForm();

				configurationForm.setLabTestID(resultSet.getInt("id"));
				configurationForm.setLabTest(resultSet.getString("test"));
				configurationForm.setTestRate(resultSet.getDouble("testRate"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while searching frequency list from table due to:::" + exception.getMessage());
		}
		return list;
	}

	public List<ConfigurationForm> retrieveGroupListByID(int groupID, String searchTest) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = new ConfigurationForm();
		try {
			connection = getConnection();

			String retrieveInstructionListByIDQuery = QueryMaker.RETRIEVE_GROUP_DETAILS_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveInstructionListByIDQuery);

			preparedStatement.setInt(1, groupID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setGroupID(resultSet.getInt("id"));
				configurationForm.setGroupName(resultSet.getString("groupName"));
				configurationForm.setGroupRate(resultSet.getDouble("groupRate"));
				configurationForm.setLabTest(resultSet.getString("testIDs"));
				configurationForm.setGroupRemark(resultSet.getString("groupRemark"));
				configurationForm.setSearchTest(searchTest);
			}

			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving instruction list by instruction ID from table due to:::"
							+ exception.getMessage());
		}
		return list;

	}

	@Override
	public List<ConfigurationForm> retriveAllLabTests(int practiceID) {

		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = null;
		try {
			String labTestName = "";
			String temp = "";
			connection = getConnection();

			String retrieveAllLabTests = QueryMaker.RETRIEVE_LAB_TEST_LIST;

			preparedStatement = connection.prepareStatement(retrieveAllLabTests);

			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				configurationForm = new ConfigurationForm();

				configurationForm.setLabTestID(resultSet.getInt("id"));
				configurationForm.setLabTest(resultSet.getString("testName"));
				configurationForm.setTestRate(resultSet.getDouble("testRate"));

				list.add(configurationForm);
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception occured while retrieving existing frequency list from table due to:::"
					+ exception.getMessage());
		}
		return list;

	}

	@Override
	public List<ConfigurationForm> retrieveLabTestRateListByID(int labTestID, String searchTest) {
		// TODO Auto-generated method stub
		List<ConfigurationForm> list = new ArrayList<ConfigurationForm>();

		ConfigurationForm configurationForm = new ConfigurationForm();
		try {
			connection = getConnection();

			String retrieveLabTestRateListByIDQuery = QueryMaker.RETRIEVE_LAB_TEST_DETAILS_LIST_BY_ID;

			preparedStatement = connection.prepareStatement(retrieveLabTestRateListByIDQuery);

			preparedStatement.setInt(1, labTestID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				configurationForm.setLabTestID(resultSet.getInt("testID"));
				configurationForm.setTestRate(resultSet.getDouble("testRate"));

				configurationForm.setSearchTest(searchTest);
			}

			list.add(configurationForm);

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(
					"Exception occured while retrieving instruction list by instruction ID from table due to:::"
							+ exception.getMessage());
		}
		return list;

	}

	@Override
	public HashMap<Integer, String> retrieveTestsByPracticeID(int practiceID) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		try {

			connection = getConnection();

			String retrieveLabListQuery = QueryMaker.RETRIEVE_Test_LIST;

			preparedStatement = connection.prepareStatement(retrieveLabListQuery);
			preparedStatement.setInt(1, practiceID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				map.put(resultSet.getInt("id"), resultSet.getString("test"));
			}

			resultSet.close();
			preparedStatement.close();

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	@Override
	public String UpdateLabTestRate(int labTestID, double testRate, int practiceID) {

		try {
			connection = getConnection();

			String UPDATELabTestRateQuery = QueryMaker.UPDATE_LAB_TEST_RATE;

			preparedStatement = connection.prepareStatement(UPDATELabTestRateQuery);

			preparedStatement.setDouble(1, testRate);
			preparedStatement.setInt(2, practiceID);
			preparedStatement.setInt(3, labTestID);

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
	
	public JSONObject RetrieveTestTypesAndTemplates() {
	    // Types to query
	    String[] reportTypes = {"X-RAY", "USG", "CT SCAN", "MRI"};

	    // Create data structure
	    List<HashMap<String, HashMap<String, String>>> arrayOfMaps = new ArrayList<>();
	    
	    // Initialize connection and statement
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	        // Establish database connection
	        connection = getConnection();

	        for (String reportType : reportTypes) {
	            // Query to execute
	            String retrieveTemplatesByTypeQuery = QueryMaker.RETRIEVE_TEMPLATE_AND_ID_BY_TYPE;

	            preparedStatement = connection.prepareStatement(retrieveTemplatesByTypeQuery);
	            preparedStatement.setString(1, reportType);

	            // Execute query
	            resultSet = preparedStatement.executeQuery();

	            // Create maps for the current report type
	            HashMap<String, String> innerMap = new HashMap<>();
	            HashMap<String, HashMap<String, String>> outerMap = new HashMap<>();
	            
	            while (resultSet.next()) {
	                String tempName = resultSet.getString("TempName");
	                String id = resultSet.getString("id");
	                innerMap.put(id, tempName);
	            }

	            outerMap.put(reportType, innerMap);  
	            arrayOfMaps.add(outerMap);
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	        // Handle exception (logging or status update)
	    } finally {
	        // Close resources in the reverse order of creation
	        try { if (resultSet != null) resultSet.close(); } catch (Exception e) { e.printStackTrace(); }
	        try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) { e.printStackTrace(); }
	        try { if (connection != null) connection.close(); } catch (Exception e) { e.printStackTrace(); }
	    }

	    // Convert to JSONObject and return
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("data", arrayOfMaps);
	    return jsonObject;
	}
}

