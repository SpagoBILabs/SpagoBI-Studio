/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.geo.util.xml;

import it.eng.spagobi.studio.geo.editors.model.geo.GuiParam;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class ParamConverter implements Converter {

    public boolean canConvert(Class clazz) {
            return clazz.equals(GuiParam.class);
    }

    public void marshal(Object value, 
    					HierarchicalStreamWriter writer,
    					MarshallingContext context) {
    	CdataPrettyPrintWriter cdataWriter = (CdataPrettyPrintWriter)writer.underlyingWriter();
    	
        GuiParam param = (GuiParam) value;
        cdataWriter.addAttribute("name", param.getName());
        
        if(!param.getName().equalsIgnoreCase("styles")){                
        	cdataWriter.setValue(param.getValue());
        }else{
        	cdataWriter.setValue("<![CDATA[");
        	cdataWriter.setValue(param.getValue());
        	cdataWriter.setValue("]]>"); 
        }

            
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
    		GuiParam par = new GuiParam();
    		String value =reader.getValue();
    		String name = reader.getAttribute("name");
    		par.setName(name);
     		if(!name.equalsIgnoreCase("styles")){    			
                par.setValue(value);
    		}else{
    			int cdatadpos= value.indexOf("<![CDATA[");
    			if(cdatadpos != -1){
    				int cdataendpos=value.indexOf("]]>");
    				String style = value.substring(cdatadpos+"<![CDATA[".length(), cdataendpos);
    				par.setValue(style);
    			}else{
    				par.setValue(value);
    			}

    			
    		}
    		
            
            return par;
    }

}
