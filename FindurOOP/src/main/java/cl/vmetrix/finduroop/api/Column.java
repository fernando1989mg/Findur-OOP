package cl.vmetrix.finduroop.api;

import cl.vmetrix.finduroop.dummies.Table;



/**
 * @author Fernando Mu�oz
 *
 * @param <T>
 */
public class Column<T> {
	protected T value;
	private String name;
	private String title;
	private Class<?> type;
	private Table table;

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public T getRow(int numRow){
		if(type.getTypeName().contains("Integer")){
			return (T)Integer.valueOf(table.getInt(this.name, numRow));
		}
		if(type.getTypeName().contains("String")){
			return (T)table.getString(this.name, numRow);
		}
		if(type.getTypeName().contains("Double")){
			return (T)Double.valueOf(table.getDouble(this.name, numRow));
		}
		
		return null;
	}
	
	Table getTable(){
		return this.table;
	}
	
	void setTable(Table table){
		this.table = table;
	}
}
