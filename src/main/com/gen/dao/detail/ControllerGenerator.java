package com.gen.dao.detail;

import com.gen.conf.Config;
import com.gen.dao.DatabaseTableInfo;
import com.gen.domain.BeanFile;
import com.gen.domain.ColumnProperties;
import com.gen.util.Utils;

import java.sql.SQLException;


/**
 * 生成Controller.java
 *
 * Created by caowei on 2017/8/31.
 */
public class ControllerGenerator {
	private String datePath = "import java.util.Date;";

	
	public String generate(DatabaseTableInfo dbTableInfo) throws SQLException {
		ColumnProperties primaryKey = dbTableInfo.getPrimaryKey();
		ColumnProperties addTimeColumn = dbTableInfo.filterColumn("add_time");
		ColumnProperties optTimeColumn = dbTableInfo.filterColumn("opt_time");

		StringBuilder data = new StringBuilder();
		StringBuilder headData = new StringBuilder();
		String classUri = Config.parentUri + "/" + dbTableInfo.getClassName().toLowerCase();
		String serviceName = Utils.lowerFirstChar(dbTableInfo.getServiceClassName());
		BeanFile po = dbTableInfo.getPo();
		BeanFile condition = dbTableInfo.getCondition();
		BeanFile queryForm = dbTableInfo.getQueryForm();
		BeanFile updateForm = dbTableInfo.getUpdateForm();
		BeanFile createForm = dbTableInfo.getCreateForm();
		BeanFile vo = dbTableInfo.getVo();

		headData.append("package " + Config.controllerPackage + ";\r\n\r\n")
				.append("import " + po.getPackageName() + "." + po.getClassName() + ";\r\n")
				.append("import " + condition.getPackageName() + "." + condition.getClassName() + ";\r\n")
				.append("import " + queryForm.getPackageName() + "." + queryForm.getClassName() + ";\r\n")
				.append("import " + updateForm.getPackageName() + "." + updateForm.getClassName() + ";\r\n")
				.append("import " + createForm.getPackageName() + "." + createForm.getClassName() + ";\r\n")
				.append("import " + vo.getPackageName() + "." + vo.getClassName() + ";\r\n")

				.append("import " + Config.servicePackage + "." + dbTableInfo.getServiceClassName() + ";\r\n")
				.append("import " + Config.exceptionPackage + "." + Config.exceptionName + ";\r\n")
				.append("import " + Config.responseEntityPackage + "." +Config.responseEntityName + ";\r\n")

				.append("import com.qianmi.base.response.CentreListResponse;\r\n")
				.append("import com.qianmi.base.response.CentreCutPageResponse;\r\n")
				.append("import java.util.List;\r\n")
				.append("import java.util.ArrayList;\r\n")
				.append("import com.bm.center.base.util.CopyUtil;\r\n")
				.append("import org.springframework.web.bind.annotation.*;\r\n")
				.append("import javax.validation.Valid;\r\n")
				.append("import io.swagger.annotations.Api;\r\n")
				.append("import io.swagger.annotations.ApiOperation;\r\n")
				.append("import io.swagger.annotations.ApiParam;\r\n")
				.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");

		data.append("@RestController\r\n")
				.append("@RequestMapping(\"" + classUri + "\")\r\n")
				.append("@Api(value = \"" + classUri + "\", description = \"" + dbTableInfo.getTableComment() + "\")\r\n")
				.append("public class " + dbTableInfo.getControllerClassName() + " extends BaseController {\r\n\r\n")
				.append("\t@Autowired\r\n")
				.append("\tprivate " + dbTableInfo.getServiceClassName() + " " + serviceName + ";\r\n\r\n")

				// 查询单个
				.append("\t@ApiOperation(value = \"查询" + dbTableInfo.getTableComment() + "\",notes = \"查询" + dbTableInfo.getTableComment() + "\",httpMethod = \"GET\")\r\n")
				.append("\t@RequestMapping(value = \"/query\", method = {RequestMethod.GET, RequestMethod.POST})\r\n")
				.append("\tpublic @ResponseBody\r\n")
				.append("\tResponseEntity<" + dbTableInfo.getVo().getClassName() + "> query(@ApiParam(value = \"" + primaryKey.getComment() + "\", required = true)@RequestParam " + primaryKey.getClazz() + " " + primaryKey.getName() + ") throws " + Config.exceptionName + " {\r\n")
				.append("\t\t" + po.getClassName() + " po = " + serviceName + ".queryWithValid(" + primaryKey.getName() + ");\r\n")
				.append("\t\t" + vo.getClassName() + " vo = CopyUtil.transfer(po, " + vo.getClassName() + ".class);\r\n")
				.append("\t\treturn getSuccessResult(vo);\r\n")
				.append("\t}\r\n\r\n")

				// 查询数量
				.append("\t@ApiOperation(value = \"查询" + dbTableInfo.getTableComment() + "数量\",notes = \"查询" + dbTableInfo.getTableComment() + "数量\",httpMethod = \"GET\")\r\n")
				.append("\t@RequestMapping(value = \"/queryCount\", method = {RequestMethod.GET, RequestMethod.POST})\r\n")
				.append("\tpublic @ResponseBody\r\n")
				.append("\tResponseEntity<Integer> queryCount(@ModelAttribute@Valid " + dbTableInfo.getQueryForm().getClassName() + " form) throws " + Config.exceptionName + " {\r\n")
				.append("\t\t" + condition.getClassName() + " condition = this.getConditionByQueryForm(form);\r\n")
				.append("\t\tint count = " + serviceName + ".queryCount(condition);\r\n")
				.append("\t\treturn getSuccessResult(count);\r\n")
				.append("\t}\r\n\r\n")


				// 查询列表
				.append("\t@ApiOperation(value = \"查询" + dbTableInfo.getTableComment() + "列表\",notes = \"查询" + dbTableInfo.getTableComment() + "列表\",httpMethod = \"GET\")\r\n")
				.append("\t@RequestMapping(value = \"/queryList\", method = {RequestMethod.GET, RequestMethod.POST})\r\n")
				.append("\tpublic @ResponseBody\r\n")
				.append("\tResponseEntity<CentreListResponse<" + dbTableInfo.getVo().getClassName() + ">> queryList(@ModelAttribute@Valid " + dbTableInfo.getQueryForm().getClassName() + " form) throws " + Config.exceptionName + " {\r\n")
				.append("\t\t" + condition.getClassName() + " condition = this.getConditionByQueryForm(form);\r\n")
				.append("\t\tList<" + po.getClassName() + "> poList = " + serviceName + ".queryList(condition);\r\n")
				.append("\t\tList<" + vo.getClassName() + "> voList = CopyUtil.transferList(poList, " + vo.getClassName() + ".class);\r\n")
				.append("\t\treturn getSuccessResult(getListResponse(voList));\r\n")
				.append("\t}\r\n\r\n")


				// 查询列表
				.append("\t@ApiOperation(value = \"查询" + dbTableInfo.getTableComment() + "列表(带分页)\",notes = \"查询" + dbTableInfo.getTableComment() + "列表(带分页)\",httpMethod = \"GET\")\r\n")
				.append("\t@RequestMapping(value = \"/queryPageList\", method = {RequestMethod.GET, RequestMethod.POST})\r\n")
				.append("\tpublic @ResponseBody\r\n")
				.append("\tResponseEntity<CentreCutPageResponse<" + dbTableInfo.getVo().getClassName() + ">> queryPageList(@ModelAttribute@Valid " + dbTableInfo.getQueryForm().getClassName() + " form) throws " + Config.exceptionName + " {\r\n")
				.append("\t\t" + condition.getClassName() + " condition = this.getConditionByQueryForm(form);\r\n")
				.append("\t\tList<" + vo.getClassName() + "> voList = new ArrayList<>();\r\n")
				.append("\t\tint count = " + serviceName + ".queryCount(condition);\r\n")
				.append("\t\tif (count > 0) {\r\n")
				.append("\t\t\tList<" + po.getClassName() + "> poList = " + serviceName + ".queryList(condition);\r\n")
				.append("\t\t\tvoList = CopyUtil.transferList(poList, " + vo.getClassName() + ".class);\r\n")
				.append("\t\t}\r\n")
				.append("\t\treturn getSuccessResult(getPageResponse(form, count, voList));\r\n")
				.append("\t}\r\n\r\n")

				// 新增
				.append("\t@ApiOperation(value = \"新增" + dbTableInfo.getTableComment() + "\",notes = \"新增" + dbTableInfo.getTableComment() + "\",httpMethod = \"POST\")\r\n")
				.append("\t@RequestMapping(value = \"/add\", method = {RequestMethod.GET, RequestMethod.POST})\r\n")
				.append("\tpublic @ResponseBody\r\n")
				.append("\tResponseEntity<" + dbTableInfo.getVo().getClassName() + "> add(@ModelAttribute@Valid " + dbTableInfo.getCreateForm().getClassName() + " form) throws " + Config.exceptionName + " {\r\n")
				.append("\t\t" + po.getClassName() + " po = CopyUtil.transfer(form, " + po.getClassName() + ".class);\r\n");
		if (!dbTableInfo.isAutoIncrement()) {
			if ("String".equals(primaryKey.getClazz())) {
				headData.append("import com.bm.center.base.util.UUIDUtil;\r\n");
				data.append("\t\tpo.set" + Utils.upperFirstChar(primaryKey.getName()) + "(UUIDUtil.getUUID());\r\n");

			} else if ("Integer".equals(primaryKey.getClazz())) {
				headData.append("import org.apache.commons.lang.math.RandomUtils;\r\n");
				data.append("\t\tpo.set" + Utils.upperFirstChar(primaryKey.getName()) + "(RandomUtils.nextInt());\r\n");
			}
		}
		if (addTimeColumn != null) {
			if (headData.toString().indexOf(datePath) == -1) {
				headData.append(datePath + "\r\n");
			}
			data.append("\t\tpo.set" + Utils.upperFirstChar(addTimeColumn.getName()) + "(new Date());\r\n");
		}
		data.append("\t\t" + serviceName + ".insert(po);\r\n")
				.append("\t\t" + vo.getClassName() + " vo = CopyUtil.transfer(po, " + vo.getClassName() + ".class);\r\n")
				.append("\t\treturn getSuccessResult(vo);\r\n")
				.append("\t}\r\n\r\n")

				// 修改
				.append("\t@ApiOperation(value = \"修改" + dbTableInfo.getTableComment() + "\",notes = \"修改" + dbTableInfo.getTableComment() + "\",httpMethod = \"POST\")\r\n")
				.append("\t@RequestMapping(value = \"/update\", method = {RequestMethod.GET, RequestMethod.POST})\r\n")
				.append("\tpublic @ResponseBody\r\n")
				.append("\tResponseEntity<" + dbTableInfo.getVo().getClassName() + "> update(@ModelAttribute@Valid " + dbTableInfo.getUpdateForm().getClassName() + " form) throws " + Config.exceptionName + " {\r\n")
				.append("\t\t" + po.getClassName() + " po = CopyUtil.transfer(form, " + po.getClassName() + ".class);\r\n");
		if (optTimeColumn != null) {
			if (headData.toString().indexOf(datePath) == -1) {
				headData.append(datePath + "\r\n");
			}
			data.append("\t\tpo.set" + Utils.upperFirstChar(optTimeColumn.getName()) + "(new Date());\r\n");
		}
		data.append("\t\t" + serviceName + ".update(po);\r\n")
				.append("\t\treturn this.query(form.get" + Utils.upperFirstChar(primaryKey.getName()) + "());\r\n")
				.append("\t}\r\n\r\n")


				// 删除
				.append("\t@ApiOperation(value = \"删除" + dbTableInfo.getTableComment() + "\",notes = \"删除" + dbTableInfo.getTableComment() + "\",httpMethod = \"POST\")\r\n")
				.append("\t@RequestMapping(value = \"/delete\", method = {RequestMethod.GET, RequestMethod.POST})\r\n")
				.append("\tpublic @ResponseBody\r\n")
				.append("\tResponseEntity delete(@ApiParam(value = \"" + primaryKey.getComment() + "\", required = true)@RequestParam " + primaryKey.getClazz() + " " + primaryKey.getName() + ") throws " + Config.exceptionName + " {\r\n")
				.append("\t\t" + serviceName + ".delete(" + primaryKey.getName() + ");\r\n")
				.append("\t\treturn getSuccessResult();\r\n")
				.append("\t}\r\n\r\n")

				// 通过queryForm转为Condition
				.append("\t/**\r\n")
				.append("\t * " + queryForm.getClassName() + "转换为" + condition.getClassName() + "\r\n")
				.append("\t *\r\n")
				.append("\t * @param form\r\n")
				.append("\t * @return\r\n")
				.append("\t */\r\n")
				.append("\tprivate " + condition.getClassName() + " getConditionByQueryForm(" + queryForm.getClassName() + " form) {\r\n")
				.append("\t\t" + condition.getClassName() + " condition = CopyUtil.transfer(form, " + condition.getClassName() + ".class);\r\n")
				.append("\t\treturn condition;\r\n")
				.append("\t}\r\n\r\n")

				.append("}");

		headData.append("\r\n");
		return headData.toString() + data.toString();
	}



}
