package cl.vmetrix.finduroop.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.vmetrix.finduroop.api.annotation.VColumn;
import cl.vmetrix.finduroop.api.enums.DataTypeColumn;
import cl.vmetrix.finduroop.api.exception.CreateDynamicInstanceException;
import cl.vmetrix.finduroop.api.exception.RowGenerateException;
import cl.vmetrix.finduroop.dummies.Table;

import com.olf.openjvs.OException;
import com.olf.openjvs.enums.COL_TYPE_ENUM;

public abstract class Entity<T extends Entity<?>> extends Table{
	
	private List<Entity<?>> rows;
	private HashMap<String,Column<?>> columnsRuntime;
	
	protected T t;
	
	public Entity() throws OException {
		super();
		
		rows = new ArrayList<>();
		columnsRuntime = new HashMap<>();
	}
	
	@SuppressWarnings("unchecked")
	public Column<T> column(String columnName){
		return (Column<T>) columnsRuntime.get(columnName);
	}
	
	private void addColumnAtRuntime(String columnName, T typeColumn){
		Column<T> column = new Column<>();
		column.setName(columnName);
		column.setTable(t);
		
		columnsRuntime.put(columnName, column);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Integer newRow()
	{
		Integer iRow = this.t.addRow();
		
		for(Field f : this.t.getClass().getDeclaredFields()){
			String name = f.getName();
			
			f.setAccessible(true);
			
			if(f.isAnnotationPresent(VColumn.class)){
				Annotation annotation = f.getAnnotation(VColumn.class);
				VColumn vColumn = (VColumn) annotation;
				
				name = vColumn.name().equals("") ? f.getName() : vColumn.name();
			}
			
			Column column;
			
			try {
				column = (Column)f.get(this.t);
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				//TODO mensaje de error 
				return null;
			} 
			
			if(f.getGenericType().toString().contains("Integer")){
				this.t.addInt(name, iRow, (int)column.getValue());
				
			}else if(f.getGenericType().toString().contains("String")){
				this.t.addString(name, iRow, (String)column.getValue());
				
			}else if(f.getGenericType().toString().contains("Double")){
				this.t.addDouble(name, iRow, (Double)column.getValue());
				
			}
		}
		
		try {
			this.rows.add(cloneTableObject(this.t));
		} catch (CreateDynamicInstanceException e) {
			//TODO agregar mensaje de error
			throw new RowGenerateException(e);
		}
		
		return iRow;
	}
	
	@SuppressWarnings("unchecked")
	public T getRow(int num){
		return (T) rows.get(num);
	}

	@SuppressWarnings("unchecked")
	public List<T> getRows() {
		return (List<T>) rows;
	}

	public void setRows(List<Entity<?>> rows) {
		this.rows = rows;
	}
	
	
	//Este método es para crear una nueva instancia del objeto pasado como parámetro
	//y agregarlo a la lista, así el programador no debe crear nuevas instancias
	//para crear filas
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private T cloneTableObject(T row) throws CreateDynamicInstanceException{
		Constructor<?> ctor;
		T t = null;
		
		try {
			ctor = row.getClass().getConstructor();
			
			t = (T) ctor.newInstance();
			
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			throw new CreateDynamicInstanceException(e1);
		}
		
		for(Field f : row.getClass().getDeclaredFields()){
			try {
				f.setAccessible(true);
				
				Column column = (Column)f.get(row);
				Column columnCopy = new Column();
				
				columnCopy.setValue(column.getValue());
				columnCopy.setTable(column.getTable());
				
				Field fieldCopy = t.getClass().getDeclaredField(f.getName());
				fieldCopy.setAccessible(true);
				fieldCopy.set(t, columnCopy);
			
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return t;
	}
	
	
	//comienza la sobreescritura de métodos para hacerlos trabajar orientado a objetos
	
	@Override
	public int addCol(String col_name, COL_TYPE_ENUM col_type)
	           throws OException{
		return super.addCol(col_name, col_type);
	}

	@Override
	public int addCol(String col_name,COL_TYPE_ENUM col_type,
	         String title)
	           throws OException{
		return super.addCol(col_name, col_type, title);
	}

}
