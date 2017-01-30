package cl.vmetrix.finduroop.api.enums;

import java.lang.reflect.Type;

import com.olf.openjvs.enums.COL_TYPE_ENUM;

public enum DataTypeColumn {
	INTEGER(COL_TYPE_ENUM.COL_INT, Integer.class),
	STRING(COL_TYPE_ENUM.COL_STRING, String.class),
	DOUBLE(COL_TYPE_ENUM.COL_DOUBLE, Double.class);
	
	private COL_TYPE_ENUM findurType;
	private Class<?> coreType;
	
	private DataTypeColumn(COL_TYPE_ENUM findurType, Class<?> coreType){
		this.setFindurType(findurType);
		this.setCoreType(coreType);
	}

	public COL_TYPE_ENUM getFindurType() {
		return findurType;
	}

	private void setFindurType(COL_TYPE_ENUM findurType) {
		this.findurType = findurType;
	}
	
	public Class<?> getCoreType() {
		return coreType;
	}

	public void setCoreType(Class<?> coreType) {
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
