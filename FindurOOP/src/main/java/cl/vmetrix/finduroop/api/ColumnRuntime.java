package cl.vmetrix.finduroop.api;

public class ColumnRuntime<T> extends Column<T> {
	public void setValue(Integer value) {
		this.value = (T) value;
	}
	
	public void setValue(String value) {
		this.value = (T) value;
	}
	
	public void setValue(Double value) {
		this.value = (T) value;
	}
	
	public void setValue(int value) {
		Integer objValor = Integer.valueOf(value);
		this.value = (T) objValor;
	}
	
	public void setValue(Long value) {
		this.value = (T) value;
	}
	
	public void setValue(Float value) {
		this.value = (T) value;
	}
	
	public void setValue(Boolean value) {
		this.value = (T) value;
	}
}
