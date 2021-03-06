package entity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Order {
	private String oID;
	private Date orderTime;
	private String diningOption;
	private double totalPrice;
	//Link reference to VIP member
	private Member member;
	public static OrderMenu menu;
	private ArrayList<NoodleOrder> noodleOrder;
	
	public Order(Date orderTime) {
		DataProcessor processor = new DataProcessor("data/configure.csv");
		noodleOrder = new ArrayList<NoodleOrder>();
		String[] currentOID = processor.Read().get(0).split(",");
		int inputID = Integer.parseInt(currentOID[0]);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(orderTime);
		int weekday = cal.get(cal.DAY_OF_WEEK);
		
		this.oID = "" + weekday; //the day of the week.
		for(int i=0;i<(5-currentOID[0].length());i++)
			this.oID += "0";
		this.oID += currentOID[0];
		
		Order.menu = new OrderMenu();
		this.orderTime = orderTime;
		this.diningOption = null;
		this.totalPrice = 0.0;
	}

	public void orderFinish() {
		DataProcessor processor = new DataProcessor("data/configure.csv");
		String[] currentOID = processor.Read().get(0).split(",");
		int inputID = Integer.parseInt(currentOID[0]);
		ArrayList<String> outputConfig = new ArrayList<String>();
		outputConfig.add("" + (++inputID)+','+currentOID[1]);
		processor.Write(outputConfig);
	}
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getDiningOption() {
		return diningOption;
	}

	public void setDiningOption(String diningOption) {
		this.diningOption = diningOption;
	}

	public double getTotalPrice() {
		double price = 0;
		for(int i=0;i<noodleOrder.size();i++)
			price += noodleOrder.get(i).getPrice();
		this.totalPrice = price;
		return this.totalPrice;
	}
	
//	public double getTotalPriceDirectly() {
//		return this.totalPrice;
//	}

	public Member getMember() {
		return member;
	}

	public String getoID() {
		return oID;
	}
	
	public boolean setMember(String memberNumber) {
		this.member = new Member(memberNumber);
		if(this.member == null)
			return false;
		else
			return true;
	}
	//add new sub order
	public void addNoodleOrder(NoodleOrder nOrder) {
		this.noodleOrder.add(nOrder);
	}
	
	public String[][] getOrderDetail(){
		String [][] detail = new String[noodleOrder.size()*5][3];
		int n = 0;
		for(int i=0;i<noodleOrder.size()*5;){
			String[][] temp = noodleOrder.get(n++).subOrderDetail();
			for(int j=0;j<temp.length;j++)
			{
				for(int k=0;k<temp[0].length;k++)
					detail[i][k] = temp[j][k];
				i++;
			}
		}
		
		return detail;
	}
	
	
	public int getSubOrderNumber() {
		return noodleOrder.size();
	}
	
	public boolean printOrder() {
		DataProcessor processor = new DataProcessor("data/orderForCustomer/" + this.oID + ".txt");
		ArrayList<String> outputOrder = new ArrayList<String>();
		outputOrder.add("**************************************************");
		outputOrder.add("");
		outputOrder.add("              Totoro Ramen restaurant");
		outputOrder.add("");
		outputOrder.add("            OrderID: " + this.oID);
		outputOrder.add("            Order time: " + new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss").format(orderTime));
		outputOrder.add("            Dining option: " + this.diningOption);
		outputOrder.add("            Total price: " + this.totalPrice + " pounds");
		outputOrder.add("");
		outputOrder.add("                 Have a good meal!");
		outputOrder.add("");
		outputOrder.add("**************************************************");
		processor.Write(outputOrder);
		DataProcessor processorForOID = new DataProcessor("data/configure.csv");
		return true;
	}
	
	
	public void outPutOrder() {
		DataProcessor processor = new DataProcessor("data/order.csv");
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(orderTime);
		int weekday = cal.get(cal.DAY_OF_WEEK);
		ArrayList<String> outputOrder = new ArrayList<String>();
		outputOrder.add(String.join(",", new String[]{oID,String.valueOf(cal.get(cal.DAY_OF_WEEK)), diningOption, String.valueOf(totalPrice),String.valueOf(noodleOrder.size())}));
		for(NoodleOrder i:noodleOrder) 
			outputOrder.add(i.toString());
		for(String i:outputOrder)
			processor.WriteLine(i);
	}
	
	public void discount() {
		
		double max = 0;
		int index = 0;
		for(int i=0;i<noodleOrder.size();i++) {
			if(max <= noodleOrder.get(i).getPrice()) {
				max = noodleOrder.get(i).getPrice();
				index = i;
			}
		}
		
		noodleOrder.get(index).setPrice(0);
		totalPrice -= max;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date = new Date();
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
		int weekday = cal.get(cal.DAY_OF_WEEK);
		System.out.println(weekday);
	}


}
