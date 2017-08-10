# JDBC-Oracle-
JDBC实现Oracle数据库的增删改查
package com.zt.test;

import java.util.Date;
import java.util.List;

import com.zt.dao.EmpDAO;
import com.zt.entity.Emp;

public class Test {
	public static void main(String[] args) {
		//要执行EmpDAO中的添加数据的方法
		//需要先实例化包含该方法的类
		EmpDAO ed = new EmpDAO();
	Emp emp = new Emp(1111, "张三", "教师", new Date(), "1000", "100");
//		Emp emp = new Emp(empno, ename, job, hiredate, sal, comm);
		//ed.saveEmp(emp);
	//返回一个结果集
		/*List<Emp> findall = ed.findAll();
		//遍历结果集，打印出来
		for (Emp emp2 : findall) {
			System.out.println(emp2);
		}*/
	/*//这里只有一个对象可以直接打印出来 不用遍历
		Emp emp1 = ed.findByid(1111);
		System.out.println(emp1);*/
	
	/**
	 * 调用修改emp的方法
	 * 调用修改emp的方法时我们需要传入一个指定了empno的emp对象进去
	 * 
	 */
	//这里我们可以通过调用findbyid的方法来获取到一个emp对象,然后再将这个对象作为参数传入到update里面
	/*Emp e= ed.findByid(1111);
	//找到1111对应的对象之后
	//修改相应的字段之后再传入到update方法里面
	e.setEname("小白");
	ed.updateEmp(e);*/
	
	/**
	 * 调用删除emp的方法，调用之前我们需要传入一个id
	 */
    /*	ed.delEmp(1111);*/
	
	/**
	 * 调用模糊查询的方法
	 * 通过名字查询
	 * 
	 */
	//返回一个集合，这里用一个集合来接返回值
	List<Emp> emps =ed.findEmpByName("S");
	//遍历返回的集合
	for (Emp emp2 : emps) {
		System.out.println(emp2);
	}

	
	}
}
