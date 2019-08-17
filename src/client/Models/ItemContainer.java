package client.Models;

import java.util.ArrayList;
import java.util.List;


/**
 *This class groups together all moving objects(item objects) from the multi-player mode,
 * and gets sent to clients from the server.
 * @author rongyi
 * @version 1.0
 * 
 *
 */
public class ItemContainer implements java.io.Serializable {
	public List<Item> items_;
	public int nofItems_;
	
	/**
	 * default constructor
	 */
	public ItemContainer() {
		items_ = new ArrayList<Item>();
	}
	
	/**
	 * 
	 * @param item1, the item to be added to the itemContainer.
	 */
	public void addItem( Item item1) {
		this.items_.add(item1);
		nofItems_++;
	}
	
	/**
	 * remove all items in this current container, 
	 * gets called in every iteration in ServerStarter.
	 */
	public void clearContainer() {
		this.items_.clear();
		this.nofItems_ = 0;
	}
	
	/**
	 * 
	 * @param i the index of item in this container.
	 * @return the ith item in this container
	 */
	public Item getItem( int i) {
		return this.items_.get(i); 
	}
	
	
}
