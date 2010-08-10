/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

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
