package cl.vmetrix.finduroop.run;

import com.olf.openjvs.OException;

import cl.vmetrix.finduroop.api.ObjectContext;
import cl.vmetrix.finduroop.exception.CreateDynamicInstanceException;

public class EjemploConOOP {
	public static void main(String[] args) throws CreateDynamicInstanceException, OException {
		
		TableNegocio table = ObjectContext.table(TableNegocio.class);
		
		table.getDealNum().setValue(3);
		table.getTransNum().setValue(4);
		table.getDetail().setValue("dsadd");
		
		table.newRow();
		
		table.getDealNum().setValue(34);
		table.getTransNum().setValue(4);
		table.getDetail().setValue("djsakdjsakjd");
		
		table.newRow();
		
		for(TableNegocio rows : table.getRows()){
			System.out.println("value String = " + rows.getDetail().getValue());
		}
		
//		System.out.println("nombre de la tabla = " + table.getTableName());
//		System.out.println("nombre de columna = " + table.getDealNum().getName());
//		System.out.println("nombre de columna = " + table.getRow(0).getTransNum().getValue());
	}
}
