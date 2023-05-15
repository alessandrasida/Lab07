/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    
    
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
    		if(this.cmbNerc.getValue() == null) {
    			this.txtResult.setText("Select a NERC.");
    			return;
    		}
    		
    		int anni = Integer.parseInt(this.txtYears.getText());
    		int ore = Integer.parseInt(this.txtHours.getText());
    		if( anni < 0) {
    			this.txtResult.setText("Insert a number greater then 0.");
    			return; 
    		}
    		if( ore < 0) {
    			this.txtResult.setText("Insert a number greater then 0.");
    			return; 
    		}
    		
    		List<PowerOutage> risultato = this.model.trovaEventi(this.cmbNerc.getValue(), anni, ore);
    		this.txtResult.appendText("Tot people affected: " + this.model.maxAffected(risultato)+ "\n");
    		this.txtResult.appendText("Tot hours of outage: " + this.model.totOre(risultato) +  "\n");
    		
    		for(PowerOutage p : risultato) {
    			this.txtResult.appendText(String.format("%s %s %s %s\n", Integer.toString(p.getData_inizio().getYear()),
    					p.getData_inizio().toString(), p.getData_fine().toString(),Integer.toString(p.getCostumers_affected()) ));
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un formato valido.");
    	}
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Nerc> nercs = model.getNercList();
        for(Nerc n : nercs) {
        	this.cmbNerc.getItems().add(n);
        }
    }
}
