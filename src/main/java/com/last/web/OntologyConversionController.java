package com.last.web;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/ontology")
public class OntologyConversionController {

    private final String OWL_FILE_PATH = "data/your_ontology.owl"; // Path to your OWL file
    private final String RDF_FILE_PATH = "data/converted_ontology.rdf"; // Path for the output RDF file

    @GetMapping("/convert")
    public ResponseEntity<String> convertOntologyToRDF() {
        // Create an ontology model
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);

        // Load the OWL ontology
        try {
            ontModel.read(OWL_FILE_PATH);

            // Save the ontology as RDF
            try (FileOutputStream out = new FileOutputStream(RDF_FILE_PATH)) {
                ontModel.write(out, "RDF/XML");
                return ResponseEntity.ok("Ontology converted to RDF and saved successfully at " + RDF_FILE_PATH);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error converting ontology: " + e.getMessage());
        }
    }
}
