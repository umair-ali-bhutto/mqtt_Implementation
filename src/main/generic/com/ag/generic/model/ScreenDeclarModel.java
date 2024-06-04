package com.ag.generic.model;


public class ScreenDeclarModel {

	private String key;
	private String value;
	
	// Constructors
	public ScreenDeclarModel(){
		
	}
	
	public ScreenDeclarModel(String key,String value){
		this.key= key;
		this.value= value;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	   // Helpers ------------------------------------------------------------------------------------

    // This must return true for another Foo object with same key/id.
    public boolean equals(Object other) {
        return other instanceof ScreenDeclarModel && (key != null) ? key.equals(((ScreenDeclarModel) other).key) : (other == this);
    }

    // This must return the same hashcode for every Foo object with the same key.
    public int hashCode() {
        return key != null ? this.getClass().hashCode() + key.hashCode() : super.hashCode();
    }

    // Override Object#toString() so that it returns a human readable String representation.
    // It is not required by the Converter or so, it just pleases the reading in the logs.
    public String toString() {
        return (key);
    }
}


