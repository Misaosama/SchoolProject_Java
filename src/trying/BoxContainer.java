package trying;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import View.Box;

public class BoxContainer implements Serializable{
	public List<Box> boxes_;
	public int nofBoxes_;
	
	public BoxContainer() {
		boxes_ = new ArrayList<Box>();
	}
	
	public void addBox( Box b1) {
		this.boxes_.add(b1);
		nofBoxes_++;
	}
	
	public void becomeThisBC( BoxContainer bc ) {
		this.boxes_.clear();
		
		for( Box box : bc.boxes_) {
			this.boxes_.add(box);
		}
		this.nofBoxes_ = bc.nofBoxes_;
		
	}
	
}
