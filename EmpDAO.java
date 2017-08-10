package com.zt.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zt.entity.Emp;
import com.zt.util.JDBCUtil;

public class EmpDAO {
	//初始化connection，statement，result set，JDBCUtil类（加载驱动，获取链接，关闭链接）
	//定义为EmpDAO的私有属性，并且设置初值null
	private Connection con = null;
	private PreparedStatement ps = null;
//	private Statement  st = null;
	private ResultSet rs = null;
	private JDBCUtil jdbc = new JDBCUtil();
	
	/**
	 * 添加数据
	 * @return 
	 */
	public void saveEmp(Emp emp){
		//首先需要获取到连接，连接到数据库才能够操作
		//通过jdbc来获取连接
		con = jdbc.getconneConnection();
		//连接到数据库之后下面就是准备SQL语句
		String sql = "insert into emp (empno,ename,job,hiredate,sal,comm) values (?,?,?,?,?,?)";
		//这里需要添加的具体数据我们用问号占位符表示
		//准备好SQL语句之后，需要利用con连接进行一个预处理
		//相当于将SQL语句输入到数据库窗口里面
		try {
			//这里将预处理的值赋值给ps变量
			ps = con.prepareStatement(sql);
			//写好sql语句之后，这里需要将我们输入的对应的值传递给emp对应的字段
			//赋值需要通过ps来向emp中传入具体的字段
			ps.setInt(1,emp.getEmpno());
			ps.setString(2, emp.getEname());
			ps.setString(3, emp.getJob());
			//这里传入的时间是sql的时间，而我们获取到的是util的时间
			//所以需要将util的时间获取到秒数，然后转为sql的时间
			//这里的Date是一个对象还需要new一下
			ps.setDate(4, new java.sql.Date(emp.getHiredate().getTime()));
			//这里传入的是日期类型，所以应该设置日期类型
			ps.setString(5, emp.getSal());
			ps.setString(6, emp.getComm());
			
			//将参数传好了之后，就是开始执行相应的SQL语句
			//这里我们多了一个步骤就是写好了SQL语句之后，还需要通过预处理将写入的字段传递给对应的表中的字段
			//同样的执行的时候，也是利用预处理的返回值进行执行
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
		/**
		 * 查询数据
		 */
		public List<Emp> findAll(){
			List<Emp> emps = new ArrayList<Emp>();
			//获取链接
			con = jdbc.getconneConnection();
			//准备SQL语句
			String  sql1 ="select empno,ename,job,hiredate,sal,comm from emp";
			//预处理
			try {
				ps = con.prepareStatement(sql1);
				//执行SQL语句
				//执行了查询语句之后会返回一个结果集
				//要显示返回的结果集，需要一次取出相应的字段
				rs = ps.executeQuery();
				//boolean next = rs.next();
				//这里不能用while(next)，相当于永远为true，是一个死循环
				
				while(rs.next()){
					//System.out.println("---");测试
				  int empno = rs.getInt(1);
				  String ename = rs.getString(2);
				  String job = rs.getString(3);
				  Date hiredate = rs.getDate(4);
				  String sal = rs.getString(5);
				  String comm = rs.getString(6);
				  //从结果集中取出相应的字段之后，需要通过一张表显示出来
				  //所以还需要将取出的字段封装为一个Emp对象
				  //取出的数据在结果集里面，我们需要从将数据取出来放在emp对象里面，然后返回emp对象
				  Emp emp1 = new Emp(empno, ename, job, hiredate, sal, comm);
				  //将取出的对象加入到集合emps中去
				  emps.add(emp1);
				}	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				jdbc.closeAll(con, ps, rs);
			}
			
			return emps;
		}	
		/**
		 * 根据id查询数据 返回一个emp对象
		 */
       public Emp findByid(int id){
    	   //定义一个emp类型用于装查询的返回的结果
    	   //设置初值为null
    	   Emp emp = null;
    	   //同样的先链接数据库
    	   con = jdbc.getconneConnection();
    	   //准备SQL语句
    	   String sql ="select empno,ename,job,hiredate,sal,comm from emp where empno=?";
    	   //这里需要输入的是empno，我们先用?占位符代替
    	   //预处理
    	   try {
			ps = con.prepareStatement(sql);
			//执行SQL语句
			//返回一个结果集
			ps.setInt(1, id);
			rs = ps.executeQuery();
			//将结果集遍历显示出来
			while(rs.next()){
				int empno = rs.getInt(1);
				String ename  =rs.getString(2);
				String job = rs.getString(3);
				Date hiredate= rs.getDate(4);
				String sal = rs.getString(5);
				String comm = rs.getString(6);
				//将查询到的数据封装成一个emp对象，将emp对象返回
				//创建一个新的对象赋值给刚才定义的变量emp
				//这里不用再定义emp的类型了，否则是新一个Emp类型
			     emp = new Emp(empno, ename, job, hiredate, sal, comm);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   
    	   return emp;
       }

		/**
		 * 更新数据库emp，可以更新的字段为ename,job,sal,comm
		 */
       public void updateEmp(Emp emp){
    	   //第一步还是连接数据库
    	   //利用con得到jdbc的getconnection
    	   con = jdbc.getconneConnection();
    	   //连接好数据库之后，就可以写SQL语句了
    	   String sql = "update emp set ename=?,job=?,sal=?,comm=? where empno=?";
    	   //利用preparestatement的操作好处是需要填写的字段直接用问号表示
    	   //先用一个占位符表示
    	   //接下来进行SQL语句的预处理
    	   //调用con的preparestatement方法
    	   try {
			ps = con.prepareStatement(sql);
			//接下来利用ps的set属性来改变emp的各个字段
			//这里需要利用empno来传入一个emp对象
			ps.setString(1, emp.getEname());
			ps.setString(2, emp.getJob());
			ps.setString(3, emp.getSal());
			ps.setString(4, emp.getComm());
			ps.setInt(5, emp.getEmpno());
			//这里的利用PS的set方法依次为5个占位符设置相应的值
			//利用ps预处理的set方法将每个需要修改的字段赋值好之后，就是执行这条SQL语句了
			ps.execute();
			//注意我们这里采用的是prepare statement的方式，所以这里不需要将sql作为参数传入进去
			//只有statement才需要将sql语句传入进去
			//ps.execute(sql);这是statement的写法
			//执行同样是利用ps来执行
			//这里执行成功之后打印一句话
			System.out.println("执行成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭数据库连接，需要jdbc的方法
			jdbc.closeAll(con, ps, rs);
		}
       }
		
		/**
		 * 删除数据
		 */
		public void delEmp(int id){
			//第一步获取连接
			con = jdbc.getconneConnection();
			//第二步准备SQL语句
			String sql = "delete from emp where empno = ?";
			try {
				//第三部预处理
				ps = con.prepareStatement(sql);
				//第四步为占位符设置具体的值，
				//而这里的empno是需要用户输入的，所以我们需要通过函数的参数传入进来
				ps.setInt(1, id);
				//第五步执行SQL语句
				ps.execute();
				//第六步执行成功打印
				System.out.println("执行成功");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				jdbc.closeAll(con, ps, rs);
			}
		}
		
		
		/**
		 * 模糊查询
		 * 通过名字查找数据
		 * @return 
		 */
		public List<Emp> findEmpByName(String ename){
			//这里传入的参数为string类型，不用是List<Emp>类型
			List<Emp> emps = new ArrayList<Emp>();
			//第一步连接数据库
			con = jdbc.getconneConnection();
			//String ename = null;
			//第二部准备SQL语句
			String sql = "select empno,ename,job,hiredate,sal,comm from emp where ename like '%"+ename+"%'";
			
			try {
				//第三步预处理SQL语句
				ps = con.prepareStatement(sql);
				//第四步执行SQL查询语句，不带sqL参数，返回查询的结果集，这里需要返回一个结果集参数
				//所以需要定义List<Emp>类型的集合用于返回
				 rs = ps.executeQuery();
				 //第五步需要从rs的结果集从取出相应的字段存入一个emp的对象中，然后加入到emps的集合中
				 while(rs.next()){
					//这里不能将Emp emp = new emp()定义在while循环里面
					 //否则会
					 Emp emp1  = new Emp(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6));
					 emps.add(emp1);
				 }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				jdbc.closeAll(con, ps, rs);
			}
			
			return emps;
		}
	}
	
