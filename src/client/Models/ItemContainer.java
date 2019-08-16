package client.Models;

import java.util.ArrayList;
import java.util.List;


public class ItemContainer implements java.io.Serializable {
	public List<Item> items_;
	public int nofItems_;
	
	public ItemContainer() {
		items_ = new ArrayList<Item>();
	}
	
	public void addItem( Item item1) {
		this.items_.add(item1);
		nofItems_++;
	}
	
	public void clearContainer() {
		this.items_.clear();
		this.nofItems_ = 0;
	}
	
	public Item getItem( int i) {
		return this.items_.get(i); 
	}
	
	
}
