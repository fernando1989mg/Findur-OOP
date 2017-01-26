package cl.vmetrix.findurreflection.api;

import cl.vmetrix.findurreflection.dummies.Table;



/**
 * @author Fernando Muñoz
 *
 * @param <T>
 */
public class Column<T> {
	private T value;
	private String name;
	private Class<?> type;
	private Table table;

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
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
