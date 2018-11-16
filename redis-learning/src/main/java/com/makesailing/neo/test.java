package com.makesailing.neo;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * #
 *
 * @author jamie
 * @date 2018/11/16 16:25
 */
public class test {

	public static void main(String[] args) throws Exception {
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJZ5HV4uKwmXSeDks6f3S0OTPY7Kpo6Dfzhq7GL96s9CBJ9Y2BEi86dzW8HKgABGLTPdKtFApfB/m/pZWQjqnoUCAwEAAQ==";
		String password = "U7Vdax1bWrtDyEUry5u0ZISkC1ZRTWXFaAZqYVeYSM+GUo2GlPj0MnARtfcxRl6viIjwA+aTcmWsus09P23STQ==";

		String paw = ConfigTools.decrypt(publicKey, password);
		System.out.println(paw);
	}
}


