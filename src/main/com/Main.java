package com;

import com.gen.dao.MybatisGenerator;

public class Main {
	public static void main(String[] args) throws Exception {
		MybatisGenerator generator = new MybatisGenerator();

		String[] maps = {
				"tb_clue","tb_train_trade"
		};

		for (int i = 0; i < maps.length; i++) {
			String tableName = maps[i];
			generator.generate(tableName);
		}
	}

}
