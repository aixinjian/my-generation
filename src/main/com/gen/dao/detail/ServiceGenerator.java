package com.gen.dao.detail;

import com.gen.conf.Config;
import com.gen.dao.DatabaseTableInfo;

import java.sql.SQLException;


public class ServiceGenerator {



	
	public String generate(DatabaseTableInfo dbTableInfo) throws SQLException {

		String poClassName=dbTableInfo.getPo().getClassName();
		String conditionClassName=dbTableInfo.getCondition().getClassName();

		StringBuffer data = new StringBuffer();

		data.append("package " + Config.servicePackage + ";\r\n\r\n")
				.append("import com.hosjoy.hbp.tms.base.service.BaseService;\r\n")
				.append("import " + dbTableInfo.getPo().getPackageName() + "." + poClassName + ";\r\n")
				.append("import " + dbTableInfo.getCondition().getPackageName() + "." + conditionClassName + ";\r\n\n")
				.append("public interface " + dbTableInfo.getServiceClassName() + " extends BaseService<" + poClassName + ", " + conditionClassName + "> {\r\n\r\n")

		.append("}");

		return data.toString();
	}



}
