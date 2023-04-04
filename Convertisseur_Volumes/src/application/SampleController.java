package application;//

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.text.DecimalFormat;

public class SampleController implements Initializable{

    @FXML
    private TextField antecedent;

    @FXML
    private TextField result;

    @FXML
    private ComboBox<String> cboAntecedent;

    @FXML
    private ComboBox<String> cboResult;
    
    private ObservableList<String> unitesVolumes = FXCollections.observableArrayList("Millilitres", "Centilitres", "Litres", "cm³", "dm³", "m³", "in³", "ft³");
    private double []unitRelationships = {1, 10, 1000, 1, 1000, 1000000, 16.3871, 28316.8};
    
    private static final DecimalFormat round = new DecimalFormat("0.0000");
    
    private boolean started = false;
    
    
    @FXML
    void checkAndConvert(KeyEvent e) {
    	TextField txt=(TextField)e.getSource();
    	String sourceID = txt.getId();
    	try{
    		if(sourceID.equals("antecedent")) {
    			result.setText(round.format(convert(sourceID, Double.parseDouble(txt.getText()), cboAntecedent.getSelectionModel().getSelectedIndex(), cboResult.getSelectionModel().getSelectedIndex(), unitRelationships)));
    		}else {
    			antecedent.setText(round.format(convert(sourceID, Double.parseDouble(txt.getText()), cboAntecedent.getSelectionModel().getSelectedIndex(), cboResult.getSelectionModel().getSelectedIndex(), unitRelationships)));
    		}
    		started = true;
    	}catch(Exception ex){
    		if(txt.getText().equals("") && started == true) {
    			result.setText("");
    			antecedent.setText("");
    		}else {
    			if(txt.getText().equals("-")) {
    				
    			}else {
    				String num = txt.getText();
    				for(int i = 0; i<num.length(); i++) {
    					if(!Character.isDigit(num.charAt(i))) {
    						String temp = num.substring(0, i);
    						num = temp + num.substring(i+1);
    						i += -1;
    					}
    				}
    				txt.setText(num);
    				Alert alert = new Alert(AlertType.ERROR);
    				alert.setHeaderText("ERREUR");
        			alert.setTitle("Attention");
        			alert.setContentText("Entrée numérique seulement");
        			alert.show();
        			checkAndConvert(e);
    				}
    			}
    		}
    	}
    
    static double convert(String sourceID, double input, int antecedentUnit, int convertedUnit, double []unitsTable) {
    	if(sourceID.equals("antecedent")) {
    		input = input*unitsTable[antecedentUnit];//Convert to ml
    		input = input/unitsTable[convertedUnit];
    		return input;
    	}else {
    		input = input*unitsTable[convertedUnit];//Convert to ml
    		input = input/unitsTable[antecedentUnit];
    		return input;
    	}
    	
    }
    
    @FXML
    void changeUnit(ActionEvent e) {
    	ComboBox<String> cbo = (ComboBox<String>)e.getSource();
    	String sourceID = cbo.getId();
    	//System.out.println(sourceID);
    	try{
    		if(sourceID.equals("cboAntecedent")) {
    			result.setText(round.format(convert("antecedent", Double.parseDouble(antecedent.getText()), cboAntecedent.getSelectionModel().getSelectedIndex(), cboResult.getSelectionModel().getSelectedIndex(), unitRelationships)));
    		}else {
    			antecedent.setText(round.format(convert("result",Double.parseDouble(result.getText()), cboAntecedent.getSelectionModel().getSelectedIndex(), cboResult.getSelectionModel().getSelectedIndex(), unitRelationships)));
    		}
    	}catch(Exception ex){
    		//System.out.println("error");
    		if(result.getText().equals("") || antecedent.getText().equals("")) {
    			result.setText("");
    			antecedent.setText("");
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("ERREUR");
        		alert.setTitle("Attention");
        		alert.setContentText("Entrée numérique seulement");
        		alert.show();
    		}
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cboAntecedent.setItems(unitesVolumes);
		cboResult.setItems(unitesVolumes);
		cboAntecedent.getSelectionModel().selectFirst();
		cboResult.getSelectionModel().select(1);
	}

}
