package entity;


/**
 *	分页Entity
 */
public class Page {
	
	//总记录数
	private int total;
	//当前页
	private int currpage;
	//每页显示记录数量
	private int pagesize;
	//总页数
	private int pagecount;
	//每页数据的开始下标
	private int start;
	
	public Page(int total, int currpage, int pagesize) {
		this.setTotal(total);
		this.setCurrpage(currpage);
		this.setPagesize(pagesize);
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrpage() {
		return currpage;
	}
	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getPagecount() {
		//调用设置总页数方法
		this.setPagecount();
		return pagecount;
	}
	//设置总页数
	public void setPagecount(){
		this.pagecount = (total % pagesize == 0) ? total / pagesize : total / pagesize + 1;
	}
	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}
	public int getStart() {
		//调用设置行号方法
		this.setStart();
		return start;
	}
	//设置每页的起始行号
	public void setStart(){
		this.start = (this.getCurrpage()-1)*this.getPagesize();
	}
	public void setStart(int start) {
		this.start = start;
	}
}

