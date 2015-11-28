package UtilAlgo;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA1 {

	public String HashData(String data)
	{
		return DigestUtils.sha1Hex(data);
	}
	
}
