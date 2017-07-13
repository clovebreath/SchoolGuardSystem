package entity;


/**
 *	��ҳEntity
 */
public class Page {
	
	//�ܼ�¼��
	private int total;
	//��ǰҳ
	private int currpage;
	//ÿҳ��ʾ��¼����
	private int pagesize;
	//��ҳ��
	private int pagecount;
	//ÿҳ���ݵĿ�ʼ�±�
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
		//����������ҳ������
		this.setPagecount();
		return pagecount;
	}
	//������ҳ��
	public void setPagecount(){
		this.pagecount = (total % pagesize == 0) ? total / pagesize : total / pagesize + 1;
	}
	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}
	public int getStart() {
		//���������кŷ���
		this.setStart();
		return start;
	}
	//����ÿҳ����ʼ�к�
	public void setStart(){
		this.start = (this.getCurrpage()-1)*this.getPagesize();
	}
	public void setStart(int start) {
		this.start = start;
	}
}

