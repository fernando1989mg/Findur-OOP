package cl.vmetrix.finduroop.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import cl.vmetrix.finduroop.api.annotation.VColumn;
import cl.vmetrix.finduroop.api.annotation.VQuery;
import cl.vmetrix.finduroop.api.annotation.VTable;
import cl.vmetrix.finduroop.api.exception.ColumnGenerateException;
import cl.vmetrix.finduroop.api.exception.CreateDynamicInstanceException;
import cl.vmetrix.finduroop.dummies.DBaseTable;
import cl.vmetrix.finduroop.dummies.Table;


//import com.olf.openjvs.DBaseTable;
import com.olf.openjvs.OException;
//import com.olf.openjvs.Table;
import com.olf.openjvs.enums.COL_TYPE_ENUM;



/**
 * @author Fernando Muñoz
 *
 */

public class ObjectContext {
	
	@SuppressWarnings("unchecked")
	public static <T extends Table> T table(Class<? extends Table> table) throws CreateDynamicInstanceException, OException{
		
		T t = null;
		
		try {
			Constructor<?> ctor = table.getConstructor();
			t = (T) ctor.newInstance();
			
			try{
				Field tableReference = t.getClass().getSuperclass().getDeclaredField("t");
				tableReference.set(t, t);
			}catch(NoSuchFieldException e){
				//TODO por ahora este código solo se ejecutará si la tabla no implementa OOP, pero esto debe cambiar
				//se valida si la tabla debe ser cargada mediante una sql query
				if(table.isAnnotationPresent(VQuery.class)){
					Annotation annotation = table.getClass().getAnnotation(VQuery.class);
					VQuery vQuery = (VQuery) annotation;
					
					DBaseTable.execISql((T)t, vQuery.value());
				}
			}
			

		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new CreateDynamicInstanceException(e);
		}
		
		
		for(Field f : table.getDeclaredFields()){
			String name = f.getName();
			String title = "";
			
			f.setAccessible(true);
			
			if(f.isAnnotationPresent(VColumn.class)){
				Annotation annotation = f.getAnnotation(VColumn.class);
				VColumn vColumn = (VColumn) annotation;
				
				name = vColumn.name().equals("") ? f.getName() : vColumn.name();
				title = vColumn.title();
			}
			
			if(f.getType().equals(Column.class))
			{
				Column<Integer> column = null;
				
				try {
					if(f.getGenericType().toString().contains("Integer")){
						t.addCol(name, COL_TYPE_ENUM.COL_INT, title);
						
						column = ObjectContext.generateObjectColumn(Integer.class, name, t);
					}else if(f.getGenericType().toString().contains("String")){
						t.addCol(name, COL_TYPE_ENUM.COL_STRING, title);
						
						column = ObjectContext.generateObjectColumn(String.class, name, t);
					}else if(f.getGenericType().toString().contains("Double")){
						t.addCol(name, COL_TYPE_ENUM.COL_DOUBLE, title);
						
						column = ObjectContext.generateObjectColumn(Double.class, name, t);
					}
					
					f.set((Object)t, column);
				}catch (IllegalArgumentException | IllegalAccessException | ColumnGenerateException e) {
					// TODO generar mensaje de error
					throw new ColumnGenerateException(e);
				}
			}
		}
		
		if(table.isAnnotationPresent(VTable.class)){
			Annotation annotation = table.getAnnotation(VTable.class);
			VTable vTable = (VTable) annotation;
			
			t.setTableName(vTable.name());
		}
		
		return t;
	}
	
	public static <T extends Table> T table(Class<? extends Table> table, Object... params){
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private static <T> Column generateObjectColumn(T t,String name, Table table){
		try {
			Column<T> column = new Column<>();
			column.setName(name);
			
			Field fieldTable = column.getClass().getDeclaredField("table");
			fieldTable.setAccessible(true);
			fieldTable.set(column, table);
			
			Field fieldType = column.getClass().getDeclaredField("type");
			fieldType.setAccessible(true);
			fieldType.set(column, t);
			
			return column;
		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException e) {
			throw new ColumnGenerateException(e);
		}
	}
}
