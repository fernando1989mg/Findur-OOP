package cl.vmetrix.finduroop.run;

import com.olf.openjvs.OException;
import com.olf.openjvs.enums.COL_TYPE_ENUM;

import cl.vmetrix.finduroop.api.ObjectContext;
import cl.vmetrix.finduroop.api.exception.CreateDynamicInstanceException;

public class EjemploConOOP {
	public static void main(String[] args) throws CreateDynamicInstanceException, OException {
		
		TableNegocio table = ObjectContext.table(TableNegocio.class);
		
		table.addCol("columna_runtime", COL_TYPE_ENUM.COL_INT);
		
		table.getDealNum().setValue(3);
		table.getTransNum().setValue(4);
		table.getDetail().setValue("dsadd");
		table.column("columna_runtime").setValue(new Integer(3));
		
		table.newRow();
		
		table.getDealNum().setValue(34);
		table.getTransNum().setValue(4);
		table.getDetail().setValue("djsakdjsakjd");
		table.column("columna_runtime").setValue("cadena en columna runtime");
		
		table.newRow();
		
		for(TableNegocio rows : table.getRows()){
			System.out.println("value String = " + rows.getDetail().getValue());
		}
		
//		System.out.println("nombre de la tabla = " + table.getTableName());
//		System.out.println("nombre de columna = " + table.getDealNum().getName());
//		System.out.println("nombre de columna = " + table.getRow(0).getTransNum().getValue());
	}
}
