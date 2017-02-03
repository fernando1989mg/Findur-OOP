package cl.vmetrix.finduroop.api.enums;

import java.lang.reflect.Type;

import cl.vmetrix.finduroop.api.ColumnRuntime;

import com.olf.openjvs.enums.COL_TYPE_ENUM;

public enum DataTypeColumn {
	INTEGER(COL_TYPE_ENUM.COL_INT, new ColumnRuntime<Integer>()),
	STRING(COL_TYPE_ENUM.COL_STRING, new ColumnRuntime<String>()),
	DOUBLE(COL_TYPE_ENUM.COL_DOUBLE, new ColumnRuntime<Double>());
	
	private COL_TYPE_ENUM findurType;
	private ColumnRuntime<?> coreType;
	
	private <T> DataTypeColumn(COL_TYPE_ENUM findurType, ColumnRuntime<?> coreType){
		this.setFindurType(findurType);
		this.setCoreType(coreType);
	}

	public COL_TYPE_ENUM getFindurType() {
		return findurType;
	}

	private void setFindurType(COL_TYPE_ENUM findurType) {
		this.findurType = findurType;
	}
	
	public ColumnRuntime<?> getCoreType() {
		return coreType;
	}

	public void setCoreType(ColumnRuntime<?> coreType) {
		this.coreType = coreType;
	}

	public static <T> DataTypeColumn getType(Class<T> type){
		return DataTypeColumn.getType(type.getTypeName());
	}
	
	public static DataTypeColumn getType(Type type){
		return DataTypeColumn.getType(type.toString());
	}
	
	public static DataTypeColumn getType(String type){
		for(DataTypeColumn typeColumn : DataTypeColumn.values()){
			if(type.toLowerCase().contains(typeColumn.name().toLowerCase())){
				return typeColumn;
			}
		}
		
		return null;
	}
	
	public static DataTypeColumn getTypeByFindurType(COL_TYPE_ENUM findurType){
		for(DataTypeColumn typeColumn : DataTypeColumn.values()){
			if(findurType == typeColumn.getFindurType()){
				return typeColumn;
			}
		}
		
		return null;
	}
}
