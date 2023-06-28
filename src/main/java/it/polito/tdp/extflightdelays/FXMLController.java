package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField compagnieMinimo;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoDestinazione;

    @FXML
    private Button btnAnalizza;

    @FXML
    private Button btnConnessione;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
        txtResult.clear();

        int x;

        try {
            x = Integer.parseInt(compagnieMinimo.getText());

        } catch (NumberFormatException e) {
            txtResult.setText("Inserisci un numero di recensioni");
            return;
        }

        this.model.creaGrafo(x);

        txtResult.appendText("GRAFO CREATO!\n");
        txtResult.appendText("# VERTICI: " + model.getNVertici() + "\n");
        txtResult.appendText("# ARCHI: " + model.getNArchi() + "\n");
        
        cmbBoxAeroportoPartenza.getItems().addAll(model.getVertici());
        cmbBoxAeroportoDestinazione.getItems().addAll(model.getVertici());

    }

    @FXML
    void doTestConnessione(ActionEvent event) {
    	txtResult.clear();
    	Airport a1 = cmbBoxAeroportoPartenza.getValue();
    	if(a1 == null) {
    		txtResult.setText("Seleziona un aeroporto di partenza");
    		return;
    	}
    	
    	Airport a2 = cmbBoxAeroportoDestinazione.getValue();
    	if(a2 == null) {
    		txtResult.setText("Seleziona un aeroporto di destinazione");
    		return;
    	}
    	
    	List<Airport> percorso = model.getPercorso(a1, a2);
    	
    	if(percorso == null) {
    		txtResult.setText("I due nodi non sono collegati");
    	} else{
    		txtResult.appendText(a1 + " e " + a2 + " sono collegati dal seguente percorso:\n\n");
    		txtResult.appendText(percorso.toString());
    	}

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessione != null : "fx:id=\"btnConnessione\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
        this.model = model;
   
    }
}
