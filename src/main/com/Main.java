package com;

import com.gen.dao.MybatisGenerator;

public class Main {
	public static void main(String[] args) throws Exception {
		MybatisGenerator generator = new MybatisGenerator();

		String[] maps = {
				"tb_api_record", "tb_api_bill"
				,"tb_report","tb_report_item"
		};

		for (int i = 0; i < maps.length; i++) {
			String tableName = maps[i];
			generator.generate(tableName);
		}
	}

}
