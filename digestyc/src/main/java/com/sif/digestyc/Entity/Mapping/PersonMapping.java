package com.sif.digestyc.Entity.Mapping;

import com.sif.digestyc.Entity.Human;

import de.bytefish.jsqlserverbulkinsert.mapping.AbstractMapping;

public class PersonMapping extends AbstractMapping<Human> {

    public PersonMapping() {
    	super("dbo", "human");
    	
    	mapLong("id", true);
    	//mapInteger("id", true);
        mapVarchar("first_name", Human::getFirstName);
        mapVarchar("last_name", Human::getLastName);
 
    }
}
