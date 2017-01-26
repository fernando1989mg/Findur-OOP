package cl.vmetrix.finduroop.dummies;

import java.util.ArrayList;
import java.util.List;

import com.olf.openjvs.OException;
import com.olf.openjvs.enums.COL_TYPE_ENUM;

public class Table {
	private List<String> cols = new ArrayList<>();
	private String tableName;
	int c=0;
	
	public int addCol(String nameColumn, COL_TYPE_ENUM type, String title) throws OException{
		cols.add(nameColumn);
		return 0;
	}
	public int addCol(String nameColumn, COL_TYPE_ENUM type) throws OException{
		cols.add(nameColumn);
		return 0;
	}
	public int getInt(String nameColumn, int iRow){
		return 0;
	}
	public String getString(String nameColumn, int iRow){
		return "valor fila";
	}
	public double getDouble(String nameColumn, int iRow){
		return 0D;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public int addRow(){ return c++;}
	
	public void addInt(String nameColumn, int iRow, int valor ){
		
	}
	
	public void addString(String nameColumn, int iRow, String valor ){
		
	}
	
	public void addDouble(String nameColumn, int iRow, Double valor ){
		
	}
	
}
