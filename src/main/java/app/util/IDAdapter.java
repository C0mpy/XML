package app.util;

import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

// Adapter class that we use to auto-generate IDs for Objects when unmarshalling from XML
public class IDAdapter extends XmlAdapter<String, String> {

	@Override
	public String marshal(String v) throws Exception {
		return v;
	}

	@Override
	public String unmarshal(String v) throws Exception {
		String str = UUID.randomUUID().toString();
		System.out.println(str);
		return str;
	}


}
