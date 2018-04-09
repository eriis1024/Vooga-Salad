package authoring;

import authoring.frontend.exceptions.MissingPropertiesException;

/**
 * 
 * @author Ben Hodgson 4/8/18
 *
 * Class to generate a generic AuthoringModel.java class and save the object
 * as an XML using XStream.
 */
public class GenericModel {
    // TODO put in properties files
    public static final String DEFAULT = "default";
    private AuthoringModel myModel;
    
    public GenericModel() {
	try {
		myModel = new AuthoringModel();
	} catch (MissingPropertiesException e) {
		// TODO 
	}
	generateGenericModel();
    }
    
    private void generateGenericModel() {
	
    }
    
}
