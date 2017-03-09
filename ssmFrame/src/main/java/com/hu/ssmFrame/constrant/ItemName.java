package com.hu.ssmFrame.constrant;

/**
 * 事项名索引
 * @author mudking
 *
 */
public enum ItemName {
	/**
	 * 工业产品生产许可
	 */
	gycp("ZJ_IND_ITEM_INF","idzj_ind_item_inf",1)
	/**
	 * 机动车检验检测机构资格核准
	 */
	,jdc("BN_ZJ_VEHICLE_INF","idzj_vehicle_inf",2);
	//成员变量
	private String tName;//事项的主表名
	private String pkName;//事项主表的主键字段名
	private int index;//索引
	
	//构造方法
	private ItemName(String tName,String pkName,int index){
		this.tName=tName;
		this.pkName=pkName;
		this.index=index;
	}
	
	/**
	 * 根据索引查询枚举中文名
	 * @param index
	 * @return
	 */
	public String gettName(int index){
		for(ItemName item:ItemName.values()){
			if(item.index==index){
				return item.gettName();
			}
		}
		return null;
	}


	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
